package org.adligo.tests4j.system.shared.trials;

import java.util.Collection;

import org.adligo.tests4j.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.common.I_Platform;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

/**
 * this is a simple class for extracting 
 * tests from large trials, to use extend this class
 * and pass in your trial.
 * 
 * This can help with the LargeClass anti-pattern
 * generally we try to keep them down to 1000 lines.
 * 
 * @author scott
 *
 */
@TrialRecursion
public class TrialDelegate implements I_Trial {
	private I_Trial delegate;
	
	public TrialDelegate(I_Trial p) {
		delegate = p;
	}

	public void assertContains(Collection<?> p, Object a) {
		delegate.assertContains(p, a);
	}

	public I_Platform getPlatform() {
		return delegate.getPlatform();
	}

	public void assertContains(String message, Collection<?> p, Object a) {
		delegate.assertContains(message, p, a);
	}

	public void assertEquals(Object p, Object a) {
		delegate.assertEquals(p, a);
	}

	public void beforeTests() {
		delegate.beforeTests();
	}

	public void assertEquals(String message, Object p, Object a) {
		delegate.assertEquals(message, p, a);
	}

	public void assertEquals(String p, String a) {
		delegate.assertEquals(p, a);
	}

	public void assertEquals(String message, String p, String a) {
		delegate.assertEquals(message, p, a);
	}

	public void afterTests() {
		delegate.afterTests();
	}

	public void assertFalse(boolean p) {
		delegate.assertFalse(p);
	}

	public void assertFalse(String message, boolean p) {
		delegate.assertFalse(message, p);
	}

	public void assertGreaterThanOrEquals(double p, double a) {
		delegate.assertGreaterThanOrEquals(p, a);
	}

	public void setBindings(I_TrialBindings bindings) {
		delegate.setBindings(bindings);
	}

	public void assertGreaterThanOrEquals(String message, double p, double a) {
		delegate.assertGreaterThanOrEquals(message, p, a);
	}

	public void assertGreaterThanOrEquals(double p, float a) {
		delegate.assertGreaterThanOrEquals(p, a);
	}

	public void assertGreaterThanOrEquals(String message, double p, float a) {
		delegate.assertGreaterThanOrEquals(message, p, a);
	}

	public I_Tests4J_Log getLog() {
		return delegate.getLog();
	}

	public void assertGreaterThanOrEquals(double p, int a) {
		delegate.assertGreaterThanOrEquals(p, a);
	}

	public void assertGreaterThanOrEquals(String message, double p, int a) {
		delegate.assertGreaterThanOrEquals(message, p, a);
	}

	public void showWidget(Object o) {
		delegate.showWidget(o);
	}

	public void assertGreaterThanOrEquals(double p, long a) {
		delegate.assertGreaterThanOrEquals(p, a);
	}

	public void assertGreaterThanOrEquals(String message, double p, long a) {
		delegate.assertGreaterThanOrEquals(message, p, a);
	}

	public void assertGreaterThanOrEquals(double p, short a) {
		delegate.assertGreaterThanOrEquals(p, a);
	}

	public void assertGreaterThanOrEquals(String message, double p, short a) {
		delegate.assertGreaterThanOrEquals(message, p, a);
	}

	public void assertNull(Object p) {
		delegate.assertNull(p);
	}

	public void assertNull(String message, Object p) {
		delegate.assertNull(message, p);
	}

	public void assertNotNull(Object p) {
		delegate.assertNotNull(p);
	}

	public void assertNotNull(String message, Object p) {
		delegate.assertNotNull(message, p);
	}

	public void assertNotEquals(Object p, Object a) {
		delegate.assertNotEquals(p, a);
	}

	public void assertNotEquals(String message, Object p, Object a) {
		delegate.assertNotEquals(message, p, a);
	}

	public void assertNotEquals(String p, String a) {
		delegate.assertNotEquals(p, a);
	}

	public void assertNotEquals(String message, String p, String a) {
		delegate.assertNotEquals(message, p, a);
	}

	public void assertNotSame(Object p, Object a) {
		delegate.assertNotSame(p, a);
	}

	public void assertNotSame(String message, Object p, Object a) {
		delegate.assertNotSame(message, p, a);
	}

	public void assertNotUniform(Object p, Object a) {
		delegate.assertNotUniform(p, a);
	}

	public void assertNotUniform(String message, Object p, Object a) {
		delegate.assertNotUniform(message, p, a);
	}

	public void assertSame(Object p, Object a) {
		delegate.assertSame(p, a);
	}

	public void assertSame(String message, Object p, Object a) {
		delegate.assertSame(message, p, a);
	}

	public void assertTrue(boolean p) {
		delegate.assertTrue(p);
	}

	public void assertTrue(String message, boolean p) {
		delegate.assertTrue(message, p);
	}

	public void assertThrown(I_ExpectedThrownData p, I_Thrower thrower) {
		delegate.assertThrown(p, thrower);
	}

	public void assertThrown(String message, I_ExpectedThrownData p,
			I_Thrower thrower) {
		delegate.assertThrown(message, p, thrower);
	}

	public void assertThrownUniform(I_ExpectedThrownData p, I_Thrower thrower) {
		delegate.assertThrownUniform(p, thrower);
	}

	public void assertThrownUniform(String message, I_ExpectedThrownData p,
			I_Thrower thrower) {
		delegate.assertThrownUniform(message, p, thrower);
	}

	public void assertUniform(Object p, Object a) {
		delegate.assertUniform(p, a);
	}

	public void assertUniform(String message, Object p, Object a) {
		delegate.assertUniform(message, p, a);
	}

	@Override
	public double getPctDone(String testName) {
		return delegate.getPctDone(testName);
	}

}
