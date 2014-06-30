package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.asserts.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.i18n.asserts.I_Tests4J_AssertionResultMessages;
import org.adligo.tests4j.models.shared.i18n.eclipse.I_EclipseErrors;
import org.adligo.tests4j.models.shared.i18n.trials.I_Tests4J_TrialDescriptionMessages;

public class Tests4J_ConstantsWrapper implements I_Tests4J_Constants {
	I_Tests4J_Constants delegate;
	
	public Tests4J_ConstantsWrapper(I_Tests4J_Constants p) {
		delegate = p;
	}

	public I_Tests4J_TrialDescriptionMessages getTrialDescriptionMessages() {
		return delegate.getTrialDescriptionMessages();
	}

	public I_EclipseErrors getEclipseErrors() {
		return delegate.getEclipseErrors();
	}

	@Override
	public I_Tests4J_AssertionInputMessages getAssertionInputMessages() {
		return delegate.getAssertionInputMessages();
	}

	@Override
	public I_Tests4J_AssertionResultMessages getAssertionResultMessages() {
		return delegate.getAssertionResultMessages();
	}

	public String getTheMethodCanOnlyBeCalledBy_PartOne() {
		return delegate.getTheMethodCanOnlyBeCalledBy_PartOne();
	}

	public String getTheMethodCanOnlyBeCalledBy_PartTwo() {
		return delegate.getTheMethodCanOnlyBeCalledBy_PartTwo();
	}

	public String getMethodBlockerRequiresAtLeastOneAllowedCallerClassNames() {
		return delegate
				.getMethodBlockerRequiresAtLeastOneAllowedCallerClassNames();
	}
}
