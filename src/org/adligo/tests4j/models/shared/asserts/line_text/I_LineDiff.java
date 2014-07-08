package org.adligo.tests4j.models.shared.asserts.line_text;

/**
 * a interface which allows data to be copied between class loaders
 * and represents a comparison of two lines of text.
 * 
 * @author scott
 *
 */
public interface I_LineDiff extends  Comparable<I_LineDiff>  {

	public abstract LineDiffType getType();

	/**
	 * note the example line is required
	 * since if there is a MISSING_ACTUAL_LINE this is the 
	 * line before it is expected
	 * @return
	 */
	public abstract int getExampleLineNbr();

	public abstract Integer getActualLineNbr();

	public abstract I_DiffIndexesPair getIndexes();

}