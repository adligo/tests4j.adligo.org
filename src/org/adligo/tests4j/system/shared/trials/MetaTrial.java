package org.adligo.tests4j.system.shared.trials;

import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.shared.common.TrialType;

/**
 * The basic meta trial, which does nothing,
 * to use extend and override methods.
 * @author scott
 *
 */
@TrialTypeAnnotation (type=TrialType.META_TRIAL_TYPE)
public class MetaTrial extends AbstractTrial implements I_MetaTrial {

	@Override
	public void afterMetadataCalculated(I_TrialRunMetadata metadata)
			throws Exception {
		
	}

	@Override
	public void afterNonMetaTrialsRun(I_TrialRunResult results)
			throws Exception {
		
	}

}
