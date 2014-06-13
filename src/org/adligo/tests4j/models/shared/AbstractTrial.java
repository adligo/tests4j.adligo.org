package org.adligo.tests4j.models.shared;

import static org.adligo.tests4j.models.shared.asserts.AssertType.AssertEquals;
import static org.adligo.tests4j.models.shared.asserts.AssertType.AssertFalse;
import static org.adligo.tests4j.models.shared.asserts.AssertType.AssertGreaterThanOrEquals;
import static org.adligo.tests4j.models.shared.asserts.AssertType.AssertNotEquals;
import static org.adligo.tests4j.models.shared.asserts.AssertType.AssertNotNull;
import static org.adligo.tests4j.models.shared.asserts.AssertType.AssertNotUniform;
import static org.adligo.tests4j.models.shared.asserts.AssertType.AssertNull;
import static org.adligo.tests4j.models.shared.asserts.AssertType.AssertSame;
import static org.adligo.tests4j.models.shared.asserts.AssertType.AssertThrown;
import static org.adligo.tests4j.models.shared.asserts.AssertType.AssertTrue;
import static org.adligo.tests4j.models.shared.asserts.AssertType.AssertUniform;

import java.util.Collection;

import org.adligo.tests4j.models.shared.asserts.AssertionProcessor;
import org.adligo.tests4j.models.shared.asserts.BooleanAssertCommand;
import org.adligo.tests4j.models.shared.asserts.CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.ContainsAssertCommand;
import org.adligo.tests4j.models.shared.asserts.DoubleAssertCommand;
import org.adligo.tests4j.models.shared.asserts.I_BasicAssertCommand;
import org.adligo.tests4j.models.shared.asserts.I_ExpectedThrownData;
import org.adligo.tests4j.models.shared.asserts.I_Thrower;
import org.adligo.tests4j.models.shared.asserts.I_ThrownAssertCommand;
import org.adligo.tests4j.models.shared.asserts.IdenticalAssertCommand;
import org.adligo.tests4j.models.shared.asserts.StringUniformAssertCommand;
import org.adligo.tests4j.models.shared.asserts.ThrowableAssertCommand;
import org.adligo.tests4j.models.shared.asserts.ThrowableUniformAssertCommand;
import org.adligo.tests4j.models.shared.asserts.ThrownAssertCommand;
import org.adligo.tests4j.models.shared.common.IsEmpty;
import org.adligo.tests4j.models.shared.system.I_AssertListener;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.i18n.trials.asserts.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.system.i18n.trials.asserts.I_Tests4J_AssertionResultMessages;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;

/**
 * TODO i18n the assert messages (currently constants).
 * @author scott
 *
 */
public abstract class AbstractTrial implements I_AbstractTrial, I_Trial {
	private static final I_Tests4J_AssertionResultMessages MESSAGES = 
			Tests4J_Constants.CONSTANTS.getAssertionResultMessages();
	
	public static final String THE_COLLECTION_SHOULD_CONTAIN_THE_VALUE = "The collection should contain the value";
	public static final String THE_ACTUAL_SHOULD_BE_GREATER_THAN_OR_EQUAL_TO_THE_EXPECTED_VALUE = "The actual value should be greater than or equal to the expected value.";
	public static final String THE_FIRST_BYTE_SHOULD_NOT_BE_LESS_THAN_THE_SECOND_BYTE = "The first Byte should NOT be less than the second Byte.";
	public static final String NOT_GREATER_THAN_BYTE = "The first byte should NOT be greater than the last byte";
	public static final String THE_EXPECTED_BYTE_SHOULD_BE_LESS_THAN_THE_ACTUAL_BYTE = "The first Byte should be less than the second byte";
	private static final String GREATER_THAN_BYTE = "The first Byte should be greater than the second Byte.";
	public static final String THE_TWO_OBJECTS_SHOULD_NOT_BE_UNIFORM = "The two objects should not be uniform.";
	public static final String THE_TWO_OBJECTS_SHOULD_BE_UNIFORM = "The two objects should be uniform.";
	public static final String THE_TWO_OBJECTS_SHOULD_NOT_BE_THE_SAME = "The Two Objects should Not be the same.";
	public static final String THE_TWO_OBJECTS_SHOULD_NOT_BE_EQUAL = "The two objects should Not be Equal.";
	public static final String THE_TWO_OBJECTS_SHOULD_BE_THE_SAME = "The two objects should be the same.";
	public static final String THESE_OBJECT_SHOULD_EQUALS = "These object should equals.";
	public static final String THE_VALUE_SHOULD_NOT_BE_NULL = "The value should not be null.";
	public static final String THE_VALUE_SHOULD_BE_NULL = "The value should be null.";
	public static final String THE_VALUE_SHOULD_BE_TRUE = "The value should be true.";
	public static final String THE_VALUE_SHOULD_BE_FALSE = "The value should be false.";
	public static final String ASSERT_LISTENER_MAY_ONLY_BE_SET_BY = 
				"The assert listener may only be set by a instance of org.adligo.jtests.run.JTestsRunner or org.adligo.jtests.run.client.JTestsGwtRunner.";
	private I_AssertListener listener;
	private I_Tests4J_Reporter reporter;
	/**
	 * Set the memory of the AbstractTrial
	 * @param p
	 */
	@Override
	public void setBindings(I_TrialProcessorBindings bindings) {
		//throw npe for nulls
		bindings.hashCode();
		listener = bindings.getAssertionListener();
		//throw npe for nulls
		listener.hashCode();
		
		reporter = bindings.getReporter();
		//throw npe for nulls
		reporter.hashCode();
	}
	
