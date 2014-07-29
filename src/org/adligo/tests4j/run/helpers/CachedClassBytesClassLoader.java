package org.adligo.tests4j.run.helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;


/**
 * a threadsafe memory container for classes,
 * it just extends ClassLoader so that they are loaded from disk only once.
 * @author scott
 *
 */
public class CachedClassBytesClassLoader extends ClassLoader implements I_CachedClassBytesClassLoader {

	//ok i must make sure
	// http://docs.oracle.com/javase/7/docs/technotes/guides/lang/cl-mt.html
	static {
		registerAsParallelCapable();
	}
	private final ConcurrentHashMap<String, byte[]> bytesCache = new ConcurrentHashMap<String, byte[]>();
	private final ConcurrentHashMap<String, Class<?>> classes = new ConcurrentHashMap<String, Class<?>>();
	private final I_Tests4J_Log log;
	private final Set<String> packagesWithoutWarning;
	private final Set<String> classesWithoutWarning;
	
	public CachedClassBytesClassLoader(I_Tests4J_Log pLog, Set<String> pPackagesWithoutWarning, 
			Set<String> pClassesWithoutWarning) { 
		
		log = pLog;
		if (pPackagesWithoutWarning == null) {
			packagesWithoutWarning = Collections.emptySet();
		} else {
			packagesWithoutWarning = Collections.unmodifiableSet(pPackagesWithoutWarning);
		}
		
		if (pClassesWithoutWarning == null) {
			classesWithoutWarning = Collections.emptySet();
		} else {
			classesWithoutWarning = Collections.unmodifiableSet(pClassesWithoutWarning);
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
	 * @see I_CachedClassBytesClassLoader#addCache(String, byte[])
	 */
	public Class<?> addCache(final String name, final byte[] bytes) throws ClassNotFoundException {
		bytesCache.putIfAbsent(name, bytes);
		return loadClass(name, true);
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
	@Override
	protected Class<?> loadClass(final String name, final boolean resolve)
			throws ClassNotFoundException {
		//quick return it is already loaded
		Class<?> cached = classes.get(name);
		if (cached != null) {
			return cached;
		}
		
		if (bytesCache.containsKey(name)) {
			
			
			return defineClassFromBytes(name);
		}
		if (log.isLogEnabled(CachedClassBytesClassLoader.class)) {
			Boolean logWarning = null;
			for (String pkg: packagesWithoutWarning) {
				if (name.indexOf(pkg) == 0) {
					logWarning = false;
					break;
				}
			}
			if (logWarning == null) {
				if (classesWithoutWarning.contains(name)) {
					logWarning = false;
				} else {
					logWarning = true;
				}
			}
			if (logWarning) {
				log.log(super.toString() + 
						" using parent class loader for the following class;" +
						log.getLineSeperator() + name);
			}
		}
		return super.loadClass(name, resolve);
	}


	protected Class<?> defineClassFromBytes(final String name)
			throws ClassFormatError {
		byte[] bytes = bytesCache.get(name);
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
			toRet =  defineClass(name, bytes, 0, bytes.length);
			classes.put(name, toRet);
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
		return new ByteArrayInputStream(bytes);
	}

	public I_Tests4J_Log getLog() {
		return log;
	}

	public Set<String> getPackagesWithoutWarning() {
		return packagesWithoutWarning;
	}

	public Set<String> getClassesWithoutWarning() {
		return classesWithoutWarning;
	}

	/**
	 * manipulate the class for testing of double 
	 * @param className
	 * @param bytes
	 */
	protected void putBytes(String className, byte [] bytes) {
		bytesCache.put(className, bytes);
	}
 }

