package org.adligo.tests4j.system.shared.api;

import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverageBrief;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;

import java.io.IOException;

/**
 * a pluggable interface for a this integrating testing api 
 * code coverage tools (like EclEmma/Jacoco)
 * Also the coverage plugin must implement a zero arg
 * constructor.
 * 
 * @author scott
 *
 */
public interface I_Tests4J_CoveragePlugin {

	/**
	 * This method should be executable by multiple threads, 
	 * so that each thread is instrumenting classes, to a shared classloader.
	 */
	public I_Tests4J_CoverageTrialInstrumentation instrument(Class<? extends I_AbstractTrial> trial) throws IOException;
	
	/**
	 * @param trial
	 * @return percent done 0.0 - 100.0
	 */
	public double getInstrumentProgress(Class<? extends I_AbstractTrial> trial);
	
	/**
	 * give the plugin a chance to clean up the instrumentation
	 * work (caches exc)
	 */
	public void instrumentationComplete();
	/**
	 *
	 * @return
	 *    true if this coverage plugin 
	 *    has the ability to record code coverage local to the thread.
	 */
	public boolean isCanThreadGroupLocalRecord();
	
	/**
	 * Create a new recorder.
	 * This should only be used to create the main recorder.
	 * @return
	 * @diagram Overview.seq sync on 5/1/2014
	 */
	public I_Tests4J_CoverageRecorder createRecorder();
	
	/**
   * Create a new recorder.
   * This should be used to create the thread local
   * recorders.
   * @param trialThreadName the name of the trial thread @see {@link Tests4J_ThreadFactory}
   *  that is used to determine if a probe hit should modify the ThreadLocal probes
   *  that pertain to the current Trial/Test threads.  As child
   *  test and custom threads will have the trialThreaName as the first part
   *  of their names.
   *  
   * @param javaFilter this is either a package name or Class name
   * to filter the results for this recorder for api trials and source file
   * trials respectively.
   * @return
   * 
   */
  public I_Tests4J_CoverageRecorder createRecorder(String trialThreadName, String javaFilter);
  
  /**
   * creates a I_SourceFileCoverage to provide detailed information
   * about what is covered in a source file, either using the 
   * instrumented classes created from the instrument method in this
   * interface, or when instrument is true it should instrument the
   * classes on the fly.
   * 
   * @param sourceFile the source file which we need line and branch
   *    details for.
   * @param instrument if this is true, and other instrument methods have been called
   * i.e. I_Tests4J_CoverageTrialInstrumentation instrument(Class<? extends I_AbstractTrial> trial) throws IOException
   * this method will throw a IllegalStateException, since we don't want 
   * classes to be instrumented during a trial run.
   * 
   * @return a I_SourceFileCoverage which can be used to 
   *   decorate a .java file when it is displayed in a GUI/IDE
   *   to show which lines and branches were covered.
   */
  public I_SourceFileCoverage analyze(I_SourceFileCoverageBrief sourceFile, boolean instrument);
  /**
   * @see #analyze(I_SourceFileCoverageBrief, boolean)
   * this always passes false to instrument
   * @param sourceFile
   * @return
   */
  public I_SourceFileCoverage analyze(I_SourceFileCoverageBrief sourceFile);
}
