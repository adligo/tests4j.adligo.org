package org.adligo.tests4j.models.shared;

public class AbstractTrialProtectedWrapper {
	private AbstractTrial trial;
	
	public AbstractTrialProtectedWrapper(AbstractTrial p) {
		trial = p;
	}
	
	public void setMemory(AbstractTrialMemory memory) {
		trial.setMemory(memory);
	}
}
