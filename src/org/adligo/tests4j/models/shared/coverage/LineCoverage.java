package org.adligo.tests4j.models.shared.coverage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * immutable @see {@link I_LineCoverage}
 * @author scott
 *
 */
public class LineCoverage implements I_LineCoverage {
	private LineCoverageMutant mutant;
	private List<I_LineCoverageSegment> segments;
	
	public LineCoverage() {
		mutant = new LineCoverageMutant();
		segments = Collections.emptyList();
	}
	
	public LineCoverage(I_LineCoverage p) {
		mutant = new LineCoverageMutant(p, false);
		if (p.hasSegments()) {
			short segmentCount = p.getLineCoverageSegmentCount();
			segments = new ArrayList<I_LineCoverageSegment>();
			for (short i = 0; i < segmentCount; i++) {
				segments.add(new LineCoverageSegment(p.getLineCoverageSegment(i)));
			}
			segments = Collections.unmodifiableList(segments);
		}
	}

	public Boolean getCovered() {
		return mutant.getCovered();
	}

	public short getCoverageUnits() {
		return mutant.getCoverageUnits();
	}

	public short getCoveredCoverageUnits() {
		return mutant.getCoveredCoverageUnits();
	}

	public short getBranches() {
		return mutant.getBranches();
	}

	public short getCoveredBranches() {
		return mutant.getCoveredBranches();
	}

	public int hashCode() {
		return mutant.hashCode();
	}

	public boolean hasSegments() {
		return mutant.hasSegments();
	}

	public short getLineCoverageSegmentCount() {
		return mutant.getLineCoverageSegmentCount();
	}

	public I_LineCoverageSegment getLineCoverageSegment(short p) {
		return mutant.getLineCoverageSegment(p);
	}

	public boolean equals(Object obj) {
		return mutant.equals(obj);
	}

	@Override
	public String toString() {
		return mutant.toString(this);
	}
}
