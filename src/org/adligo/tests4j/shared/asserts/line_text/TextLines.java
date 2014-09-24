package org.adligo.tests4j.shared.asserts.line_text;

import java.util.ArrayList;
import java.util.List;

/**
 * a class to represent text 
 * as individual lines with out line feeds in a list.
 * 
 * @author scott
 *
 */
public class TextLines implements I_TextLines {
	private List<String> lines = new ArrayList<String>();
	
	public TextLines(String text) {
		this(text, true);
	}
	/**
	 * 
	 * @param text
	 * @param normalizeLineFeed if true then all line feed chars are remove from the line data
	 *   if false then the \r \n exc are added to the line data, to help with assertEquals 
	 *   for string data.
	 */
	public TextLines(String text, boolean normalizeLineFeed) {
		char [] chars = text.toCharArray();
		StringBuilder line = new StringBuilder();
		
		
		LineSplitter splitter = new LineSplitter();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (splitter.isNewLineChar(c)) {
				if (splitter.isMultiCharNewLine(c)) {
					if (!normalizeLineFeed) {
						line.append(c);
					}
					String lineText = line.toString();
					lines.add(lineText);
					
					line = new StringBuilder();
					splitter.setLastNewLineChar(null);
				} else if (splitter.isLastCharNewLine()) {
					if (!normalizeLineFeed) {
						line.append(c);
					}
					String lineText = line.toString();
					line = new StringBuilder();
					
					lines.add(lineText);
					splitter.setLastNewLineChar(c);
				}  else {
					splitter.setLastNewLineChar(c);
					if (!normalizeLineFeed) {
						line.append(c);
					}
				}
			} else if (splitter.isLastCharNewLine()) {
				String lineText = line.toString();
				lines.add(lineText);
				
				line = new StringBuilder();
				line.append(c);
				splitter.setLastNewLineChar(null);
			} else {
				line.append(c);
				splitter.setLastNewLineChar(null);
			}
		}
		if (splitter.isLastCharNewLine()) {
			//append empty line
			String lineText = line.toString();
			lines.add(lineText);
		} else {
			String lineText = line.toString();
			if (lineText.length() >= 1) {
				lines.add(lineText);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_TextLines#getLines()
	 */
	@Override
	public int getLines() {
		return lines.size();
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_TextLines#getLine(int)
	 */
	@Override
	public String getLine(int i) {
		if (lines.size() <= i) {
			return "";
		}
		return lines.get(i);
	}

	@Override
	public String toString() {
		return "TextLines [lines=" + lines + "]";
	}
}
