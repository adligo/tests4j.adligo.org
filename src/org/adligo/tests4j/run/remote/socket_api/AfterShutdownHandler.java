package org.adligo.tests4j.run.remote.socket_api;

import org.adligo.tests4j.models.shared.system.I_Tests4J_Controls;

public class AfterShutdownHandler implements I_AfterMessageHandler {
	private I_Tests4J_Controls controls;
	
	public AfterShutdownHandler() {}
	
	public AfterShutdownHandler(I_Tests4J_Controls p) {
		controls = p;
	}
	@Override
	public void afterMessageTransported() {
		if (controls != null) {
			try {
				controls.cancel();
			} catch (Exception x) {
				x.printStackTrace();
			}
		}
		System.exit(0);
	}

}
