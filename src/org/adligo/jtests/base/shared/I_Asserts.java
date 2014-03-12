package org.adligo.jtests.base.shared;

public interface I_Asserts {
	public void assertTrue(boolean p);
	public void assertTrue(String message, boolean p);
	public void assertFalse(boolean p);
	public void assertFalse(String message, boolean p);	
	public void assertNull(Object p);
	public void assertNull(String message, Object p);
	public void assertNotNull(Object p);
	public void assertNotNull(String message, Object p);	
}
