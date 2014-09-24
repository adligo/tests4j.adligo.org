package org.adligo.tests4j.shared.asserts.line_text;

/**
 * a pair of line indexes which allows 
 * the two lines to show their differences.
 * So in the following example;
 * expected="abc123dabc"
 * actual="a23dabc"
 * the expected indexes would be 
 * start 1, end 4
 * the actual indexes would be
 * start 1, end 1
 * 
 * @author scott
 *
 */
public interface I_DiffIndexesPair {
	/**
	 * the indexes where the example line
	 * is different with the actual line
	 * starts and ends
	 * @return
	 */
	public abstract I_DiffIndexes getExpected();
	/**
	 * the indexes where the actual lines 
	 * is different with the example line
	 * starts and ends
	 * @return
	 */
	public abstract I_DiffIndexes getActual();

}