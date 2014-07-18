package org.adligo.tests4j.shared.report.summary;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
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
import org.adligo.tests4j.models.shared.asserts.line_text.I_DiffIndexes;
import org.adligo.tests4j.models.shared.asserts.line_text.I_DiffIndexesPair;
import org.adligo.tests4j.models.shared.asserts.line_text.I_LineDiff;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLines;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.models.shared.asserts.line_text.LineDiffType;
import org.adligo.tests4j.models.shared.common.StringMethods;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.metadata.RelevantClassesWithTrialsCalculator;
import org.adligo.tests4j.models.shared.results.I_TestFailure;
import org.adligo.tests4j.models.shared.results.I_TestResult;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Reporter;

public class SummaryReporter implements I_Tests4J_Reporter, I_LineOut {
	public static final String DEFAULT_PREFIX = "Tests4J: ";
	
	private String prefix = DEFAULT_PREFIX;
	private boolean snare = false;
	private boolean redirect = true;
	private Set<String> enabledLogClasses = new HashSet<String>();
	private List<I_TrialResult> failedTrials = new ArrayList<I_TrialResult>();
	private boolean hadTrialTestsWhichDidNOTRun = false;
	private I_LineOut out = new SystemOut();
	private boolean listRelevantClassesWithoutTrials = false;
	private I_TrialRunMetadata metadata;
	
	public SummaryReporter() {
		setLogOn(SummaryReporter.class);
	}
	
	public SummaryReporter(I_LineOut p) {
		setLogOn(SummaryReporter.class);
		out = p;
	}
	
	@Override
	public void onMetadataCalculated(I_TrialRunMetadata p) {
		if (isLogEnabled(SummaryReporter.class)) {
			log("Metadata Calculated: " + p.getAllTrialsCount() + " trials with " +
					p.getAllTestsCount() + " tests.");
		}
		metadata = p;
	}

	@Override
	public synchronized void onStartingTrial(String trialName) {
		if (isLogEnabled(SystemOut.class)) {
			log("startingTrial: " + trialName);
		}
	}

	@Override
	public synchronized void onStartingTest(String trialName, String testName) {
		if (isLogEnabled(SystemOut.class)) {
			log("startingTest: " + trialName + "." + testName);
		}
	}

	@Override
	public synchronized void onTestCompleted(String trialName, String testName,
			boolean passed) {
		if (isLogEnabled(SummaryReporter.class)) {
			String passedString = " passed!";
			if (!passed) {
				passedString = " failed!";
			}
			log("Test: " + trialName + "." + testName + passedString);
		}
	}

	@Override
	public synchronized void onTrialCompleted(I_TrialResult result) {
		if (isLogEnabled(SummaryReporter.class)) {
			String passedString = " passed!";
			if (!result.isPassed()) {
				passedString = " failed!";
				failedTrials.add(result);
			}
			log("Trial: " + result.getName() + passedString);
			if (result.isHadAfterTrialTests()) {
				if (!result.isRanAfterTrialTests()) {
					hadTrialTestsWhichDidNOTRun = true;
				}
			}
		}
	}

	@Override
	public void onRunCompleted(I_TrialRunResult result) {
		if (isLogEnabled(SummaryReporter.class)) {
			log("------------------------Test Results-------------------------");
			log("\t\tTests completed in " + result.getRunTimeSecs() + " seconds.");
			DecimalFormat formatter = new DecimalFormat("###.##");
			
			RelevantClassesWithTrialsCalculator calc = new RelevantClassesWithTrialsCalculator(metadata);
			
			double pctD = calc.getPct();
			
			log("\t\t" + formatter.format(pctD) + 
					"% of relevant classes have corresponding trials.");
			if (listRelevantClassesWithoutTrials) {
				Set<String> classes = calc.getClassesWithOutTrials();
				for (String clazz: classes) {
					log("\t\t" + clazz);
				}
			}
			BigDecimal pct = logCoverage(result,  formatter );
			
			
			if (result.getTrialsPassed() == result.getTrials()) {
				log("\t\tTests: " + result.getTestsPassed() + "/" +
						result.getTests());
				log("\t\tUnique/Assertions: " + 
						result.getUniqueAsserts() + "/" +
						result.getAsserts());
				log("\t\tAll Trials " + result.getTrialsPassed()  + "/" 
						+ result.getTrials() + " Trials with " + formatter.format(pct) + "% coverage;");
				log("");
				log("\t\t\tPassed!");
				if (hadTrialTestsWhichDidNOTRun) {
					log("\t\tWarning afterTrialTests methods/assertions did not run!");
				}
				log("");
			} else {
				for (I_TrialResult trial: failedTrials) {
					logTrialFailure(trial);
				}
				log("\t\tTests: " + result.getTestsPassed() + "/" +
						result.getTests());
				log("\t\tUnique/Assertions: " + 
						result.getUniqueAsserts() + "/" +
						result.getAsserts());
				log("\t\t" + result.getTrialFailures()  + "/" 
						+ result.getTrials() + " Trials with " + formatter.format(pct) + "% coverage;");
				log("");
				log("\t\t\tFAILED!");
				log("");
				
			}
			
			log("------------------------Test Results End---------------------");
		}
	}

	

