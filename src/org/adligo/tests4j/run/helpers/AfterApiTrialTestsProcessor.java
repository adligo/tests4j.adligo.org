package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Method;
import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.results.ApiTrialResult;
import org.adligo.tests4j.models.shared.results.ApiTrialResultMutant;
import org.adligo.tests4j.models.shared.results.I_ApiTrialResult;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_ApiTrial;

/**
 * This class should behave like a model (not thread safe)
 * 
 * @author scott
 *
 */
public class AfterApiTrialTestsProcessor extends AbstractAfterTrialTestsProcessor {
	private static final String AFTER_API_TRIAL_TESTS_METHOD =
			"afterTrialTests(I_ApiTrialResult p)";
	
	public AfterApiTrialTestsProcessor(Tests4J_Memory memory) {
		super(memory);
	}
	
	public TestResultMutant afterApiTrialTests(ApiTrialResultMutant trialResultMutant) {
		
		Method clazzMethod = null;
		List<I_PackageCoverage> coverage;
		I_AbstractTrial trial = super.getTrial();
		Class<? extends I_AbstractTrial> trialClass = trial.getClass();
		TrialDescription trialDesc = super.getTrialDescription();
		
		try {
			clazzMethod = trialClass.getMethod(AFTER_TRIAL_TESTS, I_ApiTrialResult.class);
			//clazzMethod = trialClass.getDeclaredMethod(AFTER_TRIAL_TESTS, I_ApiTrialResult.class);
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
			I_PackageCoverage cover = trialDesc.findPackageCoverage(coverage);
			if (cover != null) {
				trialResultMutant.setPackageCoverage(cover);
			}
		}
		
		
		boolean passed = false;
		try {
			if (trial instanceof I_ApiTrial) {
				super.startDelegatedTest();
				trialResultMutant.setRanAfterTrialTests(true);
				ApiTrialResult result = new ApiTrialResult(trialResultMutant);
				((I_ApiTrial) trial).afterTrialTests(result);
			}
			passed = true;
		} catch (DelegateTestAssertionFailure x) {
			//the test failed, in one of it's asserts
		} catch (Throwable x) {
			super.onDelegatedTestMethodException(x, AFTER_API_TRIAL_TESTS_METHOD);
		}
		TestResultMutant afterTrialTestsResultMutant = super.getAfterTrialTestsResultMutant();
		if (passed) {
			flushAssertionHashes(afterTrialTestsResultMutant);
		}
		afterTrialTestsResultMutant.setPassed(passed);
		afterTrialTestsResultMutant.setName(AFTER_API_TRIAL_TESTS_METHOD);
		return afterTrialTestsResultMutant;
	}
}
