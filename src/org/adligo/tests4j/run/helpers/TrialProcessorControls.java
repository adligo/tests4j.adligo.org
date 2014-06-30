package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.system.I_Tests4J_Controls;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Reporter;

public class TrialProcessorControls implements I_Tests4J_Controls {
	private Tests4J_Manager threadManager;
	private I_Tests4J_Reporter reporter;
	private Tests4J_NotificationManager notificationManager;
	
	
	public TrialProcessorControls(I_Tests4J_Reporter pReporter,
			Tests4J_Manager pThreadManager,
			Tests4J_NotificationManager pNotificationManager) {
		
		reporter = pReporter;
		threadManager = pThreadManager;
		notificationManager = pNotificationManager;
	}
	@Override
	public void shutdown() {
		threadManager.shutdown();
	}


	public I_Tests4J_Reporter getReporter() {
		return reporter;
	}


	public void setReporter() {
		this.reporter = reporter;
	}


	public Tests4J_Manager getThreadManager() {
		return threadManager;
	}




	public Tests4J_NotificationManager getNotificationManager() {
		return notificationManager;
	}
	@Override
	public boolean isRunning() {
		return notificationManager.isRunning();
	}

	
}
