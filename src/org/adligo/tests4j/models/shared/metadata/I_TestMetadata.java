package org.adligo.tests4j.models.shared.metadata;

import org.adligo.tests4j.models.shared.xml.I_XML_Consumer;
import org.adligo.tests4j.models.shared.xml.I_XML_Producer;

/**
 * A interface which contains information
 * about a a Test in a Trial
 * @author scott
 *
 */
public interface I_TestMetadata extends I_XML_Producer {
	public static final String TAG_NAME = "testMetadata";
	public static final String NAME_ATTRIBUTE = "name";
	public static final String TIMEOUT_ATTRIBUTE="timeout";
	public static final String IGNORED_ATTRIBUTE = "ignored";
	/**
	 * the name of the test method
	 * @return
	 */
	public abstract String getTestName();

	/**
	 * the timeout setting
	 * @return
	 */
	public abstract Long getTimeout();

	/**
	 * if the test has been ignored
	 * @return
	 */
	public abstract boolean isIgnored();

}