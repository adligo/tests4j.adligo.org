package org.adligo.tests4j.models.shared.system;

import java.util.List;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;

/**
 * This class encapsulates parameters
 * to pass into the Tests4J run method.
 * 
 * @author scott
 *
 */
public interface I_Tests4J_Params {
	/**
	 * The trials/tests to run.
	 * 
	 * @return
	 */
	public List<Class<? extends I_AbstractTrial>> getTrials();
	/**
	 * The reporter originally used to print messages to the console,
	 * since Test4J usually modifies System.out and System.err
	 * so that it can be captured as part of the TrialResult.
	 * 
	 * @return
	 */
	public I_Tests4J_Reporter getReporter();
	/**
	 * The coverage plug-in, used to compute
	 * the java source code covered by this test.
	 * A null value means don't calculate coverage.
	 * 
	 * @return
	 */
	public I_CoveragePlugin getCoveragePlugin();
	/**
	 * The number of threads which to process 
	 * Trials concurrently, the thread pool size
	 * defaults to 32, but is automatically
	 * reduced to the number of trials if it is smaller.
	 * 
	 * @return
	 */
	public int getThreadPoolSize();
	/**
	 * This determines if System.exit(0);
	 *  should be called after the last notification.
	 *  
	 * @return
	 */
	public boolean isExitAfterLastNotification();
	
	/**
	 * This is the minimum number of passing trials
	 * required for a passing run result.
	 * A null value means don't fail the run.
	 * @return
	 */
	public Integer getMinTrials();
	/**
	 * This is the minimum number of passing tests
	 * required for a passing run result.
	 * A null value means don't fail the run.
	 * @return
	 */
	public Integer getMinTests();
	/**
	 * This is the minimum number of passing asserts
	 * required for a passing run result.
	 * A null value means don't fail the run.
	 * @return
	 */
	public Integer getMinAsserts() ;
	/**
	 * This is the minimum number of unique passing asserts
	 * required for a passing run result.
	 * A null value means don't fail the run.
	 * @return
	 */
	public Integer getMinUniqueAssertions();
}
