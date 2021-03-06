package org.adligo.tests4j.models.shared.association;

import java.util.Set;

import org.adligo.tests4j.shared.asserts.reference.I_ClassAliasLocal;

/**
 * This interface contains information
 * about which classes/interfaces/enum/annotation
 * a particular class/interface/enum/annotation
 * depends on.
 * 
 * A circular a dependency loop ie;
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
public interface I_ClassAssociationsLocal extends I_ClassAssociations, I_ClassParentsLocal {

	/**
	 * the names of the circular dependencies
	 * @return
	 */
	public Set<I_ClassAliasLocal> getCircularDependenciesLocal();
	
	/**
	 * the class names of the classes
	 * which are dependend on.
	 * @return
	 */
	public abstract Set<I_ClassParentsLocal> getDependenciesLocal();
}
