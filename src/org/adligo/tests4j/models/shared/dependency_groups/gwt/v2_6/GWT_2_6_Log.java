package org.adligo.tests4j.models.shared.dependency_groups.gwt.v2_6;

import org.adligo.tests4j.models.shared.dependency_groups.jse.JSE_Lang;
import org.adligo.tests4j.models.shared.dependency_groups.jse.JSE_Log;
import org.adligo.tests4j.models.shared.dependency_groups.jse.JSE_Util;
import org.adligo.tests4j.shared.asserts.dependency.ClassAttributes;
import org.adligo.tests4j.shared.asserts.dependency.ClassAttributesMutant;
import org.adligo.tests4j.shared.asserts.dependency.FieldSignature;
import org.adligo.tests4j.shared.asserts.dependency.MethodSignature;
import org.adligo.tests4j.shared.common.ClassMethods;

public class GWT_2_6_Log {
	public static ClassAttributes getFormatter() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Log.FORMATTER);

		addFormatterMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addFormatterMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addObjectMembers(toRet);
		toRet.addMethod(new MethodSignature("format", 
			new String[] {JSE_Log.LOG_RECORD}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("formatMessage", 
			new String[] {JSE_Log.LOG_RECORD}, 
			JSE_Lang.STRING));
	}
	public static ClassAttributes getHandler() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Log.HANDLER);


		addHandlerMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addHandlerMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addObjectMembers(toRet);
		toRet.addMethod(new MethodSignature("isLoggable", 
			new String[] {JSE_Log.LOG_RECORD}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("getFormatter", 
			JSE_Log.FORMATTER));
		toRet.addMethod(new MethodSignature("getLevel", 
			JSE_Log.LEVEL));
		toRet.addMethod(new MethodSignature("close"));
		toRet.addMethod(new MethodSignature("flush"));
		toRet.addMethod(new MethodSignature("publish", 
			new String[] {JSE_Log.LOG_RECORD}));
		toRet.addMethod(new MethodSignature("setFormatter", 
			new String[] {JSE_Log.FORMATTER}));
		toRet.addMethod(new MethodSignature("setLevel", 
			new String[] {JSE_Log.LEVEL}));
	}
	public static ClassAttributes getLevel() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Log.LEVEL);

		addLevelMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addLevelMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addObjectMembers(toRet);
		toRet.addField(new FieldSignature("ALL", JSE_Log.LEVEL));
		toRet.addField(new FieldSignature("CONFIG", JSE_Log.LEVEL));
		toRet.addField(new FieldSignature("FINE", JSE_Log.LEVEL));
		toRet.addField(new FieldSignature("FINER", JSE_Log.LEVEL));
		toRet.addField(new FieldSignature("FINEST", JSE_Log.LEVEL));
		toRet.addField(new FieldSignature("INFO", JSE_Log.LEVEL));
		toRet.addField(new FieldSignature("OFF", JSE_Log.LEVEL));
		toRet.addField(new FieldSignature("SEVERE", JSE_Log.LEVEL));
		toRet.addField(new FieldSignature("WARNING", JSE_Log.LEVEL));
		toRet.addMethod(new MethodSignature("intValue", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getName", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("parse", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Log.LEVEL));
	}
	
	public static ClassAttributes getLogManager() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Log.LOG_MANAGER);

		addLogManagerMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addLogManagerMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addObjectMembers(toRet);
		toRet.addMethod(new MethodSignature("addLogger", 
			new String[] {JSE_Log.LOGGER}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("getLoggerNames", 
			JSE_Util.ENUMERATION));
		toRet.addMethod(new MethodSignature("getLogManager", 
			JSE_Log.LOG_MANAGER));
		toRet.addMethod(new MethodSignature("getLogger", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Log.LOGGER));
	}
	public static ClassAttributes getLogRecord() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Log.LOG_RECORD);

		//constructors
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Log.LEVEL, JSE_Lang.STRING}));

		addLogRecordMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addLogRecordMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addObjectMembers(toRet);
		toRet.addMethod(new MethodSignature("getLoggerName", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("getMessage", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("getThrown", 
			JSE_Lang.THROWABLE));
		toRet.addMethod(new MethodSignature("getLevel", 
			JSE_Log.LEVEL));
		toRet.addMethod(new MethodSignature("getMillis", 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("setLevel", 
			new String[] {JSE_Log.LEVEL}));
		toRet.addMethod(new MethodSignature("setLoggerName", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("setMessage", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("setMillis", 
			new String[] {ClassMethods.LONG}));
		toRet.addMethod(new MethodSignature("setThrown", 
			new String[] {JSE_Lang.THROWABLE}));
	}
	public static ClassAttributes getLogger() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Log.LOGGER);

		addLoggerMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addLoggerMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addObjectMembers(toRet);
		toRet.addMethod(new MethodSignature("getHandlers", 
			"[" + JSE_Log.HANDLER));
		toRet.addMethod(new MethodSignature("getUseParentHandlers", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isLoggable", 
			new String[] {JSE_Log.LEVEL}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("getName", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("getLevel", 
			JSE_Log.LEVEL));
		toRet.addMethod(new MethodSignature("getLogger", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Log.LOGGER));
		toRet.addMethod(new MethodSignature("getParent", 
			JSE_Log.LOGGER));
		toRet.addMethod(new MethodSignature("addHandler", 
			new String[] {JSE_Log.HANDLER}));
		toRet.addMethod(new MethodSignature("config", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("fine", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("finer", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("finest", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("info", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("log", 
			new String[] {JSE_Log.LEVEL, JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("log", 
			new String[] {JSE_Log.LEVEL, JSE_Lang.STRING, JSE_Lang.THROWABLE}));
		toRet.addMethod(new MethodSignature("log", 
			new String[] {JSE_Log.LOG_RECORD}));

		toRet.addMethod(new MethodSignature("removeHandler", 
			new String[] {JSE_Log.HANDLER}));
		toRet.addMethod(new MethodSignature("setLevel", 
			new String[] {JSE_Log.LEVEL}));
		toRet.addMethod(new MethodSignature("setParent", 
			new String[] {JSE_Log.LOGGER}));
		toRet.addMethod(new MethodSignature("setUseParentHandlers", 
			new String[] {ClassMethods.BOOLEAN}));
		toRet.addMethod(new MethodSignature("severe", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("warning", 
			new String[] {JSE_Lang.STRING}));
	}
}
