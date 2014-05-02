package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.system.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4J_TrialDescriptionMessages;

public class Tests4J_ConstantsWrapper implements I_Tests4J_Constants {
	I_Tests4J_Constants delegate;
	
	public Tests4J_ConstantsWrapper(I_Tests4J_Constants p) {
		delegate = p;
	}

	public I_Tests4J_TrialDescriptionMessages getTrialDescriptionMessages() {
		return delegate.getTrialDescriptionMessages();
	}
}
