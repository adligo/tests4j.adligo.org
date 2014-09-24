package org.adligo.tests4j.shared.asserts.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
 * Extension plug-ins to the framework will only be done via assertUniform, assertNotUniform
 * and assertUniformThrown, therefore only the assert types in this enum
 * will be needed for any sort of rendering of test results.
 * 
 * @author scott
 *
 */
public enum AssertType implements I_AssertType {
	AssertTrue(0), AssertFalse(1), AssertNull(2), AssertNotNull(3),
	AssertEquals(4), AssertNotEquals(5), AssertSame(6), AssertNotSame(7),
	AssertThrown(8), AssertThrownUniform(9), 
	AssertUniform(10), AssertNotUniform(11), AssertContains(12), 
	AssertGreaterThanOrEquals(13), AssertDependency(14), AssertCircularDependency(15);
	
	public static Set<AssertType> BOOLEAN_TYPES = getBooleanTypes();
	public static Set<AssertType> EQUAL_TYPES = getEqualTypes();
	public static Set<AssertType> IDENTICAL_TYPES = getIdenticalTypes();
	public static Set<AssertType> UNIFORM_TYPES = getUniformTypes();
	public static Map<Integer, AssertType> TYPES_BY_ID = getTypesById();
	
	private static Set<AssertType> getBooleanTypes() {
		Set<AssertType> toRet = new HashSet<AssertType>();
		toRet.add(AssertTrue);
		toRet.add(AssertFalse);
		toRet.add(AssertNull);
		toRet.add(AssertNotNull);
		return Collections.unmodifiableSet(toRet);
	}
	
	private static Set<AssertType> getEqualTypes() {
		Set<AssertType> toRet = new HashSet<AssertType>();
		toRet.add(AssertEquals);
		toRet.add(AssertNotEquals);
		return Collections.unmodifiableSet(toRet);
	}
	
	private static Set<AssertType> getIdenticalTypes() {
		Set<AssertType> toRet = new HashSet<AssertType>();
		toRet.addAll(EQUAL_TYPES);
		toRet.add(AssertSame);
		toRet.add(AssertNotSame);
		return Collections.unmodifiableSet(toRet);
	}
	
	
	
	private static Set<AssertType> getUniformTypes() {
		Set<AssertType> toRet = new HashSet<AssertType>();
		toRet.add(AssertUniform);
		toRet.add(AssertNotUniform);
		return Collections.unmodifiableSet(toRet);
	}
	
	/**
	 * copy assert types between classloaders
	 * @param p
	 * @return
	 */
	public static AssertType getType(I_AssertType p) {
		return TYPES_BY_ID.get(p.getId());
	}
	
	private static Map<Integer, AssertType> getTypesById() {
		Map<Integer, AssertType> toRet = new HashMap<Integer, AssertType>();
		add(AssertTrue, toRet);
		add(AssertFalse, toRet);
		add(AssertNull, toRet);
		add(AssertNotNull, toRet);
		add(AssertEquals, toRet);
		add(AssertNotEquals, toRet); 
		add(AssertSame, toRet);
		add(AssertNotSame, toRet);
		add(AssertThrown, toRet);
		add(AssertThrownUniform, toRet);
		add(AssertUniform, toRet);
		add(AssertNotUniform, toRet);
		add(AssertContains, toRet);
		add(AssertGreaterThanOrEquals, toRet);
		add(AssertDependency, toRet);
		add(AssertCircularDependency, toRet);
		return Collections.unmodifiableMap(toRet);
	}
	
	private static void add(AssertType type, Map<Integer, AssertType> map) {
		map.put(type.getId(), type);
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
