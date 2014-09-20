package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Method;

import org.adligo.tests4j.models.shared.results.I_UseCaseTrialResult;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.results.UseCaseTrialResult;
import org.adligo.tests4j.models.shared.results.UseCaseTrialResultMutant;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_UseCaseTrial;
import org.adligo.tests4j.run.common.I_Tests4J_Memory;

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
public class AfterUseCaseTrialTestsProcessor extends AbstractAfterTrialTestsProcessor {
	private static final String AFTER_USE_CASE_TRIAL_TESTS_METHOD =
			"afterTrialTests(I_UseCaseTrialResult p)";
	
	public AfterUseCaseTrialTestsProcessor(I_Tests4J_Memory memory) {
		super(memory);
	}
	
	public TestResultMutant afterUseCaseTrialTests(UseCaseTrialResultMutant trialResultMutant) {
		Method clazzMethod = null;
		I_AbstractTrial trial = super.getTrial();
		Class<? extends I_AbstractTrial> trialClass = trial.getClass();
		
		try {
			clazzMethod = trialClass.getMethod(AFTER_TRIAL_TESTS, I_UseCaseTrialResult.class);
			//clazzMethod = trialClass.getDeclaredMethod(AFTER_TRIAL_TESTS, I_UseCaseTrialResult.class);
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
			if (trial instanceof I_UseCaseTrial) {
				super.startDelegatedTest();
				trialResultMutant.setRanAfterTrialTests(true);
				((I_UseCaseTrial) trial).afterTrialTests(new UseCaseTrialResult(trialResultMutant));
			}
			passed = true;
		} catch (DelegateTestAssertionFailure x) {
			//the test failed, in one of it's asserts
		} catch (Throwable x) {
			super.onDelegatedTestMethodException(x, AFTER_USE_CASE_TRIAL_TESTS_METHOD);
		}
		TestResultMutant afterTrialTestsResultMutant = super.getAfterTrialTestsResultMutant();
		
		flushAssertionHashes(afterTrialTestsResultMutant);
		afterTrialTestsResultMutant.setAssertionCount(getAssertions());
		afterTrialTestsResultMutant.setPassed(passed);
		afterTrialTestsResultMutant.setName(AFTER_USE_CASE_TRIAL_TESTS_METHOD);
		return afterTrialTestsResultMutant;
	}
}
