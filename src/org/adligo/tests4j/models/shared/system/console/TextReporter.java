package org.adligo.tests4j.models.shared.system.console;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adligo.tests4j.models.shared.asserts.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.line_text.LineTextCompareResult;
import org.adligo.tests4j.models.shared.results.I_TestFailure;
import org.adligo.tests4j.models.shared.results.I_TestResult;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Reporter;

public class TextReporter {
	private I_Tests4J_Reporter reporter;
	private List<I_TrialFailure> failures = new CopyOnWriteArrayList<I_TrialFailure>();
	
	public TextReporter(I_Tests4J_Reporter p) {
		reporter = p;
	}
	
	public void onRunCompleted(I_TrialRunResult result) {
		reporter.log("Tests completed in " + result.getRunTimeSecs() + " secs");
		
		if (result.getTestFailures() == 0) {
			reporter.log("All " + result.getTrials() + " trials passed sucessfully!");
			reporter.log("Tests: " + result.getTests());
		} else {
			reporter.log("" + result.getTrialFailures() + "/" + result.getTrials() + " trials failed!");
			reporter.log("Tests: " + result.getTestsPassed() + "/" + result.getTests());
		}
		reporter.log("Assertions: " + result.getUniqueAsserts() + "/" + result.getAsserts());
		if (failures.size() >= 1) {
			reporter.log("The following trials failed;");
		}
		for (I_TrialFailure tf: failures) {
			//log.log(tf.);
			
		}
	}
	
	public void printTestCompleted( I_TrialResult result) {
		if (result.isPassed()) {
			reporter.log("The trial " + result.getName() + " passed!");
			reporter.log("Tests: " + result.getTestCount() + 
					" Assertions: " + result.getUniqueAssertionCount() + "/" + 
					result.getAssertionCount());
		} else {
			reporter.log("The trial " + result.getName() + " FAILED!");
			I_TrialFailure failure = result.getFailure();
			if (failure != null) {
				printFailure(failure);
			} else {
				List<I_TestResult> ers =  result.getResults();
				for (I_TestResult er: ers) {
					if (!er.isPassed()) {
						//printFailure(er.getFailure());
					}
				}
			}
		}
	}
	
	private void printFailure(I_TestFailure failure) {
		reporter.log(failure.getMessage());
		failures.add(failure);
		
		I_AssertionData data = failure.getData();
		if (data != null) {
			Set<String> keys = data.getKeys();
			if (keys.contains(I_AssertionData.LINE_TEXT_RESULT)) {
				LineTextCompareResult result = (LineTextCompareResult)
						data.getData(I_AssertionData.LINE_TEXT_RESULT);
				LineTextComparisonReport.display(reporter, result);
			} else {
				for (String key: keys) {
					reporter.log(key + "=" + data.getData(key));
				}
			}
			reporter.log(failure.getMessage());
		}
		Throwable location = failure.getLocationFailed();
		if (location != null) {
			reporter.onError(location);
		} else {
			Throwable exception = failure.getException();
			reporter.onError(exception);
		}
	}


	private void printFailure(I_TrialFailure failure) {
		reporter.log(failure.getMessage());
		Throwable exception = failure.getException();
		reporter.onError(exception);
	}
}
