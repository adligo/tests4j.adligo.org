package org.adligo.tests4j.models.shared.metadata;

import org.adligo.tests4j.shared.xml.I_XML_Producer;

/**
 * a in memory representation of a use case
 * http://en.wikipedia.org/wiki/Use_case
 * 
 * @author scott
 *
 */
public interface I_UseCaseBrief {
	public static final String TAG_NAME = "useCase";
	public static final String NAME_ATTRIBUTE = "name";
	/**
	 * The unique name of the use case
	 * which must match the name of a use case
	 * in a use case path which is part of
	 * a system or project in requirements.xml
	 * 
	 * @return
	 */
	public String getName();
}
