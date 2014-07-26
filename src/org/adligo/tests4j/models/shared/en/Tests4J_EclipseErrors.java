package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_EclipseErrors;

public class Tests4J_EclipseErrors implements I_Tests4J_EclipseErrors {

	private static final String ERROR_RUNNING_REMOTE_TESTS4J = "Known issues with running Tests4J_RemoterRunner include;\n" +
			"1) Multiple project selection breaks classpath of launcher @see MultiProjectSelectionLaunchHelper.";
	private static final String ERROR_RUNNING_TEST4J_REMOTER_RUNNER = "Error running Test4J_RemoterRunner!";

	@Override
	public String getErrorRunningTests4J_RemoteRunner_Title() {
		return ERROR_RUNNING_TEST4J_REMOTER_RUNNER;
	}

	@Override
	public String getErrorRunningTests4J_RemoteRunner_Message() {
		return ERROR_RUNNING_REMOTE_TESTS4J;
	}

	Tests4J_EclipseErrors() {}
}
