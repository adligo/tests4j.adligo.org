package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.metadata.I_UseCase;


public class UseCaseTrialResult extends BaseTrialResult {
	private UseCaseTrialResultMutant mutant;
	
	public UseCaseTrialResult() {
		mutant = new UseCaseTrialResultMutant();
	}
	
	public UseCaseTrialResult(I_UseCaseTrialResult p) {
		super(p);
		mutant = new UseCaseTrialResultMutant(p, false);
	}

	public I_UseCase getUseCase() {
		return mutant.getUseCase();
	}

	public String getSystem() {
		return mutant.getSystem();
	}

}
