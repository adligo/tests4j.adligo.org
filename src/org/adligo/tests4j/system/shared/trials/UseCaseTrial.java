package org.adligo.tests4j.system.shared.trials;

import org.adligo.tests4j.models.shared.results.I_UseCaseTrialResult;
import org.adligo.tests4j.shared.common.TrialType;

@TrialTypeAnnotation(type = TrialType.USE_CASE_TRIAL_TYPE)
public class UseCaseTrial extends AbstractTrial implements I_UseCaseTrial {

	/**
	 * override this if you want to test your results
	 * at a detailed level
	 */
	@Override
	public void afterTrialTests(I_UseCaseTrialResult p) {
		
	}

}
