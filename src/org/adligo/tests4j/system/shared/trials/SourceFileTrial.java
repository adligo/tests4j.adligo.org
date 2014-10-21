package org.adligo.tests4j.system.shared.trials;

import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;
import org.adligo.tests4j.shared.common.TrialType;

@TrialTypeAnnotation(type = TrialType.SOURCE_FILE_TRIAL_TYPE)
public class SourceFileTrial extends AbstractTrial implements I_SourceFileTrial  {
	/**
	 * @see I_SourceFileTrial#afterTrialTests(I_SourceFileTrialResult)
	 */
	public void afterTrialTests(I_SourceFileTrialResult p) {}
	
	/**
	 * this method simply reserves the method name, 
	 * so it can't be over ridden, the actual logic for this
	 * is in the SourceFileTrialProcessor
	 */
	public final void testMinCoverage(I_SourceFileTrialResult trialResultMutant) {}
	
	/**
	 * this method simply reserves the method name, 
	 * so it can't be over ridden, the actual logic for this
	 * is in the SourceFileTrialProcessor
	 */
	public final void testDependencies(I_SourceFileTrialResult trialResultMutant) {}
	
	/**
	 * this method simply reserves the method name, 
	 * so it can't be over ridden, the actual logic for this
	 * is in the SourceFileTrialProcessor
	 */
	public final void testReferences(I_SourceFileTrialResult trialResultMutant) {}
}
