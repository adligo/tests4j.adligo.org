package org.adligo.tests4j.shared.asserts.line_text;

/**
 * a interface to represent text as a list of Strings 
 * used to copy data between classloaders.
 * @author scott
 *
 */
public interface I_TextLines {

	/**
	 * the number of lines of text
	 * @return
	 */
	public abstract int getLines();

	/**
	 * @param i
	 * @return the line without any line feeds in the string
	 */
	public abstract String getLine(int i);

}