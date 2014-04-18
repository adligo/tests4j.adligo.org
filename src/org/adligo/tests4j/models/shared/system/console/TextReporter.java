package org.adligo.tests4j.models.shared.system.console;

import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.line_text.LineTextCompareResult;
import org.adligo.tests4j.models.shared.results.I_TestFailure;
import org.adligo.tests4j.models.shared.results.I_TestResult;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;

public class TextReporter {
	private I_Tests4J_Logger log;
	
	public TextReporter(I_Tests4J_Logger p) {
		log = p;
	}
	
	public void printTestCompleted( I_TrialResult result) {
		if (!log.isEnabled()) {
			return;
		}
		if (result.isPassed()) {
			log.log("The trial " + result.getName() + " passed!");
			log.log("Tests: " + result.getTestCount() + 
					" Assertions: " + result.getUniqueAssertionCount() + "/" + 
					result.getAssertionCount());
		} else {
			log.log("The trial " + result.getName() + " FAILED!");
			I_TrialFailure failure = result.getFailure();
			if (failure != null) {
				printFailure(failure);
			} else {
				List<I_TestResult> ers =  result.getResults();
				for (I_TestResult er: ers) {
					if (!er.isPassed()) {
						printFailure(er.getFailure());
					}
				}
			}
		}
	}
	
	private void printFailure(I_TestFailure failure) {
		log.log(failure.getMessage());
		
		I_AssertionData data = failure.getData();
		if (data != null) {
			Set<String> keys = data.getKeys();
			if (keys.contains(I_AssertionData.LINE_TEXT_RESULT)) {
				LineTextCompareResult result = (LineTextCompareResult)
						data.getData(I_AssertionData.LINE_TEXT_RESULT);
				LineTextComparisonReport.display(log, result);
			} else {
				for (String key: keys) {
					log.log(key + "=" + data.getData(key));
				}
			}
			log.log(failure.getMessage());
		}
		Throwable location = failure.getLocationFailed();
		if (location != null) {
			location.printStackTrace(log.getOutput());
		} else {
			Throwable exception = failure.getException();
			exception.printStackTrace(log.getOutput());
		}
	}


	private void printFailure(I_TrialFailure failure) {
		log.log(failure.getMessage());
		Throwable exception = failure.getException();
		exception.printStackTrace(log.getOutput());
	}
}
