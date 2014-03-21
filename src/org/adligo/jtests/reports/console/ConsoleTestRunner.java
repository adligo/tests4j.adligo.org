package org.adligo.jtests.reports.console;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.adligo.jtests.models.shared.I_AbstractTrial;
import org.adligo.jtests.models.shared.asserts.I_AssertionData;
import org.adligo.jtests.models.shared.asserts.line_text.LineTextCompareResult;
import org.adligo.jtests.models.shared.results.I_TestFailure;
import org.adligo.jtests.models.shared.results.I_TestResult;
import org.adligo.jtests.models.shared.results.I_TrialFailure;
import org.adligo.jtests.models.shared.results.I_TrialResult;
import org.adligo.jtests.models.shared.results.I_TrialRunResult;
import org.adligo.jtests.models.shared.system.I_TestRunListener;
import org.adligo.jtests.models.shared.system.RunParameters;
import org.adligo.jtests.run.JTests;

public class ConsoleTestRunner implements I_TestRunListener {
	private PrintStream originalOut = System.out;
	private PrintStream originalErr = System.err;
	private int allTests;
	private int passedTrials;
	private int tests;
	private int asserts;
	private int uniqueAsserts;
	private int minTests = 0;
	private int minAsserts = 0;
	private int minUniqueAssertions = 0;
	private boolean checkMinimums = false;
	
	public static void run(I_RunList runLists, boolean pCheckMinimums) {
		run(Collections.singletonList(runLists), pCheckMinimums);
	}
	
	public static void run(List<I_RunList> runLists, boolean pCheckMinimums) {
		ConsoleTestRunner ctr = new ConsoleTestRunner();
		ctr.runInternal(runLists, pCheckMinimums);
	}
	
	private void runInternal(List<I_RunList> runLists, boolean pCheckMinimums) {
		try {
			checkMinimums = pCheckMinimums;
			List<Class<? extends I_AbstractTrial>> all = new ArrayList<Class<? extends I_AbstractTrial>>();
			for (I_RunList rl: runLists) {
				all.addAll(rl.getTrials());
				minTests = minTests + rl.getMinTests();
				minAsserts = minAsserts + rl.getMinAssertions();
				minUniqueAssertions = minUniqueAssertions + rl.getMinUniqueAssertions();
			}
			RunParameters params = new RunParameters();
			params.setTrials(all);
			allTests = all.size();
			
			
			System.out.println("Running all tests ");
			JTests.run(params, this);
		} catch (Exception x) {
			x.printStackTrace(originalErr);
		}
	}

	private void printFailure(I_TestFailure failure) {
		originalOut.println(failure.getMessage());
		
		I_AssertionData data = failure.getData();
		if (data != null) {
			Set<String> keys = data.getKeys();
			if (keys.contains(I_AssertionData.LINE_TEXT_RESULT)) {
				LineTextCompareResult result = (LineTextCompareResult)
						data.getData(I_AssertionData.LINE_TEXT_RESULT);
				LineTextComparisonReport.display(originalOut, originalErr, result);
			} else {
				for (String key: keys) {
					originalOut.println(key + "=" + data.getData(key));
				}
			}
			originalOut.println(failure.getMessage());
		}
		Throwable location = failure.getLocationFailed();
		if (location != null) {
			location.printStackTrace(originalOut);
		} else {
			Throwable exception = failure.getException();
			exception.printStackTrace(originalOut);
		}
	}


	private void printFailure(I_TrialFailure failure) {
		originalOut.println(failure.getMessage());
		Throwable exception = failure.getException();
		exception.printStackTrace(originalOut);
	}
	
	@Override
	public void onRunCompleted(I_TrialRunResult result) {
		originalOut.println("Tests completed in " + result.getRunTimeSecs() + " secs");
		
		if (!checkMinimums) {
			printAllPassed(result);
		} else {
			if (tests >= minTests && asserts >= minAsserts && uniqueAsserts >= minUniqueAssertions) {
				printAllPassed(result);
			} else if (tests < minTests) {
				originalOut.println("Not enough tests expected at least "
						+ minTests + " and there were " + tests);
			} else if (uniqueAsserts < minUniqueAssertions) {
				originalOut.println("Not enough unique assertions expected at least "
						+ minUniqueAssertions + " and there were " + uniqueAsserts + "/" + asserts);
			} else {
				originalOut.println("Not enough assert expected at least "
						+ minAsserts + " and there were " + asserts + "/" + asserts);
			}
		}
		System.exit(0);
	}

	private void printAllPassed(I_TrialRunResult result) {
		originalOut.println("");
		originalOut.println("All " + allTests + " trials passed sucessfully in " + result.getRunTimeSecs() + " secs");
		originalOut.println("" + tests + " tests and " + uniqueAsserts + "/" + asserts + " asserts");
	}

	@Override
	public void onTestCompleted(Class<? extends I_AbstractTrial> testClass,
			I_AbstractTrial test, I_TrialResult result) {
		if (result.isPassed()) {
			passedTrials++;
			originalOut.println("The trial " + result.getName() + " passed!");
			tests = tests + result.getTestCount();
			asserts = asserts + result.getAssertionCount();
			uniqueAsserts = uniqueAsserts + result.getUniqueAssertionCount();
			originalOut.println("Tests: " + result.getTestCount() + 
					" Assertions: " + result.getUniqueAssertionCount() + "/" + 
					result.getAssertionCount());
		} else {
			originalOut.println("The trial " + result.getName() + " FAILED!");
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

	public int getMinTests() {
		return minTests;
	}

	public int getMinAsserts() {
		return minAsserts;
	}

	public void setMinTests(int minTests) {
		this.minTests = minTests;
	}

	public void setMinAsserts(int minAsserts) {
		this.minAsserts = minAsserts;
	}

	public int getMinUniqueAssertions() {
		return minUniqueAssertions;
	}

	public void setMinUniqueAssertions(int minUniqueAssertions) {
		this.minUniqueAssertions = minUniqueAssertions;
	}

	public boolean isCheckMinimums() {
		return checkMinimums;
	}

	public void setCheckMinimums(boolean checkMinimums) {
		this.checkMinimums = checkMinimums;
	}

}
