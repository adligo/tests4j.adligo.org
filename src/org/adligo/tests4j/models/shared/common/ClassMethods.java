package org.adligo.tests4j.models.shared.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class for reflection like code that runs the 
 * same in GWT compiled java-script as the JSE.
 * Static methods are thread safe.
 * 
 * @author scott
 *
 */
public class ClassMethods {
	private static final char ARRAY_CHAR = '[';
	public static final String BOOLEAN = "boolean";
	public static final String BYTE = "byte";
	public static final String CHAR = "char";
	public static final String FLOAT = "float";
	public static final String INT = "int";
	public static final String LONG = "long";
	public static final String SHORT = "short";
	public static final String DOUBLE = "double";
	private static final Map<Character,String> PRIMITIVES = getPrimitives();
	private static final Map<String,String> PRIMITIVES_CONSTANT_MAP = getPrimitivesConstantMap();
	
	private static Map<Character,String> getPrimitives() {
		Map<Character,String> toRet = new HashMap<Character,String>();
		toRet.put('Z', BOOLEAN);
		toRet.put('B', BYTE);
		toRet.put('C', CHAR);
		toRet.put('F', FLOAT);
		
		toRet.put('I', INT);
		toRet.put('J', LONG);
		toRet.put('S', SHORT);
		toRet.put('D', DOUBLE);
		return Collections.unmodifiableMap(toRet);
	}
	
	private static Map<String,String> getPrimitivesConstantMap() {
		Map<String,String> toRet = new HashMap<String,String>();
		toRet.put(BOOLEAN,"BOOLEAN");
		toRet.put(BYTE,"BYTE");
		toRet.put(CHAR,"CHAR");
		toRet.put(FLOAT,"FLOAT");
		
		toRet.put(INT,"INT");
		toRet.put(LONG,"LONG");
		toRet.put(SHORT,"SHORT");
		toRet.put(DOUBLE, "DOUBLE");
		return Collections.unmodifiableMap(toRet);
	}
	/**
	 * This method uses the set of 
	 * primitives and the class name to discover
	 * if the class is a primitive, rather
	 * than reflection which isn't available in GWT.
	 * @param c
	 * @return
	 */
	public static boolean isPrimitive(Class<?> c) {
		return PRIMITIVES.containsValue(c.getName());
	}
	/**
	 * This method uses the set of 
	 * primitives and the class name to discover
	 * if the class is a primitive, rather
	 * than reflection which isn't available in GWT.
	 * @param c
	 * @return
	 */
	public static boolean isPrimitive(String s) {
		return PRIMITIVES.containsValue(s);
	}
	
	
	
	/**
	 * 
	 * @param className
	 * @return
	 */
	public String removeArrays(String className) {
		int arrays = getArrays(className);
		return className.substring(arrays, className.length());
	}
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
	 * 
	 * ie 
	 *  Lorg/adligo/tests4j/models/shared/system/I_Tests4J_System;
	 *  or
	 *  [Lorg/adligo/tests4j/models/shared/system/I_Tests4J_System;
	 *  turns into
	 *  [org.adligo.tests4j.models.shared.system.I_Tests4J_System
	 * @param clazzName
	 * @return
	 */
	public static String fromTypeDescription(String clazzName) {
		if (StringMethods.isEmpty(clazzName)) {
			throw new IllegalArgumentException();
		}
		int arrayChars = getArrays(clazzName);
		if (arrayChars >= 1) {
			clazzName = clazzName.substring(arrayChars, clazzName.length());
		}
		if (clazzName.length() == 0) {
			throw new IllegalArgumentException(clazzName);
		} else if (clazzName.length() == 1){
			return createArrayChars(arrayChars) + getPrimitive(clazzName.charAt(0));
		} else if (clazzName.length() >= 2) {
			if (clazzName.charAt(0) != 'L') {
				throw new IllegalArgumentException(clazzName);
			}
			//remove object wrapper chars L;
			clazzName = clazzName.substring(1, clazzName.length() -1);
		}
		return createArrayChars(arrayChars) + clazzName.replace('/', '.');
	}
	
	/**
	 * returns the type of the array 
	 * (just strips out the array char [)
	 * @param clazzName
	 * @return
	 */
	public static String getArrayType(String clazzName) {
		int arrayChars = getArrays(clazzName);
		return clazzName.substring(arrayChars, clazzName.length());
	}
	/**
	 * if this char is a primitive abbreviation
	 * character
	 * @param c
	 * @return
	 */
	public static boolean isPrimitiveClassChar(char c) {
		return PRIMITIVES.containsKey(c);
	}
	
	/**
	 * return the primitive class name
	 * for the abbreviation
	 * @param p
	 * @return
	 */
	public static String getPrimitive(char p) {
		return PRIMITIVES.get(p);
	}
	
	/**
	 * return the name of the constant in this 
	 * class that has the type value
	 * @param type
	 * @return
	 */
	public static String getPrimitiveConstant(String type) {
		return PRIMITIVES_CONSTANT_MAP.get(type);
	}
	/**
	 * if it is the start class character
	 * @param p
	 * @return
	 */
	public static boolean isClass(char p) {
		if (p == 'L') {
			return true;
		}
		return false;
		
	}
	/**
	 * if it is the array character
	 * @param p
	 * @return
	 */
	public static boolean isArray(char p) {
		if (p == ARRAY_CHAR) {
			return true;
		}
		return false;
	}
	
	/**
	 * this counts the number of arrays for a Class
	 * @param className
	 * @return
	 */
	public static int getArrays(String className) {
		int toRet = 0;
		char [] chars = className.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (isArray(c)) {
				toRet++;
			} else {
				break;
			}
		}
		return toRet;
	}
	
	public static boolean isArray(String className) {
		int count = getArrays(className);
		if (count >= 1) {
			return true;
		}
		return false;
	}
	public static String createArrayChars(int i) {
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < i; j++) {
			sb.append(ARRAY_CHAR);
		}
		return sb.toString();
	}
}
