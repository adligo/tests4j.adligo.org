package org.adligo.tests4j.models.shared.reference_groups.gwt.v2_6;

import org.adligo.tests4j.models.shared.reference_groups.jse.JSE_IO;
import org.adligo.tests4j.models.shared.reference_groups.jse.JSE_Lang;
import org.adligo.tests4j.models.shared.reference_groups.jse.JSE_Util;
import org.adligo.tests4j.shared.asserts.reference.ClassAttributes;
import org.adligo.tests4j.shared.asserts.reference.ClassAttributesMutant;
import org.adligo.tests4j.shared.asserts.reference.FieldSignature;
import org.adligo.tests4j.shared.asserts.reference.MethodSignature;
import org.adligo.tests4j.shared.asserts.reference.ReferenceGroup;
import org.adligo.tests4j.shared.asserts.reference.ReferenceGroupMutant;
import org.adligo.tests4j.shared.common.ClassMethods;

public class GWT_2_6_Lang  {
	
	public static ClassAttributes getAppendable() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.APPENDABLE);

		//constructors

		addAppendableMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addAppendableMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("append", 
			new String[] {ClassMethods.CHAR}, 
			JSE_Lang.APPENDABLE));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.CHAR_SEQUENCE}, 
			JSE_Lang.APPENDABLE));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.CHAR_SEQUENCE, ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.APPENDABLE));
	}
	
	public static ClassAttributes getAutoCloseable() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.AUTO_CLOSEABLE);

		addAutoCloseableMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addAutoCloseableMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("close"));
	}
	
	public static ClassAttributes getBoolean() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.BOOLEAN);
		addObjectMembers(toRet);
		
		toRet.addField(new FieldSignature("FALSE", JSE_Lang.BOOLEAN));
		toRet.addField(new FieldSignature("TYPE", JSE_Lang.CLASS));
		toRet.addField(new FieldSignature("TRUE", JSE_Lang.BOOLEAN));
		
		toRet.addMethod(new MethodSignature("<init>", 
				new String[] {ClassMethods.BOOLEAN}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Lang.BOOLEAN));
		
		toRet.addMethod(new MethodSignature("compare", 
			new String[] {ClassMethods.BOOLEAN, ClassMethods.BOOLEAN}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.BOOLEAN}, 
			JSE_Lang.BOOLEAN));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Lang.BOOLEAN}, 
			ClassMethods.INT));
		
		toRet.addMethod(new MethodSignature("booleanValue", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("parseBoolean", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.BOOLEAN));
		return new ClassAttributes(toRet);
	}
	public static ClassAttributes getByte() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.BYTE);

		//constructors
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.BYTE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addByteMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addByteMembers(ClassAttributesMutant toRet) {
		addNumberMembers(toRet);
		/**
		 * note MIN_VALUE, MAX_VALUE, SIZE 
		 * will get compiled out of the .class file
		 * and replaced with the primitive
		 */
		toRet.addField(new FieldSignature("TYPE", JSE_Lang.CLASS));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("byteValue", 
			ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("parseByte", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("parseByte", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("compare", 
			new String[] {ClassMethods.BYTE, ClassMethods.BYTE}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Lang.BYTE}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("decode", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Lang.BYTE));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.BYTE}, 
			JSE_Lang.BYTE));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Lang.BYTE));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			JSE_Lang.BYTE));
	}
	
	public static ClassAttributes getCharacter() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.CHARACTER);

		//constructors
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.CHAR}));

		addCharacterMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addCharacterMembers(ClassAttributesMutant toRet) {
		addObjectMembers(toRet);
		

		toRet.addField(new FieldSignature("TYPE", JSE_Lang.CLASS));
		/**
		 * other fields are compile to primitives
		 */
		toRet.addMethod(new MethodSignature("toChars", 
			new String[] {ClassMethods.INT}, 
			"[" +ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("isDigit", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isHighSurrogate", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isLetter", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isLetterOrDigit", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isLowSurrogate", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isLowerCase", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isSpace", 
				new String[] {ClassMethods.CHAR}, 
				ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isSupplementaryCodePoint", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isSurrogate", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isSurrogatePair", 
				new String[] {ClassMethods.CHAR, ClassMethods.CHAR}, 
				ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isUpperCase", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isValidCodePoint", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("charValue", 
			ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("forDigit", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.CHAR));
		
		toRet.addMethod(new MethodSignature("toLowerCase", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("toUpperCase", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("charCount", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("codePointAt", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("codePointAt", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("codePointAt", 
			new String[] {JSE_Lang.CHAR_SEQUENCE, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("codePointBefore", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("codePointBefore", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("codePointBefore", 
			new String[] {JSE_Lang.CHAR_SEQUENCE, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("codePointCount", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("codePointCount", 
			new String[] {JSE_Lang.CHAR_SEQUENCE, ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("compare", 
			new String[] {ClassMethods.CHAR, ClassMethods.CHAR}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Lang.CHARACTER}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("digit", 
			new String[] {ClassMethods.CHAR, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("offsetByCodePoints", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.INT, 
				ClassMethods.INT, ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("offsetByCodePoints", 
			new String[] {JSE_Lang.CHAR_SEQUENCE, ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("toChars", 
			new String[] {ClassMethods.INT, "[" + ClassMethods.CHAR, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("toCodePoint", 
			new String[] {ClassMethods.CHAR, ClassMethods.CHAR}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.CHAR}, 
			JSE_Lang.CHARACTER));
	}
	
	public static ClassAttributes getEnum() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.ENUM);
		
		toRet.addMethod(new MethodSignature("<init>", 
				new String[] {JSE_Lang.STRING, ClassMethods.INT}));
		addEnumMembers(toRet);
		
		return new ClassAttributes(toRet);
	}
	public static void addEnumMembers(ClassAttributesMutant toRet) {
		addObjectMembers(toRet);
		
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Lang.ENUM}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.CLASS, JSE_Lang.STRING}, 
			JSE_Lang.ENUM));
		toRet.addMethod(new MethodSignature("getDeclaringClass", 
			JSE_Lang.CLASS));
		
		toRet.addMethod(new MethodSignature("ordinal", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("name", 
			JSE_Lang.STRING));
	}
	
	public static ClassAttributes getObject() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.OBJECT);
		toRet.addMethod(new MethodSignature("<init>"));
		addObjectMembers(toRet);
		return new ClassAttributes(toRet);
	}
	
	/**
	 * everything that isn't a constructor (fields and methods)
	 * @param toRet
	 */
	public static void addObjectMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("getClass", 
			JSE_Lang.CLASS));
		toRet.addMethod(new MethodSignature("hashCode", 
			ClassMethods.INT));
		
		toRet.addMethod(new MethodSignature("toString", 
			JSE_Lang.STRING));
	}
	
	public static ClassAttributes getStackTraceElement() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.STACK_TRACE_ELEMENT);

		//constructors
		//weird the GWT Jre emulation doc has a additional no arg constructor
		//http://www.gwtproject.org/doc/latest/RefJreEmulation.html which isn't in
		//http://docs.oracle.com/javase/8/docs/api/java/lang/StackTraceElement.html
		//also this hidden init with no args, shows up when getting the 
		//called methods from ASM, so...
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING, JSE_Lang.STRING, JSE_Lang.STRING, ClassMethods.INT}));

		addStackTraceElementMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addStackTraceElementMembers(ClassAttributesMutant toRet) {
		addObjectMembers(toRet);
		toRet.addMethod(new MethodSignature("getLineNumber", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getClassName", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("getFileName", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("getMethodName", 
			JSE_Lang.STRING));
	}
	
	public static ClassAttributes getThrowable() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.THROWABLE);
		
		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.THROWABLE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING, JSE_Lang.THROWABLE}));
		toRet.addMethod(new MethodSignature("<init>", 
				new String[] {JSE_Lang.STRING}));
		
		addThrowableMembers(toRet);
		return new ClassAttributes(toRet);
	}
	
	
	/**
	 * everything that isn't a constructor (fields and methods)
	 * @param toRet
	 */
	public static void addThrowableMembers(ClassAttributesMutant toRet) {
		addObjectMembers(toRet);
		toRet.addMethod(new MethodSignature("addSuppressed", 
			new String[] {JSE_Lang.THROWABLE}));
		toRet.addMethod(new MethodSignature("fillInStackTrace", 
			JSE_Lang.THROWABLE));
		toRet.addMethod(new MethodSignature("getCause", 
			JSE_Lang.THROWABLE));
		
		toRet.addMethod(new MethodSignature("getLocalizedMessage", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("getMessage", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("getStackTrace", 
			"[" + JSE_Lang.STACK_TRACE_ELEMENT));
		
		toRet.addMethod(new MethodSignature("getSuppressed", 
			"[" + JSE_Lang.THROWABLE));
		toRet.addMethod(new MethodSignature("initCause", 
			new String[] {JSE_Lang.THROWABLE}, 
			JSE_Lang.THROWABLE));
		toRet.addMethod(new MethodSignature("printStackTrace"));
		
		toRet.addMethod(new MethodSignature("printStackTrace", 
				new String[] {JSE_IO.PRINT_STREAM}));
		toRet.addMethod(new MethodSignature("setStackTrace", 
			new String[] {"[" + JSE_Lang.STACK_TRACE_ELEMENT}));
		//11
	}
	
	
	public static ClassAttributes getException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.EXCEPTION);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING, JSE_Lang.THROWABLE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.THROWABLE}));

		addExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addExceptionMembers(ClassAttributesMutant toRet) {
		addThrowableMembers(toRet);
	}
	
	public static ClassAttributes getRuntimeException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.RUNTIME_EXCEPTION);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING, JSE_Lang.THROWABLE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.THROWABLE}));

		addRuntimeExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addRuntimeExceptionMembers(ClassAttributesMutant toRet) {
		addExceptionMembers(toRet);
	}
	
	
	public static ClassAttributes getRunnable() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.RUNNABLE);

		//constructors

		addRunnableMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addRunnableMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("run"));

	}
	/**
	 * NOTE this class isn't really in GWT,
	 * I tried to add it but got denied
	 * so don't blame tests4j if you get a false positive
	 * http://code.google.com/p/google-web-toolkit/issues/detail?id=8954
	 * @return
	 */
	public static ClassAttributes getIncompatibleClassChangeError() {
    ClassAttributesMutant toRet = new ClassAttributesMutant();
    toRet.setName(JSE_Lang.INCOMPATIBLE_CLASS_CHANGE_ERROR);

    //constructors
    toRet.addMethod(new MethodSignature("<init>"));
    toRet.addMethod(new MethodSignature("<init>", 
      new String[] {JSE_Lang.STRING}));

    addThrowableMembers(toRet);
    return new ClassAttributes(toRet);
  }
	
	public static ClassAttributes getIndexOutOfBoundsException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.INDEX_OUT_OF_BOUNDS_EXCEPTION);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addIndexOutOfBoundsExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static ClassAttributes getFloat() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.FLOAT);

		//constructors
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.DOUBLE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.FLOAT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addFloatMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addFloatMembers(ClassAttributesMutant toRet) {
		addNumberMembers(toRet);
		toRet.addField(new FieldSignature("TYPE", JSE_Lang.CLASS));

		toRet.addMethod(new MethodSignature("isNaN", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isNaN", 
			new String[] {ClassMethods.FLOAT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("byteValue", 
			ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("doubleValue", 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("floatValue", 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("intBitsToFloat", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("parseFloat", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("compare", 
			new String[] {ClassMethods.FLOAT, ClassMethods.FLOAT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Lang.FLOAT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("floatToIntBits", 
			new String[] {ClassMethods.FLOAT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("intValue", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.FLOAT}, 
			JSE_Lang.FLOAT));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Lang.FLOAT));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {ClassMethods.FLOAT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("longValue", 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("shortValue", 
			ClassMethods.SHORT));
	}
	
	public static void addIndexOutOfBoundsExceptionMembers(ClassAttributesMutant toRet) {
		addRuntimeExceptionMembers(toRet);
	}
	
	public static ClassAttributes getArrayIndexOutOfBoundsException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addArrayIndexOutOfBoundsExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addArrayIndexOutOfBoundsExceptionMembers(ClassAttributesMutant toRet) {
		addIndexOutOfBoundsExceptionMembers(toRet);
	}
	
	public static ClassAttributes getArithmeticException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.ARITHMETIC_EXCEPTION);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addArithmeticExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addArithmeticExceptionMembers(ClassAttributesMutant toRet) {
		addRuntimeExceptionMembers(toRet);
	}
	
	public static ClassAttributes getArrayStoreException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.ARRAY_STORE_EXCEPTION);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addArrayStoreExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addArrayStoreExceptionMembers(ClassAttributesMutant toRet) {
		addRuntimeExceptionMembers(toRet);
	}
	
	public static ClassAttributes getError() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.ERROR);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING, JSE_Lang.THROWABLE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.THROWABLE}));

		addErrorMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addErrorMembers(ClassAttributesMutant toRet) {
		addThrowableMembers(toRet);
	}
	
	public static ClassAttributes getAssertionError() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.ASSERTION_ERROR);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.BOOLEAN}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.CHAR}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.DOUBLE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.FLOAT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.OBJECT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.LONG}));

		addAssertionErrorMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addAssertionErrorMembers(ClassAttributesMutant toRet) {
		addErrorMembers(toRet);
	}
	
	public static ClassAttributes getClassCastException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.CLASS_CAST_EXCEPTION);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addClassCastExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addClassCastExceptionMembers(ClassAttributesMutant toRet) {
		addRuntimeExceptionMembers(toRet);
	}
	
	public static ClassAttributes getIllegalArgumentException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.ILLEGAL_ARGUMENT_EXCEPTION);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING, JSE_Lang.THROWABLE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.THROWABLE}));

		addIllegalArgumentExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addIllegalArgumentExceptionMembers(ClassAttributesMutant toRet) {
		addRuntimeExceptionMembers(toRet);
	}
	
	public static ClassAttributes getIllegalStateException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.ILLEGAL_STATE_EXCEPTION);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING, JSE_Lang.THROWABLE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.THROWABLE}));

		addIllegalStateExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addIllegalStateExceptionMembers(ClassAttributesMutant toRet) {
		addRuntimeExceptionMembers(toRet);
	}
	public static ClassAttributes getNegativeArraySizeException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.NEGATIVE_ARRAY_SIZE_EXCEPTION);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addNegativeArraySizeExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addNegativeArraySizeExceptionMembers(ClassAttributesMutant toRet) {
		addRuntimeExceptionMembers(toRet);
	}
	/*
	public static ClassAttributes getReflectiveOperationException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.REFLECTIVE_OPERATION_EXCEPTION);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING, JSE_Lang.THROWABLE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.THROWABLE}));

		addReflectiveOperationExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}
	 */
	
	/**
	 * note this class isn't in GWT yet, which seems
	 * like it is still on 1.6 on 8/28/2014
	 * although it is linking to the 1.7 javadoc not the 1.6
	 * http://www.gwtproject.org/doc/latest/RefJreEmulation.html
	 * this seems like the violation of some sort of
	 * a rule, and should probably be added to GWT.
	 * @param toRet
	 */
	public static void addReflectiveOperationExceptionMembers(ClassAttributesMutant toRet) {
		addExceptionMembers(toRet);
	}
	
	public static ClassAttributes getNoSuchMethodException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.NO_SUCH_METHOD_EXCEPTION);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addNoSuchMethodExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	 /**
   * NOTE this class isn't really in GWT,
   * I tried to add it but got denied
   * so don't blame tests4j if you get a false positive
   * http://code.google.com/p/google-web-toolkit/issues/detail?id=8954
   * @return
   */
  public static ClassAttributes getNoSuchFieldError() {
    ClassAttributesMutant toRet = new ClassAttributesMutant();
    toRet.setName(JSE_Lang.NO_SUCH_FIELD_ERROR);

    //constructors
    toRet.addMethod(new MethodSignature("<init>"));
    toRet.addMethod(new MethodSignature("<init>", 
      new String[] {JSE_Lang.STRING}));
    
    addThrowableMembers(toRet);
    return new ClassAttributes(toRet);
  }
  
	public static void addNoSuchMethodExceptionMembers(ClassAttributesMutant toRet) {
		addReflectiveOperationExceptionMembers(toRet);
	}
	public static ClassAttributes getNullPointerException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.NULL_POINTER_EXCEPTION);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addNullPointerExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addNullPointerExceptionMembers(ClassAttributesMutant toRet) {
		addRuntimeExceptionMembers(toRet);
	}
	public static ClassAttributes getNumberFormatException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.NUMBER_FORMAT_EXCEPTION);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addNumberFormatExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static ClassAttributes getNumber() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.NUMBER);

		addNumberMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addNumberMembers(ClassAttributesMutant toRet) {
		addObjectMembers(toRet);
		toRet.addMethod(new MethodSignature("byteValue", 
			ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("doubleValue", 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("floatValue", 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("intValue", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("longValue", 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("shortValue", 
			ClassMethods.SHORT));
	}
	
	public static void addNumberFormatExceptionMembers(ClassAttributesMutant toRet) {
		addIllegalArgumentExceptionMembers(toRet);
	}
	public static ClassAttributes getStringIndexOutOfBoundsException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.STRING_INDEX_OUT_OF_BOUNDS_EXCEPTION);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addStringIndexOutOfBoundsExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addStringIndexOutOfBoundsExceptionMembers(ClassAttributesMutant toRet) {
		addIndexOutOfBoundsExceptionMembers(toRet);
	}
	public static ClassAttributes getUnsupportedOperationException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.UNSUPPORTED_OPERATION_EXCEPTION);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING, JSE_Lang.THROWABLE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.THROWABLE}));

		addUnsupportedOperationExceptionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addUnsupportedOperationExceptionMembers(ClassAttributesMutant toRet) {
		addRuntimeExceptionMembers(toRet);
	}
	
	public static ClassAttributes getCharSequence() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.CHAR_SEQUENCE);


		addCharSequenceMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addCharSequenceMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("charAt", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("length", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("subSequence", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.CHAR_SEQUENCE));
		toRet.addMethod(new MethodSignature("toString", 
			JSE_Lang.STRING));
	}
	
	public static ClassAttributes getCloneable() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.CLONEABLE);


		return new ClassAttributes(toRet);
	}

	public static ClassAttributes getComparable() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.COMPARABLE);


		addComparableMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addComparableMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.INT));
	}
	public static ClassAttributes getDeprecated() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.DEPRECATED);

		return new ClassAttributes(toRet);
	}

	public static ClassAttributes getDouble() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.DOUBLE);

		//constructors
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.DOUBLE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addDoubleMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addDoubleMembers(ClassAttributesMutant toRet) {
		addNumberMembers(toRet);
		toRet.addField(new FieldSignature("TYPE", JSE_Lang.CLASS));

		toRet.addMethod(new MethodSignature("isNaN", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isNaN", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("byteValue", 
			ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("doubleValue", 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("parseDouble", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("floatValue", 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("compare", 
			new String[] {ClassMethods.DOUBLE, ClassMethods.DOUBLE}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Lang.DOUBLE}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("intValue", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.DOUBLE}, 
			JSE_Lang.DOUBLE));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Lang.DOUBLE));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {ClassMethods.DOUBLE}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("longValue", 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("shortValue", 
			ClassMethods.SHORT));
	}
	public static ClassAttributes getOverride() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.OVERRIDE);

		return new ClassAttributes(toRet);
	}

	public static ClassAttributes getSuppressWarnings() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.SUPPRESS_WARNINGS);


		addSuppressWarningsMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addSuppressWarningsMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("value", 
			"[" + JSE_Lang.STRING));
	}
	public static ClassAttributes getClassAttributeMembers() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.CLASS);


		addClassMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addClassMembers(ClassAttributesMutant toRet) {
		addObjectMembers(toRet);
		
		toRet.addMethod(new MethodSignature("desiredAssertionStatus", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isArray", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isEnum", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isInterface", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isPrimitive", 
			ClassMethods.BOOLEAN));
		
		toRet.addMethod(new MethodSignature("getComponentType", 
			JSE_Lang.CLASS));
		toRet.addMethod(new MethodSignature("getEnumConstants", 
				"[" + JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("getName", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("getSimpleName", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("getSuperclass", 
				JSE_Lang.CLASS));
	}
	
	public static ClassAttributes getInteger() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.INTEGER);

		//constructors
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addIntegerMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addIntegerMembers(ClassAttributesMutant toRet) {
		addNumberMembers(toRet);
		toRet.addField(new FieldSignature("TYPE", JSE_Lang.CLASS));
		
		toRet.addMethod(new MethodSignature("byteValue", 
			ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("doubleValue", 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("floatValue", 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("bitCount", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("compare", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Lang.INTEGER}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("highestOneBit", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("intValue", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("lowestOneBit", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("numberOfLeadingZeros", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("numberOfTrailingZeros", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("parseInt", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("parseInt", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("reverse", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("reverseBytes", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("rotateLeft", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("rotateRight", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("signum", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("decode", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Lang.INTEGER));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.INTEGER));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Lang.INTEGER));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			JSE_Lang.INTEGER));
		toRet.addMethod(new MethodSignature("toBinaryString", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toHexString", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toOctalString", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("longValue", 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("shortValue", 
			ClassMethods.SHORT));
	}
	
	
	public static ClassAttributes getIterable() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.ITERABLE);


		addIterableMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addIterableMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("iterator", 
			JSE_Util.ITERATOR));
	}
	
	/**
   * NOTE this class isn't really in GWT,
   * I tried to add it but got denied
   * so don't blame tests4j if you get a false positive
   * http://code.google.com/p/google-web-toolkit/issues/detail?id=8954
   * @return
   */
  public static ClassAttributes getLinkageError() {
    ClassAttributesMutant toRet = new ClassAttributesMutant();
    toRet.setName(JSE_Lang.LINKAGE_ERROR);

    //constructors
    toRet.addMethod(new MethodSignature("<init>"));
    toRet.addMethod(new MethodSignature("<init>", 
      new String[] {JSE_Lang.STRING}));
    toRet.addMethod(new MethodSignature("<init>", 
        new String[] {JSE_Lang.STRING, JSE_Lang.THROWABLE}));
    
    addThrowableMembers(toRet);
    return new ClassAttributes(toRet);
  }
  
	public static ClassAttributes getLong() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.LONG);

		//constructors
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.LONG}));

		addLongMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addLongMembers(ClassAttributesMutant toRet) {
		addNumberMembers(toRet);
		toRet.addField(new FieldSignature("TYPE", JSE_Lang.CLASS));

		toRet.addMethod(new MethodSignature("byteValue", 
			ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("doubleValue", 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("floatValue", 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("bitCount", 
			new String[] {ClassMethods.LONG}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("compare", 
			new String[] {ClassMethods.LONG, ClassMethods.LONG}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Lang.LONG}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("hashCode", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("intValue", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("numberOfLeadingZeros", 
			new String[] {ClassMethods.LONG}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("numberOfTrailingZeros", 
			new String[] {ClassMethods.LONG}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("signum", 
			new String[] {ClassMethods.LONG}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("decode", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Lang.LONG));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Lang.LONG));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			JSE_Lang.LONG));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.LONG}, 
			JSE_Lang.LONG));
		toRet.addMethod(new MethodSignature("toBinaryString", 
			new String[] {ClassMethods.LONG}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toHexString", 
			new String[] {ClassMethods.LONG}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toOctalString", 
			new String[] {ClassMethods.LONG}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {ClassMethods.LONG}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {ClassMethods.LONG, ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("highestOneBit", 
			new String[] {ClassMethods.LONG}, 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("longValue", 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("lowestOneBit", 
			new String[] {ClassMethods.LONG}, 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("parseLong", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("parseLong", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("reverse", 
			new String[] {ClassMethods.LONG}, 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("reverseBytes", 
			new String[] {ClassMethods.LONG}, 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("rotateLeft", 
			new String[] {ClassMethods.LONG, ClassMethods.INT}, 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("rotateRight", 
			new String[] {ClassMethods.LONG, ClassMethods.INT}, 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("shortValue", 
			ClassMethods.SHORT));
	}
	
	public static ClassAttributes getMath() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.MATH);


		addMathMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addMathMembers(ClassAttributesMutant toRet) {
		
		toRet.addMethod(new MethodSignature("abs", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("acos", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("asin", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("atan", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("atan2", 
			new String[] {ClassMethods.DOUBLE, ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("cbrt", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("ceil", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("copySign", 
			new String[] {ClassMethods.DOUBLE, ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("cos", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("cosh", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("exp", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("expm1", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("floor", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("hypot", 
			new String[] {ClassMethods.DOUBLE, ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("log", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("log10", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("log1p", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("max", 
			new String[] {ClassMethods.DOUBLE, ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("min", 
			new String[] {ClassMethods.DOUBLE, ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("pow", 
			new String[] {ClassMethods.DOUBLE, ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("random", 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("rint", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("scalb", 
			new String[] {ClassMethods.DOUBLE, ClassMethods.INT}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("signum", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("sin", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("sinh", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("sqrt", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("tan", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("tanh", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("toDegrees", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("toRadians", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("abs", 
			new String[] {ClassMethods.FLOAT}, 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("copySign", 
			new String[] {ClassMethods.FLOAT, ClassMethods.FLOAT}, 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("max", 
			new String[] {ClassMethods.FLOAT, ClassMethods.FLOAT}, 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("min", 
			new String[] {ClassMethods.FLOAT, ClassMethods.FLOAT}, 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("scalb", 
			new String[] {ClassMethods.FLOAT, ClassMethods.INT}, 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("signum", 
			new String[] {ClassMethods.FLOAT}, 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("abs", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("max", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("min", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("round", 
			new String[] {ClassMethods.FLOAT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("abs", 
			new String[] {ClassMethods.LONG}, 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("max", 
			new String[] {ClassMethods.LONG, ClassMethods.LONG}, 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("min", 
			new String[] {ClassMethods.LONG, ClassMethods.LONG}, 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("round", 
			new String[] {ClassMethods.DOUBLE}, 
			ClassMethods.LONG));
	}
	
	public static ClassAttributes getShort() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.SHORT);

		//constructors
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.SHORT}));

		addShortMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addShortMembers(ClassAttributesMutant toRet) {
		addNumberMembers(toRet);
		toRet.addField(new FieldSignature("TYPE", JSE_Lang.CLASS));
		toRet.addMethod(new MethodSignature("byteValue", 
			ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("doubleValue", 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("floatValue", 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("compare", 
			new String[] {ClassMethods.SHORT, ClassMethods.SHORT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Lang.SHORT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("intValue", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("decode", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Lang.SHORT));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Lang.SHORT));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			JSE_Lang.SHORT));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.SHORT}, 
			JSE_Lang.SHORT));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {ClassMethods.SHORT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("longValue", 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("parseShort", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.SHORT));
		toRet.addMethod(new MethodSignature("parseShort", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			ClassMethods.SHORT));
		toRet.addMethod(new MethodSignature("reverseBytes", 
			new String[] {ClassMethods.SHORT}, 
			ClassMethods.SHORT));
		toRet.addMethod(new MethodSignature("shortValue", 
			ClassMethods.SHORT));
	}
	
	public static ClassAttributes getString() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.STRING);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {"[" + ClassMethods.BYTE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {"[" + ClassMethods.BYTE, ClassMethods.INT, 
				ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {"[" + ClassMethods.BYTE, ClassMethods.INT, 
				ClassMethods.INT, JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {"[" + ClassMethods.BYTE, JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {"[" + ClassMethods.CHAR}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {"[" + ClassMethods.INT, 
				ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING_BUFFER}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING_BUILDER}));

		addStringMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addStringMembers(ClassAttributesMutant toRet) {
		addObjectMembers(toRet);
		toRet.addField(new FieldSignature("CASE_INSENSITIVE_ORDER", 
				JSE_Util.COMPARATOR));
		toRet.addMethod(new MethodSignature("getBytes", 
			"[" + ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("getBytes", 
			new String[] {JSE_Lang.STRING}, 
			"[" + ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("toCharArray", 
			"[" + ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("split", 
			new String[] {JSE_Lang.STRING}, 
			"[" + JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("split", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			"[" + JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("contains", 
			new String[] {JSE_Lang.CHAR_SEQUENCE}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("contentEquals", 
			new String[] {JSE_Lang.CHAR_SEQUENCE}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("contentEquals", 
			new String[] {JSE_Lang.STRING_BUFFER}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("endsWith", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("equalsIgnoreCase", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isEmpty", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("matches", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("regionMatches", 
			new String[] {ClassMethods.BOOLEAN, ClassMethods.INT, JSE_Lang.STRING, ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("regionMatches", 
			new String[] {ClassMethods.INT, JSE_Lang.STRING, ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("startsWith", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("startsWith", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("charAt", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("codePointAt", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("codePointBefore", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("codePointCount", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("compareToIgnoreCase", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("indexOf", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("indexOf", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("indexOf", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("indexOf", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("lastIndexOf", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("lastIndexOf", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("lastIndexOf", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("lastIndexOf", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("length", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("offsetByCodePoints", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("subSequence", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.CHAR_SEQUENCE));
		toRet.addMethod(new MethodSignature("concat", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("copyValueOf", 
			new String[] {"[" + ClassMethods.CHAR}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("copyValueOf", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("intern", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("replace", 
			new String[] {ClassMethods.CHAR, ClassMethods.CHAR}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("replace", 
			new String[] {JSE_Lang.CHAR_SEQUENCE, JSE_Lang.CHAR_SEQUENCE}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("replaceAll", 
			new String[] {JSE_Lang.STRING, JSE_Lang.STRING}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("replaceFirst", 
			new String[] {JSE_Lang.STRING, JSE_Lang.STRING}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("substring", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("substring", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toLowerCase", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toUpperCase", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("trim", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {"[" + ClassMethods.CHAR}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.BOOLEAN}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.CHAR}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.DOUBLE}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.FLOAT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.LONG}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("getChars", 
			new String[] {ClassMethods.INT, ClassMethods.INT, 
				"[" + ClassMethods.CHAR, ClassMethods.INT}));
	}
	
	public static ClassAttributes getStringBuffer() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.STRING_BUFFER);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.CHAR_SEQUENCE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addStringBufferMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addStringBufferMembers(ClassAttributesMutant toRet) {
		addObjectMembers(toRet);
		toRet.addMethod(new MethodSignature("charAt", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("capacity", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("indexOf", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("indexOf", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("lastIndexOf", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("lastIndexOf", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("length", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {ClassMethods.CHAR}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.CHAR_SEQUENCE}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.CHAR_SEQUENCE, ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("subSequence", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.CHAR_SEQUENCE));
		toRet.addMethod(new MethodSignature("substring", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("substring", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {"[" + ClassMethods.CHAR}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {ClassMethods.BOOLEAN}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {ClassMethods.CHAR}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {ClassMethods.DOUBLE}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {ClassMethods.FLOAT}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.CHAR_SEQUENCE}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.CHAR_SEQUENCE, ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.STRING_BUFFER}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {ClassMethods.LONG}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("delete", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("deleteCharAt", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, "[" + ClassMethods.CHAR}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, "[" + ClassMethods.CHAR, 
				ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, ClassMethods.BOOLEAN}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, ClassMethods.CHAR}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, ClassMethods.DOUBLE}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, ClassMethods.FLOAT}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, JSE_Lang.CHAR_SEQUENCE}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, JSE_Lang.CHAR_SEQUENCE, 
				ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, JSE_Lang.OBJECT}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, JSE_Lang.STRING}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, ClassMethods.LONG}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("replace", 
			new String[] {ClassMethods.INT, ClassMethods.INT, JSE_Lang.STRING}, 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("reverse", 
			JSE_Lang.STRING_BUFFER));
		toRet.addMethod(new MethodSignature("ensureCapacity", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("getChars", 
			new String[] {ClassMethods.INT, ClassMethods.INT, 
				"[" + ClassMethods.CHAR, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("setCharAt", 
			new String[] {ClassMethods.INT, ClassMethods.CHAR}));
		toRet.addMethod(new MethodSignature("setLength", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("trimToSize"));
	}
	
	public static ClassAttributes getStringBuilder() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.STRING_BUILDER);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.CHAR_SEQUENCE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addStringBuilderMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addStringBuilderMembers(ClassAttributesMutant toRet) {
		addObjectMembers(toRet);
		toRet.addMethod(new MethodSignature("charAt", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("capacity", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("indexOf", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("indexOf", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("lastIndexOf", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("lastIndexOf", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("length", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {ClassMethods.CHAR}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.CHAR_SEQUENCE}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.CHAR_SEQUENCE, ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("subSequence", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.CHAR_SEQUENCE));
		toRet.addMethod(new MethodSignature("substring", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("substring", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {"[" + ClassMethods.CHAR}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {ClassMethods.BOOLEAN}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {ClassMethods.CHAR}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {ClassMethods.DOUBLE}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {ClassMethods.FLOAT}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.CHAR_SEQUENCE}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.CHAR_SEQUENCE, ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.STRING_BUFFER}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {ClassMethods.LONG}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("appendCodePoint", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("delete", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("deleteCharAt", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, "[" + ClassMethods.CHAR}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, "[" + ClassMethods.CHAR, 
				ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, ClassMethods.BOOLEAN}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, ClassMethods.CHAR}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, ClassMethods.DOUBLE}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, ClassMethods.FLOAT}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, JSE_Lang.CHAR_SEQUENCE}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, JSE_Lang.CHAR_SEQUENCE,
				ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, JSE_Lang.OBJECT}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, JSE_Lang.STRING}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("insert", 
			new String[] {ClassMethods.INT, ClassMethods.LONG}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("replace", 
			new String[] {ClassMethods.INT, ClassMethods.INT, JSE_Lang.STRING}, 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("reverse", 
			JSE_Lang.STRING_BUILDER));
		toRet.addMethod(new MethodSignature("ensureCapacity", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("getChars", 
			new String[] {ClassMethods.INT, ClassMethods.INT, "[" + 
					ClassMethods.CHAR, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("setCharAt", 
			new String[] {ClassMethods.INT, ClassMethods.CHAR}));
		toRet.addMethod(new MethodSignature("setLength", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("trimToSize"));
	}
	
	public static ClassAttributes getSystem() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.SYSTEM);
		
		//constructor not visible

		addSystemMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addSystemMembers(ClassAttributesMutant toRet) {
		toRet.addField(new FieldSignature("err", JSE_IO.PRINT_STREAM));
		toRet.addField(new FieldSignature("out", JSE_IO.PRINT_STREAM));
		toRet.addMethod(new MethodSignature("identityHashCode", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getProperty", 
			new String[] {JSE_Lang.STRING, JSE_Lang.STRING}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("currentTimeMillis", 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("arraycopy", 
			new String[] {JSE_Lang.OBJECT, ClassMethods.INT, 
				JSE_Lang.OBJECT, ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("gc"));
		toRet.addMethod(new MethodSignature("setErr", 
			new String[] {JSE_IO.PRINT_STREAM}));
		toRet.addMethod(new MethodSignature("setOut", 
			new String[] {JSE_IO.PRINT_STREAM}));
	}
	public static ClassAttributes getVoid() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.VOID);

		addVoidMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addVoidMembers(ClassAttributesMutant toRet) {
		toRet.addField(new FieldSignature("TYPE", JSE_Lang.CLASS));
	}
}
