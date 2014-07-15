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
	 * @return  the line number of the example text
	 * or the line number above where this line is missing 
	 * if it is missing in the example text.
	 * -1 means that the actual line came before the start of the example lines.
	 */
	public abstract int getExampleLineNbr();
	/**
	 * @return the line number of the actual text
	 * or the line above the missing line if the actual line is missing.
	 * -1 means that the example text had lines before the start of the actual text.
	 */
	public abstract Integer getActualLineNbr();

	/**
	 * 
	 * @return the indexes where the example line was different
	 * from the actual line.
	 */
	public abstract I_DiffIndexesPair getIndexes();

}