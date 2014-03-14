package org.adligo.jtests.models.shared.asserts;

public interface I_Asserts {
	public void assertTrue(boolean p);
	public void assertTrue(String message, boolean p);
	public void assertFalse(boolean p);
	public void assertFalse(String message, boolean p);	
	public void assertNull(Object p);
	public void assertNull(String message, Object p);
	public void assertNotNull(Object p);
	public void assertNotNull(String message, Object p);	

	public void assertEquals(Object p, Object a);
	public void assertEquals(String message, Object p, Object a);	
	public void assertNotEquals(Object p, Object a);
	public void assertNotEquals(String message, Object p, Object a);
	
	public void assertSame(Object p, Object a);
	public void assertSame(String message, Object p, Object a);	
	public void assertNotSame(Object p, Object a);
	public void assertNotSame(String message, Object p, Object a);	
}
