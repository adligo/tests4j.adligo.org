package org.adligo.tests4j.models.shared.results;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.common.IsEmpty;
import org.adligo.tests4j.models.shared.common.TrialTypeEnum;

public class BaseTrialResultMutant implements I_TrialResult {
	public static final String COVERAGE_IS_NOT_CURRENTLY_SUPPORTED_BY_J_TESTS = "Coverage is not currently supported by JTests";
	public static final String TRIAL_RESULT_MUTANT_S_WITH_A_CLASS_TEST_TYPE_REQUIRE_A_TESTED_CLASS_NAME = 
				"TrialResultMutant's with a ClassTest type require a testedClassName";
	public static final String TRIAL_RESULT_MUTANT_S_WITH_A_CLASS_TEST_TYPE_REQUIRE_A_TESTED_PACKAGE_NAME = 
				"TrialResultMutant's with a ClassTest type require a testedUseCaseName";
	public static final String TRIAL_RESULT_MUTANT_S_WITH_A_CLASS_TEST_TYPE_REQUIRE_A_TESTED_USE_CASE_NAME = 
				"TrialResultMutant's with a ClassTest type require a testedPackageName";
	
	public static final String TRIAL_RESULT_MUTANT_REQUIRES_A_NON_NULL_TYPE = 
			"TrialResultMutant requires a non null type.";
	public static final String TRIAL_RESULT_MUTANT_REQUIRES_A_NON_EMPTY_TRIAL_NAME = 
			"TrialResultMutant requires a non empty trialName.";
	private String trialName;
	
	private TrialTypeEnum testType;
	private List<TestResultMutant> results = 
			new ArrayList<TestResultMutant> ();
	private boolean ignored;
	//skip packageCoverage because there is no PackageCoverageMutant yet
	//skip classCoverage because there is no ClassCoverageMutant yet
	private String beforeTestOutput;
	private String afterTestOutput;
	private TrialFailure failure;
	private Boolean passed = null;
	
	public BaseTrialResultMutant() {}
	
	public BaseTrialResultMutant(I_TrialResult p) {
		this(p, true);
	}
	
	public BaseTrialResultMutant(I_TrialResult p, boolean cloneRelations) {
		trialName = p.getName();
		IsEmpty.isEmpty(trialName,
				TRIAL_RESULT_MUTANT_REQUIRES_A_NON_EMPTY_TRIAL_NAME);
		testType = p.getType();
		if (testType == null) {
			throw new IllegalArgumentException(
				TRIAL_RESULT_MUTANT_REQUIRES_A_NON_NULL_TYPE);
		}
		
		if (cloneRelations) {
			List<I_TestResult> results = p.getResults();
			setResults(results);
		}
		ignored = p.isIgnored();
		beforeTestOutput = p.getBeforeTrialOutput();
		afterTestOutput = p.getAfterTrialOutput();
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
		return trialName;
	}
	

	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TrialResult#getTrialType()
	 */
	@Override
	public TrialTypeEnum getType() {
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
	public String getBeforeTrialOutput() {
		return beforeTestOutput;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getAfterTestOutput()
	 */
	@Override
	public String getAfterTrialOutput() {
		return afterTestOutput;
	}
	public void setTrialName(String testName) {
		this.trialName = testName;
	}
	public void setType(TrialTypeEnum p) {
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
		return toString(this);
	}
	
	public String toString(I_TrialResult p) {
		StringBuilder sb = new StringBuilder();
		toString(p, sb);
		sb.append("]");
		return sb.toString();
	}
	
	void toString(I_TrialResult c, StringBuilder sb) {
		sb.append(c.getClass().getName());
		sb.append(" [name=");
		sb.append(c.getName());
		sb.append(", type=");
		sb.append(c.getType());
		sb.append(", testResults=");
		sb.append(c.getResults());
		sb.append(", ignored=");
		sb.append(c.isIgnored());
		sb.append(", beforeTrialOutput=");
		sb.append(c.getBeforeTrialOutput());
		sb.append(", afterTrialOutput=");
		sb.append(c.getAfterTrialOutput());
	}

	public I_TrialFailure getFailure() {
		return failure;
	}

	public void setFailure(TrialFailure failure) {
		this.failure = failure;
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
			if (!result.isIgnored()) {
				if (!result.isPassed()) {
					return false;
				}
			}
		}
		return true;
	}

	
	
	@Override
	public int getTestCount() {
		return results.size();
	}

	@Override
	public int getTestFailureCount() {
		return getTestFailureCount(results);
	}
	
	public static int getTestFailureCount(List<? extends I_TestResult> p) {
		int toRet = 0;
		for (I_TestResult tr: p) {
			if (!tr.isPassed()) {
				toRet++;
			}
		}
		return toRet;
	}
	
	@Override
	public int getAssertionCount() {
		return getAssertionCount(results);
	}
	
	public static int getAssertionCount(List<? extends I_TestResult> p) {
		int toRet = 0;
		for (I_TestResult result: p) {
			toRet = toRet + result.getAssertionCount();
		}
		return toRet;
	}
	
	@Override
	public int getUniqueAssertionCount() {
		return getUniqueAssertionCount(results);
	}
	
	public static int getUniqueAssertionCount(List<? extends I_TestResult> p) {
		int toRet = 0;
		for (I_TestResult result: p) {
			toRet = toRet + result.getUniqueAssertionCount();
		}
		return toRet;
	}

	@Override
	public I_TrialResult clone(boolean cloneRelations) {
		return new BaseTrialResultMutant(this, cloneRelations);
	}
	
	Boolean getPassed() {
		return passed;
	}
}
