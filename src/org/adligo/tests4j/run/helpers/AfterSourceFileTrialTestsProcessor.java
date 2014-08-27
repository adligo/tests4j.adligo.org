package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.common.AssertCompareFailureMutant;
import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.dependency.I_ClassAlias;
import org.adligo.tests4j.models.shared.dependency.I_ClassDependenciesLocal;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ResultMessages;
import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResultMutant;
import org.adligo.tests4j.models.shared.results.TestResult;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.trials.CircularDependencies;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_CircularDependencies;
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
	public static final String TEST_DEPENDENCIES = "testDependencies(SourceFileTrialResultMutant trialResultMutant)";
	private static final String AFTER_SOURCE_FILE_TRIAL_TESTS_METHOD =
			"afterTrialTests(I_SourceFileTrialResult p)";
	private static final String TEST_MIN_COVERAGE =
			"testMinCoverage";
	public AfterSourceFileTrialTestsProcessor(I_Tests4J_Memory memory) {
		super(memory);
	}

	
	/**
	 * @param trialResultMutant which gets populated
	 *    with the code coverage and class dependencies here.
	 *    
	 * @return
	 */
	public TestResultMutant afterSourceFileTrialTests(SourceFileTrialResultMutant trialResultMutant) {
		Method clazzMethod = null;
		
		I_AbstractTrial trial = super.getTrial();
		Class<? extends I_AbstractTrial> trialClass = trial.getClass();
		TrialDescription trialDesc = super.getTrialDescription();
		
		I_ClassDependenciesLocal deps =  trialDesc.getSourceClassDependencies();
		trialResultMutant.setDependencies(deps);
		
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
	
	
	public TestResult testDependencies(SourceFileTrialResultMutant trialResultMutant) {
		TrialDescription trialDesc = super.getTrialDescription();
		TestResultMutant testResultMutant = new TestResultMutant();
		testResultMutant.setName(TEST_DEPENDENCIES);
		I_CircularDependencies circle = trialDesc.getAllowedCircularDependencies();
		CircularDependencies c = CircularDependencies.get(circle);
		
		I_ClassDependenciesLocal deps = trialDesc.getSourceClassDependencies();
		if (deps != null) {
			switch (c) {
				case INNER_CLASSES_ONLY_:
					if (deps.hasCircularDependencies()) {
						Set<String> classes = deps.getCircularDependenciesNames();
						Class<?> sourceClass = trialDesc.getSourceFileClass();
						String name = sourceClass.getName();
						//a crude test for inner classes
						for (String className: classes) {
							if (className.indexOf(name) != 0) {
								failCircularDependencies(deps, testResultMutant);
								break;
							}
						}
						testResultMutant.incrementAssertionCount(1);
					}
					break;
				case NONE_:
				default:
					if (deps.hasCircularDependencies()) {
						failCircularDependencies(deps, testResultMutant);
					}
					testResultMutant.incrementAssertionCount(1);
			}
		}
		testResultMutant.setPassed(true);
		TestResult result = new TestResult(testResultMutant);
		return result;
	}


	public void failCircularDependencies(I_ClassDependenciesLocal deps,
			TestResultMutant testResultMutant) {
		I_Tests4J_ResultMessages messages = Tests4J_Constants.CONSTANTS.getResultMessages();
		
		AssertCompareFailureMutant tfm = new AssertCompareFailureMutant();
		tfm.setFailureMessage(messages.getSourceClassHasCircularDependency());
		tfm.setActualClass(Set.class.getName());
		tfm.setExpectedClass(Set.class.getName());
		
		tfm.setExpectedValue("");
		tfm.setActualValue(deps.getCircularDependencies().toString());
		tfm.setAssertType(AssertType.AssertEquals);
		testResultMutant.setFailure(tfm);
		testResultMutant.setPassed(false);
	}
}
