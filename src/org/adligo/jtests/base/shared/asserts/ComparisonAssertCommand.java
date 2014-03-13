package org.adligo.jtests.base.shared.asserts;

import org.adligo.jtests.base.shared.asserts.line_text.LineTextCompairResult;

public class ComparisonAssertCommand implements I_ComparisonAssertCommand {
	private ComparisonAssertCommandMutant mutant;
	
	public ComparisonAssertCommand(I_ComparisonAssertCommand p) {
		mutant = new ComparisonAssertCommandMutant(p);
	}

	public String getFailureMessage() {
		return mutant.getFailureMessage();
	}

	public AssertCommandType getType() {
		return mutant.getType();
	}

	public Object getExpected() {
		return mutant.getExpected();
	}

	public Object getActual() {
		return mutant.getActual();
	}

	public boolean evaluate() {
		return mutant.evaluate();
	}

	public I_AssertData getData() {
		return mutant.getData();
	}

	public LineTextCompairResult getLineTextCompairResult() {
		return mutant.getLineTextCompairResult();
	}

	public int hashCode() {
		return mutant.hashCode();
	}

	public boolean equals(Object obj) {
		return mutant.equals(obj);
	}
}
