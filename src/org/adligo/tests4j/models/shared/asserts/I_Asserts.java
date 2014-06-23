package org.adligo.tests4j.models.shared.asserts;

import java.util.Collection;

import org.adligo.tests4j.models.shared.common.I_Platform;

public interface I_Asserts extends I_Platform {
	public void assertContains(Collection<?> p, Object a);
	public void assertContains(String message, Collection<?> p, Object a);	
	
	public void assertEquals(Object p, Object a);
	public void assertEquals(String message, Object p, Object a);	
	public void assertEquals(Throwable p, Throwable a);
	public void assertEquals(String message, Throwable p, Throwable a);	

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
	
	public void assertNotSame(Object p, Object a);
	public void assertNotSame(String message, Object p, Object a);	

	public void assertNotUniform(String p, String a);
	public void assertNotUniform(String message, String p, String a);	
	public void assertNotUniform(Throwable p, Throwable a);
	public void assertNotUniform(String message, Throwable p, Throwable a);	
	
	public void assertSame(Object p, Object a);
	public void assertSame(String message, Object p, Object a);	
	
	public void assertTrue(boolean p);
	public void assertTrue(String message, boolean p);

	
	public void assertThrown(I_ExpectedThrownData p, I_Thrower thrower);
	public void assertThrown(String message, I_ExpectedThrownData p, I_Thrower thrower);	
	public void assertThrownUniform(I_ExpectedThrownData p, I_Thrower thrower);
	public void assertThrownUniform(String message, I_ExpectedThrownData p, I_Thrower thrower);	
	
	public void assertUniform(String p, String a);
	public void assertUniform(String message, String p, String a);	
	public void assertUniform(Throwable p, Throwable a);
	public void assertUniform(String message, Throwable p, Throwable a);	

	
}
