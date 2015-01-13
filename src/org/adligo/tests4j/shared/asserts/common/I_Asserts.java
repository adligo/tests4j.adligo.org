package org.adligo.tests4j.shared.asserts.common;

import java.util.Collection;

import org.adligo.tests4j.shared.asserts.uniform.StringUniformEvaluator;
import org.adligo.tests4j.shared.common.I_PlatformContainer;

public interface I_Asserts extends I_PlatformContainer {
	public void assertContains(Collection<?> p, Object a);
	public void assertContains(String message, Collection<?> p, Object a);	
	
	public void assertEquals(Object p, Object a);
	public void assertEquals(String message, Object p, Object a);	
	public void assertEquals(String p, String a);
	public void assertEquals(String message, String p, String a);	
	
	public void assertFalse(boolean p);
	public void assertFalse(String message, boolean p);	
	
	public void assertGreaterThanOrEquals(double p, double a);
	public void assertGreaterThanOrEquals(String message, double p, double a);	
	public void assertGreaterThanOrEquals(double p, float a);
	public void assertGreaterThanOrEquals(String message, double p, float a);	
	public void assertGreaterThanOrEquals(double p, int a);
	public void assertGreaterThanOrEquals(String message, double p, int a);	
	public void assertGreaterThanOrEquals(double p, long a);
	public void assertGreaterThanOrEquals(String message, double p, long a);	
	public void assertGreaterThanOrEquals(double p, short a);
	public void assertGreaterThanOrEquals(String message, double p, short a);	
	
	public void assertNull(Object p);
	public void assertNull(String message, Object p);
	public void assertNotNull(Object p);
	public void assertNotNull(String message, Object p);	


	public void assertNotEquals(Object p, Object a);
	public void assertNotEquals(String message, Object p, Object a);
	public void assertNotEquals(String p, String a);
	public void assertNotEquals(String message, String p, String a);
	
	public void assertNotSame(Object p, Object a);
	public void assertNotSame(String message, Object p, Object a);	

	public void assertNotUniform(Object p, Object a);
	public void assertNotUniform(String message, Object p, Object a);	
	
	public void assertSame(Object p, Object a);
	public void assertSame(String message, Object p, Object a);	
	
	public void assertTrue(boolean p);
	public void assertTrue(String message, boolean p);

	
	public void assertThrown(I_ExpectedThrowable p, I_Thrower thrower);
	public void assertThrown(String message, I_ExpectedThrowable p, I_Thrower thrower);	
	
	public void assertThrownUniform(I_ExpectedThrowable p, I_Thrower thrower);
	public void assertThrownUniform(String message, I_ExpectedThrowable p, I_Thrower thrower);	
	
	/**
	 * This method is may be backed by a custom implementation.
	 * The default for String's is {@link StringUniformEvaluator#isUniform(I_CompareAssertionData)}
	 * which compares the text of two strings, normalizing new lines
	 * (dos and unix line seperators will match).
	 * @param p
	 * @param a
	 */
	public void assertUniform(Object p, Object a);
	/**
	 * This method is may be backed by a custom implementation.
   * The default for String's is {@link ThrownUniformEvaluator#isUniform(I_CompareAssertionData)}
   * which compares the text of the Throwable messages, normalizing new lines 
   * (dos and unix line seperators will match).
	 * @param message
	 * @param p
	 * @param a
	 */
	public void assertUniform(String message, Object p, Object a);	

	
}
