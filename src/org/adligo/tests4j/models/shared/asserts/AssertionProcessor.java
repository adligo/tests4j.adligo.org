package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.results.TestFailure;
import org.adligo.tests4j.models.shared.results.TestFailureMutant;
import org.adligo.tests4j.models.shared.system.I_AssertListener;

public class AssertionProcessor {

	/**
	 * @param cmd
	 */
	public static void evaluate(I_AssertionHelperInfo info, I_BasicAssertCommand cmd) {
		if (cmd.evaluate()) {
			I_AssertListener listener = info.getListener();
			synchronized (listener) {
				listener.assertCompleted(cmd);
			}
		} else {
			I_AssertListener listener = info.getListener();
			synchronized (listener) {
				TestFailureMutant fm = new TestFailureMutant();
				fm.setMessage(cmd.getFailureMessage());
				fm.setLocationFailed(new AssertionFailureLocation(info));
				fm.setData(cmd);
				listener.assertFailed(new TestFailure(fm));
			}
		}
	}
	
	public static void evaluate(I_AssertionHelperInfo info, I_ThrownAssertCommand cmd, I_Thrower p) {
		if (cmd.evaluate(p)) {
			I_AssertListener listener = info.getListener();
			synchronized (listener) {
				listener.assertCompleted(cmd);
			}
		} else {
			I_AssertListener listener = info.getListener();
			synchronized (listener) {
				TestFailureMutant fm = new TestFailureMutant();
				fm.setMessage(cmd.getFailureMessage());
				fm.setLocationFailed(new AssertionFailureLocation(info));
				fm.setData(cmd);
				listener.assertFailed(new TestFailure(fm));
			}
		}
	}
}
