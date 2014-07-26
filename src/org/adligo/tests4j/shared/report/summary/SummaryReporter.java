package org.adligo.tests4j.shared.report.summary;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.adligo.tests4j.models.shared.asserts.ContainsAssertCommand;
import org.adligo.tests4j.models.shared.asserts.StringCompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.ThrownAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.models.shared.common.LineSeperator;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.metadata.RelevantClassesWithTrialsCalculator;
import org.adligo.tests4j.models.shared.results.I_TestFailure;
import org.adligo.tests4j.models.shared.results.I_TestResult;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.models.shared.system.DefaultLog;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Listener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

public class SummaryReporter implements I_Tests4J_Listener  {
	

	
	private Map<String,AbstractProgressDisplay> processes = new HashMap<String,AbstractProgressDisplay>();
	
	private boolean snare = false;
	private boolean redirect = true;
	private Set<String> enabledLogClasses = new HashSet<String>();
	
	private I_Tests4J_Log logger;
	private boolean listRelevantClassesWithoutTrials = false;
	private I_TrialRunMetadata metadata;
	private TestsDisplay testsReporter;
	private TrialsDisplay trialsReporter;
	
	public SummaryReporter() {
		this(new DefaultLog());
	}
	
	public SummaryReporter(I_Tests4J_Log p) {
		logger = p;
		testsReporter = new TestsDisplay(p);
		trialsReporter = new TrialsDisplay(p);
		processes.put("setup", new SetupProgressDisplay(p));
		processes.put("trials", new TrialsProgressDisplay(p));
		processes.put("tests", new TestsProgressDisplay(p));
	}
	
	@Override
	public synchronized  void onMetadataCalculated(I_TrialRunMetadata p) {
		I_Tests4J_ReportMessages messages =  Tests4J_Constants.CONSTANTS.getReportMessages();
		
		logger.log(I_Tests4J_Constants.PREFIX +  messages.getMetadataCalculatedHeading() + LineSeperator.getLineSeperator() +
				I_Tests4J_Constants.PREFIX + messages.getTrialsHeading() + p.getAllTrialsCount() + LineSeperator.getLineSeperator() +
			 	I_Tests4J_Constants.PREFIX + messages.getTestsHeading() + p.getAllTestsCount()
			 	+  LineSeperator.getLineSeperator());
			
		metadata = p;
	}

	@Override
	public synchronized void onStartingTrial(String trialName) {
		trialsReporter.onStartingTrial(trialName);
	}

	@Override
	public synchronized void onStartingTest(String trialName, String testName) {
		testsReporter.onStartingTest(trialName, testName);
	}

	@Override
	public synchronized void onTestCompleted(String trialName, String testName,
			boolean passed) {
		testsReporter.onTestCompleted(trialName, testName, passed);
	}

	@Override
	public synchronized void onTrialCompleted(I_TrialResult result) {
		trialsReporter.onTrialCompleted(result);
	}

	@Override
	public synchronized void onRunCompleted(I_TrialRunResult result) {
		logger.log("------------------------Test Results-------------------------");
		logger.log("\t\tTests completed in " + result.getRunTimeSecs() + " seconds on ");
		DecimalFormat formatter = new DecimalFormat("###.##");
		
		RelevantClassesWithTrialsCalculator calc = new RelevantClassesWithTrialsCalculator(metadata);
		
		double pctD = calc.getPct();
		
		logger.log("\t\t" + formatter.format(pctD) + 
				"% of relevant classes have corresponding trials.");
		if (listRelevantClassesWithoutTrials) {
			Set<String> classes = calc.getClassesWithOutTrials();
			for (String clazz: classes) {
				logger.log("\t\t" + clazz);
			}
		}
		BigDecimal pct = logCoverage(result,  formatter );
		
		
		if (result.getTrialsPassed() == result.getTrials()) {
			logger.log("\t\tTests: " + result.getTestsPassed() + "/" +
					result.getTests());
			logger.log("\t\tUnique/Assertions: " + 
					result.getUniqueAsserts() + "/" +
					result.getAsserts());
			logger.log("\t\tAll Trials " + result.getTrialsPassed()  + "/" 
					+ result.getTrials() + " Trials with " + formatter.format(pct) + "% coverage;");
			logger.log("");
			logger.log("\t\t\tPassed!");
			if (trialsReporter.isHadTrialTestsWhichDidNOTRun()) {
				logger.log("\t\tWarning afterTrialTests methods/assertions did not run!");
			}
			logger.log("");
		} else {
			List<I_TrialResult> failedTrials = trialsReporter.getFailedTrials();
			for (I_TrialResult trial: failedTrials) {
				logTrialFailure(trial);
			}
			logger.log("\t\tTests: " + result.getTestsPassed() + "/" +
					result.getTests());
			logger.log("\t\tUnique/Assertions: " + 
					result.getUniqueAsserts() + "/" +
					result.getAsserts());
			logger.log("\t\t" + result.getTrialFailures()  + "/" 
					+ result.getTrials() + " Trials with " + formatter.format(pct) + "% coverage;");
			logger.log("");
			logger.log("\t\t\tFAILED!");
			logger.log("");
			
		}
		
		logger.log("------------------------Test Results End---------------------");
	}

	

