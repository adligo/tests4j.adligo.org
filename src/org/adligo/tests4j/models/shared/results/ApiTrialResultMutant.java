package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.PackageCoverageMutant;

public class ApiTrialResultMutant extends BaseTrialResultMutant implements I_ApiTrialResult {
	private PackageCoverageMutant packageCoverage;
	private String packageName;
	
	public ApiTrialResultMutant() {}
	
	public ApiTrialResultMutant(I_ApiTrialResult p) {
		this(p, true);
	}
	
	public ApiTrialResultMutant(I_ApiTrialResult p, boolean cloneRelations) {
		super(p, cloneRelations);
		if (cloneRelations) {
			if (p.hasRecordedCoverage()) {
				packageCoverage = new PackageCoverageMutant(p.getPackageCoverage()); 
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
	public I_PackageCoverage getPackageCoverage() {
		return packageCoverage;
	}
	@Override
	public String getPackageName() {
		// TODO Auto-generated method stub
		return packageName;
	}

	public void setPackageCoverage(I_PackageCoverage p) {
		packageCoverage = new PackageCoverageMutant(p);
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
	
	@Override
	public boolean hasRecordedCoverage() {
		if (packageCoverage == null) {
			return false;
		}
		return true;
	}
}
