package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.association.I_ClassAssociations;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileProbes;
import org.adligo.tests4j.models.shared.coverage.SourceFileProbes;
import org.adligo.tests4j.shared.asserts.reference.I_ClassAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceFileTrialResult extends BaseTrialResult implements I_SourceFileTrialResult {
	private SourceFileTrialResultMutant mutant;
	private SourceFileProbes probes;
	private I_ClassAssociations dependencies;
	private Map<String, I_ClassAttributes> references_;
	
	public SourceFileTrialResult() {
		mutant = new SourceFileTrialResultMutant();
	}
	
	public SourceFileTrialResult(I_SourceFileTrialResult p) {
		super(p);
		mutant = new SourceFileTrialResultMutant(p, false);
		if (p.hasRecordedCoverage()) {
			probes = new SourceFileProbes( p.getSourceFileProbes());
		}
		dependencies = p.getDependencies();
		if (dependencies != null) {
			references_ = new HashMap<>();
			List<I_ClassAttributes> ars = dependencies.getReferences();
			for (I_ClassAttributes ar: ars) {
				references_.put(ar.getName(), ar);
			}
			references_ = Collections.unmodifiableMap(references_);
		} else {
			references_ = Collections.emptyMap();
		}
	}
	
	public I_SourceFileProbes getSourceFileProbes() {
		return probes;
	}

	public String getSourceFileName() {
		return mutant.getSourceFileName();
	}
	
	@Override
	public boolean hasRecordedCoverage() {
		if (probes == null) {
			return false;
		}
		return true;
	}

	public I_ClassAssociations getDependencies() {
		return dependencies;
	}

	public void setDependencies(I_ClassAssociations dependencies) {
		this.dependencies = dependencies;
	}

	@Override
	public I_ClassAttributes getReferences(String className) {
		return references_.get(className);
	}

	@Override
	public I_ClassAttributes getSourceClassAttributes() {
		return references_.get(mutant.getSourceFileName());
	}
}
