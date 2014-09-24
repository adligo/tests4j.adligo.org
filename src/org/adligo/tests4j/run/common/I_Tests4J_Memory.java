package org.adligo.tests4j.run.common;

import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.dependency.I_DependencyGroup;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_Tests4J_SourceInfoParams;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public interface I_Tests4J_Memory {

	public I_Tests4J_Log getLog();

	public I_EvaluatorLookup getEvaluationLookup();
	
	public boolean hasCoveragePlugin();
	
	public I_Tests4J_CoveragePlugin getCoveragePlugin();

	public void addResultBeforeMetadata(I_TrialResult p);
	
	public I_TrialResult pollFailureResults();
	
	public int getFailureResultsSize();

	public I_DependencyGroup getDependencyGroup(Class<?> c);
	
	public void putIfAbsent(Class<?> c, I_DependencyGroup group);
	
}