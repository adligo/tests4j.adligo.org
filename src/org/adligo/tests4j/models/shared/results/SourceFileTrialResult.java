package org.adligo.tests4j.models.shared.results;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.coverage.SourceFileCoverage;
import org.adligo.tests4j.models.shared.dependency.I_ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.I_ClassDependencies;
import org.adligo.tests4j.shared.common.TrialType;

public class SourceFileTrialResult extends BaseTrialResult implements I_SourceFileTrialResult {
	private SourceFileTrialResultMutant mutant;
	private SourceFileCoverage coverage;
	private I_ClassDependencies dependencies;
	private Map<String, I_ClassAttributes> attributeRefs_;
	
	public SourceFileTrialResult() {
		mutant = new SourceFileTrialResultMutant();
	}
	
	public SourceFileTrialResult(I_SourceFileTrialResult p) {
		super(p);
		mutant = new SourceFileTrialResultMutant(p, false);
		if (p.hasRecordedCoverage()) {
			coverage = new SourceFileCoverage( p.getSourceFileCoverage());
		}
		dependencies = p.getDependencies();
		if (dependencies != null) {
			attributeRefs_ = new HashMap<>();
			List<I_ClassAttributes> ars = dependencies.getReferences();
			for (I_ClassAttributes ar: ars) {
				attributeRefs_.put(ar.getName(), ar);
			}
			attributeRefs_ = Collections.unmodifiableMap(attributeRefs_);
		} else {
			attributeRefs_ = Collections.emptyMap();
		}
	}
	
	public I_SourceFileCoverage getSourceFileCoverage() {
		return coverage;
	}

	public String getSourceFileName() {
		return mutant.getSourceFileName();
	}
	
	@Override
	public boolean hasRecordedCoverage() {
		if (coverage == null) {
			return false;
		}
		return true;
	}

	public I_ClassDependencies getDependencies() {
		return dependencies;
	}

	public void setDependencies(I_ClassDependencies dependencies) {
		this.dependencies = dependencies;
	}

	@Override
	public I_ClassAttributes getAttributes(String className) {
		return attributeRefs_.get(className);
	}

	@Override
	public I_ClassAttributes getSourceClassAttributes() {
		return attributeRefs_.get(mutant.getSourceFileName());
	}
}
