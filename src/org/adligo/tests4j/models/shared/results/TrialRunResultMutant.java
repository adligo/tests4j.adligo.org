package org.adligo.tests4j.models.shared.results;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;

public class TrialRunResultMutant implements I_TrialRunResult {
	private long startTime;
	private long runTime;
	private List<I_PackageCoverage> coverage = new ArrayList<I_PackageCoverage>();
	private int trials;
	private int trialsIgnored;
	private int trialFailures;
	private long tests;
	private long testsIgnored;
	private long testFailures;
	private long asserts;
	private long uniqueAsserts;
	
	public TrialRunResultMutant() {}
	
	public TrialRunResultMutant(I_TrialRunResult p) {
		startTime = p.getStartTime();
		runTime = p.getRunTime();
		coverage.addAll(p.getCoverage());
		
		trials = p.getTrials();
		trialsIgnored = p.getTrialsIgnored();
		trialFailures = p.getTrialFailures();
		
		tests = p.getTests();
		testsIgnored = p.getTestsIgnored();
		testFailures = p.getTestFailures();
		
		asserts = p.getAsserts();
		uniqueAsserts = p.getUniqueAsserts();
	}

	public long getStartTime() {
		return startTime;
	}

	public long getRunTime() {
		return runTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void setRunTime(long runTime) {
		this.runTime = runTime;
	}
	
	public BigDecimal getRunTimeSecs() {
		BigDecimal rt = new BigDecimal(runTime);
		rt = rt.divide(new BigDecimal(1000));
		return rt;
	}

	public List<I_PackageCoverage> getCoverage() {
		return coverage;
	}

	public synchronized void setCoverage(List<I_PackageCoverage> coverage) {
		this.coverage = coverage;
	}

	public int getTrials() {
		return trials;
	}

	public long getTests() {
		return tests;
	}

	public long getAsserts() {
		return asserts;
	}

	public synchronized void setTrials(int trials) {
		this.trials = trials;
	}

	public synchronized void addTrials(int p) {
		this.trials = trials + p;
	}
	
	public synchronized void setTests(long tests) {
		this.tests = tests;
	}

	public synchronized void addTests(long p) {
		this.tests = tests + p;
	}
	
	public synchronized void setAsserts(long asserts) {
		this.asserts = asserts;
	}

	public synchronized void addAsserts(long p) {
		this.asserts = asserts + p;
	}
	
	public long getTestFailures() {
		return testFailures;
	}

	public synchronized void setTestFailures(long testFailures) {
		this.testFailures = testFailures;
	}

	public synchronized void addTestFailures(long p) {
		this.testFailures = testFailures + p;
	}
	
	public int getTrialsPassed() {
		return trials - trialFailures;
	}
	
	public long getTestsPassed() {
		return tests - testFailures;
	}
	public int getTrialFailures() {
		return trialFailures;
	}

	public synchronized void setTrialFailures(int p) {
		this.trialFailures = p;
	}
	
	public synchronized void addTrialFailures(int p) {
		this.trialFailures = trialFailures + p;
	}

	public long getUniqueAsserts() {
		return uniqueAsserts;
	}

	public synchronized void setUniqueAsserts(long uniqueAsserts) {
		this.uniqueAsserts = uniqueAsserts;
	}
	
	public synchronized void addUniqueAsserts(long p) {
		this.uniqueAsserts = uniqueAsserts + p;
	}

	@Override
	public int getTrialsIgnored() {
		return trialsIgnored;
	}

	@Override
	public long getTestsIgnored() {
		return testsIgnored;
	}

	public void setIgnoredTrials(int trialIgnored) {
		this.trialsIgnored = trialIgnored;
	}

	public void setIgnoredTests(long testIgnored) {
		this.testsIgnored = testIgnored;
	}
}
