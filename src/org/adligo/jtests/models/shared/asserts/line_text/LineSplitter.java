package org.adligo.jtests.models.shared.asserts.line_text;

public class LineSplitter {
	private static String LINE_SEPERATOR = "\n";
	private boolean lineSeperatorOneChar;
	private Character lastChar;
	
	public LineSplitter() {
		if (LINE_SEPERATOR.length() == 1) {
			lineSeperatorOneChar = true;
		} else {
			lineSeperatorOneChar = false;
		}
	}
	
	public boolean isLineFeedChar(char c) {
		if (lineSeperatorOneChar) {
			if (c == LINE_SEPERATOR.charAt(0)) {
				return true;
			}
		} else {
			if (c == LINE_SEPERATOR.charAt(0)) {
				lastChar = c;
				return true;
			} else if (lastChar == LINE_SEPERATOR.charAt(0)){
				if (LINE_SEPERATOR.length() == 2) {
					if (c == LINE_SEPERATOR.charAt(1)) {
						lastChar = null;
						return true;
					}
				}
				lastChar = null;
				return false;
			} 
		}
		return false;
	}
	
}
