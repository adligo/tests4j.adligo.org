package org.adligo.jtests.models.shared.common;

public class ClassUtils {

	/**
	 * this is generally for GWT compiled javascript
	 * so that it runs the same on JSE
	 * @param base
	 * @param toCheck
	 * @return
	 */
	public static boolean isSubType(Class<?> base, Class<?> toCheck) {
		if (base.getName().equals(toCheck.getName())) {
			return true;
		} else {
			Class<?> clazz = base.getSuperclass();
			if (Object.class.getName().equals(clazz.getName())) {
				return false;
			} else {
				return isSubType(clazz, toCheck);
			}
		}
	}
}
