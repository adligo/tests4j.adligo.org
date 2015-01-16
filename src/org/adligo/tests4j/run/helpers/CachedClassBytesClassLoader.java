package org.adligo.tests4j.run.helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.adligo.tests4j.shared.common.I_CacheControl;
import org.adligo.tests4j.shared.common.JavaAPIVersion;
import org.adligo.tests4j.shared.common.LegacyAPI_Issues;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;


/**
 * a threadsafe memory container for classes,
 * it just extends ClassLoader so that they are loaded from disk only once.
 * @author scott
 *
 */
public class CachedClassBytesClassLoader extends ClassLoader implements I_CachedClassBytesClassLoader {
	public static final LegacyAPI_Issues ISSUES = new LegacyAPI_Issues();
	
	//ok i must make sure
	// http://docs.oracle.com/javase/7/docs/technotes/guides/lang/cl-mt.html
	static {
		try {
			//keep reflective until jdk 1.7 is the last minor verion
			//to version forward api call the method from jdk 1.6 apis
			Method rapc = ClassLoader.class.getMethod("registerAsParallelCapable", new Class[] {});
			rapc.invoke(ClassLoader.class, "registerAsParallelCapable");
		} catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException x) {
			ISSUES.addIssues(new JavaAPIVersion("1.6.0"),new IllegalStateException("Note the jdk 1.6 "
					+ "parallel class loader may not work properly.", x));
		}
	}
	private final ConcurrentHashMap<String, byte[]> bytesCache = new ConcurrentHashMap<String, byte[]>();
	private final ConcurrentHashMap<String, Class<?>> classes = new ConcurrentHashMap<String, Class<?>>();
	private final I_Tests4J_Log log;
	private final Set<String> packagesNotRequired;
	private final Set<String> classesNotRequired;
	
	public CachedClassBytesClassLoader(I_Tests4J_Log pLog) { 
		this(pLog,null, null, null);
	}
	
	public CachedClassBytesClassLoader(I_Tests4J_Log pLog, Set<String> pPackagesWithoutWarning, 
			Set<String> pClassesWithoutWarning, I_CacheControl cacheControl) { 
		
		if (cacheControl != null) {
			cacheControl.addClearRunnable(new Runnable() {
				
				@Override
				public void run() {
					bytesCache.clear();
					classes.clear();
				}
			});
		}
		
		log = pLog;
		if (pPackagesWithoutWarning == null) {
			packagesNotRequired = Collections.singleton("java.");
		} else {
			packagesNotRequired = Collections.unmodifiableSet(pPackagesWithoutWarning);
		}
		
		if (pClassesWithoutWarning == null) {
			classesNotRequired = Collections.emptySet();
		} else {
			classesNotRequired = Collections.unmodifiableSet(pClassesWithoutWarning);
		}
	}
	

	/**
	 * @see I_CachedClassBytesClassLoader#addCache(InputStream, String)
	 */
	@Override
	public Class<?> addCache(InputStream in, String name)
			throws ClassNotFoundException, IOException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte [] bytes = new byte[1];
			while(in.read(bytes) != -1) {
				baos.write(bytes);
			}
			bytes = baos.toByteArray();
			return addCache(name, bytes);
		} catch (IOException x) {
			throw x;
		} finally {
			in.close();
		}
	}
	
	/**
	 * @see I_CachedClassBytesClassLoader#addCache(InputStream, String)
	 */
	@Override
	public void addCache(InputStream in, Class<?> clazz)
			throws IOException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte [] bytes = new byte[1];
			while(in.read(bytes) != -1) {
				baos.write(bytes);
			}
			bytes = baos.toByteArray();
			addCache(clazz, bytes);
		} catch (IOException x) {
			throw x;
		} finally {
			in.close();
		}
	}
	/**
	 * @see I_CachedClassBytesClassLoader#addCache(String, byte[])
	 */
	public Class<?> addCache(final String name, final byte[] bytes) throws ClassNotFoundException {
		Class<?> toRet = defineClassFromBytes(name, bytes);
		bytesCache.putIfAbsent(name, bytes);
		return toRet;
	}

	/**
	 * @see I_CachedClassBytesClassLoader#addCache(String, byte[], Class<?> clazz)
	 */
	public void addCache(final Class<?> clazz, final byte[] bytes) {
		classes.put(clazz.getName(), clazz);
		bytesCache.putIfAbsent(clazz.getName(), bytes);
	}
	/**
	 * @see I_CachedClassBytesClassLoader#hasCache(String)
	 */
	public boolean hasCache(final String name) {
		return bytesCache.containsKey(name);
	}
	
	/**
	 * @see I_CachedClassBytesClassLoader#getCachedClass(String)
	 */
	public Class<?> getCachedClass(final String name) {
		return classes.get(name);
	}
	
	/**
	 * This makes the regular ClassLoader protected method
	 * findLoadedClass(String)
	 * public, so it is easier to test if 
	 * got loaded.
	 */
	public Class<?> getLoadedClass(final String name) {
		return super.findLoadedClass(name);
	}
	
	/**
	 * Override the java.lang.ClassLoader method,
	 * keep the byte [] of the class file
	 * so we can use it later on.
	 * 
	 */
	@SuppressWarnings("boxing")
  @Override
	protected Class<?> loadClass(final String name, final boolean resolve)
			throws ClassNotFoundException {
		//quick return it is already loaded
		Class<?> cached = classes.get(name);
		if (cached != null) {
			return cached;
		}
		
		Boolean logWarning = null;
		for (String pkg: packagesNotRequired) {
			if (name.indexOf(pkg) == 0) {
				logWarning = false;
				break;
			}
		}
		if (logWarning == null) {
			if (classesNotRequired.contains(name)) {
				logWarning = false;
			} else {
				logWarning = true;
			}
		}
		if (logWarning) {
			String message = super.toString() + 
					" the following class should to be cached at this point," + log.getLineSeperator() +
					" using the parent classloader (which can mess up code coverage assertions);" +
					log.getLineSeperator() + name; 
			//not really a exception, but this is generally something that needs to be
			//fixed when it happens, and this gets your attention in the log
			IllegalStateException x = new IllegalStateException(message);
			x.fillInStackTrace();
			throw x;
			//log.onThrowable(x);
		}
		return super.loadClass(name, resolve);
	}


	protected Class<?> defineClassFromBytes(final String name, byte[] bytes)
			throws ClassFormatError {
		//ok i must make sure
		// http://docs.oracle.com/javase/7/docs/technotes/guides/lang/cl-mt.html
		// If your custom class loader overrides either the protected loadClass(String, boolean) 
		// method or the public loadClass(String) method, you must also ensure that the protected 
		// defineClass() method is called only once for each class loader and class name pair.
		synchronized (classes) {
			//call this a 'map re-check' pattern 
			//to distinguish vs double checked locking?
			Class<?> toRet = classes.get(name);
			if (toRet != null) {
				return toRet;
			}
			toRet = super.findLoadedClass(name);
			if (toRet == null) {
				if (log.isLogEnabled(CachedClassBytesClassLoader.class)) {
					log.log("CachedClassBytesClassLoader.defineClassFromBytes " + name);
				}
				toRet =  defineClass(name, bytes, 0, bytes.length);
				classes.put(name, toRet);
			}
			return classes.get(name);
		}
	}

	/**
	 * @see I_CachedClassBytesClassLoader#getCachedClassesInPackage(String)
	 */
	@Override
	public List<String> getCachedClassesInPackage(String pkgName) {
		Enumeration<String> keys =  bytesCache.keys();
		List<String> toRet = new ArrayList<String>();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (key.contains(pkgName)) {
				toRet.add(key);
			}
		}
		return toRet;
	}

	/**
	 * @see I_CachedClassBytesClassLoader#getAllCachedClasses()
	 */
	@Override
	public List<String> getAllCachedClasses() {
		Enumeration<String> keys =  bytesCache.keys();
		List<String> toRet = new ArrayList<String>();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			toRet.add(key);
		}
		return toRet;
	}

	/**
	 * @see I_CachedClassBytesClassLoader#getCachedBytesStream(String)
	 */
	@Override
	public InputStream getCachedBytesStream(String name) {
		byte[] bytes = bytesCache.get(name);
		if (bytes == null) {
			bytes = new byte[] {};
		}
		return new ByteArrayInputStream(bytes);
	}

	public I_Tests4J_Log getLog() {
		return log;
	}

	public Set<String> getPackagesNotRequired() {
		return packagesNotRequired;
	}

	public Set<String> getClassesNotRequired() {
		return classesNotRequired;
	}

	/**
	 * manipulate the class for testing of double 
	 * @param className
	 * @param bytes
	 */
	protected void putBytes(String className, byte [] bytes) {
		defineClassFromBytes(className, bytes);
		bytesCache.put(className, bytes);
	}

	@Override
	public int getCacheSize() {
		return bytesCache.size();
	}
 }

