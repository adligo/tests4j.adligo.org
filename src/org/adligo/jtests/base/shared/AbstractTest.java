package org.adligo.jtests.base.shared;

import static org.adligo.jtests.base.shared.asserts.AssertCommandType.*;

import org.adligo.jtests.base.shared.asserts.AssertionFailureLocation;
import org.adligo.jtests.base.shared.asserts.BooleanAssertCommand;
import org.adligo.jtests.base.shared.asserts.I_AbstractTest;
import org.adligo.jtests.base.shared.asserts.I_AssertCommand;
import org.adligo.jtests.base.shared.asserts.I_Asserts;
import org.adligo.jtests.models.shared.common.IsEmpty;
import org.adligo.jtests.models.shared.common.TestType;
import org.adligo.jtests.models.shared.results.Failure;
import org.adligo.jtests.models.shared.results.FailureMutant;
import org.adligo.jtests.models.shared.run.I_AssertListener;

public abstract class AbstractTest implements I_AbstractTest, I_Asserts {
	public static final String THE_VALUE_SHOULD_NOT_BE_NULL = "The value should not be null.";
	public static final String THE_VALUE_SHOULD_BE_NULL = "The value should be null.";
	public static final String THE_VALUE_SHOULD_BE_TRUE = "The value should be true.";
	public static final String THE_VALUE_SHOULD_BE_FALSE = "The value should be false.";
	public static final String ASSERT_LISTENER_MAY_ONLY_BE_SET_BY = 
				"The assert listener may only be set by a instance of org.adligo.jtests.run.JTestsRunner or org.adligo.jtests.run.client.JTestsGwtRunner.";
	private static I_AssertListener listener;

	public static void setListener(I_AssertListener p) {
		Exception x = new Exception();
		x.fillInStackTrace();
		StackTraceElement[] elements = x.getStackTrace();
		if (elements != null) {
			if (elements.length >= 2) {
				StackTraceElement callingClass = elements[1];
				if (!"org.adligo.jtests.run.JTestsRunner".equals(
						callingClass.getClassName()) &&
					!"org.adligo.jtests.run.client.JTestsGwtRunner".equals(
							callingClass.getClassName())) {
					throw new IllegalStateException(
							ASSERT_LISTENER_MAY_ONLY_BE_SET_BY);
				}
			}
		}
		listener = p;
	}
	
	private void evaluate(I_AssertCommand cmd) {
		if (cmd.evaluate()) {
			listener.assertCompleted(cmd);
		} else {
			FailureMutant fm = new FailureMutant();
			fm.setMessage(cmd.getFailureMessage());
			fm.setLocationFailed(new AssertionFailureLocation());
			fm.setExpected(cmd.getExpected());
			fm.setActual(cmd.getActual());
			listener.assertFailed(new Failure(fm));
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
	public abstract TestType getType();

	@Override
	public void beforeExhibits() {
		//do nothing, allow overrides
	}

	@Override
	public void afterExhibits() {
		//do nothing, allow overrides
	}
	
	
}
