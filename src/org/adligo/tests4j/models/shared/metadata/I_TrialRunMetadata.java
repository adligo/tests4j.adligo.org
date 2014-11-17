package org.adligo.tests4j.models.shared.metadata;

import java.util.Collection;
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
	public long getAllTestsCount();
	
	/**
	 * returns the source file for .java files name which
	 * are the target of some trial 
	 * (see SoruceFileTrial,ApiTrial annotations)
	 * i.e.  "org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata"
	 * @param name
	 * @return
	 */
	public I_SourceInfoMetadata getSourceInfo(String name);
	
	/**
	 * All of the names of all of the .java files that 
	 * @return
	 */
	public Collection<String> getAllSourceInfo();
	
}