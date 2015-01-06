package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class PackageCoverageBrief implements I_PackageCoverageBrief {
	private PackageCoverageBriefMutant mutant_;
	private Map<String, I_SourceFileCoverageBrief> coverage_;
	private List<I_PackageCoverageBrief> children_;
	
	public PackageCoverageBrief() {
		mutant_ = new PackageCoverageBriefMutant();
		coverage_ = Collections.emptyMap();
		children_ = Collections.emptyList();
	}
	
	public PackageCoverageBrief(I_PackageCoverageBrief p) {
		List<I_PackageCoverageBrief> otherChildren =  p.getChildPackageCoverage();
		if (otherChildren.size() >= 1) {
			children_ = new ArrayList<I_PackageCoverageBrief>();
		}
		for (I_PackageCoverageBrief other: otherChildren) {
			children_.add(new PackageCoverageBrief(other));
		}
		Set<String> sourceFileNames = p.getSourceFileNames();
		if (sourceFileNames.size() >= 1) {
			coverage_ = new HashMap<String, I_SourceFileCoverageBrief>();
			for (String name: sourceFileNames) {
				coverage_.put(name, new SourceFileCoverageBrief(p.getCoverage(name)));
			}
		} else {
			coverage_ = Collections.emptyMap();
		}
		
		mutant_ = new PackageCoverageBriefMutant(p, false);
		/*
		if (mutant_.getCoverageUnits() == null) {
		  throw new IllegalArgumentException(PackageCoverageBrief.class.getSimpleName() + " requires coverage units.");
		}
		if (mutant_.getCoveredCoverageUnits() == null) {
      throw new IllegalArgumentException(PackageCoverageBrief.class.getSimpleName() + " requires covered coverage units.");
    }
    */
	}

	public I_CoverageUnits getCoverageUnits() {
		return mutant_.getCoverageUnits();
	}

	public I_CoverageUnits getCoveredCoverageUnits() {
		return mutant_.getCoveredCoverageUnits();
	}

	public BigDecimal getPercentageCovered() {
		return mutant_.getPercentageCovered();
	}

	public String getPackageName() {
		return mutant_.getPackageName();
	}

	public I_SourceFileCoverageBrief getCoverage(String sourceFileName) {
		return coverage_.get(sourceFileName);
	}

	public Set<String> getSourceFileNames() {
		return coverage_.keySet();
	}

	public List<I_PackageCoverageBrief> getChildPackageCoverage() {
		return children_;
	}

	public boolean hasChildPackageCoverage() {
		return mutant_.hasChildPackageCoverage();
	}



	public String toString() {
		return mutant_.toString();
	}

	@Override
	public double getPercentageCoveredDouble() {
		return getPercentageCovered().doubleValue();
	}
	
  @Override
  public I_PackageCoverageBrief getPackageCoverage(String packageName) {
    String myName = mutant_.getPackageName();
    if (myName.equals(packageName)) {
      return this;
    }
    for (I_PackageCoverageBrief pkg: children_) {
      String cname = pkg.getPackageName();
      if (packageName.indexOf(cname) == 0) {
        return pkg.getPackageCoverage(packageName);
      }
    }
    return null;
  }
}
