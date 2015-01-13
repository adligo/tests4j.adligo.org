package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.results.I_PhaseState;
import org.adligo.tests4j.run.api.Tests4J_UncaughtExceptionHandler;
import org.adligo.tests4j.run.common.I_JseSystem;
import org.adligo.tests4j.run.common.I_ThreadManager;
import org.adligo.tests4j.run.discovery.I_PackageDiscovery;
import org.adligo.tests4j.run.discovery.PackageDiscovery;
import org.adligo.tests4j.run.discovery.Tests4J_ParamsReader;
import org.adligo.tests4j.run.memory.Tests4J_Memory;
import org.adligo.tests4j.run.output.ConcurrentOutputDelegateor;
import org.adligo.tests4j.run.output.JsePrintOutputStream;
import org.adligo.tests4j.shared.asserts.reference.AllowedReferences;
import org.adligo.tests4j.shared.asserts.reference.I_ReferenceGroup;
import org.adligo.tests4j.shared.common.JavaAPIVersion;
import org.adligo.tests4j.shared.common.LegacyAPI_Issues;
import org.adligo.tests4j.shared.common.SystemWithPrintStreamDelegate;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.output.DefaultLog;
import org.adligo.tests4j.shared.output.DelegatingLog;
import org.adligo.tests4j.shared.output.I_ConcurrentOutputDelegator;
import org.adligo.tests4j.shared.output.I_OutputBuffer;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.shared.output.ListDelegateOutputBuffer;
import org.adligo.tests4j.shared.output.PrintStreamOutputBuffer;
import org.adligo.tests4j.shared.output.SafeOutputStreamBuffer;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Controls;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Delegate;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Listener;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Params;
import org.adligo.tests4j.system.shared.report.summary.SummaryReporter;
import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

/**
 * ok this is the main processing class which does this;
 * 1) put all the test classes in memory
 * 2) start the thread pool
 * 
 * @diagram_sync on 1/8/2015 with Overview.seq
 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
 * 
 * @author scott
 *
 */
public class Tests4J_Processor implements I_Tests4J_Delegate, Runnable {
	public static final String REQUIRES_SETUP_IS_CALLED_BEFORE_RUN = " requires setup is called before run.";
	private Tests4J_Memory memory_;
	private I_Tests4J_Log log_;
	private I_Tests4J_Constants constants_;
	
	private final I_JseSystem system_;
	private Tests4J_ParamsReader reader_;
	private I_ThreadManager threadManager_;
	private I_Tests4J_NotificationManager notifier_;
	
	private Tests4J_Controls controls_;
	private ConcurrentOutputDelegateor cod_;
	
	/**
	 * @diagram_sync on 1/8/2014 with Overview.seq
	 * 
	 * @param systemIn
	 */
	public Tests4J_Processor(I_JseSystem system) {
		system_ = system;
	}
	
	/**
	 * This method sets up everything for the run,
	 * assumes all other setters have been called.
	 * 
	 * @diagram_sync on 1/8/2015 with Overview.seq
	 * @param listener
	 * @param params
	 * 
	 */
	public boolean setup(I_Tests4J_Listener listener, I_Tests4J_Params params) {
		
	  constants_ = params.getConstants();
		//@diagram_sync on 1/8/2015 with Overview.seq
		reader_ = new Tests4J_ParamsReader(system_,  params);
		/**
		 * note this code is a bit confusing,
		 * the log is single threaded for a short time,
		 * and then is 
		 */
		log_ = reader_.getLogger();
		displayJavaVerionErrors();
		/**
		 * now the log is concurrent
		 */
		Map<Class<?>, Boolean> logStates = reader_.getLogStates();
		cod_ = setupLogging(logStates);
		//pass the concurrent log back to the reader
		//to set up the CoveragePluginFactory
		reader_.read(log_);
		//@diagram_sync on 1/8/2014 with Overview.seq
		memory_ = new Tests4J_Memory(log_);
		
		memory_.setSystem(system_);
		
		List<OutputStream> outs =  params.getAdditionalReportOutputStreams();
		if (outs.size() >= 1) {
			List<I_OutputBuffer> outputBuffers = new ArrayList<I_OutputBuffer>();
			for (OutputStream ob: outs) {
				outputBuffers.add(new SafeOutputStreamBuffer(log_, ob));
			}
			outputBuffers.add(new PrintStreamOutputBuffer(system_.getOut()));
			PrintStream out = new  JsePrintOutputStream(new ListDelegateOutputBuffer(outputBuffers));
			
			SystemWithPrintStreamDelegate sysPs = new SystemWithPrintStreamDelegate(system_, out);
			log_ = new DefaultLog(sysPs, constants_, params.getLogStates());
		}
		
		//use the dynamic log
		memory_.setReporter(new SummaryReporter(constants_, log_));
		
		memory_.setListener(listener);
		
		//@diagram_sync on 1/8/2015 with Overview.seq
		if (reader_.isRunnable()) {
		  //@diagram_sync on 1/8/2015 with Overview.seq
			memory_.initialize(reader_);
			threadManager_ = memory_.getThreadManager();
		}
		memory_.getListener().onStartingSetup(params);
		controls_ = new Tests4J_Controls(memory_);
		return reader_.isRunnable();
	}	
	

