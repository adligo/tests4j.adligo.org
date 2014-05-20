package org.adligo.tests4j.run.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adligo.tests4j.models.shared.system.I_Tests4J_Controls;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;

public class TrialProcessorControls implements I_Tests4J_Controls {
	private Tests4J_ThreadManager threadManager;
	private I_Tests4J_Reporter reporter;
	private Tests4J_NotificationManager notificationManager;
	
	
	public TrialProcessorControls(I_Tests4J_Reporter pReporter,
			Tests4J_ThreadManager pThreadManager,
			Tests4J_NotificationManager pNotificationManager) {
		
		reporter = pReporter;
		threadManager = pThreadManager;
		notificationManager = pNotificationManager;
	}
	@Override
	public void cancel() {
		threadManager.shutdown();
	}


	public I_Tests4J_Reporter getReporter() {
		return reporter;
	}


	public void setReporter() {
		this.reporter = reporter;
	}


	public Tests4J_ThreadManager getThreadManager() {
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
