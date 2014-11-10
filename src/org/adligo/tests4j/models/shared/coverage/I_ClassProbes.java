package org.adligo.tests4j.models.shared.coverage;



/**
 * The probes for just one class,
 * which could be a inner class.
 * This is was extracted from ExecutionData.
 * 
 * @author scott
 *
 */
public interface I_ClassProbes extends I_ProbesCoverageContainer {
	/**
	 * the name of the class
	 * @return
	 */
	public String getClassName();
	/**
	 * the id of the class used for 
	 * This is mostly for the tests4j_4jacoco
	 * implementation, it made sense to have
	 * one less copy of this interface/class structure.
	 * @return
	 */
	public long getClassId();
	/**
	 * the probes 
	 * @return
	 */
	public I_Probes getProbes();
}
