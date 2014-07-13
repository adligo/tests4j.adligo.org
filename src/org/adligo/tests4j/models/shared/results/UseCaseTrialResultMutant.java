package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.metadata.I_UseCaseMetadata;

public class UseCaseTrialResultMutant extends BaseTrialResultMutant implements I_UseCaseTrialResult {
	private I_UseCaseMetadata useCase;
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
	
	public I_UseCaseMetadata getUseCase() {
		return useCase;
	}

	public String getSystem() {
		return system;
	}

	public void setUseCase(I_UseCaseMetadata useCase) {
		this.useCase = useCase;
	}

	public void setSystem(String system) {
		this.system = system;
	}
	
	
}
