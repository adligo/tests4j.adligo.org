package org.adligo.tests4j.models.shared.i18n;

import org.adligo.tests4j.models.shared.i18n.asserts.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.i18n.asserts.I_Tests4J_AssertionResultMessages;
import org.adligo.tests4j.models.shared.i18n.eclipse.I_EclipseErrors;
import org.adligo.tests4j.models.shared.i18n.trials.I_Tests4J_TrialDescriptionMessages;

public interface I_Tests4J_Constants {
	public I_Tests4J_TrialDescriptionMessages getTrialDescriptionMessages();
	public I_Tests4J_AssertionInputMessages getAssertionInputMessages();
	public I_Tests4J_AssertionResultMessages getAssertionResultMessages();
	public I_EclipseErrors getEclipseErrors();
	
	public String getTheMethodCanOnlyBeCalledBy_PartOne();
	public String getTheMethodCanOnlyBeCalledBy_PartTwo();
	public String getMethodBlockerRequiresAtLeastOneAllowedCallerClassNames();
}