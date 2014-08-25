package org.adligo.tests4j.models.shared.dependency;

import java.util.Collection;
import java.util.Set;

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
	 * the class names in this dependency group
	 * @return
	 */
	public Set<String> getClassNames();
	
	/**
	 * return all of the class methods instances
	 * in this dependency group
	 * @return
	 */
	public Collection<I_ClassAttributes> getClassMethods();
	
	/**
	 * returns the methods for a particular class,
	 * which could be different depending on the platform 
	 * (i.e. compare http://docs.oracle.com/javase/7/docs/api/java/lang/System.html
	 *    with http://www.gwtproject.org/doc/latest/RefJreEmulation.html)
	 * @param className
	 * @return
	 */
	public Set<I_MethodSignature> getMethods(String className);
	/**
	 * return true if the class is in this dependency
	 * @param className
	 * @return
	 */
	public boolean isInGroup(String className, I_MethodSignature method);
}
