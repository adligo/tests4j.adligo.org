package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.I_SimpleAssertCommand;
import org.adligo.tests4j.models.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.models.shared.asserts.common.I_ThrownAssertCommand;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionCommand;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionEvaluator;
import org.adligo.tests4j.models.shared.results.TestFailure;
import org.adligo.tests4j.models.shared.results.TestFailureMutant;
import org.adligo.tests4j.models.shared.system.I_AssertListener;

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
				TestFailureMutant fm = new TestFailureMutant();
				fm.setMessage(cmd.getFailureMessage());
				fm.setLocationFailed(new AssertionFailureLocation());
				fm.setData(cmd.getData());
				TestFailure tf = new TestFailure(fm);
				listener.assertFailed(tf);
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
				TestFailureMutant fm = new TestFailureMutant();
				fm.setMessage(cmd.getFailureMessage());
				fm.setLocationFailed(new AssertionFailureLocation());
				fm.setData(cmd.getData());
				TestFailure tf = new TestFailure(fm);
				listener.assertFailed(tf);
			}
		}
	}
	
	public static void evaluate(I_AssertListener listener, I_UniformAssertionCommand cmd, 
			I_UniformAssertionEvaluator<?> uae) {
		if (cmd.evaluate(uae)) {
			synchronized (listener) {
				listener.assertCompleted(cmd);
			}
		} else {
			synchronized (listener) {
				TestFailureMutant fm = new TestFailureMutant();
				fm.setMessage(cmd.getFailureMessage());
				fm.setLocationFailed(new AssertionFailureLocation());
				fm.setData(cmd.getData());
				TestFailure tf = new TestFailure(fm);
				listener.assertFailed(tf);
			}
		}
	}
}
