package org.adligo.tests4j.models.shared.xml;

import java.util.Collection;
import java.util.Map;


/**
 * a class to assist in building 
 * a string that represents xml
 * @author scott
 *
 */
public class XML_Builder implements I_XML_Builder {
	private int indent = 0;
	private String indentChars;
	private String endOfLineChars;
	private StringBuilder sb = new StringBuilder();
	private int attributesPerLine = 3;
	private int attributeCount = 0;
	private boolean inAttributes = false;
	private boolean prettyPrint = true;
	private boolean tagStartedLine = false;
	
	public XML_Builder() {
		this(XML_Chars.TAB, XML_Chars.NEW_LINE_UNIX);
	}
	
	public XML_Builder(String pIndentChars, String pEndOfLineChars) {
		if (pIndentChars != null) {
			indentChars = pIndentChars;
		} else {
			indentChars = "";
		}
		if (pEndOfLineChars != null) {
			endOfLineChars = pEndOfLineChars;
		} else {
			endOfLineChars = "";
		}
		
		if (indentChars.length() == 0 && endOfLineChars.length() == 0) {
			prettyPrint = false;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.xml.I_XML_Builder#indent()
	 */
	@Override
	public void indent() {
		if (prettyPrint) {
			for (int i = 0; i < indent; i++) {
				sb.append(indentChars);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.xml.I_XML_Builder#addIndent()
	 */
	@Override
	public void addIndent() {
		indent++;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.xml.I_XML_Builder#removeIndent()
	 */
	@Override
	public void removeIndent() {
		indent--;
		if (indent < 0) {
			indent = 0;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.xml.I_XML_Builder#endLine()
	 */
	@Override
	public void endLine() {
		if (prettyPrint) {
			sb.append(endOfLineChars);
			attributeCount = 0;
			tagStartedLine = false;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.xml.I_XML_Builder#addStartTag(java.lang.String)
	 */
	@Override
	public void addStartTag(String tagName) {
		sb.append(XML_Chars.START);
		sb.append(tagName);
		if (prettyPrint) {
			tagStartedLine = true;
		}
	}
	
	@Override
	public void addStartTagWithoutAttributes(String tagName, boolean endLine) {
		sb.append(XML_Chars.START);
		sb.append(tagName);
		sb.append(XML_Chars.END);
		if (prettyPrint) {
			if (endLine) {
				endLine();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.xml.I_XML_Builder#addEndTag(java.lang.String)
	 */
	@Override
	public void addEndTag(String tagName) {
		sb.append(XML_Chars.END_START);
		sb.append(tagName);
		sb.append(XML_Chars.END);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.xml.I_XML_Builder#addAttribute(java.lang.String, java.lang.String)
	 */
	@Override
	public void addAttribute(String attributeName, String content) {
		if (!tagStartedLine) {
			if (attributeCount == 0) {
				indent();
			}
		}
		sb.append(" ");
		sb.append(attributeName);
		sb.append(XML_Chars.EQUALS_QUOTE);
		String toXml = XML_Chars.toXml(content);
		sb.append(toXml);
		sb.append(XML_Chars.QUOTE);
		
		if (prettyPrint) {
			attributeCount++;
			if (attributeCount == attributesPerLine) {
				if (!inAttributes) {
					addIndent();
					inAttributes = true;
				}
				endLine();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.xml.I_XML_Builder#addText(java.lang.String)
	 */
	@Override
	public void addText(String text) {
		String toXml = XML_Chars.toXml(text);
		sb.append(toXml);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.xml.I_XML_Builder#append(java.lang.String)
	 */
	@Override
	public void append(String p) {
		sb.append(p);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.xml.I_XML_Builder#toXmlString()
	 */
	@Override
	public String toXmlString() {
		return sb.toString();
	}

	public int getAttributesPerLine() {
		return attributesPerLine;
	}

	public void setAttributesPerLine(int attributesPerLine) {
		this.attributesPerLine = attributesPerLine;
	}

	@Override
	public void endHeader() {
		if (prettyPrint) {
			if (inAttributes) {
				inAttributes = false;
				removeIndent();
			}
		}
		sb.append(">");
		if (prettyPrint) {
			endLine();
		}
	}


	public void addCollection(Collection<String> items, String pluralTagName, String singularTagName) {
		addIndent();
		indent();
		addStartTagWithoutAttributes(pluralTagName, true);
		
		addIndent();
		for (String item: items) {
			if (item != null) {
				indent();
				addStartTagWithoutAttributes(singularTagName, false);
				addText(item);
				addEndTag(singularTagName);
				endLine();
			}
		}
		removeIndent();
		
		indent();
		addEndTag(pluralTagName);
		endLine();
		removeIndent();
	}

	@Override
	public void addMap(Map<I_XML_Producer, I_XML_Producer> items, String tagName) {
		indent();
		addStartTag(tagName);
		
		
		
		
	}
}
