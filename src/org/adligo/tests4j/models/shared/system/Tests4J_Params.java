package org.adligo.tests4j.models.shared.system;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.system.console.ConsoleLogger;


public class Tests4J_Params implements I_Tests4J_Params {
	private List<Class<? extends I_AbstractTrial>> trials = 
				new ArrayList<Class<? extends I_AbstractTrial>>();
	/**
	 * this would only be false if running a single
	 * ClassTest instance
	 */
	private boolean packageScope = true;
	private boolean failFast = true;
	private I_Tests4J_Logger log = new ConsoleLogger(true);
	private I_CoveragePlugin coveragePlugin;
	private boolean checkMins = false;
	private int minTests = 0;
	private int minAsserts = 0;
	private int minUniqueAssertions = 0;
	private int threadPoolSize = 4;
	/**
	 * this flaggs a jvm exit
	 */
	private boolean exitAfterLastNotification = true;
	
	public List<Class<? extends I_AbstractTrial>> getTrials() {
		return trials;
	}
	public boolean isPackageScope() {
		return packageScope;
	}
	public void setTrials(List<Class<? extends I_AbstractTrial>> p) {
		trials.clear();
		trials.addAll(p);
	}
	
	public void setPackageScope(boolean packageScope) {
		this.packageScope = packageScope;
	}
	public boolean isFailFast() {
		return failFast;
	}
	public void setFailFast(boolean failFast) {
		this.failFast = failFast;
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
}
