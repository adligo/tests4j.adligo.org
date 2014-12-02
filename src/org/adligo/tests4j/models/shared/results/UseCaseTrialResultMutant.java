package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.metadata.I_UseCaseBrief;

public class UseCaseTrialResultMutant extends BaseTrialResultMutant implements I_UseCaseTrialResult {
	private I_UseCaseBrief useCase_;
	private String unit_;
	
	public UseCaseTrialResultMutant() {}
	
	public UseCaseTrialResultMutant(I_UseCaseTrialResult p) {
		this(p, true);
	}
	
	public UseCaseTrialResultMutant(I_UseCaseTrialResult p, boolean cloneRelaions) {
		super(p, cloneRelaions);
		useCase_ = p.getUseCase();
		unit_ = p.getName();
	}

	public UseCaseTrialResultMutant(I_TrialResult p) {
		this(p, true);
	}
	
	public UseCaseTrialResultMutant(I_TrialResult p, boolean cloneRelaions) {
		super(p, cloneRelaions);
	}
	
	public I_UseCaseBrief getUseCase() {
		return useCase_;
	}

	public String getUnit() {
		return unit_;
	}

	public void setUseCase(I_UseCaseBrief useCase) {
		this.useCase_ = useCase;
	}

	public void setUnit(String unit) {
		this.unit_ = unit;
	}
	
	
}
