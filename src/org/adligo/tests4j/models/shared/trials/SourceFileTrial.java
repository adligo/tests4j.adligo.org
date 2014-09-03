package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;

@TrialTypeAnnotation(type = TrialType.SOURCE_FILE_TRIAL_TYPE)
public class SourceFileTrial extends AbstractTrial implements I_SourceFileTrial  {
	public static final String TEST_SOURCE_CLASS_MIN_COVERAGE = "testSourceClassMinCoverage";
	public static final String TEST_SOURCE_CLASS_DEPENDENCIES = "testSourceClassDependencies";
	/**
	 * @see I_SourceFileTrial#afterTrialTests(I_SourceFileTrialResult)
	 */
	public void afterTrialTests(I_SourceFileTrialResult p) {}
	
	/**
	 * this method simply reserves the method name, 
	 * so it can't be over ridden, the actual logic for this
	 * is in the SourceFileTrialProcessor
	 */
	public final void testSourceClassMinCoverage() {}
	
	/**
	 * this method simply reserves the method name, 
	 * so it can't be over ridden, the actual logic for this
	 * is in the SourceFileTrialProcessor
	 */
	public final void testSourceClassDependencies() {}
}
