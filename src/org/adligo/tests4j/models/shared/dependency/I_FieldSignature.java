package org.adligo.tests4j.models.shared.dependency;

import org.adligo.tests4j.models.shared.common.I_Immutable;

/**
 * a way to represent java
 * class non private fields in a way 
 * that can be passed between class loaders.
 *  implementations should not be mutable,
 *  and implement equals and hash code on the 
 *  name and className.
 * @author scott
 *
 */
public interface I_FieldSignature extends I_Immutable, Comparable<I_FieldSignature> {
	/**
	 * the class name of the field,
	 * for your purposes (usually a regular class name
	 * from Class.getName(), but could be a ASM type name)
	 * @return
	 */
	public String getClassName();
	
	/**
	 * the name of the field i.e. "FALSE" from 
	 * http://docs.oracle.com/javase/7/docs/api/java/lang/Boolean.html
	 * @return
	 */
	public String getName();
}
