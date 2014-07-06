package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Method;
import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResultMutant;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_SourceFileTrial;

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
	
	public AfterSourceFileTrialTestsProcessor(Tests4J_Memory memory) {
		super(memory);
	}
	
	public TestResultMutant afterSourceFileTrialTests(SourceFileTrialResultMutant trialResultMutant) {
		Method clazzMethod = null;
		List<I_PackageCoverage> coverage;
		I_AbstractTrial trial = super.getTrial();
		Class<? extends I_AbstractTrial> trialClass = trial.getClass();
		TrialDescription trialDesc = super.getTrialDescription();
		
		try {
			clazzMethod = trialClass.getDeclaredMethod(AFTER_TRIAL_TESTS, I_SourceFileTrialResult.class);
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
		
		I_CoverageRecorder rec = super.getTrialThreadLocalCoverageRecorder();
		if (rec != null) {
			coverage = rec.endRecording();
			I_SourceFileCoverage cover = trialDesc.findSourceFileCoverage(coverage);
			if (cover != null) {
				trialResultMutant.setSourceFileCoverage(cover);
			}
		}
		trialResultMutant.setRanAfterTrialTests(true);
		
		boolean passed = false;
		try {
			if (trial instanceof I_SourceFileTrial) {
				((I_SourceFileTrial) trial).afterTrialTests(trialResultMutant);
			}
			passed = true;
		} catch (AfterTrialTestsAssertionFailure x) {
			//the test failed, in one of it's asserts
		} catch (Throwable x) {
			super.onAfterTrialTestsMethodException(x, AFTER_SOURCE_FILE_TRIAL_TESTS_METHOD);
		}
		TestResultMutant afterTrialTestsResultMutant = super.getAfterTrialTestsResultMutant();
		if (afterTrialTestsResultMutant == null) {
			afterTrialTestsResultMutant = new TestResultMutant();
			afterTrialTestsResultMutant.setPassed(passed);
			flushAssertionHashes(afterTrialTestsResultMutant);
			afterTrialTestsResultMutant.setName(AFTER_SOURCE_FILE_TRIAL_TESTS_METHOD);
		}
		return afterTrialTestsResultMutant;
	}
}
