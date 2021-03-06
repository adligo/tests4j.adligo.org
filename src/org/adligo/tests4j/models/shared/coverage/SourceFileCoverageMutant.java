package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @see I_SourceFileCoverage
 * @author scott
 *
 */
public class SourceFileCoverageMutant extends CoverageUnitContinerMutant implements I_SourceFileCoverage {
	private String className;
	private List<LineCoverageMutant> lines = new ArrayList<LineCoverageMutant>();
	
	public SourceFileCoverageMutant() {}
	
	public SourceFileCoverageMutant(I_SourceFileCoverage p) {
		this(p, true);
	}
	
	public SourceFileCoverageMutant(I_SourceFileCoverage p, boolean cloneRelations) {
		super(p);
		className = p.getClassName();
		if (cloneRelations) {
			int lineCount = p.getLastLine();
			for (int i = 0; i < lineCount; i++) {
				I_LineCoverage cover = p.getLineCoverage(i);
				if (cover == null) {
					LineCoverageMutant lcm = new LineCoverageMutant();
					lcm.setCovered(true);
					lines.add(lcm);
				} else {
					lines.add(new LineCoverageMutant(cover));
				}
			}
		}
		
	}
	
	
	@Override
	public String getClassName() {
		return className;
	}
	
	@Override
	public int getLastLine() {
		return lines.size();
	}
	
	@Override
	public I_LineCoverage getLineCoverage(int p) {
		return lines.get(p);
	}

	@Override
	public String toString() {
		return toString(this);
	}
	
	public String toString(I_SourceFileCoverage p) {
		return p.getClass().getName() + " [className=" + p.getClassName() + ", lines="
				+ p.getLastLine() + ", coverageUnits=" + p.getCoverageUnits()
				+ ", coveredCoverageUnits=" + p.getCoveredCoverageUnits() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((lines == null) ? 0 : lines.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof I_SourceFileCoverage) {
				I_SourceFileCoverage other = (I_SourceFileCoverage) obj;
				if (className == null) {
					if (other.getClassName() != null)
						return false;
				} else if (!className.equals(other.getClassName()))
					return false;
				if (getLastLine() != other.getLastLine()) {
					return false;
				} else {
					int lineCount = lines.size();
					for (int i = 0; i < lineCount; i++) {
						I_LineCoverage line = lines.get(i);
						I_LineCoverage otherLine = other.getLineCoverage(i);
						if (!line.equals(otherLine)) {
							return false;
						}
					}
				}
		} else {
			return false;
		}
		return true;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setLines(List<? extends I_LineCoverage> p) {
		lines.clear();
		for (I_LineCoverage cover: p) {
			add(cover);
		}
	}

	public void add(I_LineCoverage p) {
		lines.add(new LineCoverageMutant(p));
	}
	
}
