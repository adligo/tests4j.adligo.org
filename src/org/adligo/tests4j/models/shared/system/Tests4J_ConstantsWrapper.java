package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AnnotationErrors;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionResultMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_EclipseErrors;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_LineDiffTextDisplayMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ParamReaderMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;

public class Tests4J_ConstantsWrapper implements I_Tests4J_Constants {
	private I_Tests4J_Constants delegate;
	
	public Tests4J_ConstantsWrapper(I_Tests4J_Constants p) {
		delegate = p;
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
	public String getNullParamsExceptionMessage() {
		return delegate.getNullParamsExceptionMessage();
	}

	@Override
	public String getNullListenerExceptionMessage() {
		return delegate.getNullListenerExceptionMessage();
	}

	@Override
	public I_Tests4J_ParamReaderMessages getParamReaderConstants() {
		return delegate.getParamReaderConstants();
	}
	public I_Tests4J_AnnotationErrors getAnnotationErrors() {
		return delegate.getAnnotationErrors();
	}


	public I_Tests4J_ReportMessages getReportMessages() {
		return delegate.getReportMessages();
	}

	@Override
	public String getBadConstuctor() {
		return delegate.getBadConstuctor();
	}

}
