package org.adligo.jtests.base.shared;

import static org.adligo.jtests.base.shared.AssertCommandType.*;

import org.adligo.jtests.base.shared.common.IsEmpty;
import org.adligo.jtests.base.shared.common.TestType;
import org.adligo.jtests.base.shared.results.Failure;
import org.adligo.jtests.base.shared.results.FailureMutant;

public abstract class AbstractTest implements I_AbstractTest, I_Asserts {
	public static final String THE_VALUE_SHOULD_NOT_BE_NULL = "The value should not be null.";
	public static final String THE_VALUE_SHOULD_BE_NULL = "The value should be null.";
	public static final String THE_VALUE_SHOULD_BE_TRUE = "The value should be true.";
	public static final String THE_VALUE_SHOULD_BE_FALSE = "The value should be false.";
	public static final String THE_RUNNER_MAY_ONLY_BE_SET_BY_A_INSTANCE_OF_ORG_ADLIGO_JTESTS_RUN_J_TESTS_RUNNER_OR_ORG_ADLIGO_JTESTS_RUN_CLIENT_J_TESTS_GWT_RUNNER = "The runner may only be set by a instance of org.adligo.jtests.run.JTestsRunner or org.adligo.jtests.run.client.JTestsGwtRunner.";
	private static I_Runner runner;

	public static void setRunner(I_Runner p) {
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
							THE_RUNNER_MAY_ONLY_BE_SET_BY_A_INSTANCE_OF_ORG_ADLIGO_JTESTS_RUN_J_TESTS_RUNNER_OR_ORG_ADLIGO_JTESTS_RUN_CLIENT_J_TESTS_GWT_RUNNER);
				}
			}
		}
		runner = p;
	}
	
	private void evaluate(I_AssertCommand cmd) {
		if (cmd.evaluate()) {
			runner.assertCompleted(cmd);
		} else {
			FailureMutant fm = new FailureMutant();
			fm.setMessage(cmd.getFailureMessage());
			fm.setLocationFailed(new AssertionFailureLocation());
			fm.setExpected(cmd.getExpected());
			fm.setActual(cmd.getActual());
			runner.assertFailed(new Failure(fm));
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
