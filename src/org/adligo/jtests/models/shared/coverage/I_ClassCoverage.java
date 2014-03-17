package org.adligo.jtests.models.shared.coverage;


/**
 * @author scott
 *
 */
public interface I_ClassCoverage {
	public String getClassName();
	public int getLineCount();
	public I_LineCoverage getCoverage(int p);
	
}