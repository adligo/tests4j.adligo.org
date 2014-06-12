package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.UseCaseScope;
import org.adligo.tests4j.models.shared.metadata.I_UseCase;

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
	public I_UseCase getUseCase();
	/**
	 * @see UseCaseScope#system()
	 * @return
	 */
	public String getSystem();
}
