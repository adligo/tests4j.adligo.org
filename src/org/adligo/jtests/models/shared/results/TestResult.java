package org.adligo.jtests.models.shared.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adligo.jtests.models.shared.common.TestType;

public class TestResult {
	private TestResultMutant mutant;
	
	public TestResult(I_TestResult p) {
		mutant = new TestResultMutant(p);
	}
	
	@Override
	public String toString() {
		return mutant.toString(TestResult.class);
	}

	public String getTestName() {
		return mutant.getTestName();
	}

	public String getTestedClassName() {
		return mutant.getTestedClassName();
	}

	public String getTestedPackageName() {
		return mutant.getTestedPackageName();
	}

	public String getTestedUseCaseName() {
		return mutant.getTestedUseCaseName();
	}

	public TestType getTestType() {
		return mutant.getTestType();
	}

	public List<I_ExhibitResult> getExhibitResults() {
		List<I_ExhibitResult> toRet = new ArrayList<I_ExhibitResult>();
		for (ExhibitResultMutant mut: mutant.getExhibitResults()) {
			toRet.add(new ExhibitResult(mut));
		}
		return Collections.unmodifiableList(toRet);
	}

	public boolean isIgnored() {
		return mutant.isIgnored();
	}

	public String getBeforeTestOutput() {
		return mutant.getBeforeTestOutput();
	}

	public String getAfterTestOutput() {
		return mutant.getAfterTestOutput();
	}
	
}
