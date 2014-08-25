package org.adligo.tests4j.models.shared.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A class for reflection like code that runs the 
 * same in GWT compiled java-script as the JSE.
 * Static methods are thread safe.
 * 
 * @author scott
 *
 */
public class ClassRoutines {

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
	
	
	/**
	 * 
	 * @param classes a collection of classes, generics are working against me here.
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> toNames(Collection classes) {
		List<String> toRet = new ArrayList<String>();
		if (classes != null) {
			if (classes.size() >= 1) {
				for (Object obj: classes) {
					if (obj != null) {
						Class<?> clazz = (Class<?>) obj;
						toRet.add(clazz.getName());
					}
				}
			}
		}
		return toRet;
	}
	
	/**
	 * turns 
	 *  org.adligo.tests4j.models.shared.system.I_Tests4J_System
	 *  into
	 *   /org/adligo/tests4j/models/shared/system/I_Tests4J_System.class
	 * @param clazzName
	 * @return
	 */
	public static String toResource(String clazzName) {
		return '/' + clazzName.replace('.', '/') + ".class";
	}
	
	/**
	 * Takes the java byte code type (from a parsed .class file/ ASM)
	 * and turns it into a regular java class name (Class.getName())
	 * Also turns arrays into their component type.
	 * 
	 * ie 
	 *  Lorg/adligo/tests4j/models/shared/system/I_Tests4J_System;
	 *  or
	 *  [Lorg/adligo/tests4j/models/shared/system/I_Tests4J_System;
	 *  turns into
	 *  org.adligo.tests4j.models.shared.system.I_Tests4J_System
	 * @param clazzName
	 * @return
	 */
	public static String fromTypeDescription(String clazzName) {
		if (StringRoutines.isEmpty(clazzName)) {
			throw new IllegalArgumentException();
		}
		if ( isArray(clazzName.charAt(0))) {
			while (isArray(clazzName.charAt(0))) {
				if (clazzName.length() >= 1) {
					clazzName = clazzName.substring(1, clazzName.length());
				} else {
					throw new IllegalArgumentException();
				}
			}
		}
		if (clazzName.length() <= 1) {
			return fromSmallType(clazzName);
		} else if (clazzName.indexOf("L") == 0) {
			clazzName = clazzName.substring(1, clazzName.length() -1);
		}
		return clazzName.replace('/', '.');
	}
	
	public static boolean isPrimitiveClassChar(char c) {
		switch (c) {
			case 'B':
			case 'C':
			case 'D':
			case 'I':
			case 'F':
			case 'J':
			case 'S':
			case 'Z':
				return true;
		}
		return false;
	}
	private static String fromSmallType(String p) {
		switch (p) {
			case "B":
				return Byte.class.getName();
			case "C":
				return Character.class.getName();
			case "D":
				return Double.class.getName();
			case "I":
				return Integer.class.getName();
			case "F":
				return Float.class.getName();
			case "J":
				return Long.class.getName();
			case "S":
				return Short.class.getName();
			case "Z":
				return Boolean.class.getName();
			default:
				throw new IllegalArgumentException("no known type for '" + p +"'");
		}
	}
	
	public static boolean isClass(char p) {
		if (p == 'L') {
			return true;
		}
		return false;
		
	}
	
	public static boolean isArray(char p) {
		if (p == '[') {
			return true;
		}
		return false;
		
	}
}
