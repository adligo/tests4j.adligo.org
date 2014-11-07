package org.adligo.tests4j.run.discovery;

import org.adligo.tests4j.run.common.I_JseSystem;
import org.adligo.tests4j.run.helpers.JseSystem;
import org.adligo.tests4j.shared.asserts.uniform.EvaluatorLookup;
import org.adligo.tests4j.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.shared.asserts.uniform.I_UniformAssertionEvaluator;
import org.adligo.tests4j.shared.common.Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ParamsReaderMessages;
import org.adligo.tests4j.shared.output.DefaultLog;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoveragePluginFactory;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoveragePluginParams;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Params;
import org.adligo.tests4j.system.shared.api.I_Tests4J_ProgressParams;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Selection;
import org.adligo.tests4j.system.shared.api.I_Tests4J_SourceInfoParams;
import org.adligo.tests4j.system.shared.api.Tests4J_CoveragePluginParams;
import org.adligo.tests4j.system.shared.api.Tests4J_RemoteInfo;
import org.adligo.tests4j.system.shared.api.Tests4J_Selection;
import org.adligo.tests4j.system.shared.report.summary.DefaultReporterStates;
import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.system.shared.trials.I_MetaTrial;
import org.adligo.tests4j.system.shared.trials.I_MetaTrialInputData;
import org.adligo.tests4j.system.shared.trials.I_MetaTrialParams;
import org.adligo.tests4j.system.shared.trials.I_Trial;
import org.adligo.tests4j.system.shared.trials.I_TrialParamsFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @diagram_sync with Overview.seq on 8/20/2014
 * 
 * since anyone can re implement 
 * I_Tests4J_Params and pass it in, 
 * exceptions/errors could be caused anywhere
 * the implementation is buggy.
 * 
 * To protect Tests4J from getting blamed
 * this class reads in the params in a way where
 * each method call is wrapped in 
 * try { call impl } catch (Throwable t) { useDefault and log exception)
 * 
 * @author scott
 *
 */
public class Tests4J_ParamsReader {
  private I_JseSystem system_;
	private I_Tests4J_Params params_;
	private Map<Class<?>, Boolean> logStates;
	private I_Tests4J_Log logger;
	/**
	 * if this boolean gets set to false
	 * the parameters did have enough not sufficient 
	 * information to actually run.
	 */
	private boolean runnable = true;
	private I_Tests4J_CoveragePlugin coveragePlugin;
	private List<Class<? extends I_AbstractTrial>> trials = new ArrayList<Class<? extends I_AbstractTrial>>();
	private Class<? extends I_MetaTrial> metaTrialClass;
	private int trialThreadCount = 0;
	private int setupThreadCount = 0;
	private I_EvaluatorLookup evaluatorLookup;
	private Set<I_Tests4J_Selection> tests = new HashSet<I_Tests4J_Selection>();
	private Throwable runFalseReason;
	private List<OutputStream> additionalReportOutputStreams = new ArrayList<OutputStream>();
	private I_MetaTrialParams<? extends I_MetaTrialInputData> metaTrialParams_;
	private I_TrialParamsFactory trialParamsQueue_;
	private I_Tests4J_SourceInfoParams sourceInfoParams_;
	private I_Tests4J_ProgressParams progressMonitor_;

	/**
	 * turn into local instances to block further propagation of issues
	 * with external implementations.  Also this recurses 
	 * when parameters are remote.
	 */
	private Map<Tests4J_RemoteInfo,Tests4J_ParamsReader> remotes = new HashMap<Tests4J_RemoteInfo, Tests4J_ParamsReader>();
	
