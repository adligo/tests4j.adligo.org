package org.adligo.tests4j.models.shared.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrialRunMetadataMutant implements I_TrialRunMetadata {
	private List<TrialMetadataMutant> trials = new ArrayList<TrialMetadataMutant>();
	private Map<String, SourceInfoMutant> sourceInfoMap = new HashMap<String, SourceInfoMutant>();
	
	public TrialRunMetadataMutant() {}

	public TrialRunMetadataMutant(I_TrialRunMetadata other) {
		setTrials(other.getAllTrialMetadata());
		Collection<String> clazzNames = other.getAllSourceInfo();
		for (String s: clazzNames) {
			setSourceInfo(s, other.getSourceInfo(s));
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata#getTrials()
	 */
	@Override
	public List<I_TrialMetadata> getAllTrialMetadata() {
		List<I_TrialMetadata> toRet = new ArrayList<I_TrialMetadata>();
		toRet.addAll(trials);
		return toRet;
	}

	public void setTrials(List<? extends I_TrialMetadata> p) {
		trials.clear();
		for (I_TrialMetadata trial: p) {
			addTrial(trial);
		}
	}

	public void addTrial(I_TrialMetadata trial) {
		trials.add(new TrialMetadataMutant(trial));
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
	
	public I_SourceInfo getSourceInfo(String name) {
		return sourceInfoMap.get(name);
	}
	
	public void setSourceInfo(String name, I_SourceInfo p) {
		sourceInfoMap.put(name, new SourceInfoMutant(p));
	}
	
	public void setSourceInfo(String name, SourceInfoMutant p) {
		sourceInfoMap.put(name, p);
	}
	
	public Collection<String> getAllSourceInfo() {
		return sourceInfoMap.keySet();
	}
}
