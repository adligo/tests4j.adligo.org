package org.adligo.jtests.run;

import java.util.List;

import org.adligo.jtests.models.shared.I_AbstractTrial;
import org.adligo.jtests.models.shared.common.LineSeperator;
import org.adligo.jtests.models.shared.coverage.I_PackageCoverage;
import org.adligo.jtests.models.shared.results.I_TrialResult;
import org.adligo.jtests.models.shared.results.I_TrialRunResult;
import org.adligo.jtests.models.shared.results.TrialRunResult;
import org.adligo.jtests.models.shared.results.TrialRunResultMutant;
import org.adligo.jtests.models.shared.system.I_CoverageRecorder;
import org.adligo.jtests.models.shared.system.I_TrialResultsProcessor;
import org.adligo.jtests.models.shared.system.I_TrialRunListener;
import org.adligo.jtests.models.shared.system.JTestParameters;

public class JTests implements I_TrialRunListener, I_JTests {
	public static final String NULL_I_TEST_RUN_LISTENER_NOT_ALLOWED = "Null I_TestRunListener not allowed.";
	private I_TrialRunListener trialRunListener;
	
	private I_CoverageRecorder recorder;
	
	public JTests() {
		Thread.setDefaultUncaughtExceptionHandler(new JTestUncaughtExceptionHandler());
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.jtests.run.I_JTests#run(org.adligo.jtests.models.shared.system.RunParameters, org.adligo.jtests.models.shared.system.I_TestRunListener)
	 */
	@Override
	public void run(JTestParameters pParams, I_TrialRunListener pListener) {
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
	public void run(JTestParameters pParams) {
		LineSeperator.setLineSeperator(System.lineSeparator());
		runInternal(pParams, this);
	}
	
	private void runInternal(final JTestParameters pParams, I_TrialRunListener pListener) {
		recorder =  pParams.getCoverageRecorder();
		TrialProcessor runner = new TrialProcessor(
				pParams.getTrials(),this, recorder);
		trialRunListener = pListener;
		
		runner.setSilent(pParams.isSilent());
		
		if (recorder != null) {
			recorder.startRecording();
		}
		runner.run();
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
	

}
