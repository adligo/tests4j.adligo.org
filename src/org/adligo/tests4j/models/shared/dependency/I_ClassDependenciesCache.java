package org.adligo.tests4j.models.shared.dependency;


/**
 * Note the I_ClassDependencies in the
 * cache should always contain zero references 
 * to the class they represent.
 * @author scott
 *
 */
public interface I_ClassDependenciesCache {
	public void putDependenciesIfAbsent(I_ClassDependenciesLocal p);
	public I_ClassDependenciesLocal getDependencies(String name);
}
