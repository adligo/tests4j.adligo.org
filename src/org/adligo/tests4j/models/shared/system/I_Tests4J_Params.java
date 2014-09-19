package org.adligo.tests4j.models.shared.system;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.models.shared.trials.I_MetaTrialParams;
import org.adligo.tests4j.models.shared.trials.I_Trial;
import org.adligo.tests4j.models.shared.trials.I_TrialParamsFactory;
import org.adligo.tests4j.models.shared.xml.I_XML_Consumer;
import org.adligo.tests4j.models.shared.xml.I_XML_Producer;

/**
 * This class encapsulates parameters
 * to pass into the Tests4J run method.
 * @see Tests4J_Params
 * 
 * @author scott
 *
 */
public interface I_Tests4J_Params extends I_XML_Producer, I_XML_Consumer {
	public static final String TAG_NAME = "tests4j_Params";
	public static final String COVERAGE_PLUGIN_FACTORY_ATTRIBUTE= "coveragePluginFactory";
	public static final String EVALUATOR_LOOKUP_ATTRIBUTE = "evaluatorLookup";
	public static final String META_TRIAL_ATTRIBUTE = "metaTrial";
	public static final String RECOMENDED_REMOTE_THREADS_ATTRIBUTE = "recomendedRemoteThreads";
	public static final String RECOMENDED_SETUP_THREADS_ATTRIBUTE = "recomendedSetupThreads";
	public static final String RECOMENDED_TRIAL_THREADS_ATTRIBUTE = "recomendedThreadThreads";
	
	public static final String TRIAL_TAG_NAME = "trial";
	public static final String TRIALS_TAG_NAME = "trials";
	
	public static final String TEST_TAG_NAME = "test";
	public static final String TESTS_TAG_NAME = "tests";
	
	public static final String LOG_CLASSESS_TAG_NAME = "logClasses";
	public static final String CLASS_NAME_TAG_NAME = "className";
	
	public static final String REMOTE_RUNS = "remoteRuns";
	public static final String REMOTE_RUN = "remoteRun";
	
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
	 * the params for the meta trial, if it
	 * implements I_TrialParamsAware, which 
	 * is not necessary.
	 * @return
	 */
	public I_MetaTrialParams<?> getMetaTrialParams();
	
	/**
	 * The actual instance of your trial params queue
	 * so your trials have parameters, which could be
	 * shared between trials.
	 * I.E. you are running use case trials and have set up,
	 * a local cluster of jetties, openDSs, oracles
	 * vms exc, and sharing seems like a good idea 
	 * give this api a shot.
	 * 
	 * @return
	 */
	public I_TrialParamsFactory getTrialParamsQueue();
	/**
	 * The specific tests to run, if this set is empty
	 * Tests4J will run all of the tests in all of the trials.
	 * The Strings should conform to the full package name
	 * of the trail dot trial name dot method name ie;
	 * org.adligo.tests4j.models.shared.system.I_Tests4J_Params.getTests
	 * 
	 * @return
	 */
	public Set<I_Tests4J_Selection> getTests();
	
	/**
	 * The factory for the coverage plug-in, used to compute
	 * the java source code covered by this test.
	 * A null value means don't calculate coverage.
	 * 
	 * @return
	 */
	public Class<? extends I_Tests4J_CoveragePluginFactory> getCoveragePluginFactoryClass();
	
	/**
	 * parameters for the coverge plug-in.
	 * @return
	 */
	public I_Tests4J_CoveragePluginParams getCoverageParams();

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
	 * If the setting is 1 then the runnable is run on the main thread
	 * by simply calling it's run method.
	 * 
	 * @return null for the defaults
	 */
	public Integer getRecommendedTrialThreadCount();
	/**
	 * This defaults to the number of remote tests4j instances
	 * requested to be run by the getRemoteInfo() method.
	 * 
	 * It may be dialed back using this setting.
	 * 
	 * If the setting is 1 then the runnable is run on the main thread
	 * by simply calling it's run method.
	 * 
	 * @return null for the defaults
	 */
	public Integer getRecommendedRemoteThreadCount();
	/**
	 * This defaults the same mechanism as getRecommendedTrialThreadCount.
	 * 
	 * It may be dialed back using this setting.
	 * 
	 * If the setting is 1 then the runnable is run on the main thread
	 * by simply calling it's run method.
	 * 
	 * @return null for the defaults
	 */
	public Integer getRecommendedSetupThreadCount();
	
	/**
	 * the list of classes to report for 
	 * reporting really is just Tests4J internal logging
	 * @return
	 */
	public Map<Class<?>, Boolean> getLogStates();
	
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
	 * 
	 * must be available to the jvm of the tests4j instance
	 * exc
	 */
	public Class<? extends I_EvaluatorLookup> getEvaluatorLookup();

	/**
	 * additional report streams are useful when you want to duplicate
	 * the results you see from the console log, to a file
	 * or some other location.  This could be useful in the 
	 * remote tests4j runners as well, to broadcast the log over 
	 * the network, for a fancy gui someday.
	 * 
	 * @return
	 */
	public List<OutputStream> getAdditionalReportOutputStreams();
	
	public void setAdditionalReportOutputStreams(Collection<OutputStream> out);
	
	public void addAdditionalReportOutputStreams(OutputStream out);
	
	/**
	 * The time between progress updates in milliseconds
	 * defaults to one second (or 1000 milliseconds).
	 * Note I have noticed on my machine that the
	 * Thread.currentThread().sleep seems to start out slow (taking longer
	 * than the value), and then becomes fast (taking less time that the setting)
	 * for this reason, the first 5 usages of this setting are cut by 25%.
	 *  
	 * @return
	 */
	public I_Tests4J_ProgressMonitor getProgressMonitor();
	
}
