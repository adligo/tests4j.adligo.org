package org.adligo.tests4j.models.shared.coverage;


/**
 * @author scott
 *
 */
public interface I_ClassCoverage {
	public int getLineCount();
	public I_LineCoverage getCoverage(int p);
	
}
