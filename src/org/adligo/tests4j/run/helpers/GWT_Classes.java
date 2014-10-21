package org.adligo.tests4j.run.helpers;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.annotation.AnnotationFormatError;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.IncompleteAnnotationException;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSequentialList;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TooManyListenersException;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.adligo.tests4j.models.shared.reference_groups.jse.JSE_Lang;
import org.adligo.tests4j.models.shared.reference_groups.jse.JSE_Util;
import org.adligo.tests4j.shared.common.JavaAPIVersion;
import org.adligo.tests4j.shared.common.LegacyAPI_Issues;


/**
 * note this is a very special class which contains
 * classes that are in the most current version of GWT ;
 * GWT 2.6
 *     http://www.gwtproject.org/doc/latest/RefJreEmulation.html
 *     
 * @author scott
 *
 */
public class GWT_Classes {
	public static final LegacyAPI_Issues ISSUES = new LegacyAPI_Issues();
	
	public static final Set<Class<?>> JAVA_LANG = getJavaLang();
	public static final Set<Class<?>> JAVA_ANNOTATION = getJavaAnnotation();
	public static final Set<Class<?>> JAVA_MATH = getJavaMath();
	
	public static final Set<Class<?>> JAVA_IO = getJavaIo();
	public static final Set<Class<?>> JAVA_SQL = getJavaSql();
	public static final Set<Class<?>> JAVA_UTIL = getJavaUtil();
	public static final Set<Class<?>> JAVA_LOGGING = getJavaLogging();
	
	
	private static Set<Class<?>> getJavaLang() {
		Set<Class<?>> javaLang = new HashSet<Class<?>>();
		
		javaLang.add(Appendable.class);
		javaLang.add(ArithmeticException.class);
		javaLang.add(ArrayIndexOutOfBoundsException.class);
		javaLang.add(ArrayStoreException.class);
		javaLang.add(AssertionError.class);
		addNewClass(javaLang, JSE_Lang.AUTO_CLOSEABLE, new JavaAPIVersion("1.7.0"));
		javaLang.add(Boolean.class);
		javaLang.add(Byte.class);
		javaLang.add(CharSequence.class);
		javaLang.add(Character.class);
		javaLang.add(Class.class);
		javaLang.add(ClassCastException.class);
		javaLang.add(Cloneable.class);
		javaLang.add(Comparable.class);
		javaLang.add(Deprecated.class);
		javaLang.add(Exception.class);
		
		javaLang.add(Double.class);
		javaLang.add(Enum.class);
		javaLang.add(Error.class);
		javaLang.add(Exception.class);
		javaLang.add(Float.class);
		javaLang.add(IllegalArgumentException.class);
		javaLang.add(IllegalStateException.class);
		javaLang.add(IndexOutOfBoundsException.class);
		javaLang.add(Integer.class);
		javaLang.add(Iterable.class);
		
		javaLang.add(Long.class);
		javaLang.add(Math.class);
		
		javaLang.add(NegativeArraySizeException.class);
		javaLang.add(NoSuchMethodException.class);
		javaLang.add(NullPointerException.class);
		javaLang.add(Number.class);
		javaLang.add(NumberFormatException.class);
		
		javaLang.add(Object.class);
		javaLang.add(Runnable.class);
		javaLang.add(RuntimeException.class);
		
		javaLang.add(Short.class);
		javaLang.add(StackTraceElement.class);
		javaLang.add(String.class);
		javaLang.add(StringBuffer.class);
		javaLang.add(StringBuilder.class);
		javaLang.add(StringIndexOutOfBoundsException.class);
		javaLang.add(SuppressWarnings.class);
		javaLang.add(System.class);
		
		javaLang.add(Throwable.class);
		javaLang.add(UnsupportedOperationException.class);
		javaLang.add(Void.class);
		
		return Collections.unmodifiableSet(javaLang);
	}

	public static void addNewClass(Set<Class<?>> javaLang, String className, JavaAPIVersion d) {
		try {
			Class<?> c = Class.forName(className);
			javaLang.add(c);
		} catch (ClassNotFoundException x) {
			String version = System.getProperty("java.version");
			JavaAPIVersion jv = new JavaAPIVersion(version);
			if (jv.isAbove(d)) {
				ISSUES.addIssues(jv, new Exception(className + " not found."));
			}
		}
	}
	
