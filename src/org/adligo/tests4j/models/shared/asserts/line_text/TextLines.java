package org.adligo.tests4j.models.shared.asserts.line_text;

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
		char [] chars = text.toCharArray();
		StringBuilder line = new StringBuilder();
		
		
		LineSplitter splitter = new LineSplitter();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (splitter.isLastCharLineFeedChar()) {
				if (splitter.isLineFeedChar(c)) {
					String lineText = line.toString();
					lines.add(lineText);
					
					line = new StringBuilder();
					splitter.setLastCharLineFeedChar(false);
				} else {
					String lineText = line.toString();
					lines.add(lineText);
					
					line = new StringBuilder();
					
					line.append(c);
					splitter.setLastCharLineFeedChar(false);
				}
			} else {
				if (!splitter.isLineFeedChar(c)) {
					line.append(c);
				}
			}
		}
		String lineText = line.toString();
		if (lineText.length() != 0) {
			lines.add(lineText);
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
}
