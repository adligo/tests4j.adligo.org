package org.adligo.tests4j.models.shared.dependency_groups.gwt.v2_6;

import org.adligo.tests4j.models.shared.dependency.ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.ClassAttributesMutant;
import org.adligo.tests4j.models.shared.dependency.MethodSignature;
import org.adligo.tests4j.models.shared.dependency_groups.jse.JSE_IO;
import org.adligo.tests4j.models.shared.dependency_groups.jse.JSE_Lang;
import org.adligo.tests4j.shared.common.ClassMethods;

public class GWT_2_6_IO {
	public static ClassAttributes getIOException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName("java.io.IOException");

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING, JSE_Lang.THROWABLE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.THROWABLE}));

		addIOExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addIOExceptionMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addExceptionMembers(toRet);
	}
	public static ClassAttributes getUnsupportedEncodingException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName("java.io.UnsupportedEncodingException");

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addUnsupportedEncodingExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addUnsupportedEncodingExceptionMembers(ClassAttributesMutant toRet) {
		addIOExceptionMembers(toRet);
	}
	
	public static ClassAttributes getFilterOutputStream() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_IO.FILTER_OUTPUT_STREAM);

		//constructors
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_IO.OUTPUT_STREAM}));
		GWT_2_6_Lang.addObjectMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static ClassAttributes getOutputStream() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_IO.OUTPUT_STREAM);

		//constructors
		//couldn't figure a way to call this from byte code, tried extending
		//toRet.addMethod(new MethodSignature("<init>"));
		GWT_2_6_Lang.addObjectMembers(toRet);
		
		return new ClassAttributes(toRet);
	}

	public static ClassAttributes getPrintStream() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_IO.PRINT_STREAM);

		//constructors
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_IO.OUTPUT_STREAM}));

		addPrintStreamMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addPrintStreamMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addObjectMembers(toRet);
		
		toRet.addMethod(new MethodSignature("print", 
			new String[] {"[" + ClassMethods.CHAR}));
		toRet.addMethod(new MethodSignature("print", 
			new String[] {ClassMethods.BOOLEAN}));
		toRet.addMethod(new MethodSignature("print", 
			new String[] {ClassMethods.CHAR}));
		toRet.addMethod(new MethodSignature("print", 
			new String[] {ClassMethods.DOUBLE}));
		toRet.addMethod(new MethodSignature("print", 
			new String[] {ClassMethods.FLOAT}));
		toRet.addMethod(new MethodSignature("print", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("print", 
			new String[] {JSE_Lang.OBJECT}));
		toRet.addMethod(new MethodSignature("print", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("print", 
			new String[] {ClassMethods.LONG}));
		toRet.addMethod(new MethodSignature("println"));
		toRet.addMethod(new MethodSignature("println", 
			new String[] {"[" + ClassMethods.CHAR}));
		toRet.addMethod(new MethodSignature("println", 
			new String[] {ClassMethods.BOOLEAN}));
		toRet.addMethod(new MethodSignature("println", 
			new String[] {ClassMethods.CHAR}));
		toRet.addMethod(new MethodSignature("println", 
			new String[] {ClassMethods.DOUBLE}));
		toRet.addMethod(new MethodSignature("println", 
			new String[] {ClassMethods.FLOAT}));
		toRet.addMethod(new MethodSignature("println", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("println", 
			new String[] {JSE_Lang.OBJECT}));
		toRet.addMethod(new MethodSignature("println", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("println", 
			new String[] {ClassMethods.LONG}));
	}
	public static ClassAttributes getSerializable() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName("java.io.Serializable");


		return new ClassAttributes(toRet);
	}

}
