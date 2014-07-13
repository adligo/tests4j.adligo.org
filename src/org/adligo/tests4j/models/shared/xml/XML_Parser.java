package org.adligo.tests4j.models.shared.xml;

public class XML_Parser {
	
	/**
	 * 
	 * @param xml
	 * @param tagName
	 * @return a int array size 2,
	 *     0 is the start index
	 *     1 is the end index
	 */
	public static int[] getIndexes(String xml, String tagName) {
		if (xml == null || tagName == null) {
			return null;
		}
		int [] indexes = new int [2];
		indexes[1] = -1;
		//find the tag name first
		int nextTagName = xml.indexOf(tagName);
		char[] chars = xml.toCharArray();
		
		while (nextTagName != -1 && indexes[1] == -1) {
			//work back to find the first < before the tagName,
			//making sure there is only white space between them
			boolean tagStartMatch = false;
			if (nextTagName >= 1) {
				for (int i = nextTagName -1; i >= 0; i--) {
					char c = chars[i];
					if (c == '<') {
						tagStartMatch = true;
						indexes[0] = i;
						break;
					} else if (!Character.isWhitespace(c)) {
						break;
					}
				}
			}
			if (!tagStartMatch) {
				//check for the next tagName in the xml string
				nextTagName = xml.indexOf(tagName, nextTagName + tagName.length());
			} else {
				boolean inQuote = false;
				boolean inNextedTag = false;
				StringBuilder lastTag = new StringBuilder();
				int start = nextTagName + tagName.length();

				for (int i = start; i < chars.length; i++) {
					char c = chars[i];
					if (inQuote) {
						if (c == '\"') {
							inQuote = false;
						}
					} else if (inNextedTag) {
						if (!Character.isWhitespace(c)) {
							lastTag.append(c);
							if (c == '>') {
								inNextedTag = false;
								String tag = lastTag.toString();
								if (tag.equals(XML_Chars.END_START + tagName + ">")) {
									indexes[1] = i;
									break;
								}
								lastTag = new StringBuilder();
							}
						}
					} else {
						if (c == '\"') {
							inQuote = true;
						} else if (c == '<') {
							inNextedTag = true;
							lastTag.append(c);
						} else if (c == '>') {
							if (chars[i-1] == '/') {
								indexes[1] = i;
								break;
							}
						}
					}
				}
			}
		}
		if (indexes[1] == -1) {
			return null;
		}
		return indexes;
	}
	
	/**
	 * 
	 * @param xml a single xml tag (which could have nested tags)
	 * @param attributeName
	 * @return
	 */
	public static String getAttributeValue(String xml, String attributeName) {
		int nextStart = xml.indexOf(attributeName);
		int lastChar = xml.indexOf(">");
		char [] chars = xml.toCharArray();
		while (nextStart != -1) {
			int start = nextStart + attributeName.length();
			if (start >= chars.length) {
				return null;
			}
			boolean foundEquals = false;
			boolean foundQuote = false;
			StringBuilder sb = null;
			for (int i = start; i < lastChar; i++) {
				char c = chars[i];
				if (!foundEquals) {
					if (c == '=') {
						foundEquals = true;
					} else if (!Character.isWhitespace(c)) {
						//not a whitespace char
						break;
					}
				} else if (!foundQuote) {
					if (c == '"') {
						foundQuote = true;
						sb = new StringBuilder();
					} else if (new String("" + c).trim().length() == 1) {
						//not a whitespace char
						break;
					}
				} else if (c == '"') {
					return sb.toString();
				} else {
					sb.append(c);
				}
			}
			nextStart = xml.indexOf(attributeName, start);
		}
		return null;
	}
}
