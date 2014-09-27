package org.adligo.tests4j.models.shared.dependency;

import java.util.List;
import java.util.Set;

import org.adligo.tests4j.shared.asserts.dependency.I_ClassAlias;
import org.adligo.tests4j.shared.asserts.dependency.I_ClassAttributes;

/**
 * A circular a reference loop ie;
 * ClassA.init()  calls -> ClassB.foo();
 * ClassB.init()  calls -> ClassA.bar();
 * 
 * These are usually due to fairly bad design, which 
 * is why tests4j should default to failing SourceFileTrials 
 * that test classes with circular dependencies.  
 * The @SourceFileTrial annotation should allow a override
 * for classes that really need a circular dependency for some strange reason,
 * like a inner class referencing its outer class.
 * 
 * @author scott
 *
 */
public interface I_ClassDependencies extends I_ClassParents {

	/**
	 * if this class has circular dependencies
	 * @return
	 */
	public boolean hasCircularDependencies();
	
	/**
	 * the circular dependencies
	 * @return
	 */
	public Set<I_ClassAlias> getCircularDependencies();
	
	/**
	 * the names of the circular dependencies
	 * @return
	 */
	public Set<String> getCircularDependenciesNames();
	
	/**
	 * if there are dependencies
	 * @return
	 */
	public boolean hasDependencies();
	
	/**
	 * the class names of the classes which are 
	 * depended on.
	 * @return
	 */
	public abstract Set<I_ClassAlias> getDependencies();
	
	/**
	 * this turns the getReferences set into Strings,
	 * mostly for ease of testing.
	 * @return
	 */
	public abstract Set<String> getDependencyNames();
	
	/**
	 * if this class (not parents)
	 * called methods or referenced fields on other classes in it's byte code.
	 * @return
	 */
	public boolean hasReferences();
	/**
	 * this list contains all of the
	 * class/methods classes/fields referenced called by 
	 * the class (not parents) this instance represents byte code.
	 * @return
	 */
	public List<I_ClassAttributes> getReferences();
}
