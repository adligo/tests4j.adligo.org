package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.metadata.I_UseCaseBrief;


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

	public String getUnit() {
		return mutant.getUnit();
	}

}
