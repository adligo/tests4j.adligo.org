package org.adligo.tests4j.system.shared.api;

import java.util.List;

import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;

/**
 * Implementations of this interface provide information about what is getting tested by 
 * the trial run to be passed in to the tests4j trial run. This can be useful when
 * users of Tests4J decide not to use the Adligo convention of keeping tests in a
 * different package than the source code.  
 * 
 * @author scott
 *
 */
public interface I_Tests4J_SourceInfoParams {
	/**
	 * this may return a list of java package names
	 * that can indicate what is getting tested by the trial run.
	 * This list is added to the set of @PackageScope packageNames
	 * as well as the packages of @SourceFileScope sourceClasses.
	 * 
	 * @return
	 */
	public List<String> getPackagesTested();
	/**
	 * this allows you to filter out classes that are not testable
	 * (meaning they are Trials/Mocks exc) and should not be included
	 * in the @see {@link I_TrialRunMetadata#getAllSourceInfo()}.
	 * 
	 * @param className
	 * @return
	 */
	public boolean isTestable(String className);
}
