package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.metadata.I_UseCaseMetadata;


public class UseCaseTrialResult extends BaseTrialResult implements I_UseCaseTrialResult {
	private UseCaseTrialResultMutant mutant;
	
	public UseCaseTrialResult() {
		mutant = new UseCaseTrialResultMutant();
	}
	
	public UseCaseTrialResult(I_UseCaseTrialResult p) {
		super(p);
		mutant = new UseCaseTrialResultMutant(p, false);
	}

	public I_UseCaseMetadata getUseCase() {
		return mutant.getUseCase();
	}

	public String getSystem() {
		return mutant.getSystem();
	}

}
