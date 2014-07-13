package org.adligo.tests4j.models.shared.xml;

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
	
	public XML_Builder() {
		this(XML_Chars.TAB, XML_Chars.NEW_LINE_UNIX);
	}
	
	public XML_Builder(String pIndentChars, String pEndOfLineChars) {
		indentChars = pIndentChars;
		endOfLineChars = pEndOfLineChars;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.xml.I_XML_Builder#indent()
	 */
	@Override
	public void indent() {
		for (int i = 0; i < indent; i++) {
			sb.append(indentChars);
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
		sb.append(endOfLineChars);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.xml.I_XML_Builder#addStartTag(java.lang.String)
	 */
	@Override
	public void addStartTag(String tagName) {
		sb.append(XML_Chars.START);
		sb.append(tagName);
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
		sb.append(" ");
		sb.append(attributeName);
		sb.append(XML_Chars.EQUALS_QUOTE);
		String toXml = XML_Chars.toXml(content);
		sb.append(toXml);
		sb.append(XML_Chars.QUOTE);
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
}
