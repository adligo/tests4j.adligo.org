package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.coverage.SourceFileCoverage;
import org.adligo.tests4j.models.shared.dependency.I_ClassDependenciesLocal;
import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResultMutant;
import org.adligo.tests4j.models.shared.results.TestResult;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.run.common.I_Tests4J_Memory;
import org.adligo.tests4j.run.discovery.TrialDescription;
import org.adligo.tests4j.shared.asserts.common.AssertCompareFailureMutant;
import org.adligo.tests4j.shared.asserts.common.AssertType;
import org.adligo.tests4j.shared.asserts.dependency.AllowedDependencyFailure;
import org.adligo.tests4j.shared.asserts.dependency.AllowedDependencyFailureMutant;
import org.adligo.tests4j.shared.asserts.dependency.CircularDependencies;
import org.adligo.tests4j.shared.asserts.dependency.CircularDependencyFailure;
import org.adligo.tests4j.shared.asserts.dependency.CircularDependencyFailureMutant;
import org.adligo.tests4j.shared.asserts.dependency.I_CircularDependencies;
import org.adligo.tests4j.shared.asserts.dependency.I_ClassAttributes;
import org.adligo.tests4j.shared.asserts.dependency.I_DependencyGroup;
import org.adligo.tests4j.shared.asserts.dependency.I_FieldSignature;
import org.adligo.tests4j.shared.asserts.dependency.I_MethodSignature;
import org.adligo.tests4j.shared.common.ClassMethods;
import org.adligo.tests4j.shared.common.Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ResultMessages;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.system.shared.trials.AbstractTrial;
import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.system.shared.trials.I_SourceFileTrial;

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

	private I_Tests4J_Log log;
	
	public AfterSourceFileTrialTestsProcessor(I_Tests4J_Memory memory) {
		super(memory);
		log = memory.getLog();
	}

	
	/**
	 * @param trialResultMutant which gets populated
	 *    with the code coverage and class dependencies here.
	 *    
	 *    also this manipuates the trialResultMutants HadAfterTrialTests
	 *    field to true or false.
	 *    
	 * @return
	 */
	public TestResultMutant afterSourceFileTrialTests(SourceFileTrialResultMutant trialResultMutant) {
		Method clazzMethod = null;
		
		I_AbstractTrial trial = super.getTrial();
		Class<? extends I_AbstractTrial> trialClass = trial.getClass();
		TrialDescription trialDesc = super.getTrialDescription();
		Class<?> sourceFileClass = trialDesc.getSourceFileClass();
		
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
			coverage = rec.endRecording(Collections.singleton(sourceFileClass.getName()));
			I_SourceFileCoverage cover = trialDesc.findSourceFileCoverage(coverage);
			Class<?> srcFile = trialDesc.getSourceFileClass();
			
			if (cover != null) {
				trialResultMutant.setSourceFileCoverage(cover);
			} else if (srcFile != null && srcFile.isInterface()) {
				trialResultMutant.setSourceFileCoverage(new SourceFileCoverage());
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
				clazzMethod = parentClass.getDeclaredMethod(AFTER_TRIAL_TESTS, I_SourceFileTrialResult.class);
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
		testResultMutant.setName(I_SourceFileTrial.TEST_MIN_COVERAGE);
		
		TestResult result = new TestResult(testResultMutant);
		return result;
	}
	
	
	public TestResult testDependencies(SourceFileTrialResultMutant trialResultMutant) {
		TrialDescription trialDesc = super.getTrialDescription();
		TestResultMutant testResultMutant = new TestResultMutant();
		testResultMutant.setName(I_SourceFileTrial.TEST_DEPENDENCIES);
		I_CircularDependencies circle = trialDesc.getAllowedCircularDependencies();
		CircularDependencies c = CircularDependencies.get(circle);
		
		boolean failed = false;
		I_ClassDependenciesLocal deps = trialDesc.getSourceClassDependencies();
		if (deps != null) {
			switch (c) {
				case InnerClasses:
					if (deps.hasCircularDependencies()) {
						Set<String> classes = deps.getCircularDependenciesNames();
						Class<?> sourceClass = trialDesc.getSourceFileClass();
						String name = ClassMethods.getSourceClassName(sourceClass.getName());
						//a crude test for inner classes
						for (String className: classes) {
							className = ClassMethods.getSourceClassName(className);
							if ( !name.equals(className)) {
								classes.remove(name);
								classes.remove(sourceClass.getName());
								CircularDependencyFailureMutant tfm =failCircularDependencies(classes,  
										trialDesc.getSourceFileClass());
								tfm.setAllowedType(CircularDependencies.InnerClasses);
								testResultMutant.setFailure(new CircularDependencyFailure(tfm));
								failed = true;
								break;
							}
						}
						testResultMutant.incrementAssertionCount(1);
					}
					break;
				case WithInPackage:
					if (deps.hasCircularDependencies()) {
						Set<String> classes = deps.getCircularDependenciesNames();
						Class<?> sourceClass = trialDesc.getSourceFileClass();
						String sourcePackageName = ClassMethods.getPackageName(sourceClass.getName());
						//a crude test for inner classes
						Iterator<String> it = classes.iterator();
						while (it.hasNext()) {
							String className = it.next();
							String pacakgeName = ClassMethods.getPackageName(className);
							if (sourcePackageName.equals(pacakgeName)) {
								it.remove();
							}
						}
						if (classes.size() >= 1) {
							CircularDependencyFailureMutant tfm =failCircularDependencies(classes,  
									trialDesc.getSourceFileClass());
							tfm.setAllowedType(CircularDependencies.WithInPackage);
							testResultMutant.setFailure(new CircularDependencyFailure(tfm));
							failed = true;
						}
						testResultMutant.incrementAssertionCount(1);
					}
					break;
				case None:
				default:
					if (deps.hasCircularDependencies()) {
						Set<String> names = deps.getCircularDependenciesNames();
						Class<?> sourceClass = trialDesc.getSourceFileClass();
						
						if (names.size() == 1) {
							if (names.contains(sourceClass.getName())) {
								break;
							}
						}
						failed = true;
						CircularDependencyFailureMutant tfm = failCircularDependencies(deps.getCircularDependenciesNames(),  
								trialDesc.getSourceFileClass());
						tfm.setAllowedType(CircularDependencies.None);
						testResultMutant.setFailure(new CircularDependencyFailure(tfm));
					}
					testResultMutant.incrementAssertionCount(1);
			}
		}
		I_DependencyGroup dg =  trialDesc.getDependencyGroup();
		List<I_ClassAttributes> refs =  deps.getReferences();
		
		String sourceClassName = trialDesc.getSourceFileClass().getName();
		AllowedDependencyFailureMutant depNotAllowed = null;
		//set above for error as in 
		// org.adligo.something.ClassA void foo(int, byte) 
		if (dg != null) {
			final int prime = 31;
			int hashCounter = 0;
			for (I_ClassAttributes ref: refs) {
				String className = ref.getName();
				if ( !sourceClassName.equals(className)) {
					boolean result = false;
					try {
						result = dg.isInGroup(className);
					} catch (Exception x) {
						log.onThrowable(new IllegalStateException("Problem with dependency group in trial " +
								log.getLineSeperator() + trialDesc.getTrialName(), x));
					}
					
					if (result) {
						//this ref is expceted
						hashCounter = hashCounter * prime + className.hashCode();
					} else {
						Set<I_FieldSignature> flds = ref.getFields();
						for (I_FieldSignature fld: flds) {
							if ( !dg.isInGroup(className, fld)) {
								depNotAllowed = new AllowedDependencyFailureMutant();
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
									depNotAllowed = new AllowedDependencyFailureMutant();
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
				testResultMutant.setFailure(new AllowedDependencyFailure(depNotAllowed));
				testResultMutant.setPassed(false); 
			}
		} else {
			if (!failed) {
				testResultMutant.setPassed(true); 
			}
		}
		TestResult result = new TestResult(testResultMutant);
		return result;
	}


	public CircularDependencyFailureMutant failCircularDependencies(Collection<String> classesOutOfBounds,
			Class<?> sourceClass) {
		I_Tests4J_ResultMessages messages = Tests4J_Constants.CONSTANTS.getResultMessages();
		
		CircularDependencyFailureMutant tfm = new CircularDependencyFailureMutant();
		tfm.setFailureMessage(messages.getSourceClassHasCircularDependency());
		tfm.setAssertType(AssertType.AssertDependency);
		tfm.setClassesOutOfBounds(classesOutOfBounds);
		tfm.setSourceClass(sourceClass);
		tfm.setAssertType(AssertType.AssertEquals);
		return tfm;
	}
}
