package org.adligo.tests4j.models.shared.en.eclipse;

import org.adligo.tests4j.models.shared.i18n.eclipse.I_EclipseErrors;

public class EclipseErrors implements I_EclipseErrors {

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
