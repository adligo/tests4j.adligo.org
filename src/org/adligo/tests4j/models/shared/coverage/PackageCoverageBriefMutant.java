package org.adligo.tests4j.models.shared.coverage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @see I_PackageCoverageBrief
 * 
 * @author scott
 *
 */
public class PackageCoverageBriefMutant extends CoverageUnitContinerMutant implements I_PackageCoverageBrief {
	private Map<String, SourceFileCoverageMutant> sourceFiles_ =
			new HashMap<String, SourceFileCoverageMutant>();
	private String packageName_;
	/**
	 * return the child packages if any
	 * or a empty list 
	 * regardless of if they were covered at all.
	 * @return
	 */
	private List<PackageCoverageBriefMutant> children_ = new ArrayList<PackageCoverageBriefMutant>();

	
	public PackageCoverageBriefMutant() {}
	
	public PackageCoverageBriefMutant(I_PackageCoverageBrief p) {
		this(p, true);
	}
	
	public PackageCoverageBriefMutant(I_PackageCoverageBrief p, boolean cloneRelations) {
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
			List<I_PackageCoverageBrief> otherChildren =  p.getChildPackageCoverage();
			if (otherChildren != null) {
				for (I_PackageCoverageBrief coverage: otherChildren) {
					children_.add(new PackageCoverageBriefMutant(coverage));
				}
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
	public List<I_PackageCoverageBrief> getChildPackageCoverage() {
		List<I_PackageCoverageBrief> toRet = new ArrayList<I_PackageCoverageBrief>();
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
	
	public String toString(I_PackageCoverageBrief p) {
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

	public List<PackageCoverageBriefMutant> getChildren() {
		return children_;
	}

	public void setChildren(List<PackageCoverageBriefMutant> children) {
		if (children != null) {
			children_.clear();
			children_.addAll(children);
		}
	}

	public void addChild(I_PackageCoverageBrief child) {
		children_.add(new PackageCoverageBriefMutant(child));
	}
	
	public void addChild(PackageCoverageBriefMutant child) {
		children_.add(child);
	}
}
