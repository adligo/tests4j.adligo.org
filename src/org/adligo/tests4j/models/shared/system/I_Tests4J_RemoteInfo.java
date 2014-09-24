package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.shared.xml.I_XML_Consumer;
import org.adligo.tests4j.shared.xml.I_XML_Producer;

/**
 * a interface for connecting to a remote jvm running a tests4j instance,
 * on a local or remote computer.
 * 
 * Note this interface does need to turn into xml and from xml
 * back, so that remote remote delegation can occur ie;
 * RemoteInfo can move from A to B to get B to call C and so on (D, E, F exc).
 * 
 * Machine A            Machine B           Machine C
 *    Tests4J ->socket->  Tests4J ->socket-> Tests4J
 *    
 * @author scott
 *
 */
public interface I_Tests4J_RemoteInfo extends I_XML_Producer {
	public static final String TAG_NAME = "remoteInfo";
	public static final String HOST_ATTRIBUTE = "host";
	public static final String PORT_ATTRIBUTE = "port";
	public static final String AUTH_ATTRIBUTE = "authCode";
	
	public abstract String getHost();

	public abstract int getPort();

	public abstract String getAuthCode();

}