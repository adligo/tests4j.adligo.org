package org.adligo.tests4j.run;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.AfterTrial;
import org.adligo.tests4j.models.shared.BeforeTrial;
import org.adligo.tests4j.models.shared.IgnoreTest;
import org.adligo.tests4j.models.shared.IgnoreTrial;
import org.adligo.tests4j.models.shared.PackageScope;
import org.adligo.tests4j.models.shared.SourceFileScope;
import org.adligo.tests4j.models.shared.TrialType;
import org.adligo.tests4j.models.shared.UseCaseScope;
import org.adligo.tests4j.models.shared.common.I_Immutable;
import org.adligo.tests4j.models.shared.common.LineSeperator;
import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.Tests4J_Params;

public class Tests4J {
	public static final String NULL_I_TEST_RUN_LISTENER_NOT_ALLOWED = "Null I_TestRunListener not allowed.";
	
	/**
	 * these are enums, interfaces and other classes
	 * that have NO methods or runtime code 
	 * which are loaded by this class's
	 * parent classloader so that wierd .
	 * These are stored here for testing of this class.
	 */
	public static List<Class<?>> COMMON_CLASSES = getCommonClasses();
	
	
	static {
		Thread.setDefaultUncaughtExceptionHandler(new Tests4J_UncaughtExceptionHandler());
	}
	
	
	public static void run(Tests4J_Params pParams, I_TrialRunListener pListener) {
		LineSeperator.setLineSeperator(System.lineSeparator());
		if (pListener == null) {
			throw new IllegalArgumentException(NULL_I_TEST_RUN_LISTENER_NOT_ALLOWED);
		}
		runInternal(pParams, pListener);
	}
	
	public static void run(Tests4J_Params pParams) {
		LineSeperator.setLineSeperator(System.lineSeparator());
		runInternal(pParams, null);
	}
	
	private static void runInternal(final Tests4J_Params pParams, I_TrialRunListener pListener) {
		/*
		recorder =  pParams.getCoverageRecorder();
		trialRunListener = pListener;
		
		
		if (recorder != null) {
			recorder.startRecording();
		}
		*/
		new TrialsProcessor(pParams,pListener);
		/** TODO
		TrialInstanceProcessor runner = new TrialInstanceProcessor(
				pParams.getTrials(),this, recorder);
		runner.run();
		*/
	}

	
	/**
	 * this is the set of common classes that 
	 * @return
	 */
	private static List<Class<?>> getCommonClasses() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
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
		return Collections.unmodifiableList(toRet);
	}


	private static void logInternal(String p) {
		System.out.println("Tests4J; " + p);
	}
	
	
}
