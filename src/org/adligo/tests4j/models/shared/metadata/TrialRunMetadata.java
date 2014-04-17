package org.adligo.tests4j.models.shared.metadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrialRunMetadata implements I_TrialRunMetadata {
	private List<I_TrialMetadata> trials = new ArrayList<I_TrialMetadata>();
	
	public TrialRunMetadata() {}
	
	public TrialRunMetadata(I_TrialRunMetadata p) {
		List<? extends I_TrialMetadata> others = p.getTrials();
		for (I_TrialMetadata trialMd: others) {
			trials.add(new TrialMetadata(trialMd));
		}
		trials = Collections.unmodifiableList(trials);
	}

	public List<? extends I_TrialMetadata> getTrials() {
		return trials;
	}
}
