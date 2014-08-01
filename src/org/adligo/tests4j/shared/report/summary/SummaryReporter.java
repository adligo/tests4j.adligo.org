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
	private TestDisplay testsReporter;
	private TrialDisplay trialsReporter;
	/**
	 * display the top three failures 
	 * so you don't need to scroll up.
	 * TODO make configurable some how.
	 */
	private int trialFailuresDetailDisplayCount = 3;
	private TrialFailedDetailDisplay trialFailedDetail;
	
	public SummaryReporter() {
		this(new DefaultLog());
	}
	
	public SummaryReporter(I_Tests4J_Log p) {
		logger = p;
		testsReporter = new TestDisplay(p);
		trialsReporter = new TrialDisplay(p);
		trialFailedDetail = new TrialFailedDetailDisplay(p);
		
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
			logger.log("");
		} else {
			List<I_TrialResult> failedTrials = trialsReporter.getFailedTrials();
			if (failedTrials.size() >= trialFailuresDetailDisplayCount) {
				for (int i = 0; i < trialFailuresDetailDisplayCount; i++) {
					I_TrialResult tr = failedTrials.get(i);
					trialFailedDetail.logTrialFailure(tr);
				}
			} else {
				for (I_TrialResult tr: failedTrials) {
					trialFailedDetail.logTrialFailure(tr);
				}
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




	@Override
	public synchronized void onProgress(String process, double pctComplete) {
		AbstractProgressDisplay apr = processes.get(process);
		apr.onProgress(process, pctComplete);
		
	}



}
