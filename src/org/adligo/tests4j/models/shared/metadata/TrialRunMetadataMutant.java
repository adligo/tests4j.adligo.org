package org.adligo.tests4j.models.shared.metadata;

import java.util.ArrayList;
import java.util.List;

public class TrialRunMetadataMutant implements I_TrialRunMetadata {
	private List<TrialMetadataMutant> trials = new ArrayList<TrialMetadataMutant>();

	public TrialRunMetadataMutant() {}

	public TrialRunMetadataMutant(I_TrialRunMetadata other) {
		setTrials(other.getTrials());
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata#getTrials()
	 */
	@Override
	public List<I_TrialMetadata> getTrials() {
		List<I_TrialMetadata> toRet = new ArrayList<I_TrialMetadata>();
		return toRet;
	}

	public void setTrials(List<? extends I_TrialMetadata> p) {
		trials.clear();
		for (I_TrialMetadata trial: p) {
			trials.add(new TrialMetadataMutant(trial));
		}
	}
	
}