	private void displayJavaVerionErrors() {
		LegacyAPI_Issues issues = new LegacyAPI_Issues();
		issues.addIssues(CachedClassBytesClassLoader.ISSUES);
		issues.addIssues(GWT_Classes.ISSUES);
		String version = system_.getJseVersion();
		JavaAPIVersion v = new JavaAPIVersion(version);
		if (issues.hasIssues()) {
			List<Throwable> thrown = issues.getIssues(v);
			if (thrown != null) {
				log_.onThrowable(new IllegalStateException("tests4j detects java " + version +
						" which has " + thrown.size() + " potential issues."));
			}
		}
	}
	/**
	 * This method kicks off the TheadPool (ExecutorService)
	 * creating as many TrialInstancesProcessors
	 * as there are params.getThreadPoolSize().
	 *  
	 * @param params
	 * @param pListener
	 * 
	 * @diagram_sync on on 7/21/2014 with Overview.seq
	 *   calls this run() through the ExecutorService
	 * 
	 */
	public void runOnAnotherThreadIfAble() {
		ExecutorService service = threadManager_.getTests4jService();
		service.submit(this);
	}
	
	/**
	 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
	 */
	public void run() {
	  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
	  memory_.initalizeExecutors();
		Thread.setDefaultUncaughtExceptionHandler(new Tests4J_UncaughtExceptionHandler());
		memory_.setSystem(system_);
		long time = system_.getTime();
		memory_.setStartTime(time);
		
	  //@diagram_sync on 1/8/2015 with Overview.seq
		notifier_ = memory_.getNotifier();
		
		
		Tests4J_PhaseOverseer setupProcessInfo = memory_.getSetupPhaseOverseer();
		Tests4J_PhaseOverseer trialProcessInfo = memory_.getTrialPhaseOverseer();
		Tests4J_PhaseOverseer remoteProcessInfo = memory_.getRemotePhaseOverseer();
		
		
		try {
		  //@diagram_sync on 1/8/2015 with Overview.seq
		  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
			startRecordingAllTrialsRun();
			instrumentAllowedReferences();
		  //@diagram_sync on 1/8/2015 with Overview.seq
		  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
			submitSetupRunnables(setupProcessInfo);
			notifier_.onSetupDone();
			
		  //@diagram_sync on 1/8/2015 with Overview.seq
			submitRemoteRunnables(remoteProcessInfo);
		  //@diagram_sync on 1/8/2015 with Overview.seq
		  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
			submitTrialsRunnables(trialProcessInfo);
		  //@diagram_sync on 1/8/2015 with Overview.seq
			if (isTrialsOrRemotesRunning()) {
			  //@diagram_sync on 1/8/2015 with Overview.seq
			  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
				monitorTrials();
			  //@diagram_sync on 1/8/2015 with Overview.seq
				monitorRemotes();
			}
		  //@diagram_sync on 1/8/2015 with Overview.seq
		  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
			notifier_.onDoneRunningNonMetaTrials();
			
			printLogs();
		} catch (Throwable t) {
			log_.onThrowable(t);
			t.printStackTrace();
			printLogs();
		}
		
		controls_.notifyFinished();
		
		//@diagram_sync on 1/8/2015 with Coverage_Overview.seq
	  //this usually calls system.exit in main runs
		threadManager_.shutdown();
		try {
			Thread.currentThread().join();
		} catch (InterruptedException x) {
			log_.onThrowable(x);
		}
	}

	/**
	 * @param setupProcessInfo
	 * @param trialProcessInfo
	 * @param remoteProcessInfo
	 * @return null if single threaded
	 */
	private ConcurrentOutputDelegateor setupLogging(Map<Class<?>, Boolean> logStates) {
		ConcurrentOutputDelegateor cod = new ConcurrentOutputDelegateor();
		JsePrintOutputStream jpos = new JsePrintOutputStream(cod);
		System.setOut(jpos);
		System.setErr(jpos);
		log_ = new DelegatingLog(system_, constants_, logStates, cod);
		return cod;
	}
	
