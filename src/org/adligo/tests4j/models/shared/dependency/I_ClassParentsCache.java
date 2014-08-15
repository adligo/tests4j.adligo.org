package org.adligo.tests4j.models.shared.dependency;

public interface I_ClassParentsCache {
	public void putParentsIfAbsent(I_ClassParentsLocal p);
	public I_ClassParentsLocal getParents(String name);
}
