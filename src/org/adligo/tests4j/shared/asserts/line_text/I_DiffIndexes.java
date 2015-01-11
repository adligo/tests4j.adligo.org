package org.adligo.tests4j.shared.asserts.line_text;

/**
 * This is a  interface for holding information about a 
 * line of text, when it is compared with another 
 * line of text and also allows passing data between class loaders.
 * All indexes are left based starting at zero and ascending to the right.
 * 
 * If the left to right index is less than the right to left index then;
 *    The chars between the indexes matches with the other line of text.
 *    The chars to the left of the left to right index is different. 
 *    The chars to the right of the right to left index is different.
 *    
 * If the left to right matches with the right to left index then;
 *    All of the chars in this line of text are in the other line of text,
 *    however the other line of text has characters between the indexes.
 *    
 * If the left to right is greater than the right to left then;
 *     All of the chars to the left of the right to left index exist
 *     in the other text, and all chars to the right of the left to right 
 *     index exist in the other line of text.
 *     The chars between the right to left and left to right index
 *     do NOT exist in the other line of text.
 *     
 * @author scott
 *
 *
 */
public interface I_DiffIndexes {
	/**
	 * the index of the first different 
	 * when comparing characters from left to right,
	 * may be null if nothing was different in this text 
	 * (the other line of text was longer, and matched)
	 * 
	 * @return 0 or greater
	 */
	public abstract Integer getDiffLeftToRight();

	/**
	 * the index of the first different 
	 * when comparing characters from right to left,
	 * may be null if nothing was different in this text 
	 * (the other line of text was longer, and matched)
	 * @return 0 or greater
	 */
	public abstract Integer getDiffRightToLeft();
	/**
	 * the index of the first matching char 
	 * when comparing characters from left to right,
	 * may be null if nothing matched
	 * 
	 * @return 0 or greater
	 */
	public abstract Integer getMatchLeftToRight();

	/**
	 * the index of the first matching char 
	 * when comparing characters from right to left,
	 * may be null if nothing matched
	 * 
	 * @return 0 or greater
	 */
	public abstract Integer getMatchRightToLeft();
	
	/**
	 * calculate and return the string differences
	 * if there are two then it matches in the middle.
	 * @param line
	 * @return
	 */
	public String[] getDifferences(String line) ;
	
	/**
	 * calculate and return the string matches
	 * if there are two, then it matches on the right and left side.
	 * @param line
	 * @return
	 */
	public String[] getMatches(String line);
	
	/**
	 * 
	 * @return if all diffs and matches are null,
	 * this is considered empty.
	 */
	public boolean isEmpty();

}