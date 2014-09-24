package org.adligo.tests4j.models.shared.coverage;

import java.math.BigInteger;

/**
 * @see I_CoverageUnits
 * This implementation trys to keep memory usage down
 * to just the Object and int if possible.
 * 
 * @author scott
 *
 */
public class CoverageUnits implements I_CoverageUnits {
	public static final String COVERAGE_UNITS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO = "CoverageUnits must be greater than or equal to zero.";
	private static final BigInteger ZERO = new BigInteger("0");
	private static final BigInteger MAX_INT = new BigInteger(
			"" + Integer.MAX_VALUE);
	
	private BigInteger big_;
	private int value_;
	
	public CoverageUnits(BigInteger p) {
		if (ZERO.compareTo(p) >= 1) {
			throw new IllegalArgumentException(
					COVERAGE_UNITS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO);
		}
		int result = MAX_INT.compareTo(p);
		if (result <= 1) {
			value_ = p.intValue();
		} else {
			big_ = p;
		}
	}
	
	public CoverageUnits(int p) {
		if (p < 0) {
			throw new IllegalArgumentException(
					COVERAGE_UNITS_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ZERO);
		}
		value_ = p;
	}
	
	
	public CoverageUnits(long p) {
		this(BigInteger.valueOf(p));
	}
	
	public boolean isInt() {
		if (big_ == null) {
			return true;
		}
		return false;
	}
	/**
	 * @see I_CoverageUnits#getBig()
	 */
	@Override
	public BigInteger getBig() {
		if (isInt()) {
			return BigInteger.valueOf(value_);
		}
		return big_;
	}

	/**
	 * @see I_CoverageUnits#get()
	 */
	@Override
	public int get() {
		return value_;
	}

	@Override
	public String toString() {
		return "CoverageUnits [" + getBig().toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (big_ == null) {
			result = prime * result + value_;
		} else {
			result = prime * result + big_.hashCode();
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		try {
			I_CoverageUnits other = (I_CoverageUnits) obj;
			if (isInt()) {
				if (value_ != other.get()) {
					return false;
				}
			} else if (!big_.equals(other.getBig())) {
				return false;
			}
		} catch (ClassCastException x) {
			//do nothing for GWT impl
			return false;
		}
		return true;
	}
}
