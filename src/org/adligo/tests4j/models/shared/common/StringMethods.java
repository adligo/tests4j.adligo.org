package org.adligo.tests4j.models.shared.common;

public class StringMethods {
	public static boolean isEmpty(String p) {
		if (p == null) {
			return true;
		}
		if (p.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	public static void isEmpty(String p, String message) {
		if (isEmpty(p)) {
			throw new IllegalArgumentException(message);
		}
	}
}