package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_EclipseErrors;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionResultMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_LineDiffTextDisplayConstants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ParamReaderConstants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_TrialDescriptionMessages;

public class Tests4J_EnglishConstants implements I_Tests4J_Constants {
	public static final String METHOD_BLOCKER_REQUIRES_AT_LEAST_ONE_ALLOWED_CALLER_CLASS_NAME = "MethodBlocker requires at least one Allowed Caller Class Name.";
	public static final String THE__METHOD_CAN_ONLY_BE_CALLED_BY_PART_ONE = "The Method ";
	public static final String THE__METHOD_CAN_ONLY_BE_CALLED_BY_PART_TWO = " may only be called by ";
	public static final String NULL_LISTENER_NOT_ALLOWED = "Null I_TrialRunListener parameter not allowed.";
	public static final String NULL_PARAMS_ALLOWED = "Null I_Tests4J_Params parameter not allowed.";
	
	
	private Tests4j_TrialDescriptionMessages trialDescriptionMessages =
			new Tests4j_TrialDescriptionMessages();
	private I_Tests4J_EclipseErrors eclipseErrors = null;
	private I_Tests4J_AssertionInputMessages assertionInputMessages =
			new Tests4J_AssertionInputMessages();
	private I_Tests4J_AssertionResultMessages assertionResultMessages =
			new Tests4J_AssertionResultMessages();
	private I_Tests4J_LineDiffTextDisplayConstants lineDiffTextMessages = 
			new Tests4J_LineDiffTextDisplayConstants();
	private I_Tests4J_ParamReaderConstants paramReaderConstants = 
			new Tests4J_ParamsReaderConstants();
	
	
	@Override
	public I_Tests4J_TrialDescriptionMessages getTrialDescriptionMessages() {
		return trialDescriptionMessages;
	}

	@Override
	public I_Tests4J_EclipseErrors getEclipseErrors() {
		if (eclipseErrors == null) {
			eclipseErrors = new Tests4JEclipseErrors();
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

	@Override
	public String getTests4J_NullParamsExceptionMessage() {
		return NULL_PARAMS_ALLOWED;
	}
	
	@Override
	public String getTests4J_NullListenerExceptionMessage() {
		return NULL_LISTENER_NOT_ALLOWED;
	}
	
	@Override
	public I_Tests4J_LineDiffTextDisplayConstants getLineDiffTextDisplayMessages() {
		return lineDiffTextMessages;
	}

	@Override
	public I_Tests4J_ParamReaderConstants getTests4j_ParamReaderConstants() {
		return paramReaderConstants;
	}

}
