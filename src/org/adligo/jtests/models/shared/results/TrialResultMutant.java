package org.adligo.jtests.models.shared.results;

import java.util.ArrayList;
import java.util.List;

import org.adligo.jtests.models.shared.common.IsEmpty;
import org.adligo.jtests.models.shared.common.TrialType;
import org.adligo.jtests.models.shared.coverage.I_ClassCoverage;
import org.adligo.jtests.models.shared.coverage.I_PackageCoverage;

public class TrialResultMutant implements I_TrialResult {
	public static final String COVERAGE_IS_NOT_CURRENTLY_SUPPORTED_BY_J_TESTS = "Coverage is not currently supported by JTests";
	public static final String TRIAL_RESULT_MUTANT_S_WITH_A_CLASS_TEST_TYPE_REQUIRE_A_TESTED_CLASS_NAME = 
				"TrialResultMutant's with a ClassTest type require a testedClassName";
	public static final String TRIAL_RESULT_MUTANT_S_WITH_A_CLASS_TEST_TYPE_REQUIRE_A_TESTED_PACKAGE_NAME = 
				"TrialResultMutant's with a ClassTest type require a testedUseCaseName";
	public static final String TRIAL_RESULT_MUTANT_S_WITH_A_CLASS_TEST_TYPE_REQUIRE_A_TESTED_USE_CASE_NAME = 
				"TrialResultMutant's with a ClassTest type require a testedPackageName";
	
	public static final String TRIAL_RESULT_MUTANT_REQUIRES_A_NON_NULL_TYPE = 
			"TrialResultMutant requires a non null type.";
	public static final String TRIAL_RESULT_MUTANT_REQUIRES_A_NON_EMPTY_TEST_NAME = 
			"TrialResultMutant requires a non empty testName.";
	private String testName;
	private String testedClassName;
	private String testedPackageName;
	
	private TrialType testType;
	private List<TestResultMutant> results = 
			new ArrayList<TestResultMutant> ();
	private boolean ignored;
	//skip packageCoverage because there is no PackageCoverageMutant yet
	//skip classCoverage because there is no ClassCoverageMutant yet
	private String beforeTestOutput;
	private String afterTestOutput;
	private TrialFailure failure;
	private Boolean passed = null;
	
	public TrialResultMutant() {}
	
	public TrialResultMutant(I_TrialResult p) {
		testName = p.getName();
		IsEmpty.isEmpty(testName,
				TRIAL_RESULT_MUTANT_REQUIRES_A_NON_EMPTY_TEST_NAME);
		testType = p.getType();
		if (testType == null) {
			throw new IllegalArgumentException(
				TRIAL_RESULT_MUTANT_REQUIRES_A_NON_NULL_TYPE);
		}
		switch(testType) {
			case ClassTrial:
				testedClassName = p.getTestedClassName();
				break;
			case PackageTrial:
				testedPackageName = p.getTestedPackageName();
				break;
			default:
		}
		
		List<I_TestResult> results = p.getResults();
		setResults(results);
		
		ignored = p.isIgnored();
		beforeTestOutput = p.getBeforeTestOutput();
		afterTestOutput = p.getAfterTestOutput();
		I_TrialFailure pFailure = p.getFailure();
		if (pFailure != null) {
			failure = new TrialFailure(pFailure);
		}
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getTestName()
	 */
	@Override
	public String getName() {
		return testName;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getTestedClassName()
	 */
	@Override
	public String getTestedClassName() {
		return testedClassName;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getTestedPackageName()
	 */
	@Override
	public String getTestedPackageName() {
		return testedPackageName;
	}

	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TrialResult#getTrialType()
	 */
	@Override
	public TrialType getType() {
		return testType;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TrialResult#getResults()
	 */
	@Override
	public List<I_TestResult> getResults() {
		List<I_TestResult> toRet = new ArrayList<I_TestResult>();
		toRet.addAll(results);
		return toRet;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#isIgnored()
	 */
	@Override
	public boolean isIgnored() {
		return ignored;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getBeforeTestOutput()
	 */
	@Override
	public String getBeforeTestOutput() {
		return beforeTestOutput;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getAfterTestOutput()
	 */
	@Override
	public String getAfterTestOutput() {
		return afterTestOutput;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public void setTestedClassName(String testedClassName) {
		this.testedClassName = testedClassName;
	}
	public void setTestedPackageName(String testedPackageName) {
		this.testedPackageName = testedPackageName;
	}
	public void setType(TrialType p) {
		this.testType = p;
	}
	public void setResults(List<I_TestResult> p) {
		results.clear();
		for (I_TestResult result: p) {
			results.add(new TestResultMutant(result));
		}
	}
	public void addResult(I_TestResult p) {
		results.add(new TestResultMutant(p));
	}
	public void setIgnored(boolean ignored) {
		this.ignored = ignored;
	}
	public void setBeforeTestOutput(String beforeTestOutput) {
		this.beforeTestOutput = beforeTestOutput;
	}
	public void setAfterTestOutput(String afterTestOutput) {
		this.afterTestOutput = afterTestOutput;
	}

	@Override
	public String toString() {
		return toString(TrialResultMutant.class);
	}
	
	String toString(Class<?> c) {
		return c.getSimpleName() + " [testName=" + testName + ", testedClassName="
				+ testedClassName + ", testedPackageName=" + testedPackageName
				+ ", testType="
				+ testType + ", exhibitResults=" + results
				+ ", ignored=" + ignored + ", beforeTestOutput="
				+ beforeTestOutput + ", afterTestOutput=" + afterTestOutput
				+ "]";
	}

	public I_TrialFailure getFailure() {
		return failure;
	}

	public void setFailure(TrialFailure failure) {
		this.failure = failure;
	}

	@Override
	public I_PackageCoverage getPackageCoverage() {
		throw new IllegalStateException(COVERAGE_IS_NOT_CURRENTLY_SUPPORTED_BY_J_TESTS);
	}

	@Override
	public I_ClassCoverage getClassCoverage() {
		throw new IllegalStateException(COVERAGE_IS_NOT_CURRENTLY_SUPPORTED_BY_J_TESTS);
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	@Override
	public boolean isPassed() {
		//passed my be null
		if (Boolean.FALSE.equals(passed)) {
			return false;
		}
		if (results.size() == 0) {
			return false;
		}
		for (I_TestResult result: results) {
			if (!result.isPassed()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int getExhibitCount() {
		return results.size();
	}

	@Override
	public int getAssertionCount() {
		int toRet = 0;
		for (I_TestResult result: results) {
			toRet += result.getAssertionCount();
		}
		return toRet;
	}
	
}
