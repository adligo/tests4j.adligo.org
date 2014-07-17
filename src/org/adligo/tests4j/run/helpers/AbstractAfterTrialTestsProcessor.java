package org.adligo.tests4j.run.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.adligo.tests4j.models.shared.asserts.common.I_AssertCommand;
import org.adligo.tests4j.models.shared.common.Platform;
import org.adligo.tests4j.models.shared.common.StringMethods;
import org.adligo.tests4j.models.shared.results.BaseTrialResultMutant;
import org.adligo.tests4j.models.shared.results.I_TestFailure;
import org.adligo.tests4j.models.shared.results.TestFailureMutant;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.system.I_AssertListener;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.TrialBindings;
import org.adligo.tests4j.models.shared.trials.TrialRecursion;

/**
 * a abstract class to help keep the TrialInstancesProcessor
 * easier to read 
 * extensions calls the 
 * afterTrialTests methods
 * and MetaTrial methods
 * 
 * This class should behave like a model (ie it Is NOT thread safe)
 * @author scott
 *
 */
public abstract class AbstractAfterTrialTestsProcessor implements I_AssertListener {
	public static final String AFTER_TRIAL_TESTS = "afterTrialTests";
	private TrialBindings bindings;
	private TrialDescription trialDescription;

	private I_CoverageRecorder trialThreadLocalCoverageRecorder;
	private I_AbstractTrial trial;
	private TestResultMutant delegatedTestResultMutant;
	private List<Integer> delegatedTestAssertionHashes; 
	
	public AbstractAfterTrialTestsProcessor(Tests4J_Memory memory) {
		bindings = new TrialBindings(Platform.JSE, memory.getEvaluationLookup(), memory.getReporter());
		bindings.setAssertListener(this);
	}

	public void reset(TrialDescription pDesc, I_CoverageRecorder pRec, I_AbstractTrial pTrial) {
		delegatedTestResultMutant = null;
		delegatedTestAssertionHashes = new ArrayList<Integer>();
		
		trialDescription = pDesc;
		trialThreadLocalCoverageRecorder = pRec;
		trial = pTrial;
		trial.setBindings(bindings);
	}
	
	public void startDelegatedTest() {
		delegatedTestResultMutant = new TestResultMutant();
	}
	
	@Override
	public void assertCompleted(I_AssertCommand cmd) {
		delegatedTestAssertionHashes.add(cmd.hashCode());
	}

	@Override
	public void assertFailed(I_TestFailure failure) {
		delegatedTestResultMutant.setFailure(failure);
		delegatedTestResultMutant.setName(AFTER_TRIAL_TESTS);
		flushAssertionHashes(delegatedTestResultMutant,delegatedTestAssertionHashes);
		throw new DelegateTestAssertionFailure();
	}
	
	private void flushAssertionHashes(TestResultMutant trm, Collection<Integer> hashes) {
		Iterator<Integer> it = hashes.iterator();
		while (it.hasNext()) {
			trm.incrementAssertionCount(it.next());
		}
	}

	protected void flushAssertionHashes(TestResultMutant trm) {
		Iterator<Integer> it = delegatedTestAssertionHashes.iterator();
		while (it.hasNext()) {
			Integer next = it.next();
			trm.incrementAssertionCount(next);
		}
	}
	
	protected TrialDescription getTrialDescription() {
		return trialDescription;
	}


	protected I_CoverageRecorder getTrialThreadLocalCoverageRecorder() {
		return trialThreadLocalCoverageRecorder;
	}

	protected I_AbstractTrial getTrial() {
		return trial;
	}

	protected TestResultMutant getAfterTrialTestsResultMutant() {
		return delegatedTestResultMutant;
	}

	
	protected void onDelegatedTestMethodException(Throwable x, String method) {
		delegatedTestResultMutant = new TestResultMutant();
		delegatedTestResultMutant.setPassed(false);
		flushAssertionHashes(delegatedTestResultMutant, delegatedTestAssertionHashes);
		delegatedTestResultMutant.setName(method);
		TestFailureMutant tfm = new TestFailureMutant();
		tfm.setException(x);
		String message = x.getMessage();
		if (StringMethods.isEmpty(message)) {
			message = "Unknown Error message.";
		}
		tfm.setMessage(message);
		delegatedTestResultMutant.setFailure(tfm);
	}
	
}
