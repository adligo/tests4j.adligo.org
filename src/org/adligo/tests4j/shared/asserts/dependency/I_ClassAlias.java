package org.adligo.tests4j.shared.asserts.dependency;



/**
 * A wrapper around a class
 * which implements Comparable .
 * 
 * Note the comparable implementation
 * should handle I_ClassAlias, String (class Name), Class (class Name)
 * and Object.getClass (className)
 * 
 * Also note this 
 *  the sub interface I_ClassAliasLocal will not
 * this is so that string only representations
 * of the class can be passed between jvms,
 * while Class instances can be passed around in the
 * jvm.
 * 
 * @author scott
 *
 */
public interface I_ClassAlias extends Comparable<Object> {

	/**
	 * the name of the class this represents
	 * 
	 * @return
	 */
	public String getName();
}
