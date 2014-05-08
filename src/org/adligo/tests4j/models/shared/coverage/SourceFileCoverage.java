package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @see I_SourceFileCoverage
 * @author scott
 *
 */
public class SourceFileCoverage implements I_SourceFileCoverage {
	private SourceFileCoverageMutant mutant;
	private List<LineCoverage> lines;
	
	public SourceFileCoverage() {
		mutant = new SourceFileCoverageMutant();
	}
	
	public SourceFileCoverage(I_SourceFileCoverage p) {
		mutant = new SourceFileCoverageMutant(p, false);
		int lastLine = p.getLastLine();
		if (lastLine >= 1) {
			lines = new ArrayList<LineCoverage>();
			for (int i = 0; i < lastLine; i++) {
				lines.add(new LineCoverage(p.getLineCoverage(i)));
			}
			lines = Collections.unmodifiableList(lines);
		} else {
			lines = Collections.emptyList();
		}
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

	public String getClassName() {
		return mutant.getClassName();
	}

	public int getLastLine() {
		return lines.size();
	}

	public I_LineCoverage getLineCoverage(int p) {
		return lines.get(p);
	}

	public String toString() {
		return mutant.toString();
	}

	public int hashCode() {
		return mutant.hashCode();
	}

	public boolean equals(Object obj) {
		return mutant.equals(obj);
	}
}
