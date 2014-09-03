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
	private Map<String, SourceFileCoverageMutant> sourceFiles_ =
			new HashMap<String, SourceFileCoverageMutant>();
	private String packageName_;
	/**
	 * return the child packages if any
	 * or a empty list 
	 * regardless of if they were covered at all.
	 * @return
	 */
	private List<PackageCoverageMutant> children_ = new ArrayList<PackageCoverageMutant>();

	
	public PackageCoverageMutant() {}
	
	public PackageCoverageMutant(I_PackageCoverage p) {
		this(p, true);
	}
	
	public PackageCoverageMutant(I_PackageCoverage p, boolean cloneRelations) {
		super(p);
		packageName_ = p.getPackageName();
		if (cloneRelations) {
			Set<String> sfNames = p.getSourceFileNames();
			for (String name: sfNames) {
				I_SourceFileCoverage sfc = p.getCoverage(name);
				if (sfc != null) {
					sourceFiles_.put(name, new SourceFileCoverageMutant(sfc));
				}
			}
			List<I_PackageCoverage> otherChildren =  p.getChildPackageCoverage();
			for (I_PackageCoverage coverage: otherChildren) {
				children_.add(new PackageCoverageMutant(coverage));
			}
		}
	}
	
	@Override
	public String getPackageName() {
		return packageName_;
	}

	@Override
	public I_SourceFileCoverage getCoverage(String sourceFileName) {
		return sourceFiles_.get(sourceFileName);
	}

	@Override
	public Set<String> getSourceFileNames() {
		return sourceFiles_.keySet();
	}

	@Override
	public List<I_PackageCoverage> getChildPackageCoverage() {
		List<I_PackageCoverage> toRet = new ArrayList<I_PackageCoverage>();
		toRet.addAll(children_);
		return toRet;
	}

	@Override
	public boolean hasChildPackageCoverage() {
		if (children_.size() >= 1) {
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return toString(this);
	}
	
	public String toString(I_PackageCoverage p) {
		return p.getClass().getName() + " [packageName=" + p.getPackageName()
				+ ", coverage=" + getPercentageCovered() +
				",child_packages=" + p.getChildPackageCoverage().size() + "]";
	}

	public void setPackageName(String packageName) {
		this.packageName_ = packageName;
	}

	public Map<String, SourceFileCoverageMutant> getSourceFiles() {
		return sourceFiles_;
	}

	public void setSourceFiles(Map<String, SourceFileCoverageMutant> sourceFiles) {
		if (sourceFiles != null) {
			sourceFiles_.clear();
			sourceFiles_.putAll(sourceFiles);
		}
	}

	public List<PackageCoverageMutant> getChildren() {
		return children_;
	}

	public void setChildren(List<PackageCoverageMutant> children) {
		if (children != null) {
			children_.clear();
			children_.addAll(children);
		}
	}

	
	public void addChild(PackageCoverageMutant child) {
		children_.add(child);
	}
}
