package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverageBrief;
import org.adligo.tests4j.models.shared.metadata.I_UseCaseBrief;

import java.util.Map;


public class UseCaseTrialResult extends BaseTrialResult implements I_UseCaseTrialResult {
	private UseCaseTrialResultMutant mutant;
	
	public UseCaseTrialResult() {
		mutant = new UseCaseTrialResultMutant();
	}
	
	public UseCaseTrialResult(I_UseCaseTrialResult p) {
		super(p);
		mutant = new UseCaseTrialResultMutant(p, false);
	}

	public I_UseCaseBrief getUseCase() {
		return mutant.getUseCase();
	}

  public String getSystem() {
    return mutant.getSystem();
  }

  public String getProject() {
    return mutant.getProject();
  }

  @Override
  public Map<String, I_PackageCoverageBrief> getCoverageMap() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public I_PackageCoverageBrief getPackageCoverage(String packageName) {
    // TODO Auto-generated method stub
    return null;
  }


}
