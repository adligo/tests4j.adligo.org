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
public class ClassMethods {

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
		if (StringMethods.isEmpty(clazzName)) {
			return "";
		}
		if (clazzName.indexOf("[") == 0) {
			// [Lorg/example;
			clazzName = clazzName.substring(2, clazzName.length() -1);
		} else if (clazzName.indexOf("L") == 0) {
			// Lorg/example;
			clazzName = clazzName.substring(1, clazzName.length() -1);
		}
		return clazzName.replace('/', '.');
	}
}
