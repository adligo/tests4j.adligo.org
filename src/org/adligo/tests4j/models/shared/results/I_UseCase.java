package org.adligo.tests4j.models.shared.results;

/**
 * a in memory representation of a use case
 * http://en.wikipedia.org/wiki/Use_case
 * 
 * @author scott
 *
 */
public interface I_UseCase {
	/**
	 * the nown ie food
	 * @return
	 */
	public String getNown();
	/**
	 * the verb ie eat
	 * @return
	 */
	public String getVerb();
}
