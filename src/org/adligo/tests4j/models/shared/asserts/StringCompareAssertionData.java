package org.adligo.tests4j.models.shared.asserts;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.models.shared.common.I_Immutable;

/**
 * a immutable class to represent the comparison data (expected and actual values)
 * for an assertion. Allows nulls in both expected and actual.
 * 
 * @author scott
 *
 * @param <T>
 * 
 */
public class StringCompareAssertionData extends CompareAssertionData<String> implements I_Immutable, I_CompareAssertionData<String> {
	public static final String STRING_COMPARE_ASSERTION_DATA_REQUIRES_A_I_TEXT_LINES_COMPARE_RESULT = "StringCompareAssertionData requires a I_TextLinesCompareResult.";
	public static final String COMPARISON = "Comparison";
	private I_TextLinesCompareResult comparison;
	
	public StringCompareAssertionData(I_CompareAssertionData<String> other, I_TextLinesCompareResult pComparison) {
		super(other);
		if (pComparison == null) {
			throw new IllegalArgumentException(STRING_COMPARE_ASSERTION_DATA_REQUIRES_A_I_TEXT_LINES_COMPARE_RESULT);
		}
		comparison = pComparison;
	}

	@Override
	public Set<String> getKeys() {
		Set<String> toRet = new HashSet<String>(super.getKeys());
		toRet.add(COMPARISON);
		return Collections.unmodifiableSet(toRet);
	}

	@Override
	public Object getData(String key) {
		if (COMPARISON.equals(key)) {
			return comparison;
		}
		return super.getData(key);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((comparison == null) ? 0 : comparison.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StringCompareAssertionData other = (StringCompareAssertionData) obj;
		if (comparison == null) {
			if (other.comparison != null)
				return false;
		} else if (!comparison.equals(other.comparison))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StringCompareAssertionData [comparison=" + comparison + 
					", expected=" + super.getExpected() +
					", actual=" + super.getActual() + "]";
	}



}
