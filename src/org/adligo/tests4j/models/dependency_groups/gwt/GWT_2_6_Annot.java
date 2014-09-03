package org.adligo.tests4j.models.dependency_groups.gwt;

import java.lang.annotation.IncompleteAnnotationException;

import org.adligo.tests4j.models.dependency_groups.jse.JSE_Lang;
import org.adligo.tests4j.models.shared.dependency.ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.ClassAttributesMutant;
import org.adligo.tests4j.models.shared.dependency.MethodSignature;

public class GWT_2_6_Annot {
	/**
	 * The GWT doc only supports a default no arg constructor
	 * which isn't in java 1.7/1.8
	 * http://docs.oracle.com/javase/7/docs/api/java/lang/annotation/AnnotationFormatError.html
	 * http://docs.oracle.com/javase/8/docs/api/java/lang/annotation/AnnotationFormatError.html
	 * 
	 * However you can still call methods on the instances that
	 * were created, when they are returned from somewhere...
	 */
	public static ClassAttributes getAnnotationFormatError() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName("java.lang.annotation.AnnotationFormatError");

		addAnnotationFormatErrorMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addAnnotationFormatErrorMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addErrorMembers(toRet);
	}
	/**
	 * The GWT doc only supports a default no arg constructor
	 * which isn't in jse java 1.7/1.8
	 * http://docs.oracle.com/javase/7/docs/api/java/lang/annotation/AnnotationTypeMismatchException.html
	 * http://docs.oracle.com/javase/8/docs/api/java/lang/annotation/AnnotationTypeMismatchException.html
	 * http://www.gwtproject.org/doc/latest/RefJreEmulation.html#Package_java_util
	 * 
	 * However you can still call methods on the instances that
	 * were created, when they are returned from somewhere...
	 */
	public static ClassAttributes getAnnotationTypeMismatchException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName("java.lang.annotation.AnnotationTypeMismatchException");

		addAnnotationTypeMismatchExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addAnnotationTypeMismatchExceptionMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addRuntimeExceptionMembers(toRet);
		toRet.addMethod(new MethodSignature("foundType", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("element", 
			"java.lang.reflect.Method"));
	}
	
	public static ClassAttributes getIncompleteAnnotationException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName("java.lang.annotation.IncompleteAnnotationException");

		//constructors
		//note this first default no arg constructor is reported by ASM
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.CLASS, JSE_Lang.STRING}));

		addIncompleteAnnotationExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addIncompleteAnnotationExceptionMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addRuntimeExceptionMembers(toRet);
		//note the GWT doc has these as fields in addition to methods
		//http://www.gwtproject.org/doc/latest/RefJreEmulation.html#Package_java_util
		toRet.addMethod(new MethodSignature("annotationType", 
			JSE_Lang.CLASS));
		toRet.addMethod(new MethodSignature("elementName", 
			JSE_Lang.STRING));
	}
}
