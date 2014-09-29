package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.metadata.I_UseCaseMetadata;
import org.adligo.tests4j.system.shared.trials.UseCaseScope;

/**
 * @see UseCaseScope
 * @author scott
 *
 */
public interface I_UseCaseTrialResult extends I_TrialResult {
	/**
	 * @see UseCaseScope#nown()
	 * @see UseCaseScope#verb()
	 * @return
	 */
	public I_UseCaseMetadata getUseCase();
	/**
	 * @see UseCaseScope#system()
	 * @return
	 */
	public String getSystem();
}
