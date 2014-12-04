package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverageBrief;
import org.adligo.tests4j.models.shared.metadata.I_UseCaseBrief;
import org.adligo.tests4j.system.shared.trials.UseCaseScope;

import java.util.Map;

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
	public I_UseCaseBrief getUseCase();
	
	/**
	 * The system this use case is testing
	 * if it is testing a system, or null
	 * if it is testing a project
	 * @return
	 */
	public String getSystem();
	/**
   * The project this use case is testing
   * if it is testing a project, or null
   * if it is testing a system.
   * @return
   */
	public String getProject();
	
	/**
	 * @return a map of top level packages
	 * which were touched by the use case trial.
	 * This may be a empty map if the OmitCodeCoverage
	 * annotation is used for the trial which corresponds 
	 * to this result.
	 */
	public Map<String,I_PackageCoverageBrief> getCoverageMap();
	/**
   * @return the I_PackageCoverageBreif of the packageName
   * for any package which was touched by the trial.
   */
	public I_PackageCoverageBrief getPackageCoverage(String packageName);
}
