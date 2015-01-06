package org.adligo.tests4j.run.discovery;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.adligo.tests4j.models.shared.metadata.I_SourceInfoMetadata;
import org.adligo.tests4j.models.shared.metadata.I_TrialMetadata;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.shared.common.ClassMethods;
import org.adligo.tests4j.shared.common.I_TrialType;
import org.adligo.tests4j.shared.common.TrialType;

public class ClassesWithSourceFileTrialsCalculator {
	private I_TrialRunMetadata metadata;
	private Map<String,I_TrialMetadata> sourceFileNamesToTrials_ = new HashMap<String, I_TrialMetadata>();
	private Map<String,List<I_TrialMetadata>> sourceFilePackageNamesToTrials_ = 
				new HashMap<String, List<I_TrialMetadata>>();
	private Map<String,Map<String,I_TrialMetadata>> sourceFilePackageTo_SourceFilesToTrials_ = 
			new HashMap<String,Map<String,I_TrialMetadata>>();
	private Set<String> sourceFileClasses_ = new HashSet<String>();
	private Set<String> sourceFileClassesWithTrials_ = new HashSet<String>();
	
	private BigDecimal pct;
	
	public ClassesWithSourceFileTrialsCalculator(I_TrialRunMetadata pMetadata) {
		metadata = pMetadata;
		List<? extends I_TrialMetadata> trialMetas =  metadata.getAllTrialMetadata();
		
		for (I_TrialMetadata tm: trialMetas) {
			I_TrialType type =  tm.getType();
			if (type == TrialType.SourceFileTrial) {
				String testedSourceFile = tm.getTestedSourceFile();
				sourceFileNamesToTrials_.put(testedSourceFile, tm);
				String packageName = ClassMethods.getPackageName(testedSourceFile);
				List<I_TrialMetadata> trials =  sourceFilePackageNamesToTrials_.get(packageName);
				if (trials == null) {
					trials = new ArrayList<I_TrialMetadata>();
					sourceFilePackageNamesToTrials_.put(packageName, trials);
				}
				trials.add(tm);
			}
		}
		Collection<String> allClasses = metadata.getAllSourceInfo();
		//set up the counting
		int srcClassesWithTrial = 0;
		int srcClasses = allClasses.size();
		
		
		for (String clazzName: allClasses) {
			sourceFileClasses_.add(clazzName);
			
			String packageName = ClassMethods.getPackageName(clazzName);
			Map<String, I_TrialMetadata> sourceFilesToTrials = 
						sourceFilePackageTo_SourceFilesToTrials_.get(packageName);
			if (sourceFilesToTrials == null) {
				sourceFilesToTrials = new HashMap<String, I_TrialMetadata>();	
				sourceFilePackageTo_SourceFilesToTrials_.put(packageName, sourceFilesToTrials);
			}
			
			String simpName = ClassMethods.getSimpleName(clazzName);
			
			I_TrialMetadata trialMeta = sourceFileNamesToTrials_.get(clazzName);
			if (trialMeta != null) {
				sourceFileClassesWithTrials_.add(clazzName);
				srcClassesWithTrial++;
			}
			if (!"package-info".equals(simpName)) {
			  sourceFilesToTrials.put(simpName, trialMeta);
			}
		}
		
		
		double srcClassesWithTrialD = srcClassesWithTrial;
		double srcClassesD = srcClasses;
		pct = new BigDecimal(100 * (srcClassesWithTrialD/srcClassesD)).setScale(2, RoundingMode.HALF_UP);
	}

	public Set<String> getClassesWithOutTrials(String packageName) {
		Map<String, I_TrialMetadata> sourceFilesToTrials = 
				sourceFilePackageTo_SourceFilesToTrials_.get(packageName);
		Set<String> toRet = new TreeSet<String>();
		if (sourceFilesToTrials != null) {
			Set<Entry<String,I_TrialMetadata>> entries = sourceFilesToTrials.entrySet();
			for (Entry<String,I_TrialMetadata> e: entries) {
				if (e.getValue() == null) {
					toRet.add(e.getKey());
				}
			}
		}
		return toRet;
	}
	
	public Set<String> getClassesWithOutTrials() {
		Set<String> toRet = new TreeSet<String>();
		toRet.addAll(sourceFileClasses_);
		toRet.removeAll(sourceFileClassesWithTrials_);
		return toRet;
	}
	public double getPctWithTrialsDouble() {
		if (pct == null) {
			return 0.0;
		}
		return pct.doubleValue();
	}
	
	public BigDecimal getPctWithTrials() {
		return pct;
	}
	public double getPctWithTrialsDouble(String packageName) {
		BigDecimal toRet = getPctWithTrials(packageName);
		if (toRet == null) {
			return 0.0;
		}
		return toRet.doubleValue();
	}
	
	public BigDecimal getPctWithTrials(String packageName) {
		Map<String, I_TrialMetadata> results = sourceFilePackageTo_SourceFilesToTrials_.get(packageName);
		if (results == null) {
			return new BigDecimal("0.00");
		}
		int size = results.keySet().size();
		if (size == 0) {
			return new BigDecimal("0.00");
		}
		Collection<I_TrialMetadata> trials =  results.values();
		int withTrials = 0;
		for (I_TrialMetadata tm: trials) {
			if (tm != null) {
				withTrials++;
			}
		}
		double withTrialsD = withTrials;
		double sizeD = size;
		double pct = 100 * (withTrialsD/sizeD);
		return new BigDecimal(pct).setScale(2, RoundingMode.HALF_UP);
	}
	
	public Set<String> getSourceFileTrialNames(String testedPackageName) throws IOException {
		I_PackageDiscovery discovery = new PackageDiscovery(testedPackageName);
		Collection<String> allClasses = discovery.getClassNames();
		
		Set<String> trialNames = new HashSet<String>();
		for (String clazzName: allClasses) {
				I_SourceInfoMetadata si = metadata.getSourceInfo(clazzName);
				if (!si.hasInterface()) {
					I_TrialMetadata tm = sourceFileNamesToTrials_.get(clazzName);
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
