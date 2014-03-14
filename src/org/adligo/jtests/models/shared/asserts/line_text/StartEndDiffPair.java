package org.adligo.jtests.models.shared.asserts.line_text;

public class StartEndDiffPair {
	private StartEndDiff example;
	private StartEndDiff actual;
	
	public StartEndDiffPair(StartEndDiff pExample, StartEndDiff pActual) {
		example = pExample;
		actual = pActual;
	}

	public StartEndDiff getExample() {
		return example;
	}

	public StartEndDiff getActual() {
		return actual;
	}
}
