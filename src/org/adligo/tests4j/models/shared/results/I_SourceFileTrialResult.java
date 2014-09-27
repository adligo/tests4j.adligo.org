package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.dependency.I_ClassDependencies;
import org.adligo.tests4j.shared.asserts.dependency.I_ClassAttributes;

public interface I_SourceFileTrialResult extends I_TrialResult {
	public I_SourceFileCoverage getSourceFileCoverage();
	/**
	 * note this is the java class or interface name that matches with 
	 * a .java file.
	 * @return
	 */
	public abstract String getSourceFileName();

	/**
	 * all of the classes depended on by 
	 * the sourceClass.
	 * @return
	 */
	public I_ClassDependencies getDependencies();

	/**
	 * The relations/attributes from the dependency,
	 * mapped.
	 * @param className
	 * @return
	 */
	public I_ClassAttributes getAttributes(String className);
	/**
	 * The relations/attributes for the sourceClass from the dependencies.
	 * mapped.
	 * @param className
	 * @return
	 */
	public I_ClassAttributes getSourceClassAttributes();
}