	private BigDecimal logCoverage(I_TrialRunResult result, DecimalFormat formatter) {
		List<I_PackageCoverage> coverage = result.getCoverage();
		if (coverage.size() >= 1) {
			log("\t\tPackage Coverage;");
		}
		Map<String, I_PackageCoverage> treeMap = new TreeMap<String, I_PackageCoverage>();
		for (I_PackageCoverage cover: coverage) {
			treeMap.put(cover.getPackageName(), cover);
		}
		Collection<I_PackageCoverage> ordered =  treeMap.values();
		for (I_PackageCoverage cover: ordered) {
			Set<String> sourceFileNames = cover.getSourceFileNames();
			log("\t\t\t+" + cover.getPackageName() + " was covered " + 
						formatter.format(cover.getPercentageCovered()) + "% with " +
						sourceFileNames.size() + " source files, " +
						cover.getChildPackageCoverage().size() + " child packages and " +
						cover.getCoveredCoverageUnits().get() + "/" +
						cover.getCoverageUnits().get() + " coverage units.");
			
		}
		return new BigDecimal(result.getCoveragePercentage()).round(new MathContext(2));
	}

	private void logTrialFailure(I_TrialResult trial) {
		log("" + trial + " failed!");
		String trialName = trial.getName();
		I_TrialFailure failure =  trial.getFailure();
		if (failure != null) {
			log("\t" + failure.getMessage());
			Throwable throwable = failure.getException();
			if (throwable != null) {
				logThrowable("\t", throwable);
			}
		}
		List<I_TestResult> testResults = trial.getResults();
		for (I_TestResult tr: testResults) {
			if (!tr.isPassed() && !tr.isIgnored()) {
				String testName = tr.getName();
				log("\t" + trialName + "."  + testName + " failed!");
				I_TestFailure tf = tr.getFailure();
				log("\t" + tf.getMessage());
				Throwable t = tf.getLocationFailed();
				I_AssertionData ad =  tf.getData();
				
				if (ad instanceof ContainsAssertCommand) {
					log("\tExpected;");
					log("\t'" + ad.getData(ContainsAssertCommand.VALUE) + "'");
				} else if (ad instanceof ThrownAssertionData) {
					logThrowableFailure((ThrownAssertionData) ad);
				} else if (ad instanceof StringCompareAssertionData) {
					logCompareFailure((StringCompareAssertionData) ad);
				} else if (ad instanceof I_CompareAssertionData) {
					logCompareFailure((I_CompareAssertionData<?>) ad);
				} 
				if (t == null) {
					log("\tUnknown Location a Test4J error please try to reproduce and report it;");
				} else {
					logThrowable("\t",t);
				}
			}
		}
	}

	private void logCompareFailure(StringCompareAssertionData ad) {
		I_TextLinesCompareResult result =  (I_TextLinesCompareResult) ad.getData(StringCompareAssertionData.COMPARISON);
		LineDiffTextDisplay.display(this, result);
	}
	
	private void logCompareFailure(I_CompareAssertionData<?> ad) {
		log("\tExpected;");
		Object expected = ad.getExpected();
		if (expected != null) {
			log("\t\tClass: " + expected.getClass());
		}
		log("\t\t'" + expected + "'");
		log("\tActual;");
		Object actual = ad.getActual();
		if (actual != null) {
			log("\t\tClass: " + actual.getClass());
		}
		log("\t\t'" + actual + "'");
	}
	
	private void logThrowableFailure(ThrownAssertionData ad) {
		Class<? extends Throwable> actualThrow = ad.getActualThrowable();
		Class<? extends Throwable> expectedThrow = ad.getExpectedThrowable();
		if (!expectedThrow.equals(actualThrow)) {
			log("\tExpected;");
			log("\t" + expectedThrow);
			log("\tActual;");
			log("\t" + actualThrow);
		} else {
			String expectedMessage = ad.getExpectedMessage();
			String actualMessage = ad.getActualMessage();
			log("\tThrowable classes matched: " + expectedThrow);
			log("\tExpected;");
			log("\t" + expectedMessage);
			log("\tActual;");
			log("\t" + actualMessage);
		}
	}

	private void logThrowable(String indentString, Throwable t) {
		logThrowable(indentString, indentString, t);
	}
	
	private void logThrowable(String currentIndent, String indentString, Throwable t) {
		StackTraceElement [] stack = t.getStackTrace();
		log(currentIndent + t.toString());
		for (int i = 0; i < stack.length; i++) {
			log(currentIndent +"at " + stack[i]);
		}
		Throwable cause = t.getCause();
		if (cause != null) {
			logThrowable(currentIndent + indentString, indentString,  cause);
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
	public boolean isLogEnabled(Class<?> clazz) {
		if (enabledLogClasses.contains(clazz.getName())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isSnare() {
		return snare;
	}
	
	public synchronized void setLogOn(Class<?> clazz)  {
		enabledLogClasses.add(clazz.getName());
	}

	public synchronized void setLogOff(Class<?> clazz)  {
		enabledLogClasses.remove(clazz.getName());
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

	@Override
	public void setListRelevantClassesWithoutTrials(boolean p) {
		listRelevantClassesWithoutTrials = p;
	}

	@Override
	public void println(String p) {
		out.println(p);
	}
}
