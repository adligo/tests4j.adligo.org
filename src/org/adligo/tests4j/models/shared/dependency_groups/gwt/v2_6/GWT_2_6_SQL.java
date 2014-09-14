package org.adligo.tests4j.models.shared.dependency_groups.gwt.v2_6;

import org.adligo.tests4j.models.shared.common.ClassMethods;
import org.adligo.tests4j.models.shared.dependency.ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.ClassAttributesMutant;
import org.adligo.tests4j.models.shared.dependency.MethodSignature;
import org.adligo.tests4j.models.shared.dependency_groups.jse.JSE_Lang;
import org.adligo.tests4j.models.shared.dependency_groups.jse.JSE_Sql;
import org.adligo.tests4j.models.shared.dependency_groups.jse.JSE_Util;

public class GWT_2_6_SQL {
	public static ClassAttributes getDate() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Sql.DATE);

		//constructors
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.LONG}));

		addDateMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addDateMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Util.addDateMembers(toRet);
		toRet.addMethod(new MethodSignature("getHours", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getMinutes", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getSeconds", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Sql.DATE));
		toRet.addMethod(new MethodSignature("setHours", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("setMinutes", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("setSeconds", 
			new String[] {ClassMethods.INT}));
	}
	public static ClassAttributes getTime() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Sql.TIME);

		//constructors
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.LONG}));

		addTimeMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addTimeMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Util.addDateMembers(toRet);
		toRet.addMethod(new MethodSignature("getDate", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getDay", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getMonth", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getYear", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("toString", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Sql.TIME));
		toRet.addMethod(new MethodSignature("setDate", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("setMonth", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("setTime", 
			new String[] {ClassMethods.LONG}));
		toRet.addMethod(new MethodSignature("setYear", 
			new String[] {ClassMethods.INT}));
	}
	public static ClassAttributes getTimestamp() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Sql.TIMESTAMP);

		//constructors
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, ClassMethods.INT, ClassMethods.INT, ClassMethods.INT, ClassMethods.INT, ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.LONG}));

		addTimestampMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addTimestampMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Util.addDateMembers(toRet);
		toRet.addMethod(new MethodSignature("after", 
			new String[] {JSE_Sql.TIMESTAMP}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("before", 
			new String[] {JSE_Sql.TIMESTAMP}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {JSE_Sql.TIMESTAMP}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Sql.TIMESTAMP}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Util.DATE}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getNanos", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Sql.TIMESTAMP));
		toRet.addMethod(new MethodSignature("getTime", 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("setNanos", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("setTime", 
			new String[] {ClassMethods.LONG}));
	}
}
