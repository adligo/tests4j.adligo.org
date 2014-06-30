package org.adligo.tests4j.models.shared.asserts.line_text;

import java.util.List;

public interface I_LineTextCompareResult {
	/**
	 * the key to assert command data
	 */
	public static final String DATA_KEY = "LineTextCompareResult";
	
	public abstract boolean isMatched();

	public abstract int getExampleLines();

	public abstract int getActualLines();

	public abstract List<LineDiff> getLineDiffs();

	public abstract String getExample();

	public abstract String getActual();

}