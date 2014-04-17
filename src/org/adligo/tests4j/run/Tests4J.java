package org.adligo.tests4j.run;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.AfterTrial;
import org.adligo.tests4j.models.shared.BeforeTrial;
import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.IgnoreTest;
import org.adligo.tests4j.models.shared.IgnoreTrial;
import org.adligo.tests4j.models.shared.PackageScope;
import org.adligo.tests4j.models.shared.SourceFileScope;
import org.adligo.tests4j.models.shared.TrialType;
import org.adligo.tests4j.models.shared.UseCaseScope;
import org.adligo.tests4j.models.shared.common.I_Immutable;
import org.adligo.tests4j.models.shared.common.LineSeperator;
import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.models.shared.results.TrialRunResult;
import org.adligo.tests4j.models.shared.results.TrialRunResultMutant;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.Tests4J_Params;

public class Tests4J implements I_TrialRunListener, I_Tests4J {
	public static final String NULL_I_TEST_RUN_LISTENER_NOT_ALLOWED = "Null I_TestRunListener not allowed.";
	
	/**
	 * these are enums, interfaces and other classes
	 * that have NO methods or runtime code 
	 * which are loaded by this class's
	 * parent classloader so that wierd .
	 * These are stored here for testing of this class.
	 */
	public static Set<Class<?>> COMMON_CLASSES = getCommonClasses();
	
	
	private I_TrialRunListener trialRunListener;
	
	private I_CoverageRecorder recorder;
	
	public Tests4J() {
		Thread.setDefaultUncaughtExceptionHandler(new Tests4J_UncaughtExceptionHandler());
	}
	
	private static void logInternal(String p) {
		System.out.println("Tests4J; " + p);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.jtests.run.I_JTests#run(org.adligo.jtests.models.shared.system.RunParameters, org.adligo.jtests.models.shared.system.I_TestRunListener)
	 */
	@Override
	public void run(Tests4J_Params pParams, I_TrialRunListener pListener) {
		LineSeperator.setLineSeperator(System.lineSeparator());
		if (pListener == null) {
			throw new IllegalArgumentException(NULL_I_TEST_RUN_LISTENER_NOT_ALLOWED);
		}
		runInternal(pParams, pListener);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.jtests.run.I_JTests#run(org.adligo.jtests.models.shared.system.RunParameters)
	 */
	@Override
	public void run(Tests4J_Params pParams) {
		LineSeperator.setLineSeperator(System.lineSeparator());
		runInternal(pParams, this);
	}
	
	private void runInternal(final Tests4J_Params pParams, I_TrialRunListener pListener) {
		recorder =  pParams.getCoverageRecorder();
		trialRunListener = pListener;
		
		
		if (recorder != null) {
			recorder.startRecording();
		}
		/** TODO
		TrialInstanceProcessor runner = new TrialInstanceProcessor(
				pParams.getTrials(),this, recorder);
		runner.run();
		*/
	}

	@Override
	public void onTestCompleted(Class<? extends I_AbstractTrial> testClass,
			I_AbstractTrial test, I_TrialResult result) {
		if (trialRunListener != null) {
			trialRunListener.onTestCompleted(testClass, test, result);
		}
	}

	@Override
	public void onRunCompleted(I_TrialRunResult result) {
		if (recorder != null) {
			List<I_PackageCoverage> allCoverage = recorder.getCoverage();
			TrialRunResultMutant trrm = new TrialRunResultMutant(result);
			trrm.setCoverage(allCoverage);
			result = new TrialRunResult(trrm);
		}
		if (trialRunListener != null) {
			trialRunListener.onRunCompleted(result);
		} else {
			System.exit(0);
		}
	}
	
	
	/**
	 * this is the set of common classes that 
	 * @return
	 */
	private static Set<Class<?>> getCommonClasses() {
		Set<Class<?>> toRet = new HashSet<Class<?>>();
		//start with common
		toRet.add(TrialTypeEnum.class);
		
		toRet.add(I_Immutable.class);
		
		//end with shared/
		toRet.add(AfterTrial.class);
		toRet.add(BeforeTrial.class);
		toRet.add(IgnoreTest.class);
		toRet.add(IgnoreTrial.class);
		toRet.add(PackageScope.class);
		toRet.add(SourceFileScope.class);
		toRet.add(TrialType.class);
		toRet.add(UseCaseScope.class);
		
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		for (Class<?> c: toRet) {
			try {
				cl.loadClass(c.getName());
				logInternal("Loaded Class " + c.getName());
			} catch (ClassNotFoundException x) {
				throw new RuntimeException(x);
			}
		}
		return Collections.unmodifiableSet(toRet);
	}

	
	
}
