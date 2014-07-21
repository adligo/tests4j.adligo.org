package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.system.I_Tests4J_Controls;

public class Tests4J_Controls implements I_Tests4J_Controls {
	private Tests4J_Manager threadManager;
	private I_Tests4J_NotificationManager notificationManager;
	
	public Tests4J_Controls() {
	}
	
	public Tests4J_Controls(Tests4J_Manager pThreadManager,
			I_Tests4J_NotificationManager pNotificationManager) {
		
		threadManager = pThreadManager;
		notificationManager = pNotificationManager;
	}
	@Override
	public void shutdown() {
		if (threadManager != null) {
			threadManager.shutdown();
		}
	}

	public Tests4J_Manager getThreadManager() {
		return threadManager;
	}


	@Override
	public boolean isRunning() {
		if (threadManager == null) {
			return false;
		}
		return notificationManager.isRunning();
	}

	
}
