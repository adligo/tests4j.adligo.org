package org.adligo.jtests.models.shared;

import static org.adligo.jtests.models.shared.asserts.AssertType.AssertEquals;
import static org.adligo.jtests.models.shared.asserts.AssertType.AssertFalse;
import static org.adligo.jtests.models.shared.asserts.AssertType.AssertNotEquals;
import static org.adligo.jtests.models.shared.asserts.AssertType.AssertNotNull;
import static org.adligo.jtests.models.shared.asserts.AssertType.AssertNotSame;
import static org.adligo.jtests.models.shared.asserts.AssertType.AssertNull;
import static org.adligo.jtests.models.shared.asserts.AssertType.AssertSame;
import static org.adligo.jtests.models.shared.asserts.AssertType.AssertTrue;

import org.adligo.jtests.models.shared.asserts.AssertType;
import org.adligo.jtests.models.shared.asserts.AssertionFailureLocation;
import org.adligo.jtests.models.shared.asserts.BooleanAssertCommand;
import org.adligo.jtests.models.shared.asserts.CompareAssertionData;
import org.adligo.jtests.models.shared.asserts.IdenticalAssertCommand;
import org.adligo.jtests.models.shared.asserts.IdenticalAssertCommand;
import org.adligo.jtests.models.shared.asserts.I_AssertCommand;
import org.adligo.jtests.models.shared.asserts.I_Asserts;
import org.adligo.jtests.models.shared.common.IsEmpty;
import org.adligo.jtests.models.shared.results.TestFailure;
import org.adligo.jtests.models.shared.results.TestFailureMutant;
import org.adligo.jtests.models.shared.run.I_AssertListener;

public abstract class AbstractTest implements I_AbstractTest, I_Asserts {
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
	public synchronized void evaluate(I_AssertCommand cmd) {
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

	@Override
	public void assertEquals(Object p, Object a) {
		assertEquals(THESE_OBJECT_SHOULD_EQUALS, p, a);
	}

	@Override
	public void assertEquals(String message, Object p, Object a) {
		evaluate(new IdenticalAssertCommand(
				AssertEquals, THESE_OBJECT_SHOULD_EQUALS, 
				new CompareAssertionData(p, a)));
	}


	@Override
	public void assertNotEquals(Object p, Object a) {
		assertNotEquals(THE_TWO_OBJECTS_SHOULD_NOT_BE_EQUAL,p, a);
	}

	@Override
	public void assertNotEquals(String message, Object p, Object a) {
		evaluate(new IdenticalAssertCommand(
				AssertNotEquals, message, 
				new CompareAssertionData(p, a)));
	}

	@Override
	public void assertSame(Object p, Object a) {
		assertSame(THE_TWO_OBJECTS_SHOULD_BE_THE_SAME, p,  a);
	}

	@Override
	public void assertSame(String message, Object p, Object a) {
		evaluate(new IdenticalAssertCommand(
				AssertSame, message, 
				new CompareAssertionData(p, a)));
	}

	@Override
	public void assertNotSame(Object p, Object a) {
		assertSame(THE_TWO_OBJECTS_SHOULD_NOT_BE_THE_SAME, p,  a);
	}

	@Override
	public void assertNotSame(String message, Object p, Object a) {
		evaluate(new IdenticalAssertCommand(
				AssertSame, message, 
				new CompareAssertionData(p, a)));
	}
	
	
}
