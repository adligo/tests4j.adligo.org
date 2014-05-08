package org.adligo.tests4j.models.shared.coverage;

/**
 * represents coverage for a single java source file.
 * 
 * @author scott
 *
 */
public interface I_SourceFileCoverage extends I_CoverageUnitsContainer {
	/**
	 * the main class name which matches up with the file name
	 * @return
	 */
	public String getClassName();
	
	/**
	 * assuming first line is always 0, this allows you
	 * to iterate through the lines.
	 * @return
	 */
	public int getLastLine();
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public I_LineCoverage getLineCoverage(int p);
}
