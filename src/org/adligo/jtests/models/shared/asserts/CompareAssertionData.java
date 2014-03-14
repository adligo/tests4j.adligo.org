package org.adligo.jtests.models.shared.asserts;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.adligo.jtests.models.shared.common.I_Immutable;

public class CompareAssertionData implements I_Immutable, I_AssertionData {
	public static final String EXPECTED = "expected";
	public static final String ACTUAL = "actual";
	private Object expected;
	private Object actual;
	
	public CompareAssertionData(Object pExpected, Object pActual) {
		expected = pExpected;
		actual = pActual;
	}

	public Object getExpected() {
		return expected;
	}

	public Object getActual() {
		return actual;
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
		if (getClass() != obj.getClass())
			return false;
		CompareAssertionData other = (CompareAssertionData) obj;
		if (actual == null) {
			if (other.actual != null)
				return false;
		} else if (!actual.equals(other.actual))
			return false;
		if (expected == null) {
			if (other.expected != null)
				return false;
		} else if (!expected.equals(other.expected))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CompareAssertionData [expected=" + expected + ", actual="
				+ actual + "]";
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
}
