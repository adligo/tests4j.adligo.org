package org.adligo.tests4j.run.discovery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.uniform.EvaluatorLookup;
import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionEvaluator;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ParamReaderConstants;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;
import org.adligo.tests4j.models.shared.system.I_CoveragePluginFactory;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Params;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.models.shared.trials.I_Trial;

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
	private I_Tests4J_Logger logger;
	/**
	 * if this boolean gets set to false
	 * the parameters did have enough not sufficient 
	 * information to actually run.
	 */
	private boolean runnable = true;
	private I_CoveragePlugin coveragePlugin;
	private List<Class<? extends I_AbstractTrial>> trials = new ArrayList<Class<? extends I_AbstractTrial>>();
	private List<Class<? extends I_AbstractTrial>> instrumentedTrials = new ArrayList<Class<? extends I_AbstractTrial>>();
	private Class<? extends I_MetaTrial> metaTrialClass;
	private int trialThreadCount = 0;
	private I_EvaluatorLookup evaluatorLookup;
	private Set<String> tests = new HashSet<String>();
	
	/**
	 * assumes non null parameters
	 * @param pParams
	 * @param pReporter
	 */
	public Tests4J_ParamsReader(I_Tests4J_Params pParams, I_Tests4J_Logger pLogger) {
		params = pParams;
		logger = pLogger;
		
		try {
			coveragePlugin = createCoveragePlugin();
		} catch (Throwable t) {
			//some error/exception with the coveragePlugin, try to recover
			logger.onError(t);
		}
		try {
			getTrialsFromParams(pParams);
		} catch (Throwable t) {
			//some error/exception with the trials, do NOT try to recover
			logger.onError(t);
			runnable = false;
			return;
		}
		if (trials.size() == 0 ) {
			I_Tests4J_ParamReaderConstants constants =  Tests4J_Constants.CONSTANTS.getTests4j_ParamReaderConstants();
			logger.log(constants.getNoTrialsToRun());
			runnable = false;
			return;
		}
		try {
			Set<String> paramTests = pParams.getTests();
			
			//remove the at most 1 element from Set
			paramTests.remove(null);
			tests.addAll(paramTests);
		} catch (Throwable t) {
			//error with tests, don't recover
			logger.onError(t);
			runnable = false;
			return;
		}
		
		//determine the threads
		Integer recommendedTrialThreads = null;
		try {
			recommendedTrialThreads = pParams.getRecommendedTrialThreadCount();
		} catch (Throwable t) {
			//some error/exception with the trials, do try to recover
			logger.onError(t);
		}
		trialThreadCount = determineTrialThreads(recommendedTrialThreads);
		if (coveragePlugin != null) {
			try {
				List<Class<? extends I_AbstractTrial>> instrumentedAbstractTrials = coveragePlugin.instrumentClasses(
						new ArrayList<Class<? extends I_AbstractTrial>>(trials));
				for (Class<? extends I_AbstractTrial> trialClass: instrumentedAbstractTrials) {
					instrumentedTrials.add((Class<? extends I_Trial>) trialClass);
				}
			} catch (Throwable t) {
				//some error/exception with the trials, do NOT try to recover
				I_Tests4J_ParamReaderConstants constants =  Tests4J_Constants.CONSTANTS.getTests4j_ParamReaderConstants();
				logger.log(constants.getThereWasAIssueInstrumentingClassesForCodeCoverage());
				logger.onError(t);
				runnable = false;
				return;
			}
		}
		try {
			evaluatorLookup = new EvaluatorLookup(params.getEvaluatorLookup());
		} catch (Throwable t) {
			logger.onError(t);
			runnable = false;
			return;
		}
		Map<String, I_UniformAssertionEvaluator<?, ?>> evals = evaluatorLookup.getLookupData();
		I_EvaluatorLookup defaultLookup =  EvaluatorLookup.DEFAULT_LOOKUP;
		Map<String, I_UniformAssertionEvaluator<?, ?>>  defaultEvals = defaultLookup.getLookupData();
		Set<String> actualKeys = evals.keySet();
		Set<String> defaultKeys = defaultEvals.keySet();
		
		if (!actualKeys.containsAll(defaultKeys)) {
			I_Tests4J_ParamReaderConstants constants =  Tests4J_Constants.CONSTANTS.getTests4j_ParamReaderConstants();
			logger.log(constants.getTheEvaluatorsAreExpectedToContainTheDefaultKeys());
			runnable = false;
			return;
		}
		
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
	
	public I_CoveragePlugin createCoveragePlugin() throws Exception {
		Class<? extends I_CoveragePluginFactory> coveragePluginFactoryClass = params.getCoveragePluginFactoryClass();
		if (coveragePluginFactoryClass == null) {
			return null;
		}
		
		I_CoveragePluginFactory factory = coveragePluginFactoryClass.newInstance();
		I_CoveragePlugin plugin = factory.create(logger);
		
		return plugin;
	}

	public I_CoveragePlugin getCoveragePlugin() {
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

	public List<Class<? extends I_AbstractTrial>> getInstrumentedTrials() {
		return instrumentedTrials;
	}

	public I_EvaluatorLookup getEvaluatorLookup() {
		return evaluatorLookup;
	}

	public Set<String> getTests() {
		return tests;
	}
}
