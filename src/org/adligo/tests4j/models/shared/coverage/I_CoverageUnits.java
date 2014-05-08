package org.adligo.tests4j.models.shared.coverage;

import java.math.BigInteger;

/**
 * Coverage units are instructions or branches 
 * or anything else the coverage plug-in decides to track
 * as a significant metric.
 * 
 * ok this interface wraps either a 
 * Object with a int for smaller memory usage
 * or a BigInteger for huge coverage recordings.
 * 
 * @author scott
 *
 */
public interface I_CoverageUnits {
	/**
	 * return the BigInteger or the 
	 * int value as a BigInteger.
	 * @return
	 */
	public BigInteger getBig();
	/**
	 * the int value
	 * @return
	 */
	public int get();
}
