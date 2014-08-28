package org.adligo.tests4j.models.shared.dependency;

import java.util.Set;

/**
 * A interface for representing 
 * @author scott
 *
 */
public interface I_ClassAttributes {
	/**
	 * The class names represented as a string for 
	 * your purposes (i.e. usually 
	 * "org.adligo.tests4j.models.shared.dependency.I_ClassMethods"
	 * but if you needed some ASM types, could be something like
	 * "Lorg/adligo/tests4j/models/shared/dependency/I_ClassMethods;"
	 * )
	 * @return
	 */
	public String getName();
	
	/**
	 * the fields associated with this class attributes
	 * @return
	 */
	public Set<I_FieldSignature> getFields();
	/**
	 * the methods associated with this class attributes
	 * @return
	 */
	public Set<I_MethodSignature> getMethods();
}
