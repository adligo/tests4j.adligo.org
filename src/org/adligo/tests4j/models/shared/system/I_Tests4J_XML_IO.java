package org.adligo.tests4j.models.shared.system;

/**
 * this is a interface to provide to and from xml
 * Serialization for a object instance.
 * Implementations of this interface 
 * also require the following;
 * 
 * 1) a Constructor with 
 * a single String parameter is implemented by the 
 * class, so that reading xml can be done.
 * 
 * 2) a Constant (public static final) String TAG_NAME
 * so that the xml for this object can be identified by
 * wrapping xml objects.
 * 
 * @author scott
 *
 */
public interface I_Tests4J_XML_IO {
	public static final String ATTRIBUTE_END = "\" ";
	public static final String EQUALS_QUOTE = "=\"";
	public static final String START = "<";
	public static final String END = ">";
	public static final String END_START = "</";
	public static final String START_END = "/>";
	public static final String NEW_LINE = "\n";
	public static final String TAB = "\t";
	
	/**
	 * return the xml
	 * @return
	 */
	public String toXml();
}
