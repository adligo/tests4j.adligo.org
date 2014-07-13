package org.adligo.tests4j.models.shared.metadata;

import org.adligo.tests4j.models.shared.xml.I_XML_Consumer;
import org.adligo.tests4j.models.shared.xml.I_XML_Producer;

/**
 * a in memory representation of a use case
 * http://en.wikipedia.org/wiki/Use_case
 * 
 * @author scott
 *
 */
public interface I_UseCaseMetadata extends I_XML_Producer, I_XML_Consumer {
	public static final String TAG_NAME = "useCase";
	public static final String NOWN_ATTRIBUTE = "nown";
	public static final String VERB_ATTRIBUTE = "verb";
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