	private BigDecimal logCoverage(I_TrialRunResult result, DecimalFormat formatter) {
		List<I_PackageCoverage> coverage = result.getCoverage();
		if (coverage.size() >= 1) {
			logger.log("\t\tPackage Coverage;");
		}
		Map<String, I_PackageCoverage> treeMap = new TreeMap<String, I_PackageCoverage>();
		for (I_PackageCoverage cover: coverage) {
			treeMap.put(cover.getPackageName(), cover);
		}
		Collection<I_PackageCoverage> ordered =  treeMap.values();
		for (I_PackageCoverage cover: ordered) {
			Set<String> sourceFileNames = cover.getSourceFileNames();
			logger.log("\t\t\t+" + cover.getPackageName() + " was covered " + 
						formatter.format(cover.getPercentageCovered()) + "% with " +
						sourceFileNames.size() + " source files, " +
						cover.getChildPackageCoverage().size() + " child packages and " +
						cover.getCoveredCoverageUnits().get() + "/" +
						cover.getCoverageUnits().get() + " coverage units.");
			
		}
		return new BigDecimal(result.getCoveragePercentage()).round(new MathContext(2));
	}

	private void logTrialFailure(I_TrialResult trial) {
		logger.log("" + trial + " failed!");
		String trialName = trial.getName();
		I_TrialFailure failure =  trial.getFailure();
		if (failure != null) {
			logger.log("\t" + failure.getMessage());
			Throwable throwable = failure.getException();
			if (throwable != null) {
				logThrowable("\t", throwable);
			}
		}
		List<I_TestResult> testResults = trial.getResults();
		for (I_TestResult tr: testResults) {
			if (!tr.isPassed() && !tr.isIgnored()) {
				String testName = tr.getName();
				logger.log("\t" + trialName + "."  + testName + " failed!");
				I_TestFailure tf = tr.getFailure();
				logger.log("\t" + tf.getMessage());
				Throwable t = tf.getLocationFailed();
				I_AssertionData ad =  tf.getData();
				
				if (ad instanceof ContainsAssertCommand) {
					logger.log("\tExpected;");
					logger.log("\t'" + ad.getData(ContainsAssertCommand.VALUE) + "'");
				} else if (ad instanceof ThrownAssertionData) {
					logThrowableFailure((ThrownAssertionData) ad);
				} else if (ad instanceof StringCompareAssertionData) {
					logCompareFailure((StringCompareAssertionData) ad);
				} else if (ad instanceof I_CompareAssertionData) {
					logCompareFailure((I_CompareAssertionData<?>) ad);
				} 
				if (t == null) {
					logger.log("\tUnknown Location a Test4J error please try to reproduce and report it;");
				} else {
					logThrowable("\t",t);
				}
			}
		}
	}

	private void logCompareFailure(StringCompareAssertionData ad) {
		I_TextLinesCompareResult result =  (I_TextLinesCompareResult) ad.getData(StringCompareAssertionData.COMPARISON);
		LineDiffTextDisplay lineDiffTextDisplay = new LineDiffTextDisplay();
		lineDiffTextDisplay.display(logger, result, 3);
	}
	
	private void logCompareFailure(I_CompareAssertionData<?> ad) {
		logger.log("\tExpected;");
		Object expected = ad.getExpected();
		if (expected != null) {
			logger.log("\t\tClass: " + expected.getClass());
		}
		logger.log("\t\t'" + expected + "'");
		logger.log("\tActual;");
		Object actual = ad.getActual();
		if (actual != null) {
			logger.log("\t\tClass: " + actual.getClass());
		}
		logger.log("\t\t'" + actual + "'");
	}
	
	private void logThrowableFailure(ThrownAssertionData ad) {
		Class<? extends Throwable> actualThrow = ad.getActualThrowable();
		Class<? extends Throwable> expectedThrow = ad.getExpectedThrowable();
		if (!expectedThrow.equals(actualThrow)) {
			logger.log("\tExpected;");
			logger.log("\t" + expectedThrow);
			logger.log("\tActual;");
			logger.log("\t" + actualThrow);
		} else {
			String expectedMessage = ad.getExpectedMessage();
			String actualMessage = ad.getActualMessage();
			logger.log("\tThrowable classes matched: " + expectedThrow);
			logger.log("\tExpected;");
			logger.log("\t" + expectedMessage);
			logger.log("\tActual;");
			logger.log("\t" + actualMessage);
		}
	}

	private void logThrowable(String indentString, Throwable t) {
		logThrowable(indentString, indentString, t);
	}
	
	private void logThrowable(String currentIndent, String indentString, Throwable t) {
		StackTraceElement [] stack = t.getStackTrace();
		logger.log(currentIndent + t.toString());
		for (int i = 0; i < stack.length; i++) {
			logger.log(currentIndent +"at " + stack[i]);
		}
		Throwable cause = t.getCause();
		if (cause != null) {
			logThrowable(currentIndent + indentString, indentString,  cause);
		}
	}


	@Override
	public synchronized void onProgress(String process, double pctComplete) {
		AbstractProgressDisplay apr = processes.get(process);
		apr.onProgress(process, pctComplete);
		
	}



}