	/**
	 * @param cmd
	 */
	public void evaluate(I_BasicAssertCommand cmd) {
		AssertionProcessor.evaluate(listener, cmd);
	}
	
	public void evaluate(I_ThrownAssertCommand cmd, I_Thrower p) {
		AssertionProcessor.evaluate(listener, cmd, p);
	}
	
	@Override
	public void assertEquals(Object p, Object a) {
		assertEquals(THESE_OBJECT_SHOULD_EQUALS, p, a);
	}

	@Override
	public void assertEquals(String message, Object p, Object a) {
		evaluate(new IdenticalAssertCommand(
				AssertEquals, THESE_OBJECT_SHOULD_EQUALS, 
				new CompareAssertionData<Object>(p, a)));
	}
	
	
	@Override
	public void assertEquals(Throwable p, Throwable a) {
		assertEquals(THESE_OBJECT_SHOULD_EQUALS, p, a);
	}

	@Override
	public void assertEquals(String message, Throwable p, Throwable a) {
		evaluate(new ThrowableAssertCommand(
				AssertEquals, THESE_OBJECT_SHOULD_EQUALS, 
				new CompareAssertionData<Throwable>(p, a)));
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


	public void beforeTests() {
		//do nothing, allow overrides
	}

	public void afterTests() {
		//do nothing, allow overrides
	}

	
	@Override
	public void assertNotEquals(Object p, Object a) {
		assertNotEquals(THE_TWO_OBJECTS_SHOULD_NOT_BE_EQUAL,p, a);
	}

	@Override
	public void assertNotEquals(String message, Object p, Object a) {
		evaluate(new IdenticalAssertCommand(
				AssertNotEquals, message, 
				new CompareAssertionData<Object>(p, a)));
	}
	
	@Override
	public void assertSame(Object p, Object a) {
		assertSame(THE_TWO_OBJECTS_SHOULD_BE_THE_SAME, p,  a);
	}

	@Override
	public void assertSame(String message, Object p, Object a) {
		evaluate(new IdenticalAssertCommand(
				AssertSame, message, 
				new CompareAssertionData<Object>(p, a)));
	}

	@Override
	public void assertNotUniform(String p, String a) {
		assertNotUniform(THE_TWO_OBJECTS_SHOULD_NOT_BE_UNIFORM, p, a);
	}
	
	@Override
	public void assertNotUniform(String message, String p, String a) {
		evaluate(new StringUniformAssertCommand(
				AssertNotUniform, message, 
				new CompareAssertionData<String>(p, a)));
	}
	
	@Override
	public void assertNotUniform(Throwable p, Throwable a) {
		assertNotUniform(THE_TWO_OBJECTS_SHOULD_NOT_BE_UNIFORM, p, a);
	}
	
	@Override
	public void assertNotUniform(String message, Throwable p, Throwable a) {
		evaluate(new ThrowableUniformAssertCommand(
				AssertNotUniform, message, 
				new CompareAssertionData<Throwable>(p, a)));
	}
				
	@Override
	public void assertNotSame(Object p, Object a) {
		assertSame(THE_TWO_OBJECTS_SHOULD_NOT_BE_THE_SAME, p,  a);
	}

	@Override
	public void assertNotSame(String message, Object p, Object a) {
		evaluate(new IdenticalAssertCommand(
				AssertSame, message, 
				new CompareAssertionData<Object>(p, a)));
	}
	
	@Override
	public void assertThrown(I_ExpectedThrownData pData, I_Thrower pThrower) {
		assertThrown(MESSAGES.getTheExpectedThrowableDataDidNotMatchTheActual(), pData, pThrower);
	}

	@Override
	public void assertThrown(String pMessage, I_ExpectedThrownData pData, I_Thrower pThrower) {
		evaluate(new ThrownAssertCommand(
				AssertThrown, pMessage, pData), pThrower);
	}
	
	@Override
	public void assertThrownUniform(I_ExpectedThrownData pData, I_Thrower pThrower) {
		assertThrownUniform(MESSAGES.getTheExpectedThrowableDataDidNotMatchTheActual(), pData, pThrower);
	}

	@Override
	public void assertThrownUniform(String pMessage, I_ExpectedThrownData pData, I_Thrower pThrower) {
		evaluate(new ThrownAssertCommand(
				AssertThrown, pMessage, pData), pThrower);
	}
	
	public void assertUniform(String p, String a) {
		assertUniform(THE_TWO_OBJECTS_SHOULD_BE_UNIFORM, p, a);
	}
	public void assertUniform(String message, String p, String a) {
		evaluate(new StringUniformAssertCommand(
				AssertUniform, message, 
				new CompareAssertionData<String>(p, a)));
	}
	
	public void assertUniform(Throwable p, Throwable a) {
		assertUniform(THE_TWO_OBJECTS_SHOULD_BE_UNIFORM, p, a);
	}
	public void assertUniform(String message, Throwable p, Throwable a) {
		evaluate(new ThrowableUniformAssertCommand(
				AssertUniform, message, 
				new CompareAssertionData<Throwable>(p, a)));
	}

	@Override
	public void assertGreaterThanOrEquals(double expected, double actual) {
		evaluate(new DoubleAssertCommand(
				AssertGreaterThanOrEquals, THE_ACTUAL_SHOULD_BE_GREATER_THAN_OR_EQUAL_TO_THE_EXPECTED_VALUE, 
				new CompareAssertionData<Double>(expected, actual)));
	}

	@Override
	public void assertGreaterThanOrEquals(String message, double expected, double actual) {
		evaluate(new DoubleAssertCommand(
				AssertGreaterThanOrEquals, message, 
				new CompareAssertionData<Double>(expected, actual)));
	}

	@Override
	public void assertContains(Collection<?> p, Object a) {
		evaluate(new ContainsAssertCommand(
				THE_COLLECTION_SHOULD_CONTAIN_THE_VALUE, p, a));
	}

	@Override
	public void assertContains(String message, Collection<?> p, Object a) {
		evaluate(new ContainsAssertCommand(
				message, p, a));
	}
	
	public void log(String p) {
		reporter.log(p);
	}

	@Override
	public void log(Throwable p) {
		reporter.onError(p);
	}

	@Override
	public boolean isLogEnabled(Class<?> c) {
		return reporter.isLogEnabled(c);
	}

	@Override
	public void assertGreaterThanOrEquals(double p, float a) {
		assertGreaterThanOrEquals(p, 0.0 + a);
	}

	@Override
	public void assertGreaterThanOrEquals(String message, double p, float a) {
		assertGreaterThanOrEquals(message, p, 0.0 + a);
	}

	@Override
	public void assertGreaterThanOrEquals(double p, int a) {
		assertGreaterThanOrEquals(p, 0.0 + a);
	}

	@Override
	public void assertGreaterThanOrEquals(String message, double p, int a) {
		assertGreaterThanOrEquals(message, p, 0.0 + a);
	}

	@Override
	public void assertGreaterThanOrEquals(double p, long a) {
		assertGreaterThanOrEquals(p, 0.0 + a);
	}

	@Override
	public void assertGreaterThanOrEquals(String message, double p, long a) {
		assertGreaterThanOrEquals(message, p, 0.0 + a);
	}

	@Override
	public void assertGreaterThanOrEquals(double p, short a) {
		assertGreaterThanOrEquals(p, 0.0 + a);
	}

	@Override
	public void assertGreaterThanOrEquals(String message, double p, short a) {
		assertGreaterThanOrEquals(message, p, 0.0 + a);
	}
}