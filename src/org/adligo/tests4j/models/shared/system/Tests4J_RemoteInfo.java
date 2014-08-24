package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.common.StringRoutines;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ParamReaderMessages;
import org.adligo.tests4j.models.shared.xml.I_XML_Builder;
import org.adligo.tests4j.models.shared.xml.XML_Parser;

/**
 *  a immutable implementation of I_Tests4J_RemoteInfo,
 *  used to tell test4j where remote tests4j instances are
 *  so they can be called/delegated to.
 *  
 * @author scott
 *
 */
public class Tests4J_RemoteInfo implements I_Tests4J_RemoteInfo {

	
	private String host;
	private int port;
	private String authCode;
	
	protected Tests4J_RemoteInfo() {}
	
	public Tests4J_RemoteInfo(String xml) {
		int [] indexes = XML_Parser.getIndexes(xml, I_Tests4J_RemoteInfo.TAG_NAME);
		if (indexes == null) {
			throw XML_Parser.getReadError(I_Tests4J_RemoteInfo.TAG_NAME);
		}
		String thisTag = xml.substring(indexes[0], indexes[1]);
		host = XML_Parser.getAttributeValue(thisTag, I_Tests4J_RemoteInfo.HOST_ATTRIBUTE);
		port = XML_Parser.getAttributeIntegerValue(thisTag, I_Tests4J_RemoteInfo.PORT_ATTRIBUTE);
		authCode = XML_Parser.getAttributeValue(thisTag, I_Tests4J_RemoteInfo.AUTH_ATTRIBUTE);
	}
	
	public Tests4J_RemoteInfo(I_Tests4J_RemoteInfo other) {
		this(other.getHost(), other.getPort(), other.getAuthCode());
	}
	
	/**
	 * Validates that there is something in the authCode field, by 
	 * the end of the constructor.  This is done to make Tests4J_ParamsReader
	 * simpler, by delegating logic.
	 * 
	 * @param other
	 * @param defaultAuthCode
	 */
	public Tests4J_RemoteInfo(I_Tests4J_RemoteInfo other, String defaultAuthCode) {
		this(other);
		if (authCode == null) {
			authCode = defaultAuthCode;
		}
		if (StringRoutines.isEmpty(authCode)) {
			I_Tests4J_ParamReaderMessages constants = Tests4J_Constants.CONSTANTS.getParamReaderMessages();
			throw new IllegalArgumentException(constants.getAuthCodeOrAuthCodeDefaultRequired());
		}
	}
	
	public Tests4J_RemoteInfo(String pHost, int pPort, String pAuthCode) {
		if (StringRoutines.isEmpty(pHost)) {
			I_Tests4J_ParamReaderMessages constants = Tests4J_Constants.CONSTANTS.getParamReaderMessages();
			throw new IllegalArgumentException(constants.getHostRequired());
		}
		host = pHost;
		port = pPort;
		//auth code can be empty or null, until the Tests4J_Params reader,
		//at which point it must either come from the I_Tests4J_Params instance or 
		// this
		authCode = pAuthCode;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.system.I_Tests4J_RemoteInfo#getHost()
	 */
	@Override
	public String getHost() {
		return host;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.system.I_Tests4J_RemoteInfo#getPort()
	 */
	@Override
	public int getPort() {
		return port;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.system.I_Tests4J_RemoteInfo#getAuthCode()
	 */
	@Override
	public String getAuthCode() {
		return authCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authCode == null) ? 0 : authCode.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + port;
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
		Tests4J_RemoteInfo other = (Tests4J_RemoteInfo) obj;
		if (authCode == null) {
			if (other.authCode != null)
				return false;
		} else if (!authCode.equals(other.authCode))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

	@Override
	public void toXml(I_XML_Builder builder) {
		builder.indent();
		int attribsPerLine = builder.getAttributesPerLine();
		builder.setAttributesPerLine(4);
		builder.addStartTag(TAG_NAME);
		builder.addAttribute(HOST_ATTRIBUTE, host);
		builder.addAttribute(PORT_ATTRIBUTE, "" + port);
		if ( !StringRoutines.isEmpty(authCode)) {
			builder.addAttribute(AUTH_ATTRIBUTE, authCode);
		}
		builder.setAttributesPerLine(attribsPerLine);
		builder.append(" />");
		builder.endLine();
	}
	
}
