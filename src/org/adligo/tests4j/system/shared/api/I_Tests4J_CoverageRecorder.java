package org.adligo.tests4j.system.shared.api;

import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverageBrief;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverageBrief;

/**
 * a coverage recorder that 
 * can record all of the code coverage, 
 * or the code coverage specific to a thread (ie a trial thread).
 * 
 * @author scott
 *
 */
public interface I_Tests4J_CoverageRecorder {
	/**
	 * this start recording for the context of the current recorder
	 */
	public void startRecording();
	/**
	 * @deprecated removing this method 
	 * this ends recording for the context of the current record.
	 * 
	 * Note the items in the I_PackageCoverage
	 * are should only be packages that actually have classes,
	 * with all child packages rolled in.
	 * @param the names of the classes for 
	 *    which coverage info is requested.
	 */
	public List<I_PackageCoverageBrief> endRecording(Set<String> classNames);
	
	/**
	 * for source file trials return the coverage information;
	 * @return
	 */
	public I_SourceFileCoverageBrief getSourceFileCoverage();
	
	 /**
   * for api trials return the coverage information;
   * @return
   */
  public I_PackageCoverageBrief getPackageCoverage();
  
  /**
   * This will return coverage information for 
   * the entire run including any package with 
   * .class files which had any coverage recorded.  
   * It will include information about all .class/.java file
   * regardless if there was any interaction.
   * In other words .java files/classes which
   * were in packages with any coverage
   * will included classes/.java files 
   * with zero coverage.
   * 
   * @return
   */
  public List<I_PackageCoverageBrief> getAllCoverage();
}
