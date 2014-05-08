package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @see I_PackageCoverage
 * 
 * @author scott
 *
 */
public class PackageCoverageMutant extends CoverageUnitContinerMutant implements I_PackageCoverage {
	private Map<String, SourceFileCoverageMutant> sourceFiles =
			new HashMap<String, SourceFileCoverageMutant>();
	private String packageName;
	/**
	 * return the child packages if any
	 * or a empty list 
	 * regardless of if they were covered at all.
	 * @return
	 */
	private List<PackageCoverageMutant> children = new ArrayList<PackageCoverageMutant>();
	/**
	 * totals delegate
	 */
	private CoverageUnitContinerMutant totalCoverageUnits = new CoverageUnitContinerMutant();

	
	public PackageCoverageMutant() {}
	
	public PackageCoverageMutant(I_PackageCoverage p) {
		
	}
	public PackageCoverageMutant(I_PackageCoverage p, boolean cloneRelations) {
		super(p);
		packageName = p.getPackageName();
		if (cloneRelations) {
			Set<String> sfNames = p.getSourceFileNames();
			for (String name: sfNames) {
				sourceFiles.put(name, new SourceFileCoverageMutant(p.getCoverage(name)));
			}
			List<I_PackageCoverage> otherChildren =  p.getChildPackageCoverage();
			for (I_PackageCoverage coverage: otherChildren) {
				children.add(new PackageCoverageMutant(coverage));
			}
		}
		totalCoverageUnits.setCoverageUnits(p.getTotalCoverageUnits());
		totalCoverageUnits.setCoveredCoverageUnits(p.getTotalCoveredCoverageUnits());
		
	}
	
	@Override
	public String getPackageName() {
		return packageName;
	}

	@Override
	public I_SourceFileCoverage getCoverage(String sourceFileName) {
		return sourceFiles.get(sourceFileName);
	}

	@Override
	public Set<String> getSourceFileNames() {
		return sourceFiles.keySet();
	}

	@Override
	public List<I_PackageCoverage> getChildPackageCoverage() {
		List<I_PackageCoverage> toRet = new ArrayList<I_PackageCoverage>();
		toRet.addAll(children);
		return toRet;
	}

	@Override
	public boolean hasChildPackageCoverage() {
		if (children.size() >= 1) {
			return true;
		}
		return false;
	}

	@Override
	public I_CoverageUnits getTotalCoverageUnits() {
		return totalCoverageUnits.getCoverageUnits();
	}

	@Override
	public I_CoverageUnits getTotalCoveredCoverageUnits() {
		return totalCoverageUnits.getCoveredCoverageUnits();
	}

	@Override
	public BigDecimal getTotalPercentageCovered() {
		return totalCoverageUnits.getPercentageCovered();
	}

	@Override
	public String toString() {
		return toString(this);
	}
	
	public String toString(I_PackageCoverage p) {
		return p.getClass().getName() + " [packageName=" + p.getPackageName()
				+ ", coverage=" + getTotalPercentageCovered() +
				",child_packages=" + p.getChildPackageCoverage().size() + "]";
	}
}
