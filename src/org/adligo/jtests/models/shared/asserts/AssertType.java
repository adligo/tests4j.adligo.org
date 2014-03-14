package org.adligo.jtests.models.shared.asserts;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * AssertTypes are fairly similar to JUnit and other assertion frameworks with 
 * a few exceptions.
 * 
 * AssertThrown and AssertNotThrown
 *     these compare the class of the Exception  
 *     as well as the Message 
 *     which is a little different and more stringent than the annotation from JUnit
 * 
 * AssertUniform, AssertNotUniform
 *     these compare values with out a exact equals 
 *     ie two text files (one with dos line feeds, one with unix) but 
 *     	   with everything else exactly the same are Uniform.
 *     The same could be done for other types of data comparisons
 *         ie BigDecimal with out precision 0 vs 0.0 vs 0.00 are all really zero
 *         and therefore Uniform.
 *      
 * @author scott
 *
 */
public enum AssertType implements I_AssertType{
	AssertTrue(0), AssertFalse(1), AssertNull(2), AssertNotNull(3),
	AssertEquals(4), AssertNotEquals(5), AssertSame(6), AssertNotSame(7),
	AssertThrown(8), AssertNotThrown(9), AssertThrownUniform(9), 
	AssertUniform(10), AssertNotUniform(11);
	
	public static Set<AssertType> BOOLEAN_TYPES = getBooleanTypes();
	public static Set<AssertType> IDENTICAL_TYPES = getIdenticalTypes();
	public static Set<AssertType> THROWN_TYPES = getThrownTypes();
	public static Set<AssertType> UNIFORM_TYPES = getUniformTypes();
	
	private static Set<AssertType> getBooleanTypes() {
		Set<AssertType> toRet = new HashSet<AssertType>();
		toRet.add(AssertTrue);
		toRet.add(AssertFalse);
		toRet.add(AssertNull);
		toRet.add(AssertNotNull);
		return Collections.unmodifiableSet(toRet);
	}
	
	private static Set<AssertType> getIdenticalTypes() {
		Set<AssertType> toRet = new HashSet<AssertType>();
		toRet.add(AssertEquals);
		toRet.add(AssertNotEquals);
		toRet.add(AssertSame);
		toRet.add(AssertNotSame);
		return Collections.unmodifiableSet(toRet);
	}
	
	private static Set<AssertType> getThrownTypes() {
		Set<AssertType> toRet = new HashSet<AssertType>();
		toRet.add(AssertThrown);
		toRet.add(AssertThrownUniform);
		return Collections.unmodifiableSet(toRet);
	}
	
	private static Set<AssertType> getUniformTypes() {
		Set<AssertType> toRet = new HashSet<AssertType>();
		toRet.add(AssertUniform);
		toRet.add(AssertNotUniform);
		return Collections.unmodifiableSet(toRet);
	}
	private int id;
	
	AssertType(int p) {
		id = p;
	}
	
	@Override
	public int getId() {
		return id;
	}
}
