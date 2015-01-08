package org.adligo.tests4j.system.shared.report.summary;

import org.adligo.tests4j.models.shared.coverage.I_CoverageUnits;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverageBrief;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_PhaseState;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.shared.common.DefaultSystem;
import org.adligo.tests4j.shared.common.Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.shared.output.DefaultLog;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Listener;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Params;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SummaryReporter implements I_Tests4J_Listener  {
  private static final I_Tests4J_Constants CONSTANTS = Tests4J_Constants.CONSTANTS;
	
	private I_Tests4J_Log log_;
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
	private SetupProcessDisplay setupProcessDisplay = new SetupProcessDisplay();
	private TrialsProcessDisplay trialsProcessDisplay = new TrialsProcessDisplay();
	private RemoteProcessDisplay remoteProcessDisplay = new RemoteProcessDisplay();
	
	private SetupProgressDisplay setupProgressDisplay = new SetupProgressDisplay();
	private TrialsProgressDisplay trialsProgressDisplay = new TrialsProgressDisplay();
	private RemoteProgressDisplay remoteProgressDisplay = new RemoteProgressDisplay();
	
	public SummaryReporter() {
		this(new DefaultLog());
	}
	
	public SummaryReporter(I_Tests4J_Log p) {
		log_ = p;
		threadDisplay = new ThreadDisplay(p, new DefaultSystem());
		testsReporter = new TestDisplay(p, threadDisplay);
		trialsReporter = new TrialDisplay(p, threadDisplay);
		trialFailedDetail = new TrialFailedDetailDisplay(p);
	}
	
	@Override
	public synchronized  void onMetadataCalculated(I_TrialRunMetadata p) {
		I_Tests4J_ReportMessages messages =  Tests4J_Constants.CONSTANTS.getReportMessages();
		
		log_.log(DefaultLog.orderLine(CONSTANTS.getPrefix(),  messages.getMetadataCalculatedHeading()) + log_.getLineSeperator() +
		    DefaultLog.orderLine(CONSTANTS.getPrefix(),  messages.getTrialsHeading(), "" + p.getAllTrialsCount()) + log_.getLineSeperator() +
			 	DefaultLog.orderLine(CONSTANTS.getPrefix(),   messages.getTestsHeading(), "" + p.getAllTestsCount()) + log_.getLineSeperator());
			
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
		log_.log("------------------------Test Results-------------------------");
		log_.log("\t\tTests completed in " + result.getRunTimeSecs() + " seconds.");
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
		
		if (result.hasCoverage()) {
			logCoverage(result );
		}
		
		
		if (result.getTrialsPassed() == result.getTrials()) {
			log_.log("\t\tTests: " + result.getTestsPassed() + "/" +
					result.getTests());
			log_.log("\t\tUnique/Assertions: " + 
					result.getUniqueAsserts() + "/" +
					result.getAsserts());
			StringBuilder sb = new StringBuilder();
			sb.append("\t\tAll Trials " + result.getTrialsPassed()  + "/" 
					+ result.getTrials() + " Trials");
			if (result.hasCoverage()) {
				sb.append(" with " + PercentFormat.format(result.getCoveragePercentage(), 2) + "% coverage;");
			}
			log_.log(sb.toString());
			log_.log("");
			log_.log("\t\t\tPassed!");
			log_.log("");
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
			log_.log("\t\tTests: " + result.getTestsPassed() + "/" +
					result.getTests());
			log_.log("\t\tUnique/Assertions: " + 
					result.getUniqueAsserts() + "/" +
					result.getAsserts());
			StringBuilder sb = new StringBuilder();
			sb.append("\t\t" + result.getTrialFailures()  + "/" 
					+ result.getTrials() + " Trials");
			if (result.hasCoverage()) {
				sb.append(" with " + PercentFormat.format(result.getCoveragePercentage(), 2) + "% coverage;");
			}
			log_.log(sb.toString());
			log_.log("");
			log_.log("\t\t\tFAILED!");
			log_.log("");
			
		}
		
		log_.log("------------------------Test Results End---------------------");
	}

	

	private BigDecimal logCoverage(I_TrialRunResult result) {
		List<I_PackageCoverageBrief> coverage = result.getCoverage();
		if (coverage.size() >= 1) {
			log_.log("\t\tPackage Coverage;");
		}
		Map<String, I_PackageCoverageBrief> treeMap = new TreeMap<String, I_PackageCoverageBrief>();
		for (I_PackageCoverageBrief cover: coverage) {
			treeMap.put(cover.getPackageName(), cover);
		}
		Collection<I_PackageCoverageBrief> ordered =  treeMap.values();
		for (I_PackageCoverageBrief cover: ordered) {
			Set<String> sourceFileNames = cover.getSourceFileNames();
			String packageName = cover.getPackageName();
			List<I_PackageCoverageBrief> childPackageCoverage = cover.getChildPackageCoverage();
			int childPackages = 0;
			if (childPackageCoverage != null) {
			  childPackages = childPackageCoverage.size();
			}
			I_CoverageUnits coverageUnits = cover.getCoverageUnits();
			I_CoverageUnits coveredCoverageUnits = cover.getCoveredCoverageUnits();
			try {
  			//todo bridge formatting with GWT
  			log_.log("\t\t\t+" + cover.getPackageName() + " was covered " + 
  						PercentFormat.format(cover.getPercentageCovered().doubleValue(), 2) + "% with " +
  						sourceFileNames.size() + " source files, " +
  						childPackages + " child packages and " +
  						coveredCoverageUnits.get() + "/" +
  						coverageUnits.get() + " coverage units.");
			} catch (Exception x) {
			  throw new IllegalStateException("Error with package coverage for package " + packageName + 
			      System.lineSeparator() + " childPackageCoverage " + System.lineSeparator() + 
			      childPackageCoverage + System.lineSeparator() +
			      " coveredCoverageUnits " + System.lineSeparator() +
			      coveredCoverageUnits + System.lineSeparator() + 
			      " coverageUnits " + System.lineSeparator() + 
			      coverageUnits
			      ,x);
			}
		}
		return new BigDecimal(result.getCoveragePercentage()).round(new MathContext(2));
	}




	@Override
	public synchronized void onProgress(I_PhaseState info) {
		String processName = info.getProcessName();
		if  (I_PhaseState.SETUP.equals(processName)) {
					setupProgressDisplay.onProgress(log_, info);
		} else if (I_PhaseState.TRIALS.equals(processName)) {
				trialsProgressDisplay.onProgress(log_, info);
		} else {
				remoteProgressDisplay.onProgress(log_, info);
		}
	}

	@Override
	public void onProccessStateChange(I_PhaseState info) {
		String processName = info.getProcessName();
		if  (I_PhaseState.SETUP.equals(processName)) {
					setupProcessDisplay.onProccessStateChange(log_, info);
		} else if (I_PhaseState.TRIALS.equals(processName)) {
				trialsProcessDisplay.onProccessStateChange(log_, info);
		} else {
				remoteProcessDisplay.onProccessStateChange(log_, info);
		}
	}

  @Override
  public void onStartingSetup(I_Tests4J_Params params) {
    // TODO Auto-generated method stub
    
  }



}
