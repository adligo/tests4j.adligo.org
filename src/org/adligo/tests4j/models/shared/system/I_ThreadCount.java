package org.adligo.tests4j.models.shared.system;

/**
 * a interface to recommend how much concurrency 
 * tests4j should use, 
 * @see org.adligo.tests4j.run.helpers.DefaultThreadCount
 * 
 * Implementations are expected to have a zero arg constructor.
 * 
 * @author scott
 *
 */
public interface I_ThreadCount {
	/**
	 * The recommended number of threads 
	 * tests4j should use for running trials
	 * @return
	 */
	public int getThreadCount();
	/**
	 * turn this instance into a UTF-8 xml String
	 * @return
	 */
	public String toXml();
	/**
	 * set the fields from this xml string
	 * @return
	 */
	public void fromXml(String xml);
}
