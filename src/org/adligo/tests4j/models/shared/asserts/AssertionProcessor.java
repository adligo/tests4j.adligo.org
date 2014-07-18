package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_SimpleAssertCommand;
import org.adligo.tests4j.models.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.models.shared.asserts.common.I_ThrownAssertCommand;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionCommand;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionEvaluator;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformThrownAssertionCommand;
import org.adligo.tests4j.models.shared.results.TestFailure;
import org.adligo.tests4j.models.shared.results.TestFailureMutant;
import org.adligo.tests4j.models.shared.system.I_AssertListener;

/**
 * this class processes the assert commands
 * by calling .evaluate and then notifying the I_AssertListener
 * about the outcome.
 * 
 * @author scott
 *
 */
public class AssertionProcessor {

	/**
	 * @param cmd
	 */
	public static void evaluate(I_AssertListener listener, I_SimpleAssertCommand cmd) {
		if (cmd.evaluate()) {
			synchronized (listener) {
				listener.assertCompleted(cmd);
			}
		} else {
			synchronized (listener) {
				onAssertionFailure(listener, cmd.getData(), cmd.getFailureMessage());
			}
		}
	}
	
	public static void evaluate(I_AssertListener listener, I_ThrownAssertCommand cmd, I_Thrower p) {
		if (cmd.evaluate(p)) {
			synchronized (listener) {
				listener.assertCompleted(cmd);
			}
		} else {
			synchronized (listener) {
				onAssertionFailure(listener, cmd.getData(), cmd.getFailureMessage());
			}
		}
	}

	public static void onAssertionFailure(I_AssertListener listener,
			I_AssertionData data, String failureMessage) {
		TestFailureMutant fm = new TestFailureMutant();
		fm.setMessage(failureMessage);
		fm.setLocationFailed(new AssertionFailureLocation());
		fm.setData(data);
		TestFailure tf = new TestFailure(fm);
		listener.assertFailed(tf);
	}
	
}
