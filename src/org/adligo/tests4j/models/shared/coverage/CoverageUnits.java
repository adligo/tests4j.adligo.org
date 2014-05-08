package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @see I_CoverageUnits
 * @author scott
 *
 */
public class CoverageUnits implements I_CoverageUnits {
	private int value = 0;
	private BigInteger big;
	
	public CoverageUnits(int p) {
		value = p;
	}
	
	public CoverageUnits(BigInteger p) {
		big = p;
		value = p.intValue();
	}

	/**
	 * @see I_CoverageUnits#getBig()
	 */
	@Override
	public BigInteger getBig() {
		if (big == null) {
			return BigInteger.valueOf(value);
		}
		return big;
	}

	/**
	 * @see I_CoverageUnits#get()
	 */
	@Override
	public int get() {
		return value;
	}

	@Override
	public String toString() {
		return "CoverageUnits [" + getBig().toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((big == null) ? 0 : big.hashCode());
		result = prime * result + value;
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
		CoverageUnits other = (CoverageUnits) obj;
		if (big == null) {
			if (other.big != null)
				return false;
		} else if (!big.equals(other.big))
			return false;
		if (value != other.value)
			return false;
		return true;
	}
}
