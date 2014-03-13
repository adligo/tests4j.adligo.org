package org.adligo.jtests.models.shared.results;

import java.util.ArrayList;
import java.util.List;

import org.adligo.jtests.models.shared.common.IsEmpty;
import org.adligo.jtests.models.shared.common.TestType;
import org.adligo.jtests.models.shared.coverage.I_ClassCoverage;
import org.adligo.jtests.models.shared.coverage.I_PackageCoverage;

public class TestResultMutant implements I_TestResult {
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
	private String testedUseCaseName;
	private TestType testType;
	private List<ExhibitResultMutant> exhibitResults = 
			new ArrayList<ExhibitResultMutant> ();
	private boolean ignored;
	//skip packageCoverage because there is no PackageCoverageMutant yet
	//skip classCoverage because there is no ClassCoverageMutant yet
	private String beforeTestOutput;
	private String afterTestOutput;
	private FailureMutant failure;
	private Boolean passed = null;
	
	public TestResultMutant() {}
	
	public TestResultMutant(I_TestResult p) {
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
				IsEmpty.isEmpty(testedClassName,
						TEST_RESULT_MUTANT_S_WITH_A_CLASS_TEST_TYPE_REQUIRE_A_TESTED_CLASS_NAME);
				break;
			case PackageTest:
				testedPackageName = p.getTestedPackageName();
				IsEmpty.isEmpty(testedPackageName,
						TEST_RESULT_MUTANT_S_WITH_A_CLASS_TEST_TYPE_REQUIRE_A_TESTED_PACKAGE_NAME);
				break;
			default:
				testedUseCaseName = p.getTestedUseCaseName();
				IsEmpty.isEmpty(testedPackageName,
						TEST_RESULT_MUTANT_S_WITH_A_CLASS_TEST_TYPE_REQUIRE_A_TESTED_USE_CASE_NAME);
		}
		
		List<I_ExhibitResult> results = p.getExhibitResults();
		setExhibitResults(results);
		
		ignored = p.isIgnored();
		beforeTestOutput = p.getBeforeTestOutput();
		afterTestOutput = p.getAfterTestOutput();
		failure = new FailureMutant(p.getFailure());
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
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getTestedUseCaseName()
	 */
	@Override
	public String getTestedUseCaseName() {
		return testedUseCaseName;
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
	public List<I_ExhibitResult> getExhibitResults() {
		List<I_ExhibitResult> toRet = new ArrayList<I_ExhibitResult>();
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
	public void setTestedUseCaseName(String testedUseCaseName) {
		this.testedUseCaseName = testedUseCaseName;
	}
	
	public void setTestType(TestType testType) {
		this.testType = testType;
	}
	public void setExhibitResults(List<I_ExhibitResult> p) {
		exhibitResults.clear();
		for (I_ExhibitResult result: p) {
			exhibitResults.add(new ExhibitResultMutant(result));
		}
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
		return toString(TestResultMutant.class);
	}
	
	String toString(Class<?> c) {
		return c.getSimpleName() + " [testName=" + testName + ", testedClassName="
				+ testedClassName + ", testedPackageName=" + testedPackageName
				+ ", testedUseCaseName=" + testedUseCaseName + ", testType="
				+ testType + ", exhibitResults=" + exhibitResults
				+ ", ignored=" + ignored + ", beforeTestOutput="
				+ beforeTestOutput + ", afterTestOutput=" + afterTestOutput
				+ "]";
	}

	public I_Failure getFailure() {
		return failure;
	}

	public void setFailure(FailureMutant failure) {
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
		if (!passed) {
			return false;
		}
		if (exhibitResults.size() == 0) {
			return false;
		}
		for (I_ExhibitResult result: exhibitResults) {
			if (!result.isPassed()) {
				return false;
			}
		}
		return true;
	}
	
}
