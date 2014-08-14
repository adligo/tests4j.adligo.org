package org.adligo.tests4j.models.shared.dependency;


/**
 * Note the I_ClassDependencies in the
 * cache should always contain zero references 
 * to the class they represent.
 * @author scott
 *
 */
public interface I_ClassReferencesCache {
	public void putReferencesIfAbsent(I_ClassReferences p);
	public I_ClassReferences getReferences(String name);
}
