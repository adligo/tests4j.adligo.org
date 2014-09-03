package org.adligo.tests4j.models.shared.dependency_groups.gwt;

import org.adligo.tests4j.models.shared.dependency.ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.ClassAttributesMutant;
import org.adligo.tests4j.models.shared.dependency.MethodSignature;
import org.adligo.tests4j.models.shared.dependency_groups.jse.JSE_Lang;

public class GWT_2_6_Util {
	public static ClassAttributes getConcurrentModificationException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName("java.util.ConcurrentModificationException");

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING, JSE_Lang.THROWABLE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.THROWABLE}));

		addConcurrentModificationExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addConcurrentModificationExceptionMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addRuntimeExceptionMembers(toRet);
	}
	public static ClassAttributes getEmptyStackException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName("java.util.EmptyStackException");

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));

		addEmptyStackExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addEmptyStackExceptionMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addRuntimeExceptionMembers(toRet);
	}
	public static ClassAttributes getMissingResourceException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName("java.util.MissingResourceException");

		//constructors
		//extra constructor that is seen only by ASM not reflection
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING, JSE_Lang.STRING, JSE_Lang.STRING}));

		addMissingResourceExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addMissingResourceExceptionMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addRuntimeExceptionMembers(toRet);
		toRet.addMethod(new MethodSignature("getClassName", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("getKey", 
			JSE_Lang.STRING));
	}
	public static ClassAttributes getNoSuchElementException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName("java.util.NoSuchElementException");

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addNoSuchElementExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addNoSuchElementExceptionMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addRuntimeExceptionMembers(toRet);
	}
	public static ClassAttributes getTooManyListenersException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName("java.util.TooManyListenersException");

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addTooManyListenersExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addTooManyListenersExceptionMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addExceptionMembers(toRet);
	}
}
