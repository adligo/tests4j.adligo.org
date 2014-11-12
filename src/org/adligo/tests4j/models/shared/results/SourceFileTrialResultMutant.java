package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.association.I_ClassAssociations;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverageBrief;
import org.adligo.tests4j.models.shared.coverage.SourceFileCoverageBriefMutant;
import org.adligo.tests4j.shared.asserts.reference.I_ClassAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceFileTrialResultMutant extends BaseTrialResultMutant implements I_SourceFileTrialResult {
	private SourceFileCoverageBriefMutant probes;
	private String sourceFileName;
	private I_ClassAssociations dependencies;
	private Map<String, I_ClassAttributes> references_;
	
	public SourceFileTrialResultMutant() {}

	public SourceFileTrialResultMutant(I_SourceFileTrialResult p) {
		this(p, true);
	}
	
	public SourceFileTrialResultMutant(I_SourceFileTrialResult p, boolean cloneRelations) {
		super(p, cloneRelations);
		if (cloneRelations) {
			probes = new SourceFileCoverageBriefMutant(p.getSourceFileCoverage());
		}
		sourceFileName = p.getSourceFileName();
		setDependencies(p.getDependencies());
	}

	public SourceFileTrialResultMutant(I_TrialResult p) {
		this(p, true);
	}
	
	public SourceFileTrialResultMutant(I_TrialResult p, boolean cloneRelations) {
		super(p, cloneRelations);
	}
	
	@Override
	public I_SourceFileCoverageBrief getSourceFileCoverage() {
		return probes;
	}

	public void setSourceFileProbes(I_SourceFileCoverageBrief p) {
		this.probes = new SourceFileCoverageBriefMutant(p);
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toString(this, sb);
		sb.append(",coverage=");
		sb.append(probes);
		sb.append(",sourceFileName=");
		sb.append(sourceFileName);
		sb.append("]");
		return sb.toString();
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

	public void setDependencies(I_ClassAssociations classDependencies) {
		if (references_ != null) {
			references_.clear();
		} else {
			references_ = new HashMap<>();
		}
		dependencies = classDependencies;
		if (dependencies != null) {
			List<I_ClassAttributes> ars = dependencies.getReferences();
			for (I_ClassAttributes ar: ars) {
				references_.put(ar.getName(), ar);
			}
		}
	}

	@Override
	public I_ClassAttributes getReferences(String className) {
		return references_.get(className);
	}

	@Override
	public I_ClassAttributes getSourceClassAttributes() {
		return references_.get(sourceFileName);
	}
}
