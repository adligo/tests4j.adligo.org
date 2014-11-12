package org.adligo.tests4j.models.shared.metadata;

import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.common.Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AnnotationMessages;
import org.adligo.tests4j.shared.xml.I_XML_Builder;
import org.adligo.tests4j.shared.xml.XML_Parser;


/**
 * @see I_UseCaseBrief
 * @author scott
 *
 */
public class UseCaseBrief implements I_UseCaseBrief {
	private String nown;
	private String verb;
	
	public UseCaseBrief(String pNown, String pVerb) {
		setup(pNown, pVerb);
	}

	protected void setup(String pNown, String pVerb) {
		nown = pNown;
		verb = pVerb;
		if (StringMethods.isEmpty(nown)) {
			I_Tests4J_AnnotationMessages messages = Tests4J_Constants.CONSTANTS.getAnnotationMessages();
			throw new IllegalArgumentException(messages.getUseCaseScopeEmptyNown());
		}
		if (StringMethods.isEmpty(verb)) {
			I_Tests4J_AnnotationMessages messages = Tests4J_Constants.CONSTANTS.getAnnotationMessages();
			throw new IllegalArgumentException(messages.getUseCaseScopeEmptyVerb());
		}
	}

	public UseCaseBrief(I_UseCaseBrief p) {
		this(p.getNown(), p.getVerb());
	}
	
	public UseCaseBrief(String xml) {
		nown = XML_Parser.getAttributeValue(xml, I_UseCaseBrief.NOWN_ATTRIBUTE);
		verb = XML_Parser.getAttributeValue(xml, I_UseCaseBrief.VERB_ATTRIBUTE);
		setup(nown, verb);
	}
	
	public String getNown() {
		return nown;
	}

	public String getVerb() {
		return verb;
	}
	
	@Override
	public String toString() {
		return "UseCase [nown=" + nown + ", verb=" + verb + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nown == null) ? 0 : nown.hashCode());
		result = prime * result + ((verb == null) ? 0 : verb.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UseCaseBrief other = (UseCaseBrief) obj;
		if (nown == null) {
			if (other.nown != null)
				return false;
		} else if (!nown.equals(other.nown))
			return false;
		if (verb == null) {
			if (other.verb != null)
				return false;
		} else if (!verb.equals(other.verb))
			return false;
		return true;
	}

	@Override
	public void toXml(I_XML_Builder builder) {
		builder.indent();
		builder.addStartTag(I_UseCaseBrief.TAG_NAME);
		builder.addAttribute(I_UseCaseBrief.NOWN_ATTRIBUTE, nown);
		builder.addAttribute(I_UseCaseBrief.VERB_ATTRIBUTE, verb);
		builder.append(" />");
		builder.endLine();
	}
}
