package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AnnotationErrors;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionResultMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_EclipseErrors;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_LineDiffTextDisplayMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ParamReaderMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;

/**
 * note this does have a public constructor
 * all other classes in this pachage should only have a package
 * constructor to make sure the i18n framework is being used.
 * 
 * @author scott
 *
 */
public class Tests4J_EnglishConstants implements I_Tests4J_Constants {
	/**
	 * for use in test code and Tests4J_ConstantsWrapper default.
	 */
	public static final Tests4J_EnglishConstants ENGLISH = new Tests4J_EnglishConstants();
	
	private static final String METHOD_BLOCKER_REQUIRES_AT_LEAST_ONE_ALLOWED_CALLER_CLASS_NAME = "MethodBlocker requires at least one Allowed Caller Class Name.";
	private static final String THE__METHOD_CAN_ONLY_BE_CALLED_BY_PART_ONE = "The Method ";
	private static final String THE__METHOD_CAN_ONLY_BE_CALLED_BY_PART_TWO = " may only be called by ";
	private static final String NULL_LISTENER_NOT_ALLOWED = "Null I_TrialRunListener parameter not allowed.";
	private static final String NULL_PARAMS_ALLOWED = "Null I_Tests4J_Params parameter not allowed.";
	private static final String CLASSES_WHICH_IMPLEMENT_I_ABSTRACT_TRIAL_MUST_HAVE_A_ZERO_ARGUMENT_CONSTRUCTOR = 
			"Classes which implement I_AbstractTrial must have a zero argument constructor.";
	
	
	private I_Tests4J_EclipseErrors eclipseErrors = null;
	private I_Tests4J_AssertionInputMessages assertionInputMessages =
			new Tests4J_AssertionInputMessages();
	private I_Tests4J_AssertionResultMessages assertionResultMessages =
			new Tests4J_AssertionResultMessages();
	private I_Tests4J_LineDiffTextDisplayMessages lineDiffTextMessages = 
			new Tests4J_LineDiffTextDisplayMessages();
	private I_Tests4J_ParamReaderMessages paramReaderConstants = 
			new Tests4J_ParamsReaderMessages();
	private Tests4J_AnnotationErrors annotationErrors = new Tests4J_AnnotationErrors();
	private Tests4J_ReportMessages reportMessages = new Tests4J_ReportMessages();
	
	private Tests4J_EnglishConstants() {}
	
	@Override
	public I_Tests4J_AnnotationErrors getAnnotationErrors() {
		return annotationErrors;
	}


	@Override
	public String getBadConstuctor() {
		return CLASSES_WHICH_IMPLEMENT_I_ABSTRACT_TRIAL_MUST_HAVE_A_ZERO_ARGUMENT_CONSTRUCTOR;
	}
	
	@Override
	public I_Tests4J_EclipseErrors getEclipseErrors() {
		if (eclipseErrors == null) {
			eclipseErrors = new Tests4J_EclipseErrors();
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
	public String getNullParamsExceptionMessage() {
		return NULL_PARAMS_ALLOWED;
	}
	
	@Override
	public String getNullListenerExceptionMessage() {
		return NULL_LISTENER_NOT_ALLOWED;
	}
	
	@Override
	public I_Tests4J_LineDiffTextDisplayMessages getLineDiffTextDisplayMessages() {
		return lineDiffTextMessages;
	}

	@Override
	public I_Tests4J_ParamReaderMessages getParamReaderConstants() {
		return paramReaderConstants;
	}

	@Override
	public I_Tests4J_ReportMessages getReportMessages() {
		return reportMessages;
	}

}
