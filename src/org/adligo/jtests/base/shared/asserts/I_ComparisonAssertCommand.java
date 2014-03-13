package org.adligo.jtests.base.shared.asserts;

import org.adligo.jtests.base.shared.asserts.line_text.LineTextCompairResult;

public interface I_ComparisonAssertCommand extends I_AssertCommand {

	public abstract String getFailureMessage();

	public abstract AssertCommandType getType();

	public abstract Object getExpected();

	public abstract Object getActual();
	public LineTextCompairResult getLineTextCompairResult();
}