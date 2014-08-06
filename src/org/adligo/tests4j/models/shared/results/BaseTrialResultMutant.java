package org.adligo.tests4j.models.shared.results;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adligo.tests4j.models.shared.common.I_TrialType;
import org.adligo.tests4j.models.shared.common.StringMethods;
import org.adligo.tests4j.models.shared.common.TrialType;

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
	private String trialClassName;
	private I_TrialType testType;
	private Map<String, TestResultMutant> results = 
			new HashMap<String,TestResultMutant> ();
	private boolean ignored;
	//skip packageCoverage because there is no PackageCoverageMutant yet
	//skip classCoverage because there is no ClassCoverageMutant yet
	private String beforeTestOutput;
	private String afterTestOutput;
	private List<I_TrialFailure> failures = Collections.emptyList();
	private Boolean passed = null;
	private boolean hadAfterTrialTests = false;
	private boolean ranAfterTrialTests = false;
	
	public BaseTrialResultMutant() {}
	
	public BaseTrialResultMutant(I_TrialResult p) {
		this(p, true);
	}
	
	public BaseTrialResultMutant(I_TrialResult p, boolean cloneRelations) {
		trialName = p.getName();
		trialClassName = p.getTrialClassName();
		
		StringMethods.isEmpty(trialName,
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
		passed = p.isPassed();
		beforeTestOutput = p.getBeforeTrialOutput();
		afterTestOutput = p.getAfterTrialOutput();
		setFailures(p.getFailures());
		hadAfterTrialTests = p.isHadAfterTrialTests();
		ranAfterTrialTests = p.isRanAfterTrialTests();
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
	public I_TrialType getType() {
		return testType;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TrialResult#getResults()
	 */
	@Override
	public List<I_TestResult> getResults() {
		List<I_TestResult> toRet = new ArrayList<I_TestResult>();
		toRet.addAll(results.values());
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
	public void setTrialName(String pTrialName) {
		this.trialName = pTrialName;
	}
	public void setType(I_TrialType p) {
		this.testType = p;
	}
	public void setResults(List<I_TestResult> p) {
		results.clear();
		for (I_TestResult result: p) {
			String name = result.getName();
			results.put(name, new TestResultMutant(result));
		}
	}
	public void addResult(I_TestResult p) {
		String name = p.getName();
		results.put(name, new TestResultMutant(p));
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

	public List<I_TrialFailure> getFailures() {
		return failures;
	}

	public void setFailures(List<I_TrialFailure> pFailures) {
		if (pFailures != null) {
			for (I_TrialFailure tf: pFailures) {
				addFailure(tf);
			}
		}
	}

	public void addFailure(I_TrialFailure tf) {
		if (tf != null) {
			if (failures.isEmpty()) {
				failures = new ArrayList<I_TrialFailure>();
			}
			failures.add(tf);
		}
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
		for (I_TestResult result: results.values()) {
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
		return getTestFailureCount(results.values());
	}
	
	public static int getTestFailureCount(Collection<? extends I_TestResult> p) {
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
		return getAssertionCount(results.values());
	}
	
	public static int getAssertionCount(Collection<? extends I_TestResult> p) {
		int toRet = 0;
		for (I_TestResult result: p) {
			toRet = toRet + result.getAssertionCount();
		}
		return toRet;
	}
	
	@Override
	public int getUniqueAssertionCount() {
		return getUniqueAssertionCount(results.values());
	}
	
	public static int getUniqueAssertionCount(Collection<? extends I_TestResult> p) {
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

	public boolean isHadAfterTrialTests() {
		return hadAfterTrialTests;
	}

	public boolean isRanAfterTrialTests() {
		return ranAfterTrialTests;
	}

	public void setHadAfterTrialTests(boolean hadAfterTrialTests) {
		this.hadAfterTrialTests = hadAfterTrialTests;
	}

	public void setRanAfterTrialTests(boolean ranAfterTrialTests) {
		this.ranAfterTrialTests = ranAfterTrialTests;
	}
	@Override
	public boolean hasRecordedCoverage() {
		return false;
	}

	public String getTrialClassName() {
		return trialClassName;
	}

	public void setTrialClassName(String trialClassName) {
		this.trialClassName = trialClassName;
	}
}
