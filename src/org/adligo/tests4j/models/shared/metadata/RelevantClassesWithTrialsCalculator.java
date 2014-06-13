package org.adligo.tests4j.models.shared.metadata;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;

public class RelevantClassesWithTrialsCalculator {
	private Set<String> classesWithOutTrials = new HashSet<String>();
	private double pct;
	
	public RelevantClassesWithTrialsCalculator(I_TrialRunMetadata metadata) {
		List<? extends I_TrialMetadata> trialMetas =  metadata.getAllTrialMetadata();
		Map<String,I_TrialMetadata> relevantClassesToTrials = new HashMap<String, I_TrialMetadata>();
		for (I_TrialMetadata tm: trialMetas) {
			TrialTypeEnum type =  tm.getType();
			if (type == TrialTypeEnum.SourceFileTrial) {
				relevantClassesToTrials.put(tm.getTestedClass(), tm);
			}
		}
		Collection<String> allClasses = metadata.getAllSourceInfo();
		
		double allRelevantClasses = 0;
		for (String clazzName: allClasses) {
			I_SourceInfo si = metadata.getSourceInfo(clazzName);
			if (!si.hasInterface()) {
				allRelevantClasses++;
				if (!relevantClassesToTrials.containsKey(clazzName)) {
					classesWithOutTrials.add(clazzName);
				}
			}
		}
		classesWithOutTrials = Collections.unmodifiableSet(classesWithOutTrials);
		double sourceFileTrials = relevantClassesToTrials.size();
		pct = 100 * (sourceFileTrials/allRelevantClasses);
	}

	public Set<String> getClassesWithOutTrials() {
		return classesWithOutTrials;
	}

	public double getPct() {
		return pct;
	}
}
