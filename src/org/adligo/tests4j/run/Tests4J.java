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
import org.adligo.tests4j.run.helpers.TrialsProcessor;

public class Tests4J {
	public static final String NULL_I_TEST_RUN_LISTENER_NOT_ALLOWED = "Null I_TestRunListener not allowed.";
	

	
	
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

	

	
	
}
