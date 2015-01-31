package org.adligo.tests4j.models.shared.association;

public interface I_PackageConstantLookup {
	public String getPackageName();
	/**
	 * 
	 * @param javaName the name of the java class Class.class.getName()
	 * @return The name of the CONSTANT (public static final String) 
	 * which returns the String value of the java name, in the 
	 * constant class (i.e. JSE_Lang).
	 */
	public String getConstantName(String javaName);
}
