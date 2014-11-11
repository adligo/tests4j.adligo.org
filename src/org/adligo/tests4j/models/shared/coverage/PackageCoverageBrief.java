package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class PackageCoverageBrief implements I_PackageCoverageBrief {
	private PackageCoverageBriefMutant mutant;
	private Map<String, I_SourceFileCoverageBrief> coverage;
	private List<I_PackageCoverageBrief> children;
	
	public PackageCoverageBrief() {
		mutant = new PackageCoverageBriefMutant();
		coverage = Collections.emptyMap();
		children = Collections.emptyList();
	}
	
	public PackageCoverageBrief(I_PackageCoverageBrief p) {
		List<I_PackageCoverageBrief> otherChildren =  p.getChildPackageCoverage();
		if (otherChildren.size() >= 1) {
			children = new ArrayList<I_PackageCoverageBrief>();
		}
		for (I_PackageCoverageBrief other: otherChildren) {
			children.add(new PackageCoverageBrief(other));
		}
		Set<String> sourceFileNames = p.getSourceFileNames();
		if (sourceFileNames.size() >= 1) {
			coverage = new HashMap<String, I_SourceFileCoverageBrief>();
			for (String name: sourceFileNames) {
				coverage.put(name, new SourceFileCoverageBrief(p.getCoverage(name)));
			}
		} else {
			coverage = Collections.emptyMap();
		}
		
		mutant = new PackageCoverageBriefMutant(p, false);
		
		
	}

	public I_CoverageUnits getCoverageUnits() {
		return mutant.getCoverageUnits();
	}

	public I_CoverageUnits getCoveredCoverageUnits() {
		return mutant.getCoveredCoverageUnits();
	}

	public BigDecimal getPercentageCovered() {
		return mutant.getPercentageCovered();
	}

	public String getPackageName() {
		return mutant.getPackageName();
	}

	public I_SourceFileCoverageBrief getCoverage(String sourceFileName) {
		return coverage.get(sourceFileName);
	}

	public Set<String> getSourceFileNames() {
		return coverage.keySet();
	}

	public List<I_PackageCoverageBrief> getChildPackageCoverage() {
		return children;
	}

	public boolean hasChildPackageCoverage() {
		return mutant.hasChildPackageCoverage();
	}



	public String toString() {
		return mutant.toString();
	}

	@Override
	public double getPercentageCoveredDouble() {
		return getPercentageCovered().doubleValue();
	}

}
