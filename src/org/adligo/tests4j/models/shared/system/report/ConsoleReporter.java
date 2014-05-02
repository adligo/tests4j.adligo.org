package org.adligo.tests4j.models.shared.system.report;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.I_CompareAssertionData;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TestFailure;
import org.adligo.tests4j.models.shared.results.I_TestResult;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;

public class ConsoleReporter implements I_Tests4J_Reporter {
	public static final String DEFAULT_PREFIX = "Tests4J: ";
	
	private String prefix = DEFAULT_PREFIX;
	private boolean snare = false;
	private boolean redirect = true;
	private Set<String> enabledLogClasses = new HashSet<String>();
	private List<I_TrialResult> failedTrials = new ArrayList<I_TrialResult>();
	private I_LineOut out = new SystemOut();
	
	public ConsoleReporter() {
		setLogOn(ConsoleReporter.class.getName());
	}
	
	public ConsoleReporter(I_LineOut p) {
		setLogOn(ConsoleReporter.class.getName());
		out = p;
	}
	
	@Override
	public void onMetadataCalculated(I_TrialRunMetadata metadata) {
		if (isLogEnabled(ConsoleReporter.class.getName())) {
			log("Metadata Calculated: " + metadata.getTrialCount() + " trials with " +
					metadata.getTestCount() + " tests.");
		}
	}

	@Override
	public synchronized void onStartingTrail(String trialName) {
		if (isLogEnabled(SystemOut.class.getName())) {
			log("startingTrial: " + trialName);
		}
	}

	@Override
	public synchronized void onStartingTest(String trialName, String testName) {
		if (isLogEnabled(SystemOut.class.getName())) {
			log("startingTest: " + trialName + "." + testName);
		}
	}

	@Override
	public synchronized void onTestCompleted(String trialName, String testName,
			boolean passed) {
		if (isLogEnabled(SystemOut.class.getName())) {
			String passedString = " passed!";
			if (!passed) {
				passedString = " failed!";
			}
			log("Test: " + trialName + "." + testName + passedString);
		}
	}

	@Override
	public synchronized void onTrialCompleted(I_TrialResult result) {
		if (isLogEnabled(ConsoleReporter.class.getName())) {
			String passedString = " passed!";
			if (!result.isPassed()) {
				passedString = " failed!";
				failedTrials.add(result);
			}
			log("Trial: " + result.getName() + passedString);
		}
	}

	@Override
	public void onRunCompleted(I_TrialRunResult result) {
		if (isLogEnabled(ConsoleReporter.class.getName())) {
			log("Tests completed in " + result.getRunTimeSecs() + " seconds.");
			if (result.getTrialsPassed() == result.getTrials()) {
				log("All Trials " + result.getTrialsPassed()  + "/" 
						+ result.getTrials() + " passed!");
				log("Tests: " + result.getTests() + " Assertions: " + 
						result.getUniqueAsserts() + "/" +
						result.getAsserts());
			} else {
				for (I_TrialResult trial: failedTrials) {
					logTestFailure(trial);
				}
				log("" + result.getTrialFailures()  + "/" 
						+ result.getTrials() + " Trials failed!");
				log("Tests " + result.getTestFailures() + "/" +
						result.getTests() + " Assertions: " + 
						result.getUniqueAsserts() + "/" +
						result.getAsserts());
			}
		}
	}

	private void logTestFailure(I_TrialResult trial) {
		log("" + trial.getName() + " failed!");
		List<I_TestResult> testResults = trial.getResults();
		for (I_TestResult tr: testResults) {
			if (!tr.isPassed()) {
				log("\t" + tr.getName() + " failed!");
				I_TestFailure tf = tr.getFailure();
				log("\t" + tf.getMessage());
				Throwable t = tf.getLocationFailed();
				I_AssertionData ad =  tf.getData();
				if (ad instanceof I_CompareAssertionData) {
					I_CompareAssertionData<?> cad = (I_CompareAssertionData<?>) ad;
					log("\tExpected;");
					log("\t" + cad.getExpected());
					log("\tActual;");
					log("\t" + cad.getActual());
				}
				if (t == null) {
					log("\tUnknown Location a Test4J error please try to reproduce and report it;");
				} else {
					logThrowable("\t",t);
				}
			}
		}
	}

	private void logThrowable(String indent, Throwable t) {
		StackTraceElement [] stack = t.getStackTrace();
		log(indent + t.toString());
		for (int i = 0; i < stack.length; i++) {
			log(indent + indent +"at " + stack[i]);
		}
	}

	@Override
	public synchronized void log(String p) {
		out.println(prefix + p);
	}

	@Override
	public void onError(Throwable p) {
		logThrowable("", p);
	}

	@Override
	public boolean isLogEnabled(String clazzName) {
		if (enabledLogClasses.contains(clazzName)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isSnare() {
		return snare;
	}
	
	public synchronized void setLogOn(String clazzName)  {
		enabledLogClasses.add(clazzName);
	}

	public synchronized void setLogOff(String clazzName)  {
		enabledLogClasses.remove(clazzName);
	}
	
	public synchronized void setLogOff()  {
		enabledLogClasses.clear();
	}
	
	public void setSnare(boolean p) {
		snare = p;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public boolean isRedirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}
}
