package org.adligo.tests4j.shared.xml;

/**
 * this is a interface to provide to xml
 * Serialization for a object instance.
 * 
 * @author scott
 */
public interface I_XML_Producer {
	/**
	 * place the xml representation of the current
	 * instance into the I_XML_Builder.
	 * 
	 * @param producer
	 * @return
	 */
	public void toXml(I_XML_Builder builder);
}
