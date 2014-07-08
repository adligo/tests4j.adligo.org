package org.adligo.tests4j.models.shared.asserts.line_text;

/**
 *  a helper class to split lines
 * @author scott
 *
 */
public class LineSplitter {
	private static final char LINE_SEPERATOR = '\n';
	private static final char DOS_LINE_SEPERATOR = '\r';
	private boolean lastCharIsLineFeed = false;
	
	
	public boolean isLineFeedChar(char c) {
		if (c == LINE_SEPERATOR) {
			lastCharIsLineFeed = true;
			return true;
		} else if (c == DOS_LINE_SEPERATOR) {
			lastCharIsLineFeed = true;
			return true;
		}
		lastCharIsLineFeed = false;
		return false;
	}
	
	public boolean isLastCharLineFeedChar() {
		return lastCharIsLineFeed;
	}
	
	public void setLastCharLineFeedChar(boolean p) {
		lastCharIsLineFeed = p;
	}
}
