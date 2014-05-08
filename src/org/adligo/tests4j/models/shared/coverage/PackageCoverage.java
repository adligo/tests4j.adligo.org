package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class PackageCoverage implements I_PackageCoverage {
	private PackageCoverageMutant mutant;
	private Map<String, I_SourceFileCoverage> coverage;
	private List<I_PackageCoverage> children;
	
	public PackageCoverage() {
		mutant = new PackageCoverageMutant();
		coverage = Collections.emptyMap();
		children = Collections.emptyList();
	}
	
	public PackageCoverage(I_PackageCoverage p) {
		List<I_PackageCoverage> otherChildren =  p.getChildPackageCoverage();
		for (I_PackageCoverage other: otherChildren) {
			children.add(new PackageCoverage(other));
		}
		Set<String> sourceFileNames = p.getSourceFileNames();
		if (sourceFileNames.size() >= 1) {
			coverage = new HashMap<String, I_SourceFileCoverage>();
		}
		for (String name: sourceFileNames) {
			coverage.put(name, new SourceFileCoverage(p.getCoverage(name)));
		}
		mutant = new PackageCoverageMutant(p, false);
		
		
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

	public I_SourceFileCoverage getCoverage(String sourceFileName) {
		return coverage.get(sourceFileName);
	}

	public Set<String> getSourceFileNames() {
		return coverage.keySet();
	}

	public List<I_PackageCoverage> getChildPackageCoverage() {
		return children;
	}

	public boolean hasChildPackageCoverage() {
		return mutant.hasChildPackageCoverage();
	}

	public I_CoverageUnits getTotalCoverageUnits() {
		return mutant.getTotalCoverageUnits();
	}

	public I_CoverageUnits getTotalCoveredCoverageUnits() {
		return mutant.getTotalCoveredCoverageUnits();
	}

	public BigDecimal getTotalPercentageCovered() {
		return mutant.getTotalPercentageCovered();
	}

	public String toString() {
		return mutant.toString();
	}
	
}
