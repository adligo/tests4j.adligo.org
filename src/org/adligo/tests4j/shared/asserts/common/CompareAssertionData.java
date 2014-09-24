package org.adligo.tests4j.shared.asserts.common;

import org.adligo.tests4j.shared.common.I_Immutable;

/**
 * a immutable class to represent the comparison data (expected and actual values)
 * for an assertion. Allows nulls in both expected and actual.
 * 
 * @author scott
 *
 * @param <T>
 */
public class CompareAssertionData<T> implements I_Immutable, I_CompareAssertionData<T> {
	private T expected;
	private T actual;
	private I_AssertType type;
	
	public CompareAssertionData(T pExpected, T pActual, I_AssertType pType) {
		expected = pExpected;
		actual = pActual;
		type = pType;
	}

	
	public CompareAssertionData(I_CompareAssertionData<T> other) {
		this(other.getExpected(), other.getActual(), other.getType());
	}


	public T getExpected() {
		return expected;
	}

	public T getActual() {
		return actual;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actual == null) ? 0 : actual.hashCode());
		result = prime * result
				+ ((expected == null) ? 0 : expected.hashCode());
		result = prime * result
				+ ((type == null) ? 0 : type.hashCode());
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
			if (type == null) {
				if (other.getType() != null)
					return false;
			} else if (!type.equals(other.getType()))
				return false;
		} catch (ClassCastException x) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "CompareAssertionData [expected=" + expected + ", actual="
				+ actual + ", type=" + type + "]";
	}


	public I_AssertType getType() {
		return type;
	}
}
