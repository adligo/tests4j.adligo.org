package org.adligo.tests4j.shared.asserts.dependency;

import org.adligo.tests4j.shared.common.I_Immutable;


/**
 * a method signature that can get passed between jvms,
 * and passed between classloaders in the same jvm.
 * Implementations of this interface are expected to be
 * immutable and implement hash code for the MethodName and 
 * the MethodParameterClassNames.
 * @author scott
 *
 */
public interface I_MethodSignature extends I_Immutable, Comparable<I_MethodSignature> {
	/**
	 * i.e. "getMethodName"
	 * @return
	 */
	public String getMethodName();
	
	/**
	 * the number of parameters in the method signature
	 * @return
	 */
	public int getParameters();
	
	/**
	 * The class names represented as a string for 
	 * your purposes (i.e. usually 
	 * "org.adligo.tests4j.models.shared.dependency.I_MethodSignature"
	 * but if you needed some ASM types, could be something like
	 * "Lorg/adligo/tests4j/models/shared/dependency/I_MethodSignature;"
	 * )
	 * @return may be null, if there were no params.
	 */
	public String getParameterClassName(int whichOne);
	
	/**
	 * should return null for void return types
	 * @return
	 */
	public String getReturnClassName();
}
