package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverageBrief;
import org.adligo.tests4j.models.shared.coverage.PackageCoverageBrief;

public class ApiTrialResult extends BaseTrialResult implements I_ApiTrialResult {
	private ApiTrialResultMutant mutant;
	private I_PackageCoverageBrief packageCoverage;
	
	public ApiTrialResult() {
		super();
		mutant = new ApiTrialResultMutant();
	}
	
	public ApiTrialResult(I_ApiTrialResult p) {
		super(p);
		mutant = new ApiTrialResultMutant(p, false);
		if (p.hasRecordedCoverage()) {
			packageCoverage = new PackageCoverageBrief(p.getPackageCoverage());
		}
		
	}

	public I_PackageCoverageBrief getPackageCoverage() {
		return packageCoverage;
	}

	public String getPackageName() {
		return mutant.getPackageName();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		mutant.toString(this, sb);
		return sb.toString();
	}
	
	@Override
	public boolean hasRecordedCoverage() {
		if (packageCoverage == null) {
			return false;
		}
		return true;
	}
}
