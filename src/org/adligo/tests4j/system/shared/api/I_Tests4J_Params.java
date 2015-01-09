package org.adligo.tests4j.system.shared.api;

import org.adligo.tests4j.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.system.shared.trials.I_MetaTrial;
import org.adligo.tests4j.system.shared.trials.I_MetaTrialParams;
import org.adligo.tests4j.system.shared.trials.I_Trial;
import org.adligo.tests4j.system.shared.trials.I_TrialParamsFactory;
import org.adligo.tests4j.system.shared.trials.PackageScope;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This interface encapsulates parameters <br/>
 * to pass into the Tests4J run method. <br/>
 * <br/>
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
	 * @return The trials to run, note trials may 
	 * be run multiple times during a Tests4J trial run
	 * for performance (load) testing.
	 */
	public List<Class<? extends I_Trial>> getTrials();
	
	/**
	 * This method may return null or a <br/>
	 * class that implements I_MetaTrial <br/>
	 * which is run after all of the regular trials, <br/>
	 * since it can assert things about the trial run. <br/>
	 */
	public Class<? extends I_MetaTrial> getMetaTrialClass();
	
	/**
	 * 
	 * @return the parameters for the meta trial, if it <br/>
   * implements I_TrialParamsAware, which <br/>
   * is not necessary.<br/>
	 */
	public I_MetaTrialParams<?> getMetaTrialParams();
	
	/**
	 * The actual instance of your trial parameters queue<br/>
	 * so your trials have parameters, which could be <br/>
	 * shared between trials. <br/>
	 * i.e.; <br/>
	 * You are running use case trials and want to share a environment <br/>
	 * with a local cluster of Jetty servers, openDSs and Oracle DBs.<br/>
	 * 
	 * @return
	 */
	public I_TrialParamsFactory getTrialParamsQueue();
	
	/**
	 * @return
	 * The specific tests to run, if this set is empty <br/>
   * Tests4J will run all of the tests in all of the trials. <br/>
   * The Strings should conform to the full package name <br/>
   * of the trail dot trial name dot method name i.e.;<br/>
   * org.adligo.tests4j.models.shared.system.I_Tests4J_Params.getTests<br/>
	 */
	public Set<I_Tests4J_Selection> getTests();
	
	/**
	 * @return
	 * The factory for the coverage plug-in, used to compute<br/>
   * the java source code covered by this test.<br/>
   * A null value means do NOT calculate coverage.<br/>
	 */
	public Class<? extends I_Tests4J_CoveragePluginFactory> getCoveragePluginFactoryClass();
	
	/**
	 * @return parameters for the coverage plug-in.<br/>
	 */
	public I_Tests4J_CoveragePluginParams getCoverageParams();

	/**
	 * 
	 * @return
	 * This is a list of partial package names <br/>
   * that should not be instrumented during the trial run. <br/>
   * i.e. "com.google.gwt." if you use GWT but <br/>
   * do NOT want to instrument the GWT classes for coverage. <br/>
	 */
	public List<String> getAdditionalNonInstrumentedPackages();
	 /**
	  * @return
   * This returns a list of classes which are loaded <br/>
   * by the main (non instrumented) class loader, <br/>
   * so that the classes in the main and instrumented class loader  <br/>
   * are the same.  This is useful for cases <br/>
   * when tests call 3rd party code that may use <br/>
   * the system class loader by default (i.e. jaxb); <br/>
   * java.lang.ClassCastException: org.adligo.fabricate.xml.io_v1.dev_v1_0.FabricateDevType <br/>
   *   cannot be cast to org.adligo.fabricate.xml.io_v1.dev_v1_0.FabricateDevType <br/>
   * 
   */
  public List<String> getAdditionalNonInstrumentedClasses();
  
	/**
	 * 
	 * 
	 * @return This method returns null (for the defaults), 
	 * or a integer which is a recommendation for the number of TrailThreads for 
   * environments that allow concurrent execution (like JSE, but not GWT). <
   * The actual number of threads is calculated as follows;<br/>
   * <br/>
   * If this setting is Null;<br/>
   * Use the smallest number of threads from either;<br/>
   *   Runtime.getRuntime().availableProcessors() * 2<br/>
   *   or <br/>
   *   the number of Trials ({@link I_Tests4J_Params#getTrials()})<br/>
   *   <br/>
   * If this setting is NOT null;<br/>
   * Use the smallest number of threads from either;<br/>
   *   this setting <br/>
   *   or <br/>
   *   the number of Trials ({@link I_Tests4J_Params#getTrials()})<br/>
   * <br/>
   * When running a a single trial, Tests4J will only use one trial thread.
   * If the setting is 1 then the runnable is run on the main thread 
   * by simply calling it's run method.
	 */
	public Integer getRecommendedTrialThreadCount();
	/**
	 * @return This method returns null (for the defaults), 
	 * or the recommended number of threads to use during setup. 
	 * This defaults the same mechanism as getRecommendedTrialThreadCount.<br/>
   * <br/>
   * It may be dialed back using this setting.<br/>
   * <br/>
   * If the setting is 1 then the runnable is run on the main thread<br/>
   * by simply calling it's run method.<br/>
	 */
	public Integer getRecommendedSetupThreadCount();
	
	/**
	 * 
	 * @return This method returns the list of classes which should log messages <br/>
   * during the Tests4J trial runs. <br/>
	 */
	public Map<Class<?>, Boolean> getLogStates();
	
	/**
	 * 
	 * @return This method returns a collection of remote tests4j servers which may be called from tests4j,
	 * to delegate running of trials. Note each remote install delegate creates a new thread locally.
	 * 
	 */
	public Collection<I_Tests4J_RemoteInfo> getRemoteInfo();
	
	/**
	 * 
	 * @param remoteInfo
	 *   The information required to find the remote process (on a local or remote machine).
	 * @return 
	 *   This method returns the parameters to send to the remote tests4j server.
	 */
	public I_Tests4J_Params getRemoteParams(I_Tests4J_RemoteInfo remoteInfo);
	
	/**
	 * 
	 * @return This method returns a evaluator lookup 
	 * for the plug-able uniform assertion framework.<br/>
	 * {@link I_Adderts#assertUniform}<br/>
	 * {@link I_Adderts#assertNotUniform}<br/>
	 * {@link I_Adderts#assertUniformThrown}<br/>
	 * <br/>
	 * Note the returned class must be available to the JVM class loader of the tests4j instance.
	 */
	public Class<? extends I_EvaluatorLookup> getEvaluatorLookup();

	/**
	 * 
	 * 
	 * @return
	 * This method returns additional report streams are useful when you want to duplicate
   * the results you see from the console log, to a file
   * or some other location.  This is be useful in the 
   * remote tests4j runners as well, to transmit the log over 
   * the network (i.e. eclipse4jtests4j eclipse plug-in).
	 */
	public List<OutputStream> getAdditionalReportOutputStreams();
	
	/**
	 * @return This method returns information about how often the progress of a Tests4J trial run
	 * should be broadcast.
	 */
	public I_Tests4J_ProgressParams getProgressParams();
	
	/**
	 * 
	 * @return
	 * This method returns  additional information about what is getting tested.  Other information 
   * come from the @SourceFileScope and @PackageScope annotations.  
   * @see SourceFileScope
   * @see PackageScope
	 */
	public I_Tests4J_SourceInfoParams getSourceInfoParams();
	
	/**
	 * 
	 * @return
	 * This method returns the packages which will be instrumented, but are not wanted as part of the final 
   * package coverage result.
	 */
	public List<String> getAdditionalNonResultPackages();

}
