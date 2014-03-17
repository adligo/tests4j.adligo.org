package org.adligo.jtests.models.shared;

import static org.adligo.jtests.models.shared.asserts.AssertType.AssertEquals;
import static org.adligo.jtests.models.shared.asserts.AssertType.AssertFalse;
import static org.adligo.jtests.models.shared.asserts.AssertType.AssertNotEquals;
import static org.adligo.jtests.models.shared.asserts.AssertType.AssertNotNull;
import static org.adligo.jtests.models.shared.asserts.AssertType.AssertNotUniform;
import static org.adligo.jtests.models.shared.asserts.AssertType.AssertNull;
import static org.adligo.jtests.models.shared.asserts.AssertType.AssertSame;
import static org.adligo.jtests.models.shared.asserts.AssertType.AssertThrown;
import static org.adligo.jtests.models.shared.asserts.AssertType.AssertTrue;
import static org.adligo.jtests.models.shared.asserts.AssertType.AssertUniform;

import org.adligo.jtests.models.shared.asserts.AssertType;
import org.adligo.jtests.models.shared.asserts.AssertionFailureLocation;
import org.adligo.jtests.models.shared.asserts.BooleanAssertCommand;
import org.adligo.jtests.models.shared.asserts.ByteAssertCommand;
import org.adligo.jtests.models.shared.asserts.CompareAssertionData;
import org.adligo.jtests.models.shared.asserts.I_Asserts;
import org.adligo.jtests.models.shared.asserts.I_BasicAssertCommand;
import org.adligo.jtests.models.shared.asserts.I_Thrower;
import org.adligo.jtests.models.shared.asserts.I_ThrownAssertCommand;
import org.adligo.jtests.models.shared.asserts.IdenticalAssertCommand;
import org.adligo.jtests.models.shared.asserts.StringUniformAssertCommand;
import org.adligo.jtests.models.shared.asserts.ThrowableAssertCommand;
import org.adligo.jtests.models.shared.asserts.ThrowableUniformAssertCommand;
import org.adligo.jtests.models.shared.asserts.ThrownAssertCommand;
import org.adligo.jtests.models.shared.asserts.ThrownAssertionData;
import org.adligo.jtests.models.shared.common.IsEmpty;
import org.adligo.jtests.models.shared.results.TestFailure;
import org.adligo.jtests.models.shared.results.TestFailureMutant;
import org.adligo.jtests.models.shared.system.I_AssertListener;

public abstract class AbstractTest implements I_AbstractTest, I_Asserts {
	public static final String THE_FIRST_BYTE_SHOULD_NOT_BE_LESS_THAN_THE_SECOND_BYTE = "The first Byte should NOT be less than the second Byte.";
	public static final String NOT_GREATER_THAN_BYTE = "The first byte should NOT be greater than the last byte";
	public static final String THE_EXPECTED_BYTE_SHOULD_BE_LESS_THAN_THE_ACTUAL_BYTE = "The first Byte should be less than the second byte";
	private static final String GREATER_THAN_BYTE = "The first Byte should be greater than the second Byte.";
	public static final String THE_TWO_OBJECTS_SHOULD_NOT_BE_UNIFORM = "The two objects should not be uniform.";
	public static final String THE_TWO_OBJECTS_SHOULD_BE_UNIFORM = "The two objects should be uniform.";
	public static final String A_INSTANCE_OF_THE_THROWABLE_CLASS_SHOULD_HAVE_BEEN_THROWN = "A instance of the Throwable Class should have been thrown.";
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

	/**
	 * TODO
	 * note I would like to hide this method somehow....
	 * @param p
	 */
	public void setListener(I_AssertListener p) {
		listener = p;
	}
	
	/**
	 * @param cmd
	 */
	public synchronized void evaluate(I_BasicAssertCommand cmd) {
		if (cmd.evaluate()) {
			listener.assertCompleted(cmd);
		} else {
			TestFailureMutant fm = new TestFailureMutant();
			fm.setMessage(cmd.getFailureMessage());
			fm.setLocationFailed(new AssertionFailureLocation());
			fm.setData(cmd);
			listener.assertFailed(new TestFailure(fm));
		}
	}
	
