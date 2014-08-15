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
public interface I_ClassReferencesLocal extends I_ClassReferences, I_ClassParentsLocal {

	/**
	 * the names of the circular dependencies
	 * @return
	 */
	public Set<I_ClassAliasLocal> getCircularReferencesLocal();
	
	/**
	 * the class names of the referenced classes.
	 * @return
	 */
	public abstract Set<I_ClassParentsLocal> getReferencesLocal();
}
