package org.adligo.tests4j.models.shared.system;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.AbstractTrial;
import org.adligo.tests4j.models.shared.system.console.ConsoleLogger;


public class Tests4J_Params implements I_Tests4J_Params {
	private List<Class<? extends AbstractTrial>> trials = 
				new ArrayList<Class<? extends AbstractTrial>>();
	/**
	 * this would only be false if running a single
	 * ClassTest instance
	 */
	private boolean packageScope = true;
	private I_Tests4J_Logger log = new ConsoleLogger(true);
	
	private boolean checkMins = false;
	private int minTests = 0;
	private int minAsserts = 0;
	private int minUniqueAssertions = 0;
	private int threadPoolSize = 32;
	/**
	 * this flaggs a jvm exit
	 */
	private boolean exitAfterLastNotification = true;
	/**
	 * All coverage is always recorded if there is a plugin,
	 * more fine grained coverage may be recorded if the 
	 * plugin supports it and recordTrialCoverage
	 * is null or true, and if recordTestCoverage is 
	 * null or true.
	 */
	private I_CoveragePlugin coveragePlugin;
	/**
	 * @see coveragePlugin
	 * null means use the coverage plugin's default
	 */
	private Boolean recordSeperateTrialCoverage = null;
	/**
	 * @see coveragePlugin
	 * null means use the coverage plugin's default
	 */
	private Boolean recordSeperateTestCoverage = null;
	
	public List<Class<? extends AbstractTrial>> getTrials() {
		return trials;
	}
	public boolean isPackageScope() {
		return packageScope;
	}
	public void setTrials(List<Class<? extends AbstractTrial>> p) {
		trials.clear();
		trials.addAll(p);
	}
	public void addTrial(Class<? extends AbstractTrial> p) {
		trials.add(p);
	}
	
	public void setPackageScope(boolean packageScope) {
		this.packageScope = packageScope;
	}

	
	public I_CoveragePlugin getCoveragePlugin() {
		return coveragePlugin;
	}
	public void setCoveragePlugin(I_CoveragePlugin coverageRecorder) {
		this.coveragePlugin = coverageRecorder;
	}
	public boolean isCheckMins() {
		return checkMins;
	}
	public int getMinTests() {
		return minTests;
	}
	public int getMinAsserts() {
		return minAsserts;
	}
	public int getMinUniqueAssertions() {
		return minUniqueAssertions;
	}
	public void setCheckMins(boolean checkMins) {
		this.checkMins = checkMins;
	}
	public void setMinTests(int minTests) {
		this.minTests = minTests;
	}
	public void setMinAsserts(int minAsserts) {
		this.minAsserts = minAsserts;
	}
	public void setMinUniqueAssertions(int minUniqueAssertions) {
		this.minUniqueAssertions = minUniqueAssertions;
	}
	
	public void addTrials(I_TrialList p) {
		trials.addAll(p.getTrials());
		/*
		minTests = minTests + p.getMinTests();
		minAsserts = minAsserts + p.getMinAsserts();
		minUniqueAssertions = minUniqueAssertions + p.getMinUniqueAssertions();
		*/
	}
	public int getThreadPoolSize() {
		if (trials.size() < threadPoolSize) {
			return trials.size();
		}
		return threadPoolSize;
	}
	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}
	public I_Tests4J_Logger getLog() {
		return log;
	}
	public void setLog(I_Tests4J_Logger logger) {
		this.log = logger;
	}
	public boolean isExitAfterLastNotification() {
		return exitAfterLastNotification;
	}
	public void setExitAfterLastNotification(boolean exitAfterLastNotification) {
		this.exitAfterLastNotification = exitAfterLastNotification;
	}
	public Boolean getRecordSeperateTrialCoverage() {
		return recordSeperateTrialCoverage;
	}
	public Boolean getRecordSeperateTestCoverage() {
		return recordSeperateTestCoverage;
	}
	public void setRecordTrialCoverage(boolean recordTrialCoverage) {
		this.recordSeperateTrialCoverage = recordTrialCoverage;
	}
	public void setRecordTestCoverage(boolean recordTestCoverage) {
		this.recordSeperateTestCoverage = recordTestCoverage;
	}
}
