package org.adligo.tests4j.models.shared.metadata;

import org.adligo.tests4j.shared.xml.I_XML_Consumer;
import org.adligo.tests4j.shared.xml.I_XML_Producer;

/**
 * A interface which contains information
 * about a a Test in a Trial
 * @author scott
 *
 */
public interface I_TestMetadata {
	/**
	 * the name of the test method
	 * @return
	 */
	public String getTestName();

	/**
	 * the timeout setting
	 * @return
	 */
	public Long getTimeout();

	/**
	 * if the test has been ignored
	 * @return
	 */
	public boolean isIgnored();
	
	/**
	 * The name of the use case from the @UseCaseScope 
	 * annotation, if this is a use case trial.
	 * 
	 * @return
	 */
	public String getUseCaseName();

}