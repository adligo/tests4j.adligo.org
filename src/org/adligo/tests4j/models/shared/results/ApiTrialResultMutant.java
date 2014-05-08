package org.adligo.tests4j.models.shared.results;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.PackageCoverageMutant;

public class ApiTrialResultMutant extends BaseTrialResultMutant implements I_ApiTrialResult {
	private List<PackageCoverageMutant> packageCoverage;
	private String packageName;
	
	public ApiTrialResultMutant() {
		packageCoverage = new ArrayList<PackageCoverageMutant>();
	}
	
	public ApiTrialResultMutant(I_ApiTrialResult p) {
		this(p, true);
	}
	
	public ApiTrialResultMutant(I_ApiTrialResult p, boolean cloneRelations) {
		super(p, cloneRelations);
		if (cloneRelations) {
			
			List<I_PackageCoverage> otherCover = p.getPackageCoverage();
			packageCoverage = new ArrayList<PackageCoverageMutant>();
			for (I_PackageCoverage pk: otherCover) {
				packageCoverage.add(new PackageCoverageMutant(pk));
			}
		}
		packageName = p.getPackageName();
	}
	
	public ApiTrialResultMutant(I_TrialResult p) {
		this(p, true);
	}
	
	public ApiTrialResultMutant(I_TrialResult p, boolean cloneRelations) {
		super(p, cloneRelations);
	}
	
	@Override
	public List<I_PackageCoverage> getPackageCoverage() {
		List<I_PackageCoverage> toRet = new ArrayList<I_PackageCoverage>();
		if (packageCoverage != null) {
			toRet.addAll(packageCoverage);
		}
		return toRet;
	}
	@Override
	public String getPackageName() {
		// TODO Auto-generated method stub
		return packageName;
	}

	public void setPackageCoverage(List<I_PackageCoverage> p) {
		packageCoverage.clear();
		for (I_PackageCoverage other: p) {
			packageCoverage.add(new PackageCoverageMutant(other));
		}
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toString(this, sb);
		return sb.toString();
	}
	
	public void toString(I_ApiTrialResult result, StringBuilder sb) {
		super.toString(result, sb);
		sb.append(",packageName=");
		sb.append(result.getPackageName());
		sb.append(",packageCoverage=");
		sb.append(result.getPackageCoverage());
		sb.append("]");
	}
}
