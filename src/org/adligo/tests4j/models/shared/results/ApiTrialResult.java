package org.adligo.tests4j.models.shared.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.PackageCoverage;

public class ApiTrialResult extends BaseTrialResult implements I_ApiTrialResult {
	private ApiTrialResultMutant mutant;
	private List<I_PackageCoverage> packageCoverage;
	
	public ApiTrialResult() {
		super();
		mutant = new ApiTrialResultMutant();
		packageCoverage = Collections.emptyList();
	}
	
	public ApiTrialResult(I_ApiTrialResult p) {
		super(p);
		mutant = new ApiTrialResultMutant(p, false);
		
		List<I_PackageCoverage> otherCover = p.getPackageCoverage();
		if (otherCover.size() >= 1) {
			packageCoverage = new ArrayList<I_PackageCoverage>();
			for (I_PackageCoverage other: otherCover) {
				packageCoverage.add(new PackageCoverage(other));
			}
			packageCoverage = Collections.unmodifiableList(packageCoverage);
		} else {
			packageCoverage = Collections.emptyList();
		}
		
		
		
	}

	public List<I_PackageCoverage> getPackageCoverage() {
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
}
