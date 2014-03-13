package org.adligo.jtests.models.shared.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adligo.jtests.models.shared.common.TestType;
import org.adligo.jtests.models.shared.coverage.I_ClassCoverage;
import org.adligo.jtests.models.shared.coverage.I_PackageCoverage;

public class TestResult implements I_TestResult {
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

	public TestType getTestType() {
		return mutant.getTestType();
	}

	public List<I_ExhibitResult> getExhibitResults() {
		List<I_ExhibitResult> toRet = new ArrayList<I_ExhibitResult>();
		for (I_ExhibitResult mut: mutant.getExhibitResults()) {
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

	public I_TestFailure getFailure() {
		return mutant.getFailure();
	}

	public I_PackageCoverage getPackageCoverage() {
		return mutant.getPackageCoverage();
	}

	public I_ClassCoverage getClassCoverage() {
		return mutant.getClassCoverage();
	}

	public boolean isPassed() {
		return mutant.isPassed();
	}

	@Override
	public int getExhibitCount() {
		return mutant.getExhibitCount();
	}

	@Override
	public int getAssertionCount() {
		return mutant.getAssertionCount();
	}
	
}
