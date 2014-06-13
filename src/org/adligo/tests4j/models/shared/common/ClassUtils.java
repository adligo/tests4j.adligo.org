package org.adligo.tests4j.models.shared.common;

/**
 * A class for reflection like code that runs the 
 * same in GWT compiled java-script as the JSE.
 * 
 * @author scott
 *
 */
public class ClassUtils {

	/**
	 * This determines if the classToCheck is a sub type
	 * meaning class extension hierarchy only.
	 * This allows for GWT compiled java-script
	 * so that it runs the same on JSE, so that
	 * the tests4j framework can run in java-script.
	 * 
	 * @param classToCheck
	 * @param parentClass
	 * @return
	 */
	public static boolean isSubType(Class<?> classToCheck, Class<?> parentClass) {
		if (classToCheck.getName().equals(parentClass.getName())) {
			return true;
		} else {
			Class<?> clazz = classToCheck.getSuperclass();
			if (Object.class.getName().equals(clazz.getName())) {
				return false;
			} else {
				return isSubType(clazz, parentClass);
			}
		}
	}
}
