package org.adligo.tests4j.run.remote.socket_api;

import org.adligo.tests4j.shared.common.I_System;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Controls;

public class AfterShutdownHandler implements I_AfterMessageHandler {
	private I_System system;
	private I_Tests4J_Controls controls;
	
	public AfterShutdownHandler(I_System pExitor) {
		system = pExitor;
	}
	
	public AfterShutdownHandler(I_System pExitor, I_Tests4J_Controls p) {
		system = pExitor;
		controls = p;
	}
	
	@Override
	public void afterMessageTransported() {
		if (controls != null) {
			try {
				controls.shutdown();
			} catch (Exception x) {
				x.printStackTrace();
			}
		}
		system.exitJvm(0);
	}
}
