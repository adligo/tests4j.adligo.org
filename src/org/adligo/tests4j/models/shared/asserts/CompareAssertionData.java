package org.adligo.tests4j.models.shared.asserts;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.models.shared.common.I_Immutable;

/**
 * a immutable class to represent the comparison data (expected and actual values)
 * for an assertion. Allows nulls in both expected and actual.
 * 
 * @author scott
 *
 * @param <T>
 */
public class CompareAssertionData<T> implements I_Immutable, I_CompareAssertionData<T> {
	public static final String EXPECTED = "expected";
	public static final String ACTUAL = "actual";
	
	private T expected;
	private T actual;
	
	public CompareAssertionData(T pExpected, T pActual) {
		expected = pExpected;
		actual = pActual;
	}

	
	public CompareAssertionData(I_CompareAssertionData<T> other) {
		this(other.getExpected(), other.getActual());
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

	@SuppressWarnings("unchecked")
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
