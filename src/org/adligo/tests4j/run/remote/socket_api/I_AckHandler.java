package org.adligo.tests4j.run.remote.socket_api;

public interface I_AckHandler {
	/**
	 * on acknowledgement of the previous message
	 */
	public void onAck();
}
