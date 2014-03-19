package org.adligo.jtests.models.shared.results;

import java.util.ArrayList;
import java.util.List;

import org.adligo.jtests.models.shared.common.IsEmpty;
import org.adligo.jtests.models.shared.common.TestType;
import org.adligo.jtests.models.shared.coverage.I_ClassCoverage;
import org.adligo.jtests.models.shared.coverage.I_PackageCoverage;

public class TrialResultMutant implements I_TrialResult {
	public static final String COVERAGE_IS_NOT_CURRENTLY_SUPPORTED_BY_J_TESTS = "Coverage is not currently supported by JTests";
	public static final String TEST_RESULT_MUTANT_S_WITH_A_CLASS_TEST_TYPE_REQUIRE_A_TESTED_CLASS_NAME = 
				"TestResultMutant's with a ClassTest type require a testedClassName";
	public static final String TEST_RESULT_MUTANT_S_WITH_A_CLASS_TEST_TYPE_REQUIRE_A_TESTED_PACKAGE_NAME = 
				"TestResultMutant's with a ClassTest type require a testedUseCaseName";
	public static final String TEST_RESULT_MUTANT_S_WITH_A_CLASS_TEST_TYPE_REQUIRE_A_TESTED_USE_CASE_NAME = 
				"TestResultMutant's with a ClassTest type require a testedPackageName";
	
	public static final String TEST_RESULT_MUTANT_REQUIRES_A_NON_NULL_TYPE = "TestResultMutant requires a non null type.";
	public static final String TEST_RESULT_MUTANT_REQUIRES_A_NON_EMPTY_TEST_NAME = "TestResultMutant requires a non empty testName.";
	private String testName;
	private String testedClassName;
	private String testedPackageName;
	
	private TestType testType;
	private List<TestResultMutant> exhibitResults = 
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
		testName = p.getTestName();
		IsEmpty.isEmpty(testName,
				TEST_RESULT_MUTANT_REQUIRES_A_NON_EMPTY_TEST_NAME);
		testType = p.getTestType();
		if (testType == null) {
			throw new IllegalArgumentException(
					TEST_RESULT_MUTANT_REQUIRES_A_NON_NULL_TYPE);
		}
		switch(testType) {
			case ClassTest:
				testedClassName = p.getTestedClassName();
				break;
			case PackageTest:
				testedPackageName = p.getTestedPackageName();
				break;
			default:
		}
		
		List<I_TestResult> results = p.getExhibitResults();
		setExhibitResults(results);
		
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
	public String getTestName() {
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
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getTestType()
	 */
	@Override
	public TestType getTestType() {
		return testType;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getExhibitResults()
	 */
	@Override
	public List<I_TestResult> getExhibitResults() {
		List<I_TestResult> toRet = new ArrayList<I_TestResult>();
		toRet.addAll(exhibitResults);
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
	public void setTestType(TestType p) {
		this.testType = p;
	}
	public void setExhibitResults(List<I_TestResult> p) {
		exhibitResults.clear();
		for (I_TestResult result: p) {
			exhibitResults.add(new TestResultMutant(result));
		}
	}
	public void addExhibitResult(I_TestResult p) {
		exhibitResults.add(new TestResultMutant(p));
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
				+ testType + ", exhibitResults=" + exhibitResults
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
		if (exhibitResults.size() == 0) {
			return false;
		}
		for (I_TestResult result: exhibitResults) {
			if (!result.isPassed()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int getExhibitCount() {
		return exhibitResults.size();
	}

	@Override
	public int getAssertionCount() {
		int toRet = 0;
		for (I_TestResult result: exhibitResults) {
			toRet += result.getAssertionCount();
		}
		return toRet;
	}
	
}
