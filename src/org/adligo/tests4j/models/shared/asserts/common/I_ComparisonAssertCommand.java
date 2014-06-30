package org.adligo.tests4j.models.shared.asserts.common;

import org.adligo.tests4j.models.shared.asserts.line_text.I_LineTextCompareResult;

public interface I_ComparisonAssertCommand extends I_AssertCommand {

	public abstract String getFailureMessage();

	public abstract AssertType getType();

	public abstract Object getExpected();

	public abstract Object getActual();
	public I_LineTextCompareResult getLineTextCompairResult();
}