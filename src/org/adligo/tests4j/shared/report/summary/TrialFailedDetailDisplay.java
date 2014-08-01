package org.adligo.tests4j.shared.report.summary;

import java.util.List;

import org.adligo.tests4j.models.shared.asserts.ContainsAssertCommand;
import org.adligo.tests4j.models.shared.asserts.StringCompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.ThrownAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.models.shared.results.I_TestFailure;
import org.adligo.tests4j.models.shared.results.I_TestResult;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;

public class TrialFailedDetailDisplay {
	private I_Tests4J_Log log;
	
	public TrialFailedDetailDisplay(I_Tests4J_Log pLog) {
		log = pLog;
	}

	public void logTrialFailure(I_TrialResult trial) {
		if ( !log.isLogEnabled(TrialFailedDetailDisplay.class)) {
			return;
		}
		String trialName = trial.getName();
		log.log("" + trialName + " failed!");
		
		I_TrialFailure failure =  trial.getFailure();
		if (failure != null) {
			log.log("\t" + failure.getMessage());
			Throwable throwable = failure.getException();
			if (throwable != null) {
				logThrowable("\t", throwable);
			}
		}
		List<I_TestResult> testResults = trial.getResults();
		for (I_TestResult tr: testResults) {
			if (!tr.isPassed() && !tr.isIgnored()) {
				String testName = tr.getName();
				log.log("\t" + trialName + "."  + testName + " failed!");
				I_TestFailure tf = tr.getFailure();
				log.log("\t" + tf.getMessage());
				Throwable t = tf.getLocationFailed();
				I_AssertionData ad =  tf.getData();
				
				if (ad instanceof ContainsAssertCommand) {
					log.log("\tExpected;");
					log.log("\t'" + ad.getData(ContainsAssertCommand.VALUE) + "'");
				} else if (ad instanceof ThrownAssertionData) {
					logThrowableFailure((ThrownAssertionData) ad);
				} else if (ad instanceof StringCompareAssertionData) {
					logCompareFailure((StringCompareAssertionData) ad);
				} else if (ad instanceof I_CompareAssertionData) {
					logCompareFailure((I_CompareAssertionData<?>) ad);
				} 
				if (t == null) {
					log.log("\tUnknown Location a Test4J error please try to reproduce and report it;");
				} else {
					logThrowable("\t",t);
				}
			}
		}
	}

	private void logCompareFailure(StringCompareAssertionData ad) {
		I_TextLinesCompareResult result =  (I_TextLinesCompareResult) ad.getData(StringCompareAssertionData.COMPARISON);
		LineDiffTextDisplay lineDiffTextDisplay = new LineDiffTextDisplay();
		lineDiffTextDisplay.display(log, result, 3);
	}
	
	private void logCompareFailure(I_CompareAssertionData<?> ad) {
		log.log("\tExpected;");
		Object expected = ad.getExpected();
		if (expected != null) {
			log.log("\t\tClass: " + expected.getClass());
		}
		log.log("\t\t'" + expected + "'");
		log.log("\tActual;");
		Object actual = ad.getActual();
		if (actual != null) {
			log.log("\t\tClass: " + actual.getClass());
		}
		log.log("\t\t'" + actual + "'");
	}
	
	private void logThrowableFailure(ThrownAssertionData ad) {
		Class<? extends Throwable> actualThrow = ad.getActualThrowable();
		Class<? extends Throwable> expectedThrow = ad.getExpectedThrowable();
		if (!expectedThrow.equals(actualThrow)) {
			log.log("\tExpected;");
			log.log("\t" + expectedThrow);
			log.log("\tActual;");
			log.log("\t" + actualThrow);
		} else {
			String expectedMessage = ad.getExpectedMessage();
			String actualMessage = ad.getActualMessage();
			log.log("\tThrowable classes matched: " + expectedThrow);
			log.log("\tExpected;");
			log.log("\t" + expectedMessage);
			log.log("\tActual;");
			log.log("\t" + actualMessage);
		}
	}


	private void logThrowable(String indentString, Throwable t) {
		logThrowable(indentString, indentString, t);
	}
	
	private void logThrowable(String currentIndent, String indentString, Throwable t) {
		StackTraceElement [] stack = t.getStackTrace();
		log.log(currentIndent + t.toString());
		for (int i = 0; i < stack.length; i++) {
			log.log(currentIndent +"at " + stack[i]);
		}
		Throwable cause = t.getCause();
		if (cause != null) {
			logThrowable(currentIndent + indentString, indentString,  cause);
		}
	}
}
