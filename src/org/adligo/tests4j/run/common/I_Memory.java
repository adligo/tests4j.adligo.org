package org.adligo.tests4j.run.common;

import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.shared.asserts.reference.I_ReferenceGroup;
import org.adligo.tests4j.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoveragePlugin;

public interface I_Memory {
  public I_Tests4J_Constants getConstants();
  
	public I_Tests4J_Log getLog();

	public I_EvaluatorLookup getEvaluationLookup();
	
	public boolean hasCoveragePlugin();
	
	public I_Tests4J_CoveragePlugin getCoveragePlugin();

	public void addResultBeforeMetadata(I_TrialResult p);
	
	public I_TrialResult pollFailureResults();
	
	public int getFailureResultsSize();

	public I_ReferenceGroup getDependencyGroup(Class<?> c);
	
	public void putIfAbsent(Class<?> c, I_ReferenceGroup group);
	
}