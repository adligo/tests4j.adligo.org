package org.adligo.tests4j.models.shared.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrialRunMetadata implements I_TrialRunMetadata {
	private List<I_TrialMetadata> trials = new ArrayList<I_TrialMetadata>();
	private Map<String, I_SourceInfo> sourceInfoMap = new HashMap<String, I_SourceInfo>();
	
	public TrialRunMetadata() {}
	
	public TrialRunMetadata(I_TrialRunMetadata p) {
		List<? extends I_TrialMetadata> others = p.getAllTrialMetadata();
		for (I_TrialMetadata trialMd: others) {
			trials.add(new TrialMetadata(trialMd));
		}
		trials = Collections.unmodifiableList(trials);
		Collection<String> sourceInfoNames = p.getAllSourceInfo();
		for (String s: sourceInfoNames) {
			sourceInfoMap.put(s, new SourceInfo(p.getSourceInfo(s)));
		}
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

	@Override
	public I_SourceInfo getSourceInfo(String name) {
		return sourceInfoMap.get(name);
	}

	@Override
	public Collection<String> getAllSourceInfo() {
		return sourceInfoMap.keySet();
	}
}
