package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public interface I_Tests4J_Memory {

	public I_Tests4J_Log getLog();

	public I_EvaluatorLookup getEvaluationLookup();
	
	public boolean hasCoveragePlugin();
	
	public I_Tests4J_CoveragePlugin getCoveragePlugin();

	public void addResultBeforeMetadata(I_TrialResult p);
	
}