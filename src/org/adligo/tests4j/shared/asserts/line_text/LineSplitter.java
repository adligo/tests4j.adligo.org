package org.adligo.tests4j.shared.asserts.line_text;

/**
 *  a helper class to split lines
 * @author scott
 *
 */
public class LineSplitter {
	private static final char LF = '\n';
	private static final char CR = '\r';
	private Character lastNewLine;
	
	
	public boolean isNewLineChar(char c) {
		if (c == LF) {
			return true;
		} else if (c == CR) {
			return true;
		}
		return false;
	}
	
	/** 
	 * should be set to null if the current char is not a line feed
	 * @param c
	 */
	public void setLastNewLineChar(Character c) {
		lastNewLine = c;
	}
	
	public boolean isLastCharNewLine() {
		if (lastNewLine == null) {
			return false;
		}
		return isNewLineChar(lastNewLine);
	}
	
	public boolean isMultiCharNewLine(char c) {
		if (lastNewLine == null) {
			return false;
		}
		if (lastNewLine == CR) {
			if (c == LF) {
				return true;
			}
		}
		return false;
	}
	
}
