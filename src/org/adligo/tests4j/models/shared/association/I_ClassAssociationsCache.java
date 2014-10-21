package org.adligo.tests4j.models.shared.association;


/**
 * Note the I_ClassDependencies in the
 * cache should always contain zero references 
 * to the class they represent.
 * @author scott
 *
 */
public interface I_ClassAssociationsCache {
	public void putDependenciesIfAbsent(I_ClassAssociationsLocal p);
	public I_ClassAssociationsLocal getDependencies(String name);
}
