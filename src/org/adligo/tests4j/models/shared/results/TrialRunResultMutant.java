package org.adligo.tests4j.models.shared.results;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.coverage.I_CoverageUnits;
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
	private Set<String> passingTrials = new HashSet<String>();
	
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
		passingTrials.addAll(p.getPassingTrials());
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

	@Override
	public boolean hasCoverage() {
		if (coverage.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public double getCoveragePercentage() {
		double cus = 0;
		double covered_cus = 0;
		
		for (I_PackageCoverage cover: coverage) {
			I_CoverageUnits cu = cover.getCoverageUnits();
			cus = cus + cu.get();
			I_CoverageUnits ccu = cover.getCoveredCoverageUnits();
			covered_cus = covered_cus + ccu.get();
		}
		if (cus == 0.0) {
			return new BigDecimal("0.00").doubleValue();
		}
		double pct = covered_cus/cus * 100;
		return new BigDecimal(pct).round(new MathContext(2)).doubleValue();
	}

	public Set<String> getPassingTrials() {
		return passingTrials;
	}

	public void setPassingTrials(Set<String> p) {
		passingTrials.clear();
		passingTrials.addAll(p);
	}
	
	public void addPassingTrial(String p) {
		passingTrials.add(p);
	}
}
