package org.adligo.tests4j.models.shared.system.i18n.en;

import org.adligo.tests4j.models.shared.system.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.i18n.eclipse.I_EclipseErrors;
import org.adligo.tests4j.models.shared.system.i18n.en.eclilpse.EclipseErrors;
import org.adligo.tests4j.models.shared.system.i18n.en.trials.Tests4j_TrialDescriptionMessages;
import org.adligo.tests4j.models.shared.system.i18n.en.trials.asserts.Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.system.i18n.en.trials.asserts.Tests4J_AssertionResultMessages;
import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4J_TrialDescriptionMessages;
import org.adligo.tests4j.models.shared.system.i18n.trials.asserts.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.system.i18n.trials.asserts.I_Tests4J_AssertionResultMessages;

public class Tests4J_EnglishConstants implements I_Tests4J_Constants {
	public static final String METHOD_BLOCKER_REQUIRES_AT_LEAST_ONE_ALLOWED_CALLER_CLASS_NAME = "MethodBlocker requires at least one Allowed Caller Class Name.";
	public static final String THE__METHOD_CAN_ONLY_BE_CALLED_BY_PART_ONE = "The Method ";
	public static final String THE__METHOD_CAN_ONLY_BE_CALLED_BY_PART_TWO = " may only be called by ";
	private Tests4j_TrialDescriptionMessages trialDescriptionMessages =
			new Tests4j_TrialDescriptionMessages();
	private I_EclipseErrors eclipseErrors = null;
	private I_Tests4J_AssertionInputMessages assertionInputMessages =
			new Tests4J_AssertionInputMessages();
	private I_Tests4J_AssertionResultMessages assertionResultMessages =
			new Tests4J_AssertionResultMessages();
	
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

	@Override
	public I_Tests4J_AssertionInputMessages getAssertionInputMessages() {
		return assertionInputMessages;
	}

	@Override
	public I_Tests4J_AssertionResultMessages getAssertionResultMessages() {
		return assertionResultMessages;
	}

	@Override
	public String getTheMethodCanOnlyBeCalledBy_PartOne() {
		return THE__METHOD_CAN_ONLY_BE_CALLED_BY_PART_ONE;
	}

	@Override
	public String getTheMethodCanOnlyBeCalledBy_PartTwo() {
		// TODO Auto-generated method stub
		return THE__METHOD_CAN_ONLY_BE_CALLED_BY_PART_TWO;
	}

	@Override
	public String getMethodBlockerRequiresAtLeastOneAllowedCallerClassNames() {
		return METHOD_BLOCKER_REQUIRES_AT_LEAST_ONE_ALLOWED_CALLER_CLASS_NAME;
	}

}
