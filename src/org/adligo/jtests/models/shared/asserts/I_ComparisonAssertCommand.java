package org.adligo.jtests.models.shared.asserts;

import org.adligo.jtests.models.shared.asserts.line_text.LineTextCompairResult;

public interface I_ComparisonAssertCommand extends I_AssertCommand {

	public abstract String getFailureMessage();

	public abstract AssertType getType();

	public abstract Object getExpected();

	public abstract Object getActual();
	public LineTextCompairResult getLineTextCompairResult();
}