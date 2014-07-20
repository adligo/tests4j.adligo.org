package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_EclipseErrors;

public class Tests4JEclipseErrors implements I_Tests4J_EclipseErrors {

	@Override
	public String getErrorRunningTests4J_RemoteRunner_Title() {
		return "Error Running Test4J_RemoterRunner!";
	}

	@Override
	public String getErrorRunningTests4J_RemoteRunner_Message() {
		return "Known issues with running Tests4J_RemoterRunner Include;\n" +
				"1) Multiple Project Selection breaks Classpath of Launcher @see MultiProjectSelectionLaunchHelper.";
	}

}
