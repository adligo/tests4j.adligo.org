package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.line_text.LineTextCompareResult;

public interface I_ComparisonAssertCommand extends I_AssertCommand {

	public abstract String getFailureMessage();

	public abstract AssertType getType();

	public abstract Object getExpected();

	public abstract Object getActual();
	public LineTextCompareResult getLineTextCompairResult();
}