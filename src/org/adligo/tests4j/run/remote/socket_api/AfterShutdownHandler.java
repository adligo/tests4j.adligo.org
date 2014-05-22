package org.adligo.tests4j.run.remote.socket_api;

import org.adligo.tests4j.models.shared.system.DefaultSystemExitor;
import org.adligo.tests4j.models.shared.system.I_SystemExit;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Controls;

public class AfterShutdownHandler implements I_AfterMessageHandler {
	private I_SystemExit exitor;
	private I_Tests4J_Controls controls;
	
	public AfterShutdownHandler(I_SystemExit pExitor) {
		exitor = pExitor;
	}
	
	public AfterShutdownHandler(I_SystemExit pExitor, I_Tests4J_Controls p) {
		exitor = pExitor;
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
		exitor.doSystemExit(0);
	}
}
