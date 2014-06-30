package org.adligo.tests4j.models.shared.system;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.I_MetaTrial;
import org.adligo.tests4j.models.shared.I_Trial;
import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;

/**
 * This class encapsulates parameters
 * to pass into the Tests4J run method.
 * @see Tests4J_Params
 * 
 * @author scott
 *
 */
public interface I_Tests4J_Params extends I_XML_IO {

	/**
	 * The trials to run.
	 * 
	 * @return
	 */
	public List<Class<? extends I_Trial>> getTrials();
	
	/**
	 * may return null or a 
	 * class that implements I_MetaTrial
	 * which is run after all of the regular trials,
	 * since it can assert things about the trial run.
	 */
	public Class<? extends I_MetaTrial> getMetaTrialClass();
	
	/**
	 * The specific tests to run, if this set is empty
	 * Tests4J will run all of the tests in all of the trials.
	 * The Strings should conform to the full package name
	 * of the trail dot trial name dot method name ie;
	 * org.adligo.tests4j.models.shared.system.I_Tests4J_Params.getTests
	 * 
	 * @return
	 */
	public Set<String> getTests();
	
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
	public Class<? extends I_CoveragePlugin> getCoveragePluginClass();
	/**
	 * The number of threads which to process 
	 * Trials concurrently, the thread pool size
	 * defaults to 32, 
	 * 
	 * @return
	 */
	public I_ThreadCount getThreadCount();
	/**
	 * the number of theads from the trial count
	 * but is automatically
	 * reduced to the number of trials if it is smaller.
	 * @return
	 */
	public int getTrialThreadCount();
	/**
	 * This determines if System.exit(0);
	 *  should be called after the last notification.
	 *  
	 * @return
	 */
	public boolean isExitAfterLastNotification();
	
	/**
	 * the list of classes to report for 
	 * reporting really is just Tests4J internal logging
	 * @return
	 */
	public List<Class<?>> getLoggingClasses();
	
	/**
	 * return the delegate for System.exit(int status)
	 * mostly for stubbing
	 * @return
	 */
	public I_SystemExit getExitor();
	
	/**
	 * 
	 * @return a collection of 
	 * remote tests4j servers which may be called
	 * from tests4j, to delegate running of trials.
	 * Note each remote install delegate creates a new
	 * thread locally.
	 */
	public Collection<I_Tests4J_RemoteInfo> getRemoteInfo();
	
	/**
	 * the params to send to the remote tests4j server.
	 * @param p
	 * @return
	 */
	public I_Tests4J_Params getRemoteParams(I_Tests4J_RemoteInfo p);
	
	/**
	 * 
	 * @return a evaluator lookup 
	 * for plug-able assertion framework.
	 */
	public I_EvaluatorLookup getEvaluatorLookup();
}
