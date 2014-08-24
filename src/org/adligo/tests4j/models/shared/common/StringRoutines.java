package org.adligo.tests4j.models.shared.common;

/**
 * a collection of thread safe methods
 * which do things that should probably get added to 
 * the actual java String class.
 * 
 * @author scott
 *
 */
public class StringRoutines {
	public static boolean isEmpty(String p) {
		if (p == null) {
			return true;
		}
		if (p.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Throws a IllegalArgumentException
	 * when the parameter p isEmpty(p); 
	 * 
	 * @param p
	 * @param message
	 */
	public static void isEmpty(String p, String message) {
		if (isEmpty(p)) {
			throw new IllegalArgumentException(message);
		}
	}
	

	public static int indexBoundsFix(String text, int index) {
		if (index < 0) {
			return 0;
		}
		if (index > text.length()) {
			return text.length();
		}
		return index;
	}
	

}
