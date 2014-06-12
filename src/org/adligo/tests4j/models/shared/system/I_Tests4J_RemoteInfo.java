package org.adligo.tests4j.models.shared.system;

/**
 * a interface for connecting to a remote tests4j server
 * @see Tests4J_RemoteInfo
 * 
 * @author scott
 *
 */
public interface I_Tests4J_RemoteInfo {

	public abstract String getHost();

	public abstract int getPort();

	public abstract String getAuthCode();

}