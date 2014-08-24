package org.adligo.tests4j.models.shared.metadata;

import org.adligo.tests4j.models.shared.common.StringRoutines;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AnnotationErrors;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.xml.I_XML_Builder;
import org.adligo.tests4j.models.shared.xml.XML_Parser;


/**
 * @see I_UseCaseMetadata
 * @author scott
 *
 */
public class UseCaseMetadata implements I_UseCaseMetadata {
	private String nown;
	private String verb;
	
	public UseCaseMetadata(String pNown, String pVerb) {
		setup(pNown, pVerb);
	}

	protected void setup(String pNown, String pVerb) {
		nown = pNown;
		verb = pVerb;
		if (StringRoutines.isEmpty(nown)) {
			I_Tests4J_AnnotationErrors messages = Tests4J_Constants.CONSTANTS.getAnnotationErrors();
			throw new IllegalArgumentException(messages.getUseCaseScopeEmptyNown());
		}
		if (StringRoutines.isEmpty(verb)) {
			I_Tests4J_AnnotationErrors messages = Tests4J_Constants.CONSTANTS.getAnnotationErrors();
			throw new IllegalArgumentException(messages.getUseCaseScopeEmptyVerb());
		}
	}

	public UseCaseMetadata(I_UseCaseMetadata p) {
		this(p.getNown(), p.getVerb());
	}
	
	public UseCaseMetadata(String xml) {
		nown = XML_Parser.getAttributeValue(xml, I_UseCaseMetadata.NOWN_ATTRIBUTE);
		verb = XML_Parser.getAttributeValue(xml, I_UseCaseMetadata.VERB_ATTRIBUTE);
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
		UseCaseMetadata other = (UseCaseMetadata) obj;
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
		builder.addStartTag(I_UseCaseMetadata.TAG_NAME);
		builder.addAttribute(I_UseCaseMetadata.NOWN_ATTRIBUTE, nown);
		builder.addAttribute(I_UseCaseMetadata.VERB_ATTRIBUTE, verb);
		builder.append(" />");
		builder.endLine();
	}
}