	/**
	 * @diagram_sync with Overview.seq on 1/8/2015
	 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
	 * @param plugin
	 */
	private void startRecordingAllTrialsRun() {
		I_Tests4J_CoveragePlugin coveragePlugin = memory_.getCoveragePlugin();
		if (coveragePlugin != null) {
		  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
			I_Tests4J_CoverageRecorder allCoverageRecorder = coveragePlugin.createRecorder();
		  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
			allCoverageRecorder.startRecording();
			//@diagram_sync on 1/8/2015 with Coverage_Overview.seq
			memory_.setMainRecorder(allCoverageRecorder);
		}
		
	}
	
	/**
	 * 
	 * @diagram_sync with Overview.seq on 7/24/2014
	 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
	 */
	private void submitSetupRunnables(Tests4J_PhaseOverseer info) {
	  I_PhaseState initial = info.getIntialPhaseState();
		notifier_.onProcessStateChange(info.getIntialPhaseState());
		int setupThreadCount = initial.getThreadCount();
		
		if (log_.isLogEnabled(Tests4J_Processor.class)) {
			log_.log(this.toString() + " Starting setup with " + setupThreadCount + " setup threads.");
		}
		ExecutorService runService = threadManager_.getSetupService();
		
		if (setupThreadCount > 1) {
			for (int i = 0; i < setupThreadCount; i++) {
			  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
				runSetupRunnable(info, runService);
			}
		} else {
		  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
			runSetupRunnable(info, runService);
		}
		//@diagram_sync with Overview.seq on 8/20/2014
		//@diagram_sync on 1/8/2015 with Coverage_Overview.seq
		monitorSetup();
		instrumentExtraClasses();
	}
	
	/**
	 * @diagram_sync with Overview.seq on 1/8/2015
	 * @param info
	 */
	private void submitRemoteRunnables(Tests4J_PhaseOverseer info) {
	  
	}
	/**
	 * In order to get a full picture
	 * of all code that could be covered,
	 * add all classes in packages referenced by 
	 * PackageScope or sourceClass.
	 */
  private void instrumentExtraClasses() {
		I_Tests4J_CoveragePlugin coverPlugin = memory_.getCoveragePlugin();
		if (coverPlugin != null) {
		  Set<String> tops = coverPlugin.getTopPackageScopes();
		  for (String top: tops) {
		    try {
          I_PackageDiscovery pd = new PackageDiscovery(top);
          instrumentNonReferencedClassesInReferencedPackages(coverPlugin, pd);
        } catch (IOException | ClassNotFoundException e) {
          throw new RuntimeException(e);
        }
		  }
		}
  }
  private void instrumentNonReferencedClassesInReferencedPackages(
      I_Tests4J_CoveragePlugin coverPlugin, I_PackageDiscovery pd) throws IOException,
      ClassNotFoundException {
    List<String> names = pd.getClassNames();
    for (String name: names) {
      if (!coverPlugin.isInstrumented(name)) {
        coverPlugin.instrument(Class.forName(name));
      }
    }
    List<I_PackageDiscovery> subs = pd.getSubPackages();
    for (I_PackageDiscovery sub: subs) {
      instrumentNonReferencedClassesInReferencedPackages(coverPlugin, sub);
    }
  }
  
  /**
   * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
   * @param info
   * @param runService
   */
	protected void runSetupRunnable(Tests4J_PhaseOverseer info,
			ExecutorService runService) {
	  // @diagram_sync on 1/8/2015 with Coverage_Overview.seq
		Tests4J_SetupRunnable sr = new Tests4J_SetupRunnable(memory_, notifier_); 
		info.addRunnable(sr);
		
		try {
			Future<?> future = runService.submit(sr);
			threadManager_.addSetupFuture(future);
		} catch (RejectedExecutionException x) {
			//do nothing, not sure why this exception is happening for me
			// it must have to do with shutdown, but it happens intermittently.
		}
	}
	
	/**
	 * @diagram_sync on 1/8/2015 with Overview.seq
	 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
	 */
	@SuppressWarnings("boxing")
  private void monitorSetup() {
		Tests4J_PhaseOverseer setupPhaseOverseer = memory_.getSetupPhaseOverseer();
		
		I_PhaseState state = null;
		try {
		  state = setupPhaseOverseer.pollPhaseState();
  		while (!setupPhaseOverseer.isCountDoneCountMatch()) {
  		  notifier_.onProgress(state);
  		  printLogs();
  		  state = setupPhaseOverseer.pollPhaseState();
  		}
		} catch (InterruptedException x) {
		  throw new RuntimeException(x);
		}
		//@diagram_sync on 1/8/2015 with Coverage_Overview.seq
		notifier_.onProgress(state);

		long now = memory_.getTime();
		memory_.setSetupEndTime(now);
		if (log_.isLogEnabled(Tests4J_Processor.class)) {
			double millis = now - memory_.getStartTime();
			double secs = millis/1000.0;
			log_.log(this.toString() + " Completed setup with after " + secs + " seconds.");
		}
		printLogs();
	}
	/**
	 * @diagram_sync on 1/8/2015 with Overview.seq
	 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
	 * @param info
	 */
	private void submitTrialsRunnables(Tests4J_PhaseOverseer info) {
		I_PhaseState initial = info.getIntialPhaseState();
		notifier_.onProcessStateChange(initial);
		int trialThreadCount = initial.getThreadCount();
		
		if (log_.isLogEnabled(Tests4J_Processor.class)) {
			log_.log("Starting submitTrialRunnables with " + trialThreadCount + " trial threads.");
		}
		ExecutorService runService = threadManager_.getTrialRunService();
		
		if (trialThreadCount > 1) {
			for (int i = 0; i < trialThreadCount; i++) {
			  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
				runTrialRunnable(info, cod_, runService);
			}
		} else {
		  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
			runTrialRunnable(info, cod_, runService);
		}
	}
	
