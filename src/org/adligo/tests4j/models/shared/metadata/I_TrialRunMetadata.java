package org.adligo.tests4j.models.shared.metadata;

import java.util.List;

/**
 * a class to represent metadata
 * about the call to Tests4J.run.
 * The metadata includes the information
 * about the trials which will run.
 * 	This is used by tests4j to compare 
 * the inital calculation of trials to run
 * with the trials which actually ran, so that
 * the run of trials can discover the 
 * end state of the Tests4J threads.
 * 	This is also used to feed back into 
 * MetadataTrials which can assert things 
 * about the 
 * @author scott
 *
 */
public interface I_TrialRunMetadata {

	/**
	 * all of the trials which 
	 * were available.
	 * 
	 * @return
	 */
	public abstract List<? extends I_TrialMetadata> getAllTrialMetadata();
	/**
	 * all of the trials 
	 * which ran
	 * @return
	 */
	public int getAllTrialsCount();
	/**
	 * all of the tests 
	 * which ran
	 * @return
	 */
	public int getAllTestsCount();
	
	/**
	 * this should return the trials which test 
	 * a specific java package
	 * through a @PackageScope annotation 
	 * or a @SourceFileScope annotation.
	 * 
	 * 
	 * @param testedPackage
	 *   if null or empty return all I_TrialM
	 * @return
	 */
	//public List<? extends I_TrialMetadata> getSourceFileTrials(String testedPackage);
	/**
	 * return all of the classes 
	 * (without interfaces) in the package
	 * with corresponding source files.
	 * 
	 * @param pTestedPackage
	 * @return
	 */
	//public List<Class<?>> getPackageSourceFiles(String pTestedPackage);

	/**
	 * 
	 * @param pTestedPackage
	 * @return the number of 
	 * source file trials in the 
	 * tested exact (non sub) 
	 * package with source file trials.
	 *  
	 */
	//public Integer getSourceFilesWithSourceFileTrials(String pTestedPackage);
	/**
	 * 
	 * @param pTestedPackage
	 *   the package which may have had
	 *   classes which could be tested by a 
	 *   SourceFileTrial.
	 *   
	 * @return the number of SourceFileTrials
	 * in the which test classes in the testedPackage.
	 *    
	 */
	//public Integer getSourceFileTrialCount(String pTestedPackage);
	
	/**
	 * @param pTestedPackage
	 *    the package you want to test for
	 * @return
	 *    the percentage of SourceFileTrials
	 *    to ClassSourceFiles in the pTestedPackage.
	 *    this can be asserted against with a MetadataTrial
	 *    @Test method.
	 */
	//public Double getSourceFileTrialToClassSourceFilePercentage(String pTestedPackage);

}