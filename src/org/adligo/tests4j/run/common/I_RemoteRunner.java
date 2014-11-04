package org.adligo.tests4j.run.common;

import org.adligo.tests4j.run.remote.RemoteRunnerStateEnum;

public interface I_RemoteRunner {
	public void shutdown();
	
	public RemoteRunnerStateEnum getState();
}