	/**
	 * @diagram_sync with Overview.seq on 8/20/2014
	 * 
	 * assumes non null parameters
	 * @param pParams
	 * @param pReporter
	 */
	public Tests4J_ParamsReader(I_JseSystem system, I_Tests4J_Params params) {
	  system_ = system;
	  params_ = params;
		
		logStates = new HashMap<Class<?>, Boolean>();
		logStates.putAll(DefaultReporterStates.getDefalutLogStates());
		try {
			Map<Class<?>, Boolean>  paramStates = params_.getLogStates();
			if (paramStates != null) {
				logStates.putAll(paramStates);
			}
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
		logger = new DefaultLog(system_, logStates);
	}
	
	public void read(I_Tests4J_Log loggerIn) {
		logger = loggerIn;
		try {
			getTrialsFromParams(params_);
		} catch (Throwable t) {
			//some error/exception with the trials, do NOT try to recover
			logger.onThrowable(t);
			runnable = false;
			return;
		}
		

		try {
			getRemotes();
		} catch (Throwable t) {
			runFalseReason = t;
			//some error/exception with the trials, do NOT try to recover
			logger.onThrowable(t);
			runnable = false;
			return;
		}
		
		if (trials.size() == 0 ) {
			I_Tests4J_ParamsReaderMessages constants =  Tests4J_Constants.CONSTANTS.getParamReaderMessages();
			logger.log(constants.getNoTrialsOrRemotesToRun());
			runnable = false;
			runFalseReason = new IllegalArgumentException(constants.getNoTrialsOrRemotesToRun());
			runFalseReason.fillInStackTrace();
			return;
		}
		
		List<OutputStream> outs = params_.getAdditionalReportOutputStreams();
		int counter = 0;
		for (OutputStream out: outs) {
			if (out != null) {
				counter++;
				String testLine = this.getClass().getName() + " " + counter + "/" + outs.size();
				try {
					out.write(testLine.getBytes());
					additionalReportOutputStreams.add(out);
				} catch (IOException x) {
					logger.onThrowable(x);
				}
			}
		}
		
		try {
			Set<I_Tests4J_Selection> paramTests = params_.getTests();
			
			//remove the at most 1 element from Set
			paramTests.remove(null);
			for (I_Tests4J_Selection sel: paramTests) {
				Class<? extends I_Trial> trial = sel.getTrial();
				if (trials.contains(trial)) {
					tests.add(new Tests4J_Selection(sel));
				} else {
					I_Tests4J_ParamsReaderMessages messages =  Tests4J_Constants.CONSTANTS.getParamReaderMessages();
					throw new IllegalArgumentException(
							messages.getTestSelectionsMustHaveACorrespondingTrial() +
							logger.getLineSeperator() + 
							trial);
				}
			}
		} catch (Throwable t) {
			runFalseReason = t;
			//error with tests, don't recover
			logger.onThrowable(t);
			runnable = false;
			return;
		}
		
		determineThreadCounts();
		
		try {
			coveragePlugin = createCoveragePlugin();
		} catch (Throwable t) {
			//some error/exception with the coveragePlugin, try to recover
			logger.onThrowable(t);
		}
		try {
			readEvaluatorLookup();
		} catch (Throwable t) {
			runFalseReason = t;
			logger.onThrowable(t);
			runnable = false;
			return;
		}
		Map<String, I_UniformAssertionEvaluator<?, ?>> evals = evaluatorLookup.getLookupData();
		I_EvaluatorLookup defaultLookup =  EvaluatorLookup.DEFAULT_LOOKUP;
		Map<String, I_UniformAssertionEvaluator<?, ?>>  defaultEvals = defaultLookup.getLookupData();
		Set<String> actualKeys = evals.keySet();
		Set<String> defaultKeys = defaultEvals.keySet();
		
		metaTrialParams_ =  params_.getMetaTrialParams();
		trialParamsQueue_ = params_.getTrialParamsQueue();
		if (!actualKeys.containsAll(defaultKeys)) {
			I_Tests4J_ParamsReaderMessages constants =  Tests4J_Constants.CONSTANTS.getParamReaderMessages();
			logger.log(constants.getTheEvaluatorsAreExpectedToContainTheDefaultKeys());
			runnable = false;
			return;
		}
		progressMonitor_ = params_.getProgressMonitor();
		sourceInfoParams_ = params_.getSourceInfoParams();
	}

	protected void determineThreadCounts() {
		Integer recommendedTrialThreads = null;
		try {
			recommendedTrialThreads = params_.getRecommendedTrialThreadCount();
		} catch (Throwable t) {
			//some error/exception with the trials, do try to recover
			logger.onThrowable(t);
		}
		trialThreadCount = determineTrialThreads(recommendedTrialThreads);
		Integer recomendedSetupThreads = null;
		
		try {
			recomendedSetupThreads = params_.getRecommendedSetupThreadCount();
		} catch (Throwable t) {
			//some error/exception with the trials, do try to recover
			logger.onThrowable(t);
		}
		if (recomendedSetupThreads == null) {
			setupThreadCount = trialThreadCount;
		} else if (trialThreadCount >= recomendedSetupThreads) {
			setupThreadCount = recomendedSetupThreads;
		}
	}

	private void readEvaluatorLookup() throws InstantiationException,
			IllegalAccessException {
		Class<?> elc = params_.getEvaluatorLookup();
		if (elc == null) {
			evaluatorLookup = EvaluatorLookup.DEFAULT_LOOKUP;
		} else if (EvaluatorLookup.class.isAssignableFrom(elc)) {
			evaluatorLookup = EvaluatorLookup.DEFAULT_LOOKUP;
		} else {
			evaluatorLookup = (I_EvaluatorLookup) elc.newInstance();
		}
	}

	private void getRemotes() {
		//
		
	}
	

	public int determineTrialThreads(Integer trialThreads) {
		if (trialThreads == null) {
			trialThreads = Runtime.getRuntime().availableProcessors() * 2;
		}
		if (trialThreads > trials.size()) {
			trialThreads = trials.size();
		}
		return trialThreads;
	}

	public void getTrialsFromParams(I_Tests4J_Params pParams) {
		List<Class<? extends I_Trial>> pTrialClasses = pParams.getTrials();
		for (Class<? extends I_Trial> pTrialClass: pTrialClasses) {
			if (pTrialClass != null) {
				trials.add(pTrialClass);
			}
		}
		metaTrialClass = pParams.getMetaTrialClass();
	}
	
	public I_Tests4J_CoveragePlugin createCoveragePlugin() throws Exception {
		Class<? extends I_Tests4J_CoveragePluginFactory> coveragePluginFactoryClass = null;
		
		
		Tests4J_CoveragePluginParams coverageParams = null;
		try {
			coveragePluginFactoryClass = params_.getCoveragePluginFactoryClass();
			I_Tests4J_CoveragePluginParams coverageParamsIn = params_.getCoverageParams();
			if (coverageParamsIn == null) {
				coverageParamsIn = new Tests4J_CoveragePluginParams();
			}
			coverageParams = new Tests4J_CoveragePluginParams(coverageParamsIn);
		} catch (Throwable t) {
			logger.onThrowable(t);
			return null;
		}
		if (coveragePluginFactoryClass == null) {
			return null;
		}
		if (trialThreadCount <= 1) {
			coverageParams.setConcurrentRecording(false);
		}
		I_Tests4J_CoveragePluginFactory factory = coveragePluginFactoryClass.newInstance();
		Map<String,Object> runtimeParams = new HashMap<String, Object>();
		runtimeParams.put(I_Tests4J_CoveragePluginFactory.LOG, logger);
    runtimeParams.put(I_Tests4J_CoveragePluginFactory.SYSTEM, system_);
		I_Tests4J_CoveragePlugin plugin = factory.create(coverageParams, runtimeParams);
		
		return plugin;
	}

	public I_Tests4J_CoveragePlugin getCoveragePlugin() {
		return coveragePlugin;
	}

	public boolean isRunnable() {
		return runnable;
	}

	public List<Class<? extends I_AbstractTrial>> getTrials() {
		return trials;
	}

	public Class<? extends I_MetaTrial> getMetaTrialClass() {
		return metaTrialClass;
	}

	public int getTrialThreadCount() {
		return trialThreadCount;
	}

	public int getSetupThreadCount() {
		return setupThreadCount;
	}
	

	public I_EvaluatorLookup getEvaluatorLookup() {
		return evaluatorLookup;
	}

	public Set<I_Tests4J_Selection> getTests() {
		return tests;
	}

	public I_Tests4J_Log getLogger() {
		return logger;
	}

	public Throwable getRunFalseReason() {
		return runFalseReason;
	}

	public Map<Class<?>, Boolean> getLogStates() {
		return logStates;
	}

	public List<OutputStream> getAdditionalReportOutputStreams() {
		return additionalReportOutputStreams;
	}

	public I_MetaTrialParams<? extends I_MetaTrialInputData> getMetaTrialParams() {
		return metaTrialParams_;
	}
	
	public I_TrialParamsFactory getTrialParamsQueue() {
		return trialParamsQueue_;
	}

	public I_Tests4J_SourceInfoParams getSourceInfoParams() {
		return sourceInfoParams_;
	}

  public I_Tests4J_ProgressParams getProgressMonitor() {
    return progressMonitor_;
  }
}
