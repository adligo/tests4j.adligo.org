package org.adligo.tests4j.shared.common;

import org.adligo.tests4j.shared.i18n.I_Tests4J_AnnotationErrors;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_EclipseErrors;
import org.adligo.tests4j.shared.i18n.I_Tests4J_LineDiffTextDisplayMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ParamsReaderMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ResultMessages;

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
	public I_Tests4J_ResultMessages getResultMessages() {
		return delegate.getResultMessages();
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
	public I_Tests4J_ParamsReaderMessages getParamReaderMessages() {
		return delegate.getParamReaderMessages();
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

	@Override
	public String getMethodBlockerRequiresABlockingClass() {
		return delegate.getMethodBlockerRequiresABlockingClass();
	}

	@Override
	public String getMethodBlockerRequiresABlockingMethod() {
		return delegate.getMethodBlockerRequiresABlockingMethod();
	}

}
