package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.results.TestFailure;
import org.adligo.tests4j.models.shared.results.TestFailureMutant;
import org.adligo.tests4j.models.shared.system.I_AssertListener;

public class AssertionProcessor {

	/**
	 * @param cmd
	 */
	public static void evaluate(I_AssertListener listener, I_BasicAssertCommand cmd) {
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
}
