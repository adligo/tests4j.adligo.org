package org.adligo.tests4j.models.shared.results;

public class UseCaseTrialResultMutant extends BaseTrialResultMutant implements I_UseCaseTrialResult {
	private I_UseCase useCase;
	private String system;
	
	public UseCaseTrialResultMutant() {}
	
	public UseCaseTrialResultMutant(I_UseCaseTrialResult p) {
		this(p, true);
	}
	
	public UseCaseTrialResultMutant(I_UseCaseTrialResult p, boolean cloneRelaions) {
		super(p, cloneRelaions);
		useCase = p.getUseCase();
		system = p.getSystem();
	}

	public UseCaseTrialResultMutant(I_TrialResult p) {
		this(p, true);
	}
	
	public UseCaseTrialResultMutant(I_TrialResult p, boolean cloneRelaions) {
		super(p, cloneRelaions);
	}
	
	public I_UseCase getUseCase() {
		return useCase;
	}

	public String getSystem() {
		return system;
	}

	public void setUseCase(I_UseCase useCase) {
		this.useCase = useCase;
	}

	public void setSystem(String system) {
		this.system = system;
	}
	
	
}
