package org.adligo.tests4j.run.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * this interface adds some custom methods
 * to the class loader, which cache the class 
 * file byte[] in memory, for later usage 
 * (ie bytecode instrumentation with ASM).
 * @author scott
 *
 */
public interface I_CachedClassBytesClassLoader {
	
	/**
	 * the number of classes cached, 
	 * mostly for test verification.
	 * @return
	 */
	public int getCacheSize();
	/**
	 * Add a in-memory representation of a class file bytes.
	 * Implementations should load the class
	 * after they store the byte[].
	 * @param name
	 *            name of the class
	 * @param bytes
	 *            class definition
	 */
	public Class<?> addCache(final String name, final byte[] bytes) throws ClassNotFoundException;
	
	/**
	 * This loads the byte [] of the class file from the input stream.
	 * It also should make sure the input stream passed in gets closed.
	 * 
	 * Implementations should close the input stream when
	 * their done with it.
	 * 
	 * @param in the input stream to read from
	 * @param name
	 * @return
	 * @throws ClassNotFoundException
	 */
	public Class<?> addCache(InputStream in, final String name) 
			throws ClassNotFoundException, IOException;
	
	/**
	 * if the byte [] of the class file has 
	 * been cached return true.
	 * @param name
	 * @return
	 */
	public boolean hasCache(final String name);
	
	/**
	 * 
	 * This should only be called after hasCache has been
	 * called to make sure the Class has been loaded into this 
	 * class loader, otherwise it may return null.
	 * 
	 * @param name
	 * @return the cached class instance.
	 */
	public Class<?> getCachedClass(final String name);
	
	/**
	 * return a list of the class names in the package
	 * which have been loaded in the class loader
	 * which implements this interface.
	 * @param pkgName
	 * @return
	 */
	public  List<String> getCachedClassesInPackage(String pkgName);
	/**
	 * return a list of the all of the class names 
	 * which have been loaded in the class loader
	 * which implements this interface.
	 * @param pkgName
	 * @return
	 */
	public  List<String> getAllCachedClasses();
	/**
	 * @return a InputStream for the 
	 * cached class byte[] in Memory.  Throws a illegal argument exception
	 * if the class has not been loaded by this class loader.  
	 */
	public InputStream getCachedBytesStream(String name);
}
