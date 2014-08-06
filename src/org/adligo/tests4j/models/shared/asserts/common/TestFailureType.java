package org.adligo.tests4j.models.shared.asserts.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum TestFailureType implements I_TestFailureType {
	TestFailure(0), AssertCompareFailure(1), AssertThrownFailure(2);
	private static final Map<Integer, TestFailureType> TYPES = getTypes();
	
	private static Map<Integer, TestFailureType> getTypes() {
		Map<Integer, TestFailureType> toRet = new HashMap<Integer, TestFailureType>();
		toRet.put(0, TestFailure);
		toRet.put(1, AssertCompareFailure);
		toRet.put(2, AssertThrownFailure);
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
