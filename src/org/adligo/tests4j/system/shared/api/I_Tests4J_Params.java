package org.adligo.tests4j.system.shared.api;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adligo.tests4j.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.shared.xml.I_XML_Consumer;
import org.adligo.tests4j.shared.xml.I_XML_Producer;
import org.adligo.tests4j.system.shared.trials.I_MetaTrial;
import org.adligo.tests4j.system.shared.trials.I_MetaTrialParams;
import org.adligo.tests4j.system.shared.trials.I_Trial;
import org.adligo.tests4j.system.shared.trials.I_TrialParamsFactory;

/**
 * This class encapsulates parameters
 * to pass into the Tests4J run method.
 * @see Tests4J_Params
 * 
 * @author scott
 *
 */
public interface I_Tests4J_Params {
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
	 * This is a list of partial package names 
	 * that should not be instrumented during the trial run.
	 * i.e. "com.google.gwt." if you use gwt but
	 * don't want to instrument the gwt classes for coverage.
	 * @return
	 */
	public List<String> getAdditionalNonInstrumentedPackages();
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
	 * 
	 *  
	 * @return
	 */
	public I_Tests4J_ProgressParams getProgressMonitor();
	
	/**
	 * this can provide additional information about 
	 * what is getting tested.  Other information 
	 * come from the @SourceFileScope and @PackageScope annotations.
	 * @return
	 */
	public I_Tests4J_SourceInfoParams getSourceInfoParams();
	
	/**
	 * The packages which could be instrumented,
	 * but are not wanted as part of the final 
	 * package coverage result.
	 * @return
	 */
	public List<String> getAdditionalNonResultPackages();
}
