package org.adligo.jtests.run;

import java.util.List;

import org.adligo.jtests.models.shared.I_AbstractTrial;
import org.adligo.jtests.models.shared.common.LineSeperator;
import org.adligo.jtests.models.shared.coverage.I_CoverageRecorder;
import org.adligo.jtests.models.shared.coverage.I_PackageCoverage;
import org.adligo.jtests.models.shared.results.I_TrialResult;
import org.adligo.jtests.models.shared.results.I_TrialRunResult;
import org.adligo.jtests.models.shared.results.TrialRunResult;
import org.adligo.jtests.models.shared.results.TrialRunResultMutant;
import org.adligo.jtests.models.shared.system.I_TestResultsProcessor;
import org.adligo.jtests.models.shared.system.I_TestRunListener;
import org.adligo.jtests.models.shared.system.RunParameters;

public class JTests implements I_TestRunListener, I_JTests {private I_TestResultsProcessor testResultProcessor;
	
	private I_CoverageRecorder recorder;
	
	public JTests() {}
	
	/* (non-Javadoc)
	 * @see org.adligo.jtests.run.I_JTests#run(org.adligo.jtests.models.shared.system.RunParameters, org.adligo.jtests.models.shared.system.I_TestRunListener)
	 */
	@Override
	public void run(RunParameters pParams, I_TestRunListener pProcessor) {
		LineSeperator.setLineSeperator(System.lineSeparator());
		runInternal(pParams, pProcessor);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.jtests.run.I_JTests#run(org.adligo.jtests.models.shared.system.RunParameters)
	 */
	@Override
	public void run(RunParameters pParams) {
		LineSeperator.setLineSeperator(System.lineSeparator());
		runInternal(pParams, this);
	}
	
	private void runInternal(final RunParameters pParams, I_TestRunListener pListener) {
		TrialProcessor runner = new TrialProcessor(
				pParams.getTrials(),pListener);
		
		
		runner.setSilent(pParams.isSilent());
		recorder =  pParams.getCoverageRecorder();
		if (recorder != null) {
			recorder.startRecording();
		}
		runner.run();
	}

	@Override
	public void onTestCompleted(Class<? extends I_AbstractTrial> testClass,
			I_AbstractTrial test, I_TrialResult result) {
		testResultProcessor.process(result);
	}

	@Override
	public void onRunCompleted(I_TrialRunResult result) {
		if (recorder != null) {
			List<I_PackageCoverage> allCoverage = recorder.getCoverage();
			TrialRunResultMutant trrm = new TrialRunResultMutant(result);
			trrm.setCoverage(allCoverage);
			result = new TrialRunResult(trrm);
		}
		testResultProcessor.process(result);
		System.exit(0);
	}
	

}
