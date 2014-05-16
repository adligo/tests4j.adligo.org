package org.adligo.tests4j.models.shared.system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.system.report.ConsoleReporter;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;


public class Tests4J_Params implements I_Tests4J_Params {
	/**
	 * @see I_Tests4J_Params#getTrials()
	 */
	private List<Class<? extends I_AbstractTrial>> trials = 
				new ArrayList<Class<? extends I_AbstractTrial>>();
	
	private Set<String> tests = new HashSet<String>();
	/**
	 * @see I_Tests4J_Params#getReporter()
	 */
	private I_Tests4J_Reporter reporter = new ConsoleReporter();
	
	/**
	 * @see I_Tests4J_Params#getMinTrials()
	 */
	private Integer minTrials = null;
	/**
	 * @see I_Tests4J_Params#getMinTests()
	 */
	private Integer minTests = null;
	/**
	 *  a null value means don't check it
	 */
	private Integer minAsserts = null;
	/**
	 *  a null value means don't check it
	 */
	private Integer minUniqueAssertions = null;
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
	
	public Tests4J_Params() {}
	
	public Tests4J_Params(I_Tests4J_Params p) {
		trials.addAll(p.getTrials());
		tests.addAll(p.getTests());
		reporter = p.getReporter();
		minTrials = p.getMinTrials();
		minTests = p.getMinTests();
		minAsserts = p.getMinAsserts();
		minUniqueAssertions = p.getMinUniqueAssertions();
		coveragePlugin = p.getCoveragePlugin();
		exitAfterLastNotification = p.isExitAfterLastNotification();
		threadPoolSize = p.getThreadPoolSize();
	}
	
	public List<Class<? extends I_AbstractTrial>> getTrials() {
		return trials;
	}
	public void setTrials(List<Class<? extends I_AbstractTrial>> p) {
		trials.clear();
		trials.addAll(p);
	}
	public void addTrial(Class<? extends I_AbstractTrial> p) {
		trials.add(p);
	}
	
	public I_CoveragePlugin getCoveragePlugin() {
		return coveragePlugin;
	}
	public void setCoveragePlugin(I_CoveragePlugin coverageRecorder) {
		this.coveragePlugin = coverageRecorder;
	}
	public Integer getMinTests() {
		return minTests;
	}
	public Integer getMinAsserts() {
		return minAsserts;
	}
	public Integer getMinUniqueAssertions() {
		return minUniqueAssertions;
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
	public I_Tests4J_Reporter getReporter() {
		return reporter;
	}
	public void setReporter(I_Tests4J_Reporter p) {
		this.reporter = p;
	}
	public boolean isExitAfterLastNotification() {
		return exitAfterLastNotification;
	}
	public void setExitAfterLastNotification(boolean exitAfterLastNotification) {
		this.exitAfterLastNotification = exitAfterLastNotification;
	}
	public Boolean getRecordSeperateTrialCoverage() {
		if (recordSeperateTestCoverage == null) {
			if (coveragePlugin != null) {
				if (coveragePlugin.canSubRecord()) {
					return true;
				}
			}
		}
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

	public Integer getMinTrials() {
		return minTrials;
	}

	public void setMinTrials(Integer minTrials) {
		this.minTrials = minTrials;
	}

	public Set<String> getTests() {
		return tests;
	}

	public void setTests(Set<String> p) {
		tests.clear();
		tests.addAll(p);
	}
	
	public void addTest(String p) {
		tests.add(p);
	}
	
	public void removeTest(String p) {
		tests.remove(p);
	}
}
