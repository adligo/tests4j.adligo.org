package org.adligo.tests4j.shared.asserts.line_text;

import java.util.List;


public interface I_TextLinesCompareResult {
	/**
	 * the key to assert command data
	 */
	public static final String DATA_KEY = "LineTextCompareResult";
	
	public abstract boolean isMatched();
	
	public abstract List<I_LineDiff> getLineDiffs();

	public abstract I_TextLines getExpectedLines();

	public abstract I_TextLines getActualLines();

	/**
	 * @return
	 * This method returns true if there was a error comparing the text lines.
	 */
	public boolean hasErrorMessage();
  
	/**
	 * @return
	 * This method returns a error message if there was one or null.
	 */
  public String getErrorMessage();
}