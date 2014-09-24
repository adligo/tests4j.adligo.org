package org.adligo.tests4j.shared.xml;

import java.util.Collection;
import java.util.Map;

/**
 * a interface to assist in building
 * xml as a UTF16 string (java internal charset)
 * can be turned into any supported charset via
 * p.getBytes("UTF8"),p.getBytes("ASCII") exc.
 * 
 * @author scott
 *
 */
public interface I_XML_Builder {

	/**
	 * indent the line, append the indent characters
	 *  for the current indent level
	 */
	public abstract void indent();

	/**
	 * add to the current indent level
	 */
	public abstract void addIndent();
	
	/**
	 * adds a list of something to the xml ie;
	 * <pluralTagName>
	 *     <singularTagName>item 1</singularTagName>
	 *     <singularTagName>item 2</singularTagName>
	 *     <singularTagName>item 3</singularTagName>
	 * </pluralTagName>
	 * @param items
	 * @param pluralTagName
	 * @param singularTagName
	 */
	public void addCollection(Collection<String> items, String pluralTagName, String singularTagName);

	/**
	 * adds a map to the xml ie;
	 * <tagName>
	 *     <key>
	 *        <mapKey/>
	 *     </key>
	 *     <value>
	 *     	  <mapValue/>
	 *     </value>
	 * </tagName>
	 * @param items
	 * @param pluralTagName
	 * @param singularTagName
	 */
	public void addMap(Map<I_XML_Producer, I_XML_Producer> items, String tagName);
	
	/**
	 * reduce the current indent level
	 */
	public abstract void removeIndent();

	/**
	 * append a new line character
	 */
	public abstract void endLine();

	/**
	 * Adds a start tag ie "<hey"
	 * @param tagName a valid xml tag name (no special character treatment)
	 */
	public abstract void addStartTag(String tagName);

	/**
	 * Adds a start tag ie "<hey>" with out any attributes
	 * @param tagName a valid xml tag name (no special character treatment)
	 */
	public abstract void addStartTagWithoutAttributes(String tagName, boolean endLine);
	
	
	/**
	 * Adds a end ie "</hey>"
	 * @param tagName a valid xml tag name (no special character treatment)
	 */
	public abstract void addEndTag(String tagName);
	/**
	 * Adds a end ie ' hey="content"'
	 * @param tagName a valid xml tag name (no special character treatment)
	 * @param content the xml content to be escaped
	 *    "a&b" would become "a&amp;b"
	 */
	public abstract void addAttribute(String attributeName, String content);

	/**
	 * a method to add the > and new line after the attributes,
	 * also removes a indent if it was increased during attribute addition.
	 */
	public abstract void endHeader();
	/**
	 * adds arbitrary text content
	 * @param text the xml content to be escaped
	 *    "a&b" would become "a&amp;b"
	 */
	public abstract void addText(String text);

	/**
	 * append any string including xml chars
	 * <,>,& exc to the xml
	 * @param p
	 */
	public void append(String p);
	
	/**
	 * return the uft16 xml string
	 * @return
	 */
	public String toXmlString();
	
	/**
	 * the current attributes per line
	 * @return
	 */
	public int getAttributesPerLine();

	/**
	 * mutates the xml builder for future calls to addAttribute
	 * @param attributesPerLine
	 */
	public void setAttributesPerLine(int attributesPerLine);

}