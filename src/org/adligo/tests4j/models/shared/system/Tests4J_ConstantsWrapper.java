package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AnnotationErrors;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionResultMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_EclipseErrors;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_LineDiffTextDisplayMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ParamReaderMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_TrialDescriptionMessages;

public class Tests4J_ConstantsWrapper implements I_Tests4J_Constants {
	private I_Tests4J_Constants delegate;
	
	public Tests4J_ConstantsWrapper(I_Tests4J_Constants p) {
		delegate = p;
	}

	public I_Tests4J_TrialDescriptionMessages getTrialDescriptionMessages() {
		return delegate.getTrialDescriptionMessages();
	}

	public I_Tests4J_EclipseErrors getEclipseErrors() {
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

	@Override
	public I_Tests4J_LineDiffTextDisplayMessages getLineDiffTextDisplayMessages() {
		return delegate.getLineDiffTextDisplayMessages();
	}

	@Override
	public String getTests4J_NullParamsExceptionMessage() {
		return delegate.getTests4J_NullParamsExceptionMessage();
	}

	@Override
	public String getTests4J_NullListenerExceptionMessage() {
		return delegate.getTests4J_NullListenerExceptionMessage();
	}

	@Override
	public I_Tests4J_ParamReaderMessages getTests4j_ParamReaderConstants() {
		return delegate.getTests4j_ParamReaderConstants();
	}
	public I_Tests4J_AnnotationErrors getAnnotationErrors() {
		return delegate.getAnnotationErrors();
	}


	public I_Tests4J_ReportMessages getTests4J_ReportMessages() {
		return delegate.getTests4J_ReportMessages();
	}

}
