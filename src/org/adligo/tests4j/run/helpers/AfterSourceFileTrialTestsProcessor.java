package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.common.AssertCompareFailureMutant;
import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.TestFailureMutant;
import org.adligo.tests4j.models.shared.common.Tests4J_Constants;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.dependency.I_ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.I_ClassDependenciesLocal;
import org.adligo.tests4j.models.shared.dependency.I_ClassParentsLocal;
import org.adligo.tests4j.models.shared.dependency.I_DependencyGroup;
import org.adligo.tests4j.models.shared.dependency.I_FieldSignature;
import org.adligo.tests4j.models.shared.dependency.I_MethodSignature;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ResultMessages;
import org.adligo.tests4j.models.shared.results.DependencyTestFailureMutant;
import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResultMutant;
import org.adligo.tests4j.models.shared.results.TestResult;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.models.shared.trials.AbstractTrial;
import org.adligo.tests4j.models.shared.trials.CircularDependencies;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_CircularDependencies;
import org.adligo.tests4j.models.shared.trials.I_SourceFileTrial;
import org.adligo.tests4j.run.discovery.TrialDescription;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

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
	private static final String TEST_MIN_COVERAGE =
			"testMinCoverage";
	private I_Tests4J_Log log;
	
	public AfterSourceFileTrialTestsProcessor(I_Tests4J_Memory memory) {
		super(memory);
		log = memory.getLog();
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
			if (log.isLogEnabled(AfterSourceFileTrialTestsProcessor.class)) {
				log.log("calling rec.endRecording() " + rec + log.getLineSeperator() + 
						Thread.currentThread().getName() + 
						log.getLineSeperator() +
						" for " + trialResultMutant.getSourceFileName());
			}
			coverage = rec.endRecording();
			I_SourceFileCoverage cover = trialDesc.findSourceFileCoverage(coverage);
			if (cover != null) {
				trialResultMutant.setSourceFileCoverage(cover);
			} else {
				log.onThrowable(new IllegalStateException("A internal error"
						+ " has occured in tests4j, no coverage for class " + 
						Thread.currentThread().getName() + 
						log.getLineSeperator() +
						" for recorder " + rec + " " +
						log.getLineSeperator() +
						trialResultMutant.getSourceFileName()));
			}
		}
		
		boolean working = true;
		Class<?> parentClass = trialClass;
		while (working) {
			try {
				clazzMethod = parentClass.getMethod(AFTER_TRIAL_TESTS, I_SourceFileTrialResult.class);
				//clazzMethod = trialClass.getDeclaredMethod(AFTER_TRIAL_TESTS, I_SourceFileTrialResult.class);
			} catch (NoSuchMethodException e) {
				//do nothing
			} catch (SecurityException e) {
				//do nothing
			}
			if (clazzMethod != null) {
				working = false;
			} else {
				parentClass = parentClass.getSuperclass();
				if (AbstractTrial.TESTS4J_TRIAL_CLASSES.contains(parentClass.getName())) {
					//no method here
					break;
				}
			} 
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
			super.onDelegatedTestMethodException(x, I_SourceFileTrial.AFTER_TRIAL_TESTS);
		}
		TestResultMutant afterTrialTestsResultMutant = super.getAfterTrialTestsResultMutant();
		flushAssertionHashes(afterTrialTestsResultMutant);
		afterTrialTestsResultMutant.setAssertionCount(getAssertions());
		afterTrialTestsResultMutant.setPassed(passed);
		afterTrialTestsResultMutant.setName(I_SourceFileTrial.AFTER_TRIAL_TESTS);
		return afterTrialTestsResultMutant;
	}
	
	
	public TestResult testMinCoverage(SourceFileTrialResultMutant trialResultMutant) {
		TrialDescription trialDesc = super.getTrialDescription();
		
		TestResultMutant testResultMutant = new TestResultMutant();
		double minCoverage = trialDesc.getMinCoverage();
		super.startDelegatedTest();
		
		
		I_SourceFileCoverage sourceCoverage =  trialResultMutant.getSourceFileCoverage();
		if (sourceCoverage == null) {
			testResultMutant.setPassed(false);
		} else {
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
		}
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
		I_DependencyGroup dg =  trialDesc.getDependencyGroup();
		Set<I_ClassParentsLocal> depsLocal = deps.getDependenciesLocal();
		List<I_ClassAttributes> refs =  deps.getReferences();
		
		String sourceClassName = trialDesc.getSourceFileClass().getName();
		DependencyTestFailureMutant depNotAllowed = null;
		//set above for error as in 
		// org.adligo.something.ClassA void foo(int, byte) 
		if (dg != null) {
			final int prime = 31;
			int hashCounter = 0;
			for (I_ClassAttributes ref: refs) {
				String className = ref.getName();
				if ( !sourceClassName.equals(className)) {
					if (dg.isInGroup(className)) {
						//this ref is expceted
						hashCounter = hashCounter * prime + className.hashCode();
					} else {
						Set<I_FieldSignature> flds = ref.getFields();
						for (I_FieldSignature fld: flds) {
							if ( !dg.isInGroup(className, fld)) {
								depNotAllowed = new DependencyTestFailureMutant();
								depNotAllowed.setSourceClass(trialDesc.getSourceFileClass());
								depNotAllowed.setField(fld);
								depNotAllowed.setCalledClass(ref.getName());
								depNotAllowed.setGroupNames(dg.getSubGroupNames());
								break;
							} else {
								hashCounter = hashCounter * prime + fld.hashCode();
							}
						}
						
						if (depNotAllowed == null) {
							Set<I_MethodSignature> mtds = ref.getMethods();
							for (I_MethodSignature mtd: mtds) {
								if ( !dg.isInGroup(className, mtd)) {
									depNotAllowed = new DependencyTestFailureMutant();
									depNotAllowed.setSourceClass(trialDesc.getSourceFileClass());
									depNotAllowed.setMethod(mtd);
									depNotAllowed.setCalledClass(className);
									depNotAllowed.setGroupNames(dg.getSubGroupNames());
									break;
								} else {
									hashCounter = hashCounter * prime + mtd.hashCode();
								}
							} 
						}
					}
					
				}
				
			}
			if (depNotAllowed == null) {	
				testResultMutant.incrementAssertionCount(
						hashCounter);
				testResultMutant.setPassed(true); 
			} else {
				testResultMutant.setFailure(depNotAllowed);
				testResultMutant.setPassed(false); 
			}
		} else {
			testResultMutant.setPassed(true); 
		}
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
