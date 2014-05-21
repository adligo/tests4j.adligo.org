package org.adligo.tests4j.models.shared.system.i18n.en;

import org.adligo.tests4j.models.shared.system.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.i18n.eclipse.I_EclipseErrors;
import org.adligo.tests4j.models.shared.system.i18n.en.eclilpse.EclipseErrors;
import org.adligo.tests4j.models.shared.system.i18n.en.trials.Tests4j_TrialDescriptionMessages;
import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4J_TrialDescriptionMessages;

public class Tests4J_EnglishConstants implements I_Tests4J_Constants {
	private Tests4j_TrialDescriptionMessages trialDescriptionMessages =
			new Tests4j_TrialDescriptionMessages();
	private I_EclipseErrors eclipseErrors = null;
	
	@Override
	public I_Tests4J_TrialDescriptionMessages getTrialDescriptionMessages() {
		return trialDescriptionMessages;
	}

	@Override
	public I_EclipseErrors getEclipseErrors() {
		if (eclipseErrors == null) {
			eclipseErrors = new EclipseErrors();
		}
		return eclipseErrors;
	}

}
