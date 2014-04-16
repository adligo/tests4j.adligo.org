package org.adligo.jtests.models.shared.coverage;

public interface I_InnerClassSourceCoverage extends I_HasInnerClasses {
	/**
	 * this would return the name of the Class
	 * for a inner class, or null for an anonymous inner class
	 * @return
	 */
	public String getClassName();
	public I_FileCoordinate getStart();
	public I_FileCoordinate getEnd();
	public I_ClassCoverage getClassCoverage();
}
