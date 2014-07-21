package org.adligo.tests4j.models.shared.system;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.models.shared.trials.I_Trial;
import org.adligo.tests4j.models.shared.xml.I_XML_Producer;

/**
 * This class encapsulates parameters
 * to pass into the Tests4J run method.
 * @see Tests4J_Params
 * 
 * @author scott
 *
 */
public interface I_Tests4J_Params extends I_XML_Producer {

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
	 * The factory for the coverage plug-in, used to compute
	 * the java source code covered by this test.
	 * A null value means don't calculate coverage.
	 * 
	 * @return
	 */
	public Class<? extends I_CoveragePluginFactory> getCoveragePluginFactoryClass();

	/**
	 * a recommendation for the number of TrailThreads for
	 * environments that allow multi threading (like JSE, but not GWT).
	 * The actual number of threads is calculated as follows;
	 * 
	 * If this setting is Null;
	 * Use the smallest number of threads from either;
	 *   Runtime.getRuntime().availableProcessors() * 2
	 *   or 
	 *   the number of Trials (@see getTrials())
	 *   
	 * If this setting is NOT null;
	 * Use the smallest number of threads from either;
	 *   this setting 
	 *   or 
	 *   the number of Trials (@see getTrials())
	 * 
	 * So a run of a single trial, will only use one trial thread
	 * for it's processing.
	 * 
	 * @return null for the defaults
	 */
	public Integer getRecommendedTrialThreadCount();
	
	/**
	 * the list of classes to report for 
	 * reporting really is just Tests4J internal logging
	 * @return
	 */
	public List<Class<?>> getLoggingClasses();
	
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
	 * for the plug-able uniform assertion framework.
	 * assertUniform
	 * assertNotUniform
	 * assertUniformThrown
	 * exc
	 */
	public I_EvaluatorLookup getEvaluatorLookup();

}
