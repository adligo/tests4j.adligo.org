package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;

@TrialTypeAnnotation(type = TrialType.SOURCE_FILE_TRIAL_TYPE)
public class SourceFileTrial extends AbstractTrial implements I_SourceFileTrial  {
	
	/**
	 * @see I_SourceFileTrial#afterTrialTests(I_SourceFileTrialResult)
	 */
	public void afterTrialTests(I_SourceFileTrialResult p) {}
}
