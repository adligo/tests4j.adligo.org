package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Method;
import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResultMutant;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
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
	
	public AfterSourceFileTrialTestsProcessor(Tests4J_Memory memory) {
		super(memory);
	}
	
	public TestResultMutant testMinCoverage(SourceFileTrialResultMutant trialResultMutant) {
		I_AbstractTrial trial = super.getTrial();
		TrialDescription trialDesc = super.getTrialDescription();
		
		double minCoverage = trialDesc.getMinCoverage();
		boolean passed = false;
		super.startDelegatedTest();
		try {
			if (trialResultMutant.hasRecordedCoverage()) {
				I_SourceFileCoverage sourceCoverage =  trialResultMutant.getSourceFileCoverage();
				trial.assertGreaterThanOrEquals(minCoverage, sourceCoverage.getPercentageCoveredDouble());
			}
			passed = true;
		} catch (DelegateTestAssertionFailure x) {
			//the test failed, in one of it's asserts
		}
		TestResultMutant testsResultMutant = super.getAfterTrialTestsResultMutant();
		if (passed) {
			flushAssertionHashes(testsResultMutant);
		}
		testsResultMutant.setPassed(passed);
		testsResultMutant.setName(TEST_MIN_COVERAGE);
		return testsResultMutant;
	}
	
	public TestResultMutant afterSourceFileTrialTests(SourceFileTrialResultMutant trialResultMutant) {
		Method clazzMethod = null;
		
		I_AbstractTrial trial = super.getTrial();
		Class<? extends I_AbstractTrial> trialClass = trial.getClass();
		TrialDescription trialDesc = super.getTrialDescription();
		
		List<I_PackageCoverage> coverage;
		I_CoverageRecorder rec = super.getTrialThreadLocalCoverageRecorder();
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
