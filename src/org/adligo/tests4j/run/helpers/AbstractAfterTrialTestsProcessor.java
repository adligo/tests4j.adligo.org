package org.adligo.tests4j.run.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.adligo.tests4j.models.shared.asserts.common.I_AssertCommand;
import org.adligo.tests4j.models.shared.common.Platform;
import org.adligo.tests4j.models.shared.common.StringMethods;
import org.adligo.tests4j.models.shared.results.I_TestFailure;
import org.adligo.tests4j.models.shared.results.TestFailureMutant;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.system.I_AssertListener;
import org.adligo.tests4j.models.shared.system.I_CoverageRecorder;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.TrialBindings;

/**
 * a abstract class to help keep the TrialInstancesProcessor
 * easier to read 
 * extensions calls the 
 * afterTrialTests methods
 * and MetaTrial methods
 * 
 * @author scott
 *
 */
public abstract class AbstractAfterTrialTestsProcessor implements I_AssertListener {
	public static final String AFTER_TRIAL_TESTS = "afterTrialTests";
	private TrialBindings bindings;
	private TrialDescription trialDescription;
	private boolean hadAfterTrialTests = false;
	private boolean ranAfterTrialTests = false;
	private I_CoverageRecorder trialThreadLocalCoverageRecorder;
	private I_AbstractTrial trial;
	private TestResultMutant afterTrialTestsResultMutant;
	private List<Integer> afterTrialTestsAssertionHashes; 
	
	public AbstractAfterTrialTestsProcessor(Tests4J_Memory memory) {
		bindings = new TrialBindings(Platform.JSE, memory.getEvaluationLookup(), memory.getReporter());
		bindings.setAssertListener(this);
	}

	public void reset(TrialDescription pDesc, I_CoverageRecorder pRec, I_AbstractTrial pTrial) {
		hadAfterTrialTests = false;
		ranAfterTrialTests = false;
		afterTrialTestsResultMutant = null;
		afterTrialTestsAssertionHashes = new ArrayList<Integer>();
		
		trialDescription = pDesc;
		trialThreadLocalCoverageRecorder = pRec;
		trial = pTrial;
		trial.setBindings(bindings);
	}
	
	@Override
	public void assertCompleted(I_AssertCommand cmd) {
		afterTrialTestsAssertionHashes.add(cmd.hashCode());
	}

	@Override
	public void assertFailed(I_TestFailure failure) {
		afterTrialTestsResultMutant = new TestResultMutant();
		afterTrialTestsResultMutant.setFailure(failure);
		afterTrialTestsResultMutant.setName(AFTER_TRIAL_TESTS);
		flushAssertionHashes(afterTrialTestsResultMutant,afterTrialTestsAssertionHashes);
		throw new AfterTrialTestsAssertionFailure();
	}
	
	private void flushAssertionHashes(TestResultMutant trm, Collection<Integer> hashes) {
		Iterator<Integer> it = hashes.iterator();
		while (it.hasNext()) {
			trm.incrementAssertionCount(it.next());
		}
	}

	protected void flushAssertionHashes(TestResultMutant trm) {
		Iterator<Integer> it = afterTrialTestsAssertionHashes.iterator();
		while (it.hasNext()) {
			Integer next = it.next();
			trm.incrementAssertionCount(next);
		}
	}
	
	protected TrialDescription getTrialDescription() {
		return trialDescription;
	}

	protected boolean isHadAfterTrialTests() {
		return hadAfterTrialTests;
	}

	protected boolean isRanAfterTrialTests() {
		return ranAfterTrialTests;
	}

	protected I_CoverageRecorder getTrialThreadLocalCoverageRecorder() {
		return trialThreadLocalCoverageRecorder;
	}

	protected I_AbstractTrial getTrial() {
		return trial;
	}

	protected TestResultMutant getAfterTrialTestsResultMutant() {
		return afterTrialTestsResultMutant;
	}

	protected void setHadAfterTrialTests(boolean hadAfterTrialTests) {
		this.hadAfterTrialTests = hadAfterTrialTests;
	}

	protected void setRanAfterTrialTests(boolean ranAfterTrialTests) {
		this.ranAfterTrialTests = ranAfterTrialTests;
	}

	protected void setAfterTrialTestsResultMutant(
			TestResultMutant afterTrialTestsResultMutant) {
		this.afterTrialTestsResultMutant = afterTrialTestsResultMutant;
	}
	
	protected void onAfterTrialTestsMethodException(Throwable x, String method) {
		afterTrialTestsResultMutant = new TestResultMutant();
		afterTrialTestsResultMutant.setPassed(false);
		flushAssertionHashes(afterTrialTestsResultMutant, afterTrialTestsAssertionHashes);
		afterTrialTestsResultMutant.setName(method);
		TestFailureMutant tfm = new TestFailureMutant();
		tfm.setException(x);
		String message = x.getMessage();
		if (StringMethods.isEmpty(message)) {
			message = "Unknown Error message.";
		}
		tfm.setMessage(message);
		afterTrialTestsResultMutant.setFailure(tfm);
	}
}
