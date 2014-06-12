package org.adligo.tests4j.models.shared.system;

public class Tests4J_RemoteInfo implements I_Tests4J_RemoteInfo {
	private String host;
	private int port;
	private String authCode;
	
	public Tests4J_RemoteInfo(String pHost, int pPort, String pAuthCode) {
		host = pHost;
		port = pPort;
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
	
}
