package org.adligo.tests4j.models.shared.asserts;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.models.shared.common.ClassUtils;
import org.adligo.tests4j.models.shared.common.I_Immutable;

public class CompareAssertionData<T> implements I_Immutable, I_CompareAssertionData<T> {
	public static final String THE_ACTUAL_VALUE_MUST_BE_A = "The Actual value must be a ";
	public static final String THE_EXPECTED_VALUE_MUST_BE_A = "The expected value must be a ";
	public static final String NULL_VALUE_NOT_ALLOWED_HERE = "Null value not allowed here.";
	public static final String COMPARE_ASSERTION_DATA_REQUIRES_A_BASE_CLASS_IF_USING_THIS_CONSTRUCTOR = "CompareAssertionData requires a baseClass if using this constructor.";
	public static final String EXPECTED = "expected";
	public static final String ACTUAL = "actual";
	private T expected;
	private T actual;
	
	public CompareAssertionData(T pExpected, T pActual) {
		expected = pExpected;
		actual = pActual;
	}

	
	public T getExpected() {
		return expected;
	}

	public T getActual() {
		return actual;
	}



	@Override
	public Set<String> getKeys() {
		Set<String> toRet = new HashSet<String>();
		toRet.add(EXPECTED);
		toRet.add(ACTUAL);
		return Collections.unmodifiableSet(toRet);
	}

	@Override
	public Object getData(String key) {
		if (EXPECTED.equals(key)) {
			return expected;
		} else if (ACTUAL.equals(key)) {
			return actual;
		}
		return null;
	}

	public T getTypedData(String key) {
		if (EXPECTED.equals(key)) {
			return expected;
		} else if (ACTUAL.equals(key)) {
			return actual;
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actual == null) ? 0 : actual.hashCode());
		result = prime * result
				+ ((expected == null) ? 0 : expected.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		try {
			I_CompareAssertionData<T> other = (I_CompareAssertionData<T>) obj;
			if (actual == null) {
				if (other.getActual() != null)
					return false;
			} else if (!actual.equals(other.getActual()))
				return false;
			if (expected == null) {
				if (other.getExpected() != null)
					return false;
			} else if (!expected.equals(other.getExpected()))
				return false;
		} catch (ClassCastException x) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "CompareAssertionData [expected=" + expected + ", actual="
				+ actual + "]";
	}
}
