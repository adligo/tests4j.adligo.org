package org.adligo.tests4j.models.shared.reference_groups.gwt.v2_6;

import org.adligo.tests4j.models.shared.reference_groups.jse.JSE_Lang;
import org.adligo.tests4j.models.shared.reference_groups.jse.JSE_LangAnnot;
import org.adligo.tests4j.shared.asserts.reference.ClassAttributes;
import org.adligo.tests4j.shared.asserts.reference.ClassAttributesMutant;
import org.adligo.tests4j.shared.asserts.reference.FieldSignature;
import org.adligo.tests4j.shared.asserts.reference.MethodSignature;
import org.adligo.tests4j.shared.common.ClassMethods;

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
		toRet.setName(JSE_LangAnnot.ANNOTATION_FORMAT_ERROR);

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
		toRet.setName(JSE_LangAnnot.ANNOTATION_TYPE_MISMATCH_EXCEPTION);

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
		toRet.setName(JSE_LangAnnot.INCOMPLETE_ANNOTATION_EXCEPTION);

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
	
	public static ClassAttributes getAnnotation() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_LangAnnot.ANNOTATION);


		addAnnotationMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addAnnotationMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("hashCode", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("annotationType", 
			JSE_Lang.CLASS));
		toRet.addMethod(new MethodSignature("toString", 
			JSE_Lang.STRING));
	}
	
	public static ClassAttributes getDocumented() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName("java.lang.annotation.Documented");
		return new ClassAttributes(toRet);
	}
	
	public static ClassAttributes getElementType() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_LangAnnot.ELEMENT_TYPE);

		addElementTypeMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addElementTypeMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addEnumMembers(toRet);
		toRet.addField(new FieldSignature("ANNOTATION_TYPE", JSE_LangAnnot.ELEMENT_TYPE));
		toRet.addField(new FieldSignature("CONSTRUCTOR", JSE_LangAnnot.ELEMENT_TYPE));
		toRet.addField(new FieldSignature("FIELD", JSE_LangAnnot.ELEMENT_TYPE));
		toRet.addField(new FieldSignature("LOCAL_VARIABLE", JSE_LangAnnot.ELEMENT_TYPE));
		toRet.addField(new FieldSignature("METHOD", JSE_LangAnnot.ELEMENT_TYPE));
		toRet.addField(new FieldSignature("PACKAGE", JSE_LangAnnot.ELEMENT_TYPE));
		toRet.addField(new FieldSignature("PARAMETER", JSE_LangAnnot.ELEMENT_TYPE));
		toRet.addField(new FieldSignature("TYPE", JSE_LangAnnot.ELEMENT_TYPE));
		toRet.addMethod(new MethodSignature("values", 
			"[" + JSE_LangAnnot.ELEMENT_TYPE));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING}, 
			JSE_LangAnnot.ELEMENT_TYPE));
	}
	
	public static ClassAttributes getInherited() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_LangAnnot.INHERITED);

		return new ClassAttributes(toRet);
	}

	
	public static ClassAttributes getRetention() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_LangAnnot.RETENTION);


		addRetentionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addRetentionMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("value", 
				JSE_LangAnnot.RETENTION_POLICY));
	}
	public static ClassAttributes getRetentionPolicy() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_LangAnnot.RETENTION_POLICY);


		addRetentionPolicyMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addRetentionPolicyMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addEnumMembers(toRet);
		toRet.addField(new FieldSignature("CLASS", JSE_LangAnnot.RETENTION_POLICY));
		toRet.addField(new FieldSignature("RUNTIME", JSE_LangAnnot.RETENTION_POLICY));
		toRet.addField(new FieldSignature("SOURCE", JSE_LangAnnot.RETENTION_POLICY));
		toRet.addMethod(new MethodSignature("values", 
				"[" + JSE_LangAnnot.RETENTION_POLICY));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING}, 
			JSE_LangAnnot.RETENTION_POLICY));
	}
	public static ClassAttributes getTarget() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_LangAnnot.TARGET);


		addTargetMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addTargetMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("value", 
				"[" + JSE_LangAnnot.ELEMENT_TYPE));
	}
}
