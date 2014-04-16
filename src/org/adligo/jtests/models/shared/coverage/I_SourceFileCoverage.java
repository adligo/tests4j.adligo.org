package org.adligo.jtests.models.shared.coverage;

public interface I_SourceFileCoverage extends I_HasInnerClasses  {
	/**
	 * the main class name which matches up with the file name
	 * @return
	 */
	public String getClassName();
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public I_ClassCoverage getCoverage();
}
