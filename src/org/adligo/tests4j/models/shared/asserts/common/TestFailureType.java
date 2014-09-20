package org.adligo.tests4j.models.shared.asserts.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * this class is a list of test failure types,
 * which are used for assisting the AssertionProcessor
 * in passing data back to the I_AssertListener
 * @author scott
 *
 */
public enum TestFailureType implements I_TestFailureType {
	TestFailure(0), AssertCompareFailure(1), AssertThrownFailure(2), AssertDependencyFailure(3);
	private static final Map<Integer, TestFailureType> TYPES = getTypes();
	
	private static Map<Integer, TestFailureType> getTypes() {
		Map<Integer, TestFailureType> toRet = new HashMap<Integer, TestFailureType>();
		toRet.put(0, TestFailure);
		toRet.put(1, AssertCompareFailure);
		toRet.put(2, AssertThrownFailure);
		toRet.put(3, AssertDependencyFailure);
		return Collections.unmodifiableMap(toRet);
	}
	
	public static TestFailureType get(I_TestFailureType p) {
		return get(p.getId());
	}
	
	public static TestFailureType get(int id) {
		return TYPES.get(id);
	}
	private int id;
	
	private TestFailureType(int pid) {
		id = pid;
	}

	public int getId() {
		return id;
	}
}
