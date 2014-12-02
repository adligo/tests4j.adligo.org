package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.metadata.I_UseCaseBrief;
import org.adligo.tests4j.system.shared.trials.UseCaseScope;

/**
 * @see UseCaseScope
 * @author scott
 *
 */
public interface I_UseCaseTrialResult extends I_UnitTrialResult {
	/**
	 * @see UseCaseScope#nown()
	 * @see UseCaseScope#verb()
	 * @return
	 */
	public I_UseCaseBrief getUseCase();
}
