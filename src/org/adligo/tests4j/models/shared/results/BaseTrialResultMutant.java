package org.adligo.tests4j.models.shared.results;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adligo.tests4j.shared.common.I_TrialType;
import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.common.TrialType;

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
	private String trialName_;
	private String trialClassName_;
	private I_TrialType testType_;
	private Map<String, TestResultMutant> results_ = 
			new HashMap<String,TestResultMutant> ();
	private boolean ignored_;
	//skip packageCoverage because there is no PackageCoverageMutant yet
	//skip classCoverage because there is no ClassCoverageMutant yet
	private String beforeTestOutput_;
	private String afterTestOutput_;
	private List<I_TrialFailure> failures_ = Collections.emptyList();
	private Boolean passed_ = null;
	private boolean hadAfterTrialTests_ = false;
	private boolean ranAfterTrialTests_ = false;
	private int runNumber_;
	
	public BaseTrialResultMutant() {}
	
	public BaseTrialResultMutant(I_TrialResult p) {
		this(p, true);
	}
	
	public BaseTrialResultMutant(I_TrialResult p, boolean cloneRelations) {
		trialName_ = p.getName();
		trialClassName_ = p.getTrialClassName();
		
		StringMethods.isEmpty(trialName_,
				TRIAL_RESULT_MUTANT_REQUIRES_A_NON_EMPTY_TRIAL_NAME);
		testType_ = p.getType();
		if (testType_ == null) {
			throw new IllegalArgumentException(
				TRIAL_RESULT_MUTANT_REQUIRES_A_NON_NULL_TYPE);
		}
		
		if (cloneRelations) {
			List<I_TestResult> results = p.getResults();
			setResults(results);
		}
		ignored_ = p.isIgnored();
		passed_ = p.isPassed();
		beforeTestOutput_ = p.getBeforeTrialOutput();
		afterTestOutput_ = p.getAfterTrialOutput();
		setFailures(p.getFailures());
		runNumber_ = p.getRunNumber();
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getTestName()
	 */
	@Override
	public String getName() {
		return trialName_;
	}
	

	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TrialResult#getTrialType()
	 */
	@Override
	public I_TrialType getType() {
		return testType_;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TrialResult#getResults()
	 */
	@Override
	public List<I_TestResult> getResults() {
		List<I_TestResult> toRet = new ArrayList<I_TestResult>();
		toRet.addAll(results_.values());
		return toRet;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#isIgnored()
	 */
	@Override
	public boolean isIgnored() {
		return ignored_;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getBeforeTestOutput()
	 */
	@Override
	public String getBeforeTrialOutput() {
		return beforeTestOutput_;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getAfterTestOutput()
	 */
	@Override
	public String getAfterTrialOutput() {
		return afterTestOutput_;
	}
	public void setTrialName(String pTrialName) {
		this.trialName_ = pTrialName;
	}
	public void setType(I_TrialType p) {
		this.testType_ = p;
	}
	public void setResults(List<I_TestResult> p) {
		results_.clear();
		for (I_TestResult result: p) {
			String name = result.getName();
			results_.put(name, new TestResultMutant(result));
		}
	}
	public void addResult(I_TestResult p) {
		String name = p.getName();
		results_.put(name, new TestResultMutant(p));
	}
	public void setIgnored(boolean ignored) {
		this.ignored_ = ignored;
	}
	public void setBeforeTestOutput(String beforeTestOutput) {
		this.beforeTestOutput_ = beforeTestOutput;
	}
	public void setAfterTestOutput(String afterTestOutput) {
		this.afterTestOutput_ = afterTestOutput;
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
		return failures_;
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
			if (failures_.isEmpty()) {
				failures_ = new ArrayList<I_TrialFailure>();
			}
			failures_.add(tf);
		}
	}


	public void setPassed(boolean passed) {
		this.passed_ = passed;
	}

	@Override
	public boolean isPassed() {
		//passed my be null
		if (Boolean.FALSE.equals(passed_)) {
			return false;
		}
		if (failures_.size() != 0) {
			return false;
		}
		/**
		 * trials with no results actually pass,
		 * so that interface trials can run successfully
		 * during a run with out the code coverage plug-in.
		 */
		for (I_TestResult result: results_.values()) {
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
		return results_.size();
	}

	@Override
	public int getTestFailureCount() {
		return getTestFailureCount(results_.values());
	}
	
	public static int getTestFailureCount(Collection<? extends I_TestResult> p) {
		int toRet = 0;
		for (I_TestResult tr: p) {
			if (!tr.isPassed() && !tr.isIgnored()) {
				toRet++;
			}
		}
		return toRet;
	}
	
	@Override
  public int getTestIgnoredCount() {
	  return getTestIgnoredCount(results_.values());
  }
  
	public static int getTestIgnoredCount(Collection<? extends I_TestResult> p) {
    int toRet = 0;
    for (I_TestResult tr: p) {
      if (tr.isIgnored()) {
        toRet++;
      }
    }
    return toRet;
  }
  
	@Override
	public int getAssertionCount() {
		return getAssertionCount(results_.values());
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
		return getUniqueAssertionCount(results_.values());
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
		return passed_;
	}

	public boolean isHadAfterTrialTests() {
		return hadAfterTrialTests_;
	}

	public boolean isRanAfterTrialTests() {
		return ranAfterTrialTests_;
	}

	public void setHadAfterTrialTests(boolean hadAfterTrialTests) {
		this.hadAfterTrialTests_ = hadAfterTrialTests;
	}

	public void setRanAfterTrialTests(boolean ranAfterTrialTests) {
		this.ranAfterTrialTests_ = ranAfterTrialTests;
	}
	@Override
	public boolean hasRecordedCoverage() {
		return false;
	}

	public String getTrialClassName() {
		return trialClassName_;
	}

	public void setTrialClassName(String trialClassName) {
		this.trialClassName_ = trialClassName;
	}

  public int getRunNumber() {
    return runNumber_;
  }

  public void setRunNumber(int runNumber) {
    this.runNumber_ = runNumber;
  }

}
