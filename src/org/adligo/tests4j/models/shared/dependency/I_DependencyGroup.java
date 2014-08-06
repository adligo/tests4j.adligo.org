package org.adligo.tests4j.models.shared.dependency;

/**
 * A marker interface which allows the users of test4j
 * to define a dependency group, which is a group 
 * of classes/packages which a sourceFile may depend on.
 * 
 * This is similar to the import statements of a .java
 * file, however the import statements are not used
 * since they are not included in the .class byte code.
 * The information about dependencies comes from
 * the reflection and reading of the virtual assembler
 * opcode instructions.
 * 
 * @author scott
 *
 */
public interface I_DependencyGroup {

	/**
	 * return true if the class is in this dependency
	 * @param className
	 * @return
	 */
	public boolean isInGroup(String className);
}
