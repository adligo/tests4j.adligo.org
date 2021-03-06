package org.adligo.tests4j.shared.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public static final String VOID = "void";
	private static final Map<Character,String> PRIMITIVES = getPrimitives();
	private static final Set<String> PRIMITIVE_CLASSES = getPrimitiveClasses();
	private static final Map<String,String> PRIMITIVES_CONSTANT_MAP = getPrimitivesConstantMap();
	
	@SuppressWarnings("boxing")
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
		toRet.put('V', VOID);
		return Collections.unmodifiableMap(toRet);
	}
	
	private static Set<String> getPrimitiveClasses() {
		Set<String> toRet = new HashSet<String>();
		toRet.add(Boolean.class.getName());
		toRet.add(Byte.class.getName());
		toRet.add(Character.class.getName());
		toRet.add(Double.class.getName());
		
		toRet.add(Float.class.getName());
		toRet.add(Long.class.getName());
		toRet.add(Integer.class.getName());
		toRet.add(Short.class.getName());
		
		toRet.add(Void.class.getName());
		return Collections.unmodifiableSet(toRet);
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
		toRet.put(VOID, "VOID");
		return Collections.unmodifiableMap(toRet);
	}
	/**
	 * This method uses the set of 
	 * primitives and the class name to discover
	 * if the class is a primitive class wrapper, rather
	 * than reflection which isn't available in GWT.
	 * @param c
	 * @return
	 */
	public static boolean isPrimitiveClass(Class<?> c) {
		return isPrimitiveClass(c.getName());
	}
	/**
	 * This method uses the set of 
	 * primitives and the class name to discover
	 * if the class is a primitive class wrapper, rather
	 * than reflection which isn't available in GWT.
	 * @param c
	 * @return
	 */
	public static boolean isPrimitiveClass(String p) {
		return PRIMITIVE_CLASSES.contains(p);
	}
	
	 /**
   * This method uses the set of 
   * primitives and the class name to discover
   * if the class is a primitive class wrapper, rather
   * than reflection which isn't available in GWT.
   * @param c
   * @return
   */
  public static boolean isPrimitiveOrArrayOfPrimitives(String name) {
    char [] chars = name.toCharArray();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];
      if (c != ARRAY_CHAR) {
        sb.append(c);
      }
    }
    String noArrays = sb.toString();
    if (noArrays.length() == 1) {
      return isPrimitiveClassChar(noArrays.charAt(0));
    }
    return isPrimitive(noArrays);
  }
  
	/**
	 * returns true if the class names match
	 * @param example
	 * @param c
	 * @return
	 */
	public static boolean isClass(Class<?> example, Class<?> c) {
		return example.getName().equals(c.getName());
	}
	/**
	 * This method uses the set of 
	 * primitives and the class name to discover
	 * if the class is a primitive, rather
	 * than reflection which isn't available in GWT.
	 * @param s The short name of the type ie "boolean".
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
	@SuppressWarnings("boxing")
  public static boolean isPrimitiveClassChar(char c) {
		return PRIMITIVES.containsKey(c);
	}
	
	/**
	 * return the primitive class name
	 * for the abbreviation
	 * @param p
	 * @return
	 */
	@SuppressWarnings("boxing")
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
	
	public static String getSimpleName(Class<?> j) {
		return getSimpleName(j.getName());
	}
	
	public static String getSimpleName(Object obj) {
		return getSimpleName(obj.getClass().getName());
	}
	
	public static String getSimpleName(String name) {
		int lastDot = name.lastIndexOf(".");
		return name.substring(lastDot + 1, name.length());
	}
	/**
	 * this removes any inner class name ie $*
	 * from your class
	 * @param className
	 * @return
	 */
	public static String getSourceClassName(String className) {
		int lastDot = className.indexOf("$");
		if (lastDot == -1) {
			return className;
		}
		return className.substring(0, lastDot);
	}
	
	/**
	 * this removes any inner class name ie $*
	 * from your class
	 * @param className
	 * @return
	 */
	public static String getPackageName(String className) {
		int lastDot = className.lastIndexOf(".");
		if (lastDot == -1) {
			return className;
		}
		return className.substring(0, lastDot);
	}
}