	/**
	 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
	 * @param info
	 * @param od
	 * @param runService
	 */
	protected void runTrialRunnable(Tests4J_PhaseOverseer info,
			I_ConcurrentOutputDelegator od, ExecutorService runService) {
	  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq 
		Tests4J_TrialsRunnable tip = new Tests4J_TrialsRunnable(memory_, notifier_); 
		info.addRunnable(tip);
		tip.setOutputDelegator(od);
		try {
		  //@diagram_sync on 1/8/2015 with Coverage_Overview.seq run()
			Future<?> future = runService.submit(tip);
			threadManager_.addTrialFuture(future);
		} catch (RejectedExecutionException x) {
			//do nothing, not sure why this exception is happening for me
			// it must have to do with shutdown, but it happens intermittently.
		}
	}
	
	/**
	 * @diagram_sync on 1/8/2015 with Overview.seq
	 * @diagram_sync on 1/8/2015 with Coverage_Overview.seq
	 */
	@SuppressWarnings("boxing")
  private void monitorTrials() {
		Tests4J_PhaseOverseer trialPhaseOverseer = memory_.getTrialPhaseOverseer();
		
		I_PhaseState state = null;
    try {
      //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
      state = trialPhaseOverseer.pollPhaseState();
      while (!trialPhaseOverseer.isCountDoneCountMatch()) {
        //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
        notifier_.onProgress(state);
        printLogs();
        //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
        state = trialPhaseOverseer.pollPhaseState();
      }
    } catch (InterruptedException x) {
      throw new RuntimeException(x);
    }
    //@diagram_sync on 1/8/2015 with Coverage_Overview.seq
		notifier_.onProgress(state);
		long now = system_.getTime();
		memory_.setTrialEndTime(now);
		if (log_.isLogEnabled(Tests4J_Processor.class)) {
			double millis = now - memory_.getSetupEndTime();
			double secs = millis/1000.0;
			log_.log(this.toString() + " Competing trials with after " + secs + " seconds.");
		}
		String next = cod_.poll();
		while (next != null) {
			system_.println(next);
			next = cod_.poll();
		}
	}
	
	/**
	 * @diagram_sync on 1/8/2015 with Overview.seq
	 * @return
	 */
	private boolean isTrialsOrRemotesRunning() {
		if (!notifier_.isDoneRunningTrials()) {
			return true;
		}
		//todo add remotes
		return false;
	}
	
	private void instrumentAllowedReferences() {
	  I_Tests4J_CoveragePlugin coveragePlugin = memory_.getCoveragePlugin();
	  if (coveragePlugin != null) {
	    List<Class<? extends I_AbstractTrial>> trials = 
	          new ArrayList<Class<? extends I_AbstractTrial>>();
	    trials.addAll(memory_.getTrialClasses());
	    for (Class<? extends I_AbstractTrial> trial: trials) {
	      AllowedReferences refs = trial.getAnnotation(AllowedReferences.class);
	      if (refs != null) {
	        Class<? extends I_ReferenceGroup> [] groups = refs.groups();
	        if (groups != null) {
	          for (int i = 0; i < groups.length; i++) {
	            Class<? extends I_ReferenceGroup> group = groups[i];
	            try {
                coveragePlugin.instrument(group);
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            }
	        }
	        
	      }
	    }
	  }
	}
	/**
	 * @diagram_sync on 1/8/2015 with Overview.seq
	 */
	private void monitorRemotes() {
		
	}
	

	/**
	 * @diagram_sync on 1/8/2015 with Overview.seq
	 */
	@Override
	public I_Tests4J_Controls getControls() {
		return controls_;
	}

	
	private void printLogs() {
	  String next = cod_.poll();
    while (next != null) {
      system_.println(next);
      next = cod_.poll();
    }
	}
}
