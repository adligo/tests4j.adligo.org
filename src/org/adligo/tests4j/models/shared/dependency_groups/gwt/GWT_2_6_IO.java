package org.adligo.tests4j.models.shared.dependency_groups.gwt;

import org.adligo.tests4j.models.shared.dependency.ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.ClassAttributesMutant;
import org.adligo.tests4j.models.shared.dependency.MethodSignature;
import org.adligo.tests4j.models.shared.dependency_groups.jse.JSE_Lang;

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
}
