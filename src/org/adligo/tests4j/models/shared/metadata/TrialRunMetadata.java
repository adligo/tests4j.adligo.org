package org.adligo.tests4j.models.shared.metadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrialRunMetadata implements I_TrialRunMetadata {
	private List<I_TrialMetadata> trials = new ArrayList<I_TrialMetadata>();
	
	public TrialRunMetadata() {}
	
	public TrialRunMetadata(I_TrialRunMetadata p) {
		List<? extends I_TrialMetadata> others = p.getAllTrialMetadata();
		for (I_TrialMetadata trialMd: others) {
			trials.add(new TrialMetadata(trialMd));
		}
		trials = Collections.unmodifiableList(trials);
	}

	public List<? extends I_TrialMetadata> getAllTrialMetadata() {
		return trials;
	}
	
	@Override
	public int getAllTrialsCount() {
		return trials.size();
	}

	@Override
	public int getAllTestsCount() {
		int toRet = 0;
		for (I_TrialMetadata trial: trials) {
			toRet = toRet + trial.getTestCount();
		}
		return toRet;
	}
}
