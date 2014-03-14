package org.adligo.jtests.models.shared.asserts.line_text;

import java.util.ArrayList;
import java.util.List;

public class LineText {
	private List<String> lines = new ArrayList<String>();
	
	public LineText(String text) {
		char [] chars = text.toCharArray();
		StringBuilder line = new StringBuilder();
		boolean previousLineFeed = false;
		
		
		LineSplitter splitter = new LineSplitter();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (splitter.isLineFeedChar(c)) {
				if (!previousLineFeed) {
					String lineText = line.toString();
					line = new StringBuilder();
					lines.add(lineText);
				}
				previousLineFeed = true;
			} else {
				line.append(c);
				previousLineFeed = false;
			}
		}
		String lineText = line.toString();
		if (lineText.length() != 0) {
			lines.add(lineText);
		}
	}
	
	public int getLines() {
		return lines.size();
	}
	
	public String getLine(int i) {
		return lines.get(i);
	}
}
