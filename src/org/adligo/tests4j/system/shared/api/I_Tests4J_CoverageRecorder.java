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
   * This will return coverage information for  <br/>
   * the entire run including any package with <br/>
   * .class files which had any coverage recorded.  <br/>
   * It will include information about all .class/.java file <br/>
   * regardless if there was any interaction. <br/>
   * In other words .java files/classes which <br/>
   * were in packages with any coverage <br/>
   * will included classes/.java files <br/>
   * with zero coverage. <br/>
   * <br/>
   * @param trialPackages, theses are the packages <br/>
   * with trials that ran, which generally don't  <br/>
   * trigger coverage to be returned here, <br/>
   * however there were a few cases when <br/>
   * tests4j trials would have a annotation <br/>
   * to a sourceClass in the trial package in order to <br/>
   * assert things about class references(dependencies).  In these <br/>
   * case although the trial had sourceClass pointed <br/>
   * at something it was actually testing something else <br/>
   * (the reference/dependency code);  <br/>
   * @see tests4j_tests.adligo.org/src_jdk_1_8/*.UseTrial for some examples of this special case. <br/>
   * @return
   */
  public List<I_PackageCoverageBrief> getAllCoverage(Set<String> trialPackages);
}
