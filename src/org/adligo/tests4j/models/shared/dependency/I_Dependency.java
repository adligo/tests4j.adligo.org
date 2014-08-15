package org.adligo.tests4j.models.shared.dependency;

/**
 * a count of references for a
 * class which is depended on.
 * 
 * @author scott
 *
 */
public interface I_Dependency extends Comparable<I_Dependency> {

	/**
	 * the name of the class which is depended on
	 * @return
	 */
	public abstract I_ClassAlias getAlias();

	/**
	 * the number of references to the class
	 * either directly 
	 * A -> B
	 * or indirectly
	 * A -> F -> B
	 *  
	 * @return
	 */
	public abstract int getReferences();

}