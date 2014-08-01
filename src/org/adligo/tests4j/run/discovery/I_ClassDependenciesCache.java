package org.adligo.tests4j.run.discovery;

/**
 * Note the I_ClassDependencies in the
 * cache should always contain zero references 
 * to the class they represent.
 * @author scott
 *
 */
public interface I_ClassDependenciesCache {
	public void putIfAbsent(I_ClassDependencies p);
	public I_ClassDependencies get(String name);
}
