package org.adligo.tests4j.run.helpers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.common.I_AssertCommand;
import org.adligo.tests4j.models.shared.common.Platform;
import org.adligo.tests4j.models.shared.common.StringMethods;
import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.BaseTrialResult;
import org.adligo.tests4j.models.shared.results.BaseTrialResultMutant;
import org.adligo.tests4j.models.shared.results.I_TestFailure;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.models.shared.results.TestFailureMutant;
import org.adligo.tests4j.models.shared.results.TestResult;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.system.I_AssertListener;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.models.shared.trials.TrialBindings;

public class MetaTrialProcessor implements I_AssertListener {
	private static final String AFTER_METADATA_CALCULATED_METHOD = 
			"afterMetadataCalculated(I_TrialRunMetadata p)";
	private static final String AFTER_NON_META_TRIALS_RUN_METHOD = 
			"afterNonMetaTrialsRun(TrialRunResultMutant p)";

	private Tests4J_Memory memory;
	private BaseTrialResultMutant trialResultMutant;
	private TestResultMutant metaTrialTestResultMutant;
	private Set<Integer> metaTrialAssertionHashes = new HashSet<Integer>();
	private String runMetaTrialMethod;
	private TrialBindings bindings;
	private I_Tests4J_NotificationManager notifier;
	
	public MetaTrialProcessor(Tests4J_Memory pMemory, I_Tests4J_NotificationManager pNotifier) {
		memory = pMemory;
		bindings = new TrialBindings(Platform.JSE, 
				memory.getEvaluationLookup(), memory.getReporter());
		bindings.setAssertListener(this);
		notifier = pNotifier;
	}
	
	/**
	 * 
	 * @param trial
	 * @param runResultMutant
	 * @return
	 */
	public I_TrialResult runMetaTrialMethods(I_MetaTrial trial, I_TrialRunResult runResultMutant) {
		trial.setBindings(bindings);
		String trialName = trial.getClass().getName();
		notifier.startingTrial(trialName);
		
		trialResultMutant = new BaseTrialResultMutant();
		trialResultMutant.setTrialName(trialName);
		trialResultMutant.setType(TrialType.MetaTrial);
		
		runMetaTrialMethod = AFTER_METADATA_CALCULATED_METHOD;
		boolean passed = false;
		notifier.startingTest(trialName, "afterMetadataCalculated");
		try {
			I_TrialRunMetadata metadata = memory.takeMetaTrialData();
			trial.afterMetadataCalculated(metadata);
			passed = true;
		} catch (MetaTrialAssertionFailure p) {
			//an assertion failed
		} catch (Throwable x) {
			onMetaTrialAfterMethodException(x, AFTER_METADATA_CALCULATED_METHOD);
		}
		if (metaTrialTestResultMutant == null) {
			metaTrialTestResultMutant = new TestResultMutant();
			metaTrialTestResultMutant.setPassed(passed);
			flushAssertionHashes(metaTrialTestResultMutant, metaTrialAssertionHashes);
			metaTrialTestResultMutant.setName(AFTER_METADATA_CALCULATED_METHOD);
		}
		notifier.onTestCompleted(trial.getClass().getName(),
				metaTrialTestResultMutant.getName(), 
				metaTrialTestResultMutant.isPassed());
		trialResultMutant.addResult(metaTrialTestResultMutant);
		
		metaTrialAssertionHashes.clear();
		metaTrialTestResultMutant = null;
		
		
		runMetaTrialMethod = AFTER_NON_META_TRIALS_RUN_METHOD;
		passed = false;
		notifier.startingTest(trialName, "afterNonMetaTrialsRun");
		try {
			trial.afterNonMetaTrialsRun(runResultMutant);
			passed = true;
		} catch (MetaTrialAssertionFailure p) {
			//an assertion failed
		} catch (Throwable x) {
			onMetaTrialAfterMethodException(x, AFTER_NON_META_TRIALS_RUN_METHOD);
		}
		if (metaTrialTestResultMutant == null) {
			metaTrialTestResultMutant = new TestResultMutant();
			metaTrialTestResultMutant.setPassed(passed);
			flushAssertionHashes(metaTrialTestResultMutant, metaTrialAssertionHashes);
			metaTrialTestResultMutant.setName(AFTER_NON_META_TRIALS_RUN_METHOD);
		}
		notifier.onTestCompleted(trial.getClass().getName(),
				metaTrialTestResultMutant.getName(), 
				metaTrialTestResultMutant.isPassed());
		trialResultMutant.addResult(metaTrialTestResultMutant);
		
		BaseTrialResult result = new BaseTrialResult(trialResultMutant);
		notifier.onTrialCompleted(result);
		return result;
	}

	private void onMetaTrialAfterMethodException(Throwable x, String method) {
		metaTrialTestResultMutant = new TestResultMutant();
		metaTrialTestResultMutant.setPassed(false);
		flushAssertionHashes(metaTrialTestResultMutant, metaTrialAssertionHashes);
		metaTrialTestResultMutant.setName(method);
		TestFailureMutant tfm = new TestFailureMutant();
		tfm.setException(x);
		String message = x.getMessage();
		if (StringMethods.isEmpty(message)) {
			tfm.setMessage("Unknown exception message in onMetaTrialAfterMethodException.");
		} else {
			tfm.setMessage(x.getMessage());
		}
		metaTrialTestResultMutant.setFailure(tfm);
	}
	
	@Override
	public void assertCompleted(I_AssertCommand cmd) {
		metaTrialAssertionHashes.add(cmd.hashCode());
	}

	@Override
	public void assertFailed(I_TestFailure failure) {
		metaTrialTestResultMutant = new TestResultMutant();
		metaTrialTestResultMutant.setFailure(failure);
		metaTrialTestResultMutant.setName(runMetaTrialMethod);
		flushAssertionHashes(metaTrialTestResultMutant,metaTrialAssertionHashes);
		throw new MetaTrialAssertionFailure();
	}

	private void flushAssertionHashes(TestResultMutant trm, Collection<Integer> hashes) {
		Iterator<Integer> it = hashes.iterator();
		while (it.hasNext()) {
			trm.incrementAssertionCount(it.next());
		}
	}
}
