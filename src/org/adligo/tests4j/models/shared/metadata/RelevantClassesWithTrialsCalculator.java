package org.adligo.tests4j.models.shared.metadata;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.run.discovery.ClassDiscovery;

public class RelevantClassesWithTrialsCalculator {
	private I_TrialRunMetadata metadata;
	private Set<String> classesWithOutTrials = new HashSet<String>();
	private Map<String,I_TrialMetadata> relevantClassesToTrials = new HashMap<String, I_TrialMetadata>();
	private double pct;
	
	public RelevantClassesWithTrialsCalculator(I_TrialRunMetadata pMetadata) {
		metadata = pMetadata;
		List<? extends I_TrialMetadata> trialMetas =  metadata.getAllTrialMetadata();
		
		for (I_TrialMetadata tm: trialMetas) {
			TrialType type =  tm.getType();
			if (type == TrialType.SourceFileTrial) {
				relevantClassesToTrials.put(tm.getTestedSourceFile(), tm);
			}
		}
		Collection<String> allClasses = metadata.getAllSourceInfo();
		
		double allRelevantClasses = 0;
		for (String clazzName: allClasses) {
			I_SourceInfoMetadata si = metadata.getSourceInfo(clazzName);
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
	
	public double getPct(String packageName) {
		Collection<String> allClasses = metadata.getAllSourceInfo();
		
		Set<String> classesWithoutTrialsInPackage = new HashSet<String>();
		double allRelevantClasses = 0;
		for (String clazzName: allClasses) {
			if (clazzName.indexOf(packageName) != -1) {
				I_SourceInfoMetadata si = metadata.getSourceInfo(clazzName);
				if (!si.hasInterface()) {
					allRelevantClasses++;
					if (!classesWithoutTrialsInPackage.contains(clazzName)) {
						classesWithoutTrialsInPackage.add(clazzName);
					}
				}
			}
		}
		double sourceFileTrials = classesWithoutTrialsInPackage.size();
		double pct = 100 * (sourceFileTrials/allRelevantClasses);
		return pct;
	}
	
	public Set<String> getSourceFileTrialNames(String testedPackageName) throws IOException {
		ClassDiscovery discovery = new ClassDiscovery(testedPackageName);
		Collection<String> allClasses = discovery.getClassNames();
		
		Set<String> trialNames = new HashSet<String>();
		for (String clazzName: allClasses) {
				I_SourceInfoMetadata si = metadata.getSourceInfo(clazzName);
				if (!si.hasInterface()) {
					I_TrialMetadata tm = relevantClassesToTrials.get(clazzName);
					if (tm != null) {
						if (tm.getType() == TrialType.SourceFileTrial) {
							trialNames.add(tm.getTrialName());
						}
					}
				}
		}
		return trialNames;
	}
}
