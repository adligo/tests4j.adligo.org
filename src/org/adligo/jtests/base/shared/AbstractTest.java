package org.adligo.jtests.base.shared;

import static org.adligo.jtests.base.shared.asserts.AssertCommandType.AssertFalse;
import static org.adligo.jtests.base.shared.asserts.AssertCommandType.AssertNotNull;
import static org.adligo.jtests.base.shared.asserts.AssertCommandType.AssertNull;
import static org.adligo.jtests.base.shared.asserts.AssertCommandType.AssertTrue;

import org.adligo.jtests.base.shared.asserts.AssertionFailureLocation;
import org.adligo.jtests.base.shared.asserts.BooleanAssertCommand;
import org.adligo.jtests.base.shared.asserts.I_AssertCommand;
import org.adligo.jtests.base.shared.asserts.I_Asserts;
import org.adligo.jtests.models.shared.common.IsEmpty;
import org.adligo.jtests.models.shared.results.TestFailure;
import org.adligo.jtests.models.shared.results.TestFailureMutant;
import org.adligo.jtests.models.shared.run.I_AssertListener;

public abstract class AbstractTest implements I_AbstractTest, I_Asserts {
	public static final String THE_VALUE_SHOULD_NOT_BE_NULL = "The value should not be null.";
	public static final String THE_VALUE_SHOULD_BE_NULL = "The value should be null.";
	public static final String THE_VALUE_SHOULD_BE_TRUE = "The value should be true.";
	public static final String THE_VALUE_SHOULD_BE_FALSE = "The value should be false.";
	public static final String ASSERT_LISTENER_MAY_ONLY_BE_SET_BY = 
				"The assert listener may only be set by a instance of org.adligo.jtests.run.JTestsRunner or org.adligo.jtests.run.client.JTestsGwtRunner.";
	private I_AssertListener listener;

	/**
	 * TODO
	 * note I would like to hide this method somehow....
	 * @param p
	 */
	public void setListener(I_AssertListener p) {
		listener = p;
	}
	
	protected synchronized void evaluate(I_AssertCommand cmd) {
		if (cmd.evaluate()) {
			listener.assertCompleted(cmd);
		} else {
			TestFailureMutant fm = new TestFailureMutant();
			fm.setMessage(cmd.getFailureMessage());
			fm.setLocationFailed(new AssertionFailureLocation());
			fm.setExpected(cmd.getExpected());
			fm.setActual(cmd.getActual());
			listener.assertFailed(new TestFailure(fm));
		}
	}
	
	@Override
	public void assertTrue(boolean p) {
		assertTrue(THE_VALUE_SHOULD_BE_TRUE, p);
	}

	@Override
	public void assertTrue(String message, boolean p) {
		if (IsEmpty.isEmpty(message)) {
			evaluate(new BooleanAssertCommand(AssertTrue, THE_VALUE_SHOULD_BE_TRUE, p));
		} else {
			evaluate(new BooleanAssertCommand(AssertTrue, message, p));
		}
	}

	@Override
	public void assertFalse(boolean p) {
		assertFalse(THE_VALUE_SHOULD_BE_FALSE, p);
	}

	@Override
	public void assertFalse(String message, boolean p) {
		if (IsEmpty.isEmpty(message)) {
			evaluate(new BooleanAssertCommand(AssertFalse, THE_VALUE_SHOULD_BE_FALSE, p));
		} else {
			evaluate(new BooleanAssertCommand(AssertFalse, message, p));
		}
	}

	@Override
	public void assertNull(Object p) {
		assertNull(THE_VALUE_SHOULD_BE_NULL, p);
	}

	@Override
	public void assertNull(String message, Object p) {
		if (IsEmpty.isEmpty(message)) {
			evaluate(new BooleanAssertCommand(AssertNull, THE_VALUE_SHOULD_BE_NULL, p));
		} else {
			evaluate(new BooleanAssertCommand(AssertNull, message, p));
		}
	}

	@Override
	public void assertNotNull(Object p) {
		assertNotNull(THE_VALUE_SHOULD_NOT_BE_NULL, p);
	}

	@Override
	public void assertNotNull(String message, Object p) {
		if (IsEmpty.isEmpty(message)) {
			evaluate(new BooleanAssertCommand(AssertNotNull, THE_VALUE_SHOULD_NOT_BE_NULL, p));
		} else {
			evaluate(new BooleanAssertCommand(AssertNotNull, message, p));
		}
	}


	@Override
	public void beforeExhibits() {
		//do nothing, allow overrides
	}

	@Override
	public void afterExhibits() {
		//do nothing, allow overrides
	}
	
	
}
