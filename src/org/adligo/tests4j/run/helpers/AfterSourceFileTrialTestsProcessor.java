package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Method;
import java.util.List;

import org.adligo.tests4j.models.shared.asserts.common.AssertCompareFailureMutant;
import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ResultMessages;
import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResultMutant;
import org.adligo.tests4j.models.shared.results.TestResult;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_SourceFileTrial;
import org.adligo.tests4j.run.discovery.TrialDescription;

/**
 * TODO extract the method 
 * private SourceFileTrialResultMutant afterSourceFileTrialTests(TrialType type) {
 * from TrialInstancesProcessor
 * 
 * This class should behave like a model (not thread safe)
 * 
 * @author scott
 *
 */
public class AfterSourceFileTrialTestsProcessor extends AbstractAfterTrialTestsProcessor {
	private static final String AFTER_SOURCE_FILE_TRIAL_TESTS_METHOD =
			"afterTrialTests(I_SourceFileTrialResult p)";
	private static final String TEST_MIN_COVERAGE =
			"testMinCoverage";
	
	public AfterSourceFileTrialTestsProcessor(I_Tests4J_Memory memory) {
		super(memory);
	}
	
	public TestResult testMinCoverage(SourceFileTrialResultMutant trialResultMutant) {
		TrialDescription trialDesc = super.getTrialDescription();
		
		TestResultMutant testResultMutant = new TestResultMutant();
		double minCoverage = trialDesc.getMinCoverage();
		super.startDelegatedTest();
		
		
		I_SourceFileCoverage sourceCoverage =  trialResultMutant.getSourceFileCoverage();
		double pct = sourceCoverage.getPercentageCoveredDouble();
		if (minCoverage > pct) {
			I_Tests4J_ResultMessages messages = Tests4J_Constants.CONSTANTS.getResultMessages();
			
			AssertCompareFailureMutant tfm = new AssertCompareFailureMutant();
			tfm.setFailureMessage(messages.getCodeCoveragIsOff());
			tfm.setActualClass(Double.class.getName());
			tfm.setExpectedClass(Double.class.getName());
			
			tfm.setExpectedValue("" + minCoverage);
			tfm.setActualValue("" + pct);
			tfm.setAssertType(AssertType.AssertGreaterThanOrEquals);
			testResultMutant.setFailure(tfm);
			testResultMutant.setPassed(false);
		} else {
			testResultMutant.setPassed(true);
		}
		testResultMutant.incrementAssertionCount(1);
		testResultMutant.setName(TEST_MIN_COVERAGE);
		
		TestResult result = new TestResult(testResultMutant);
		return result;
	}
	
	public TestResultMutant afterSourceFileTrialTests(SourceFileTrialResultMutant trialResultMutant) {
		Method clazzMethod = null;
		
		I_AbstractTrial trial = super.getTrial();
		Class<? extends I_AbstractTrial> trialClass = trial.getClass();
		TrialDescription trialDesc = super.getTrialDescription();
		
		List<I_PackageCoverage> coverage;
		I_Tests4J_CoverageRecorder rec = super.getTrialThreadLocalCoverageRecorder();
		if (rec != null) {
			coverage = rec.endRecording();
			I_SourceFileCoverage cover = trialDesc.findSourceFileCoverage(coverage);
			if (cover != null) {
				trialResultMutant.setSourceFileCoverage(cover);
			}
		}
		
		try {
			clazzMethod = trialClass.getMethod(AFTER_TRIAL_TESTS, I_SourceFileTrialResult.class);
			//clazzMethod = trialClass.getDeclaredMethod(AFTER_TRIAL_TESTS, I_SourceFileTrialResult.class);
		} catch (NoSuchMethodException e) {
			//do nothing
		} catch (SecurityException e) {
			//do nothing
		}
		trialResultMutant.setRanAfterTrialTests(false);
		if (clazzMethod != null) {
			trialResultMutant.setHadAfterTrialTests(true);
		} else {
			trialResultMutant.setHadAfterTrialTests(false);
			return null;
		}
		
		
		
		
		boolean passed = false;
		try {
			if (trial instanceof I_SourceFileTrial) {
				super.startDelegatedTest();
				trialResultMutant.setRanAfterTrialTests(true);
				((I_SourceFileTrial) trial).afterTrialTests(new SourceFileTrialResult(trialResultMutant));
			}
			passed = true;
		} catch (DelegateTestAssertionFailure x) {
			//the test failed, in one of it's asserts
		} catch (Throwable x) {
			super.onDelegatedTestMethodException(x, AFTER_SOURCE_FILE_TRIAL_TESTS_METHOD);
		}
		TestResultMutant afterTrialTestsResultMutant = super.getAfterTrialTestsResultMutant();
		if (passed) {
			flushAssertionHashes(afterTrialTestsResultMutant);
		}
		afterTrialTestsResultMutant.setPassed(passed);
		afterTrialTestsResultMutant.setName(AFTER_SOURCE_FILE_TRIAL_TESTS_METHOD);
		return afterTrialTestsResultMutant;
	}
}
