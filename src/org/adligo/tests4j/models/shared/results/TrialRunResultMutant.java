package org.adligo.tests4j.models.shared.results;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.coverage.I_CoverageUnits;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverageBrief;

public class TrialRunResultMutant implements I_TrialRunResult {
	private long startTime;
	private long runTime;
	private List<I_PackageCoverageBrief> coverage = new ArrayList<I_PackageCoverageBrief>();
	private int trials;
	private int trialsIgnored;
	private int trialFailures;
	private int tests;
	private int testsIgnored;
	private int testFailures;
	private long asserts;
	private long uniqueAsserts;
	private Set<String> passingTrials = new HashSet<String>();
	private Set<String> failingTrials = new HashSet<String>();
	private Set<String> ignoredTrials = new HashSet<String>();
	
	public TrialRunResultMutant() {}
	
	public TrialRunResultMutant(I_TrialRunResult p) {
		startTime = p.getStartTime();
		runTime = p.getRunTime();
		
		setCoverage(p.getCoverage());
		
		trials = p.getTrials();
		trialsIgnored = p.getTrialsIgnored();
		trialFailures = p.getTrialFailures();
		
		tests = p.getTests();
		testsIgnored = p.getTestsIgnored();
		testFailures = p.getTestsFailed();
		
		asserts = p.getAsserts();
		uniqueAsserts = p.getUniqueAsserts();
		setPassingTrials(p.getPassingTrials());
		setFailingTrials(p.getFailingTrials());
		setIgnoredTrials(p.getIgnoredTrials());
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

	public List<I_PackageCoverageBrief> getCoverage() {
		return coverage;
	}

	public synchronized void setCoverage(List<I_PackageCoverageBrief> coverage) {
		this.coverage = coverage;
	}

	public int getTrials() {
		return trials;
	}

	public int getTests() {
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
	
	public synchronized void setTests(int tests) {
		this.tests = tests;
	}

	public synchronized void addTests(int p) {
		this.tests = tests + p;
	}
	
	public synchronized void setAsserts(long asserts) {
		this.asserts = asserts;
	}

	public synchronized void addAsserts(long p) {
		this.asserts = asserts + p;
	}
	
	public int getTestsFailed() {
		return testFailures;
	}

	public synchronized void setTestFailures(int testFailures) {
		this.testFailures = testFailures;
	}

	public synchronized void addTestFailures(int p) {
		this.testFailures = testFailures + p;
	}
	
	public int getTrialsPassed() {
		return trials - trialFailures;
	}
	
	public int getTestsPassed() {
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
	public int getTestsIgnored() {
		return testsIgnored;
	}

	public void setIgnoredTrials(int trialIgnored) {
		this.trialsIgnored = trialIgnored;
	}

	public void setIgnoredTests(int testIgnored) {
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
		
		for (I_PackageCoverageBrief cover: coverage) {
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
		if (p != null) {
		  passingTrials.addAll(p);
		}
	}
	
	public void addPassingTrial(String p) {
		passingTrials.add(p);
	}

	public void setFailingTrials(Set<String> p) {
    failingTrials.clear();
    if (p != null) {
      failingTrials.addAll(p);
    }
  }
	
  @Override
  public Set<String> getFailingTrials() {
    return failingTrials;
  }

  public void setIgnoredTrials(Set<String> p) {
    ignoredTrials.clear();
    if (p != null) {
      ignoredTrials.addAll(p);
    }
  }
  
  @Override
  public Set<String> getIgnoredTrials() {
    return ignoredTrials;
  }
}
