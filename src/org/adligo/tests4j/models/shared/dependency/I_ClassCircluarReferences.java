package org.adligo.tests4j.models.shared.dependency;

import java.util.Set;

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
public interface I_ClassCircluarReferences {

	/**
	 * the name of the class which references other classes
	 * @return
	 */
	public abstract String getClassName();
	/**
	 * if this class has circular dependencies
	 * @return
	 */
	public boolean hasCircularReferences();
	
	/**
	 * the names of the circular dependencies
	 * @return
	 */
	public Set<String> getCircularReferences();
}
