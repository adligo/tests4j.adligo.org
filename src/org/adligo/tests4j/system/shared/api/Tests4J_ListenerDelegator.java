package org.adligo.tests4j.system.shared.api;

import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_PhaseState;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

/**
 * just catches any sort of error/exception and loggs it to the I_Tests4J_Reporter
 * so that tests4j isn't blamed for external code.
 * 
 * @author scott
 *
 */
public class Tests4J_ListenerDelegator implements I_Tests4J_Listener {
	public static final String TRIAL_RUN_LISTENER_DELEGATE_REQUIRES_A_I_TESTS4J_LOGGER = "TrialRunListenerDelegate requires a I_Tests4J_Logger.";
	private I_Tests4J_Listener delegate;
	private I_Tests4J_Log logger;
	
	public Tests4J_ListenerDelegator(I_Tests4J_Listener p, I_Tests4J_Log pLogger) {
		//allow null delegate
		delegate = p;
		
		//DO NOT allow null logger
		if (pLogger == null) {
			throw new IllegalArgumentException(TRIAL_RUN_LISTENER_DELEGATE_REQUIRES_A_I_TESTS4J_LOGGER);
		}
		logger = pLogger;
		
	}
	
	public void onMetadataCalculated(I_TrialRunMetadata metadata) {
		if (delegate == null) {
			return;
		}
		try {
			delegate.onMetadataCalculated(metadata);
		} catch (Throwable t) {
			logger.onThrowable(t);
		}
	}
	
	@Override
	public void onProgress(I_PhaseState process) {
		if (delegate == null) {
			return;
		}
		try {
			delegate.onProgress(process);
		} catch (Throwable t) {
			logger.onThrowable(t);
		}
	}

	public void onStartingTrial(String trialName) {
		if (delegate == null) {
			return;
		}
		try {
			delegate.onStartingTrial(trialName);
		} catch (Throwable t) {
			logger.onThrowable(t);
		}
	}

	public void onStartingTest(String trialName, String testName) {
		if (delegate == null) {
			return;
		}
		try {
			delegate.onStartingTest(trialName, testName);
		} catch (Throwable t) {
			logger.onThrowable(t);
		}
	}

	public void onTestCompleted(String trialName, String testName,
			boolean passed) {
		if (delegate == null) {
			return;
		}
		try {
			delegate.onTestCompleted(trialName, testName, passed);
		} catch (Throwable t) {
			logger.onThrowable(t);
		}
	}

	public void onTrialCompleted(I_TrialResult result) {
		if (delegate == null) {
			return;
		}
		try {
			delegate.onTrialCompleted(result);
		} catch (Throwable t) {
			logger.onThrowable(t);
		}
	}

	public void onRunCompleted(I_TrialRunResult result) {
		if (delegate == null) {
			return;
		}
		try {
			delegate.onRunCompleted(result);
		} catch (Throwable t) {
			logger.onThrowable(t);
		}
	}

	@Override
	public void onProccessStateChange(I_PhaseState info) {
		if (delegate == null) {
			return;
		}
		try {
			delegate.onProccessStateChange(info);
		} catch (Throwable t) {
			logger.onThrowable(t);
		}
	}

  @Override
  public void onStartingSetup(I_Tests4J_Params params) {
    if (delegate == null) {
      return;
    }
    try {
      delegate.onStartingSetup(params);
    } catch (Throwable t) {
      logger.onThrowable(t);
    }
  }

	
	
}
