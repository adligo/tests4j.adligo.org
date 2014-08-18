package org.adligo.tests4j.shared.report.summary;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Listener;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.shared.output.DefaultLog;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

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
	private ThreadDisplay threadDisplay;
	
	public SummaryReporter() {
		this(new DefaultLog());
	}
	
	public SummaryReporter(I_Tests4J_Log p) {
		logger = p;
		threadDisplay = new ThreadDisplay(p);
		testsReporter = new TestDisplay(p, threadDisplay);
		trialsReporter = new TrialDisplay(p);
		trialFailedDetail = new TrialFailedDetailDisplay(p);
		
		processes.put("setup", new SetupProgressDisplay(p));
		processes.put("trials", new TrialsProgressDisplay(p));
		processes.put("tests", new TestsProgressDisplay(p));
	}
	
	@Override
	public synchronized  void onMetadataCalculated(I_TrialRunMetadata p) {
		I_Tests4J_ReportMessages messages =  Tests4J_Constants.CONSTANTS.getReportMessages();
		
		logger.log(I_Tests4J_Constants.PREFIX +  messages.getMetadataCalculatedHeading() + logger.getLineSeperator() +
				I_Tests4J_Constants.PREFIX + messages.getTrialsHeading() + p.getAllTrialsCount() + logger.getLineSeperator() +
			 	I_Tests4J_Constants.PREFIX + messages.getTestsHeading() + p.getAllTestsCount()
			 	+  logger.getLineSeperator());
			
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
		logger.log("\t\tTests completed in " + result.getRunTimeSecs() + " seconds.");
		//DecimalFormat formatter = new DecimalFormat("###.##");
		
		/*
		double pctD = calc.getPct();
		
		logger.log("\t\t" + PercentFormat.format(pctD, 2) + 
				"% of relevant classes have corresponding trials.");
				
		if (listRelevantClassesWithoutTrials) {
			Set<String> classes = calc.getClassesWithOutTrials();
			for (String clazz: classes) {
				logger.log("\t\t" + clazz);
			}
		}
		*/
		
		BigDecimal pct = null;
		if (result.hasCoverage()) {
			logCoverage(result );
		}
		
		
		if (result.getTrialsPassed() == result.getTrials()) {
			logger.log("\t\tTests: " + result.getTestsPassed() + "/" +
					result.getTests());
			logger.log("\t\tUnique/Assertions: " + 
					result.getUniqueAsserts() + "/" +
					result.getAsserts());
			StringBuilder sb = new StringBuilder();
			sb.append("\t\tAll Trials " + result.getTrialsPassed()  + "/" 
					+ result.getTrials() + " Trials");
			if (result.hasCoverage()) {
				sb.append(" with " + PercentFormat.format(result.getCoveragePercentage(), 2) + "% coverage;");
			}
			logger.log(sb.toString());
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
			StringBuilder sb = new StringBuilder();
			sb.append("\t\t" + result.getTrialFailures()  + "/" 
					+ result.getTrials() + " Trials");
			if (result.hasCoverage()) {
				sb.append(" with " + PercentFormat.format(result.getCoveragePercentage(), 2) + "% coverage;");
			}
			logger.log(sb.toString());
			logger.log("");
			logger.log("\t\t\tFAILED!");
			logger.log("");
			
		}
		
		logger.log("------------------------Test Results End---------------------");
	}

	

	private BigDecimal logCoverage(I_TrialRunResult result) {
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
			//todo bridge formatting with GWT
			logger.log("\t\t\t+" + cover.getPackageName() + " was covered " + 
						cover.getPercentageCovered() + "% with " +
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