	private static Set<Class<?>> getJavaAnnotation() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(Annotation.class);
		classes.add(AnnotationFormatError.class);
		classes.add(AnnotationTypeMismatchException.class);
		classes.add(Documented.class);
		classes.add(ElementType.class);
		classes.add(IncompleteAnnotationException.class);
		classes.add(Inherited.class);
		classes.add(Retention.class);
		classes.add(RetentionPolicy.class);
		classes.add(Target.class);
		return Collections.unmodifiableSet(classes);
	}
	
	private static Set<Class<?>> getJavaMath() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(BigDecimal.class);
		classes.add(BigInteger.class);
		classes.add(MathContext.class);
		classes.add(RoundingMode.class);
		return Collections.unmodifiableSet(classes);
	}
	
	private static Set<Class<?>> getJavaIo() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(FilterOutputStream.class);
		classes.add(IOException.class);
		classes.add(OutputStream.class);
		classes.add(PrintStream.class);
		classes.add(Serializable.class);
		classes.add(UnsupportedEncodingException.class);
		return Collections.unmodifiableSet(classes);
	}
	
	private static Set<Class<?>> getJavaSql() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(Date.class);
		classes.add(Time.class);
		classes.add(Timestamp.class);
		return Collections.unmodifiableSet(classes);
	}
	
	private static Set<Class<?>> getJavaUtil() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(AbstractCollection.class);
		classes.add(AbstractList.class);
		classes.add(AbstractMap.class);
		classes.add(AbstractQueue.class);
		classes.add(AbstractSequentialList.class);
		classes.add(AbstractSet.class);
		classes.add(ArrayList.class);
		classes.add(Arrays.class);
		
		classes.add(Collection.class);
		classes.add(Collections.class);
		classes.add(Comparator.class);
		classes.add(ConcurrentModificationException.class);
		classes.add(java.util.Date.class);
		
		classes.add(EnumMap.class);
		classes.add(EnumSet.class);
		classes.add(Enumeration.class);
		classes.add(EventListener.class);
		classes.add(EventObject.class);
		
		classes.add(HashMap.class);
		classes.add(HashSet.class);
		classes.add(IdentityHashMap.class);
		classes.add(Iterator.class);
		
		classes.add(LinkedHashMap.class);
		classes.add(LinkedHashSet.class);
		classes.add(LinkedList.class);
		classes.add(List.class);
		classes.add(ListIterator.class);
		
		classes.add(Map.class);
		classes.add(Map.Entry.class);
		classes.add(MissingResourceException.class);
		classes.add(NoSuchElementException.class);
		addNewClass(classes, JSE_Util.OBJECTS, new JavaAPIVersion("1.7.0"));
		
		classes.add(PriorityQueue.class);
		classes.add(Queue.class);
		classes.add(Random.class);
		classes.add(RandomAccess.class);
		
		classes.add(Set.class);
		classes.add(SortedMap.class);
		classes.add(SortedMap.class);
		classes.add(Stack.class);
		
		classes.add(TooManyListenersException.class);
		classes.add(TreeMap.class);
		classes.add(TreeSet.class);
		classes.add(Vector.class);
		
		return Collections.unmodifiableSet(classes);
	}
	
	private static Set<Class<?>> getJavaLogging() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(Formatter.class);
		classes.add(Handler.class);
		classes.add(Level.class);
		classes.add(LogManager.class);
		classes.add(LogRecord.class);
		classes.add(Logger.class);
		return Collections.unmodifiableSet(classes);
	}
	/**
	 * helps generate test code
	 * please keep
	 * @param args
	 */
	/*
	public static void main(String [] args) {
		Set<Class<?>> classes = JAVA_LOGGING;
		Set<String> simpleNames = new TreeSet<String>();
		for (Class<?> c: classes) {
			simpleNames.add(c.getSimpleName());
			
		}
		for (String clazz: simpleNames) {
			System.out.println("\tassertContains(classes," + 
					clazz+ ".class);");
		}
	}
	*/

	

}
