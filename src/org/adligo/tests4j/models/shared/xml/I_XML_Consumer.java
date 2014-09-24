package org.adligo.tests4j.models.shared.xml;

/**
 * a interface for reading xml,
 * implementations should be thread safe.
 * 
 * @author scott
 *
 */
public interface I_XML_Consumer<T> {
	public String getPrimaryTagName();
	public T create(String xml);
}
