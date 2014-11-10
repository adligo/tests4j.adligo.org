package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.association.I_ClassAssociations;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverageBrief;
import org.adligo.tests4j.shared.asserts.reference.I_ClassAttributes;

public interface I_SourceFileTrialResult extends I_TrialResult {
	public I_SourceFileCoverageBrief getSourceFileProbes();
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
	public I_ClassAssociations getDependencies();

	/**
	 * The references from the dependencies
	 * to the other classes.
	 * @param className
	 * @return
	 */
	public I_ClassAttributes getReferences(String className);
	/**
	 * The relations/attributes for the sourceClass from the dependencies.
	 * mapped.
	 * @param className
	 * @return
	 */
	public I_ClassAttributes getSourceClassAttributes();
}
