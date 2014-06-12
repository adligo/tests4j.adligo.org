package org.adligo.tests4j.run.remote.socket_api;


public enum Tests4J_Commands {
	CONNECT,
	ACK,
	RUN,
	METADATA,
	TEST_FINISHED,
	TRIAL_FINISHED,
	RUN_FINISHED,
	SHUTDOWN;
	
	public static int getMinLength() {
		int toRet = 100;
		Tests4J_Commands [] values = Tests4J_Commands.values();
		for (int i = 0; i < values.length; i++) {
			int len = values[i].toString().length();
			if (len < toRet) {
				toRet = len;
			}
		}
		return toRet;
	}
}
