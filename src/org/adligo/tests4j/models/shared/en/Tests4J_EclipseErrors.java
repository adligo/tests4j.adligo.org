package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_EclipseErrors;

public class Tests4J_EclipseErrors implements I_Tests4J_EclipseErrors {

	private static final String ERROR_RUNNING_REMOTE_TESTS4J = "Known issues with running Tests4J_RemoterRunner Include;\n" +
			"1) Multiple Project Selection breaks Classpath of Launcher @see MultiProjectSelectionLaunchHelper.";
	private static final String ERROR_RUNNING_TEST4J_REMOTER_RUNNER = "Error Running Test4J_RemoterRunner!";

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
