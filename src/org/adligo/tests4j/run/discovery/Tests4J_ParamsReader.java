package org.adligo.tests4j.run.discovery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.uniform.EvaluatorLookup;
import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionEvaluator;
import org.adligo.tests4j.models.shared.common.I_System;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ParamReaderMessages;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoveragePluginFactory;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Params;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Selection;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.Tests4J_RemoteInfo;
import org.adligo.tests4j.models.shared.system.Tests4J_Selection;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.models.shared.trials.I_Trial;
import org.adligo.tests4j.shared.output.DefaultLog;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.shared.report.summary.DefaultReporterStates;

/**
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
	private I_Tests4J_Params params;
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
	private List<Class<? extends I_AbstractTrial>> instrumentedTrials = new ArrayList<Class<? extends I_AbstractTrial>>();
	private Class<? extends I_MetaTrial> metaTrialClass;
	private int trialThreadCount = 0;
	private int setupThreadCount = 0;
	private I_EvaluatorLookup evaluatorLookup;
	private Set<I_Tests4J_Selection> tests = new HashSet<I_Tests4J_Selection>();
	private Throwable runFalseReason;
	
	/**
	 * turn into local instances to block further propagation of issues
	 * with external implementations.  Also this recurses 
	 * when parameters are remote.
	 */
	private Map<Tests4J_RemoteInfo,Tests4J_ParamsReader> remotes = new HashMap<Tests4J_RemoteInfo, Tests4J_ParamsReader>();
	
	/**
	 * assumes non null parameters
	 * @param pParams
	 * @param pReporter
	 */
	public Tests4J_ParamsReader(I_System pSystem, I_Tests4J_Params pParams) {
		params = pParams;
		
		logStates = new HashMap<Class<?>, Boolean>();
		logStates.putAll(DefaultReporterStates.getDefalutLogStates());
		try {
			Map<Class<?>, Boolean>  paramStates = pParams.getLogStates();
			if (paramStates != null) {
				logStates.putAll(paramStates);
			}
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
		logger = new DefaultLog(pSystem, logStates);
		
		try {
			getTrialsFromParams(pParams);
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
			I_Tests4J_ParamReaderMessages constants =  Tests4J_Constants.CONSTANTS.getParamReaderMessages();
			logger.log(constants.getNoTrialsOrRemotesToRun());
			runnable = false;
			runFalseReason = new IllegalArgumentException(constants.getNoTrialsOrRemotesToRun());
			runFalseReason.fillInStackTrace();
			return;
		}
		
		try {
			coveragePlugin = createCoveragePlugin();
		} catch (Throwable t) {
			//some error/exception with the coveragePlugin, try to recover
			logger.onThrowable(t);
		}
	
		
		try {
			Set<I_Tests4J_Selection> paramTests = pParams.getTests();
			
			//remove the at most 1 element from Set
			paramTests.remove(null);
			for (I_Tests4J_Selection sel: paramTests) {
				Class<? extends I_Trial> trial = sel.getTrial();
				if (trials.contains(trial)) {
					tests.add(new Tests4J_Selection(sel));
				} else {
					I_Tests4J_ParamReaderMessages messages =  Tests4J_Constants.CONSTANTS.getParamReaderMessages();
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
		
		//determine the threads
		Integer recommendedTrialThreads = null;
		try {
			recommendedTrialThreads = pParams.getRecommendedTrialThreadCount();
		} catch (Throwable t) {
			//some error/exception with the trials, do try to recover
			logger.onThrowable(t);
		}
		trialThreadCount = determineTrialThreads(recommendedTrialThreads);
		Integer recomendedSetupThreads = pParams.getRecommendedSetupThreadCount();
		if (recomendedSetupThreads == null) {
			setupThreadCount = trialThreadCount;
		} else if (trialThreadCount >= recomendedSetupThreads) {
			setupThreadCount = recomendedSetupThreads;
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
		
		if (!actualKeys.containsAll(defaultKeys)) {
			I_Tests4J_ParamReaderMessages constants =  Tests4J_Constants.CONSTANTS.getParamReaderMessages();
			logger.log(constants.getTheEvaluatorsAreExpectedToContainTheDefaultKeys());
			runnable = false;
			return;
		}
		
	}

	private void readEvaluatorLookup() throws InstantiationException,
			IllegalAccessException {
		Class<?> elc = params.getEvaluatorLookup();
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
		Class<? extends I_Tests4J_CoveragePluginFactory> coveragePluginFactoryClass = params.getCoveragePluginFactoryClass();
		if (coveragePluginFactoryClass == null) {
			return null;
		}
		
		I_Tests4J_CoveragePluginFactory factory = coveragePluginFactoryClass.newInstance();
		I_Tests4J_CoveragePlugin plugin = factory.create(params.getCoverageParams(), logger);
		
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
	
	public List<Class<? extends I_AbstractTrial>> getInstrumentedTrials() {
		return instrumentedTrials;
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

}