	public synchronized void evaluate(I_ThrownAssertCommand cmd, I_Thrower p) {
		if (cmd.evaluate(p)) {
			listener.assertCompleted(cmd);
		} else {
			TestFailureMutant fm = new TestFailureMutant();
			fm.setMessage(cmd.getFailureMessage());
			fm.setLocationFailed(new AssertionFailureLocation());
			fm.setData(cmd);
			listener.assertFailed(new TestFailure(fm));
		}
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
	public void assertEquals(Byte p, Byte a) {
		assertEquals(THESE_OBJECT_SHOULD_EQUALS, p, a);
	}

	@Override
	public void assertEquals(String message, Byte p, Byte a) {
		evaluate(new ByteAssertCommand(
				AssertEquals, THESE_OBJECT_SHOULD_EQUALS, 
				new CompareAssertionData<Byte>(p, a)));
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
	public void assertGreaterThan(Byte p, Byte a) {
		assertGreaterThan(GREATER_THAN_BYTE, p, a);
	}

	@Override
	public void assertGreaterThan(String message, Byte p, Byte a) {
		evaluate(new ByteAssertCommand(
				AssertType.AssertGreaterThan, message, 
				new CompareAssertionData<Byte>(p, a)));
	}
	
	@Override
	public void assertLessThan(Byte p, Byte a) {
		assertLessThan(THE_EXPECTED_BYTE_SHOULD_BE_LESS_THAN_THE_ACTUAL_BYTE, p, a);
	}

	@Override
	public void assertLessThan(String message, Byte p, Byte a) {
		evaluate(new ByteAssertCommand(
				AssertType.AssertLessThan, message, 
				new CompareAssertionData<Byte>(p, a)));
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
	public void assertNotGreaterThan(Byte p, Byte a) {
		assertNotGreaterThan(NOT_GREATER_THAN_BYTE, p, a);
	}

	@Override
	public void assertNotGreaterThan(String message, Byte p, Byte a) {
		evaluate(new ByteAssertCommand(
				AssertType.AssertNotGreaterThan, message, 
				new CompareAssertionData<Byte>(p, a)));
	}
	
	@Override
	public void assertNotLessThan(Byte p, Byte a) {
		assertNotLessThan(THE_FIRST_BYTE_SHOULD_NOT_BE_LESS_THAN_THE_SECOND_BYTE, p, a);
	}

	@Override
	public void assertNotLessThan(String message, Byte p, Byte a) {
		evaluate(new ByteAssertCommand(
				AssertType.AssertNotLessThan, message, 
				new CompareAssertionData<Byte>(p, a)));
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
	public void assertNotEquals(Byte p, Byte a) {
		assertNotEquals(THE_TWO_OBJECTS_SHOULD_NOT_BE_EQUAL,p, a);
	}

	@Override
	public void assertNotEquals(String message, Byte p, Byte a) {
		evaluate(new ByteAssertCommand(
				AssertNotEquals, message, 
				new CompareAssertionData<Byte>(p, a)));
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
	public void assertThrown(ThrownAssertionData pData, I_Thrower pThrower) {
		assertThrown(A_INSTANCE_OF_THE_THROWABLE_CLASS_SHOULD_HAVE_BEEN_THROWN, pData, pThrower);
	}

	@Override
	public void assertThrown(String pMessage, ThrownAssertionData pData, I_Thrower pThrower) {
		evaluate(new ThrownAssertCommand(
				AssertThrown, pMessage, pData), pThrower);
	}
	
	@Override
	public void assertThrownUniform(ThrownAssertionData pData, I_Thrower pThrower) {
		assertThrownUniform(A_INSTANCE_OF_THE_THROWABLE_CLASS_SHOULD_HAVE_BEEN_THROWN, pData, pThrower);
	}

	@Override
	public void assertThrownUniform(String pMessage, ThrownAssertionData pData, I_Thrower pThrower) {
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
}
