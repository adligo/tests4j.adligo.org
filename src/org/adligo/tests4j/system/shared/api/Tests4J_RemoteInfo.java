package org.adligo.tests4j.system.shared.api;

import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ParamsReaderMessages;
import org.adligo.tests4j.shared.xml.I_XML_Builder;
import org.adligo.tests4j.shared.xml.XML_Parser;

/**
 *  a immutable implementation of I_Tests4J_RemoteInfo,
 *  used to tell test4j where remote tests4j instances are
 *  so they can be called/delegated to.
 *  
 * @author scott
 *
 */
public class Tests4J_RemoteInfo implements I_Tests4J_RemoteInfo {

	private final I_Tests4J_ParamsReaderMessages messages_;
	private String host;
	private int port;
	private String authCode;
	
	protected Tests4J_RemoteInfo(I_Tests4J_Constants constants) {
	  messages_ = constants.getParamReaderMessages();
	}
	
	
	public Tests4J_RemoteInfo(I_Tests4J_Constants constants, I_Tests4J_RemoteInfo other) {
		this(constants, other.getHost(), other.getPort(), other.getAuthCode());
	}
	
	/**
	 * Validates that there is something in the authCode field, by 
	 * the end of the constructor.  This is done to make Tests4J_ParamsReader
	 * simpler, by delegating logic.
	 * 
	 * @param other
	 * @param defaultAuthCode
	 */
	public Tests4J_RemoteInfo(I_Tests4J_Constants constants, I_Tests4J_RemoteInfo other, String defaultAuthCode) {
		this(constants, other);
		if (authCode == null) {
			authCode = defaultAuthCode;
		}
		if (StringMethods.isEmpty(authCode)) {
			throw new IllegalArgumentException(messages_.getAuthCodeOrAuthCodeDefaultRequired());
		}
	}
	
	public Tests4J_RemoteInfo(I_Tests4J_Constants constants, String pHost, int pPort, String pAuthCode) {
	  messages_ = constants.getParamReaderMessages();
		if (StringMethods.isEmpty(pHost)) {
			throw new IllegalArgumentException(messages_.getHostRequired());
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
		if ( !StringMethods.isEmpty(authCode)) {
			builder.addAttribute(AUTH_ATTRIBUTE, authCode);
		}
		builder.setAttributesPerLine(attribsPerLine);
		builder.append(" />");
		builder.endLine();
	}
	
}
