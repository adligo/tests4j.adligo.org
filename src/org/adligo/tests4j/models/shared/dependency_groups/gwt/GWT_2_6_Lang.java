package org.adligo.tests4j.models.shared.dependency_groups.gwt;

import org.adligo.tests4j.models.shared.common.ClassMethods;
import org.adligo.tests4j.models.shared.dependency.ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.ClassAttributesMutant;
import org.adligo.tests4j.models.shared.dependency.DependencyGroup;
import org.adligo.tests4j.models.shared.dependency.DependencyGroupMutant;
import org.adligo.tests4j.models.shared.dependency.FieldSignature;
import org.adligo.tests4j.models.shared.dependency.MethodSignature;
import org.adligo.tests4j.models.shared.dependency_groups.jse.JSE_1_7_Lang;
import org.adligo.tests4j.models.shared.dependency_groups.jse.JSE_Lang;

public class GWT_2_6_Lang extends DependencyGroup {
	
	public static DependencyGroupMutant create() {
		DependencyGroupMutant dgm = new DependencyGroupMutant();
		
		dgm.addClassMethods(getAppendable());
		dgm.addClassMethods(getArithmeticException());
		dgm.addClassMethods(getArrayIndexOutOfBoundsException());
		dgm.addClassMethods(getArrayStoreException());
		dgm.addClassMethods(getAssertionError());
		dgm.addClassMethods(getAutoCloseable());
		dgm.addClassMethods(getBoolean());
		dgm.addClassMethods(getCharacter());
		dgm.addClassMethods(getCharSequence());
		
		return dgm;
	}
	public static ClassAttributes getAppendable() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.APPENDABLE);
		toRet.addMethod(new MethodSignature("append", 
			new String[] {ClassMethods.CHAR}, 
			JSE_Lang.APPENDABLE));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.CHAR_SEQUENCE, ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.APPENDABLE));
		toRet.addMethod(new MethodSignature("append", 
			new String[] {JSE_Lang.CHAR_SEQUENCE}, 
			JSE_Lang.APPENDABLE));
		return new ClassAttributes(toRet);
	}

	public static ClassAttributes getArrayIndexOutOfBoundsException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION);
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		return new ClassAttributes(toRet);
	}
	public static ClassAttributes getArrayStoreException() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.ARRAY_STORE_EXCEPTION);
		
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		
		return new ClassAttributes(toRet);
	}
	
	public static ClassAttributes getAssertionError() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.ASSERTION_ERROR);
		
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.FLOAT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.CHAR}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.LONG}));
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.DOUBLE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.BOOLEAN}));
		toRet.addMethod(new MethodSignature("<init>", 
				new String[] {JSE_Lang.OBJECT}));
		return new ClassAttributes(toRet);
	}
	public static ClassAttributes getAutoCloseable() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.AUTO_CLOSEABLE);
		toRet.addMethod(new MethodSignature("close"));
		return new ClassAttributes(toRet);
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
		toRet.addMethod(new MethodSignature("notifyAll"));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("hashCode", 
			ClassMethods.INT));

		toRet.addMethod(new MethodSignature("toString", 
			new String[] {ClassMethods.BOOLEAN}, 
			JSE_Lang.STRING));
		
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.BOOLEAN}, 
			JSE_Lang.BOOLEAN));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Lang.BOOLEAN}, 
			ClassMethods.INT));
		
		toRet.addMethod(new MethodSignature("toString", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("booleanValue", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("parseBoolean", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.BOOLEAN));
		return new ClassAttributes(toRet);
	}
	public static ClassAttributes getByte() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.addField(new FieldSignature("MAX_VALUE", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("SIZE", ClassMethods.INT));
		toRet.addField(new FieldSignature("MIN_VALUE", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("TYPE", JSE_Lang.CLASS));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Lang.BYTE));
		
		toRet.addMethod(new MethodSignature("floatValue", 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {ClassMethods.BYTE}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Lang.BYTE}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.BYTE}));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("wait", 
			new String[] {ClassMethods.LONG}));
		toRet.addMethod(new MethodSignature("decode", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Lang.BYTE));
		toRet.addMethod(new MethodSignature("longValue", 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.BYTE}, 
			JSE_Lang.BYTE));
		toRet.addMethod(new MethodSignature("doubleValue", 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("getClass", 
			JSE_Lang.CLASS));
		toRet.addMethod(new MethodSignature("intValue", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("wait"));
		toRet.addMethod(new MethodSignature("hashCode", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("byteValue", 
			ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("parseByte", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("shortValue", 
			ClassMethods.SHORT));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}, 
			JSE_Lang.BYTE));
		toRet.addMethod(new MethodSignature("parseByte", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("compare", 
			new String[] {ClassMethods.BYTE, ClassMethods.BYTE}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("toString", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("notify"));
		return new ClassAttributes(toRet);
	}
	public static ClassAttributes getCharSequence() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.addMethod(new MethodSignature("subSequence", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			JSE_Lang.CHAR_SEQUENCE));
		toRet.addMethod(new MethodSignature("length", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("charAt", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("toString", 
			JSE_Lang.STRING));
		return new ClassAttributes(toRet);
	}
	
	
	public static ClassAttributes getCharacter() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.addField(new FieldSignature("FORMAT", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("CONNECTOR_PUNCTUATION", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("INITIAL_QUOTE_PUNCTUATION", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("UPPERCASE_LETTER", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("MAX_CODE_POINT", ClassMethods.INT));
		toRet.addField(new FieldSignature("DIRECTIONALITY_UNDEFINED", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("MAX_SURROGATE", ClassMethods.CHAR));
		toRet.addField(new FieldSignature("DIRECTIONALITY_BOUNDARY_NEUTRAL", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("FINAL_QUOTE_PUNCTUATION", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("MIN_CODE_POINT", ClassMethods.INT));
		toRet.addField(new FieldSignature("DIRECTIONALITY_COMMON_NUMBER_SEPARATOR", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("MAX_LOW_SURROGATE", ClassMethods.CHAR));
		toRet.addField(new FieldSignature("DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("DIRECTIONALITY_ARABIC_NUMBER", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("MAX_HIGH_SURROGATE", ClassMethods.CHAR));
		toRet.addField(new FieldSignature("MAX_RADIX", ClassMethods.INT));
		toRet.addField(new FieldSignature("MAX_VALUE", ClassMethods.CHAR));
		toRet.addField(new FieldSignature("MIN_SUPPLEMENTARY_CODE_POINT", ClassMethods.INT));
		toRet.addField(new FieldSignature("DIRECTIONALITY_EUROPEAN_NUMBER", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("DASH_PUNCTUATION", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("DIRECTIONALITY_NONSPACING_MARK", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("TITLECASE_LETTER", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("NON_SPACING_MARK", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("MATH_SYMBOL", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("DIRECTIONALITY_WHITESPACE", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("PRIVATE_USE", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("MIN_RADIX", ClassMethods.INT));
		toRet.addField(new FieldSignature("SIZE", ClassMethods.INT));
		toRet.addField(new FieldSignature("MIN_VALUE", ClassMethods.CHAR));
		toRet.addField(new FieldSignature("DIRECTIONALITY_RIGHT_TO_LEFT", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("MIN_SURROGATE", ClassMethods.CHAR));
		toRet.addField(new FieldSignature("MODIFIER_LETTER", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("CURRENCY_SYMBOL", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("SURROGATE", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("MODIFIER_SYMBOL", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("PARAGRAPH_SEPARATOR", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("SPACE_SEPARATOR", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("DIRECTIONALITY_LEFT_TO_RIGHT", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("TYPE", JSE_Lang.CLASS));
		toRet.addField(new FieldSignature("DIRECTIONALITY_PARAGRAPH_SEPARATOR", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("END_PUNCTUATION", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("DIRECTIONALITY_POP_DIRECTIONAL_FORMAT", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("DIRECTIONALITY_SEGMENT_SEPARATOR", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("LINE_SEPARATOR", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("OTHER_PUNCTUATION", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("MIN_LOW_SURROGATE", ClassMethods.CHAR));
		toRet.addField(new FieldSignature("LETTER_NUMBER", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("OTHER_LETTER", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("OTHER_NUMBER", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("COMBINING_SPACING_MARK", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("OTHER_SYMBOL", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("UNASSIGNED", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("CONTROL", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("START_PUNCTUATION", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("LOWERCASE_LETTER", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("DECIMAL_DIGIT_NUMBER", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("ENCLOSING_MARK", ClassMethods.BYTE));
		toRet.addField(new FieldSignature("MIN_HIGH_SURROGATE", ClassMethods.CHAR));
		toRet.addField(new FieldSignature("DIRECTIONALITY_OTHER_NEUTRALS", ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("isJavaLetterOrDigit", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isUpperCase", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("toChars", 
			new String[] {ClassMethods.INT, "C", ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("wait", 
			new String[] {ClassMethods.LONG, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("toUpperCase", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("toUpperCase", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("isJavaIdentifierStart", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isSurrogatePair", 
			new String[] {ClassMethods.CHAR, ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("getDirectionality", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("isJavaIdentifierStart", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("codePointBefore", 
			new String[] {"C", ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("isSupplementaryCodePoint", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("getDirectionality", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("forDigit", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("isTitleCase", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("codePointBefore", 
			new String[] {"C", ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {ClassMethods.CHAR}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("isTitleCase", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isJavaIdentifierPart", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("notifyAll"));
		toRet.addMethod(new MethodSignature("isJavaIdentifierPart", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isIdeographic", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isJavaLetter", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("toChars", 
			new String[] {ClassMethods.INT}, 
			"C"));
		toRet.addMethod(new MethodSignature("isWhitespace", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("toTitleCase", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("isWhitespace", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isLowSurrogate", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.CHAR}));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("toTitleCase", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("reverseBytes", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("wait", 
			new String[] {ClassMethods.LONG}));
		toRet.addMethod(new MethodSignature("charCount", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.CHAR}, 
			JSE_Lang.CHARACTER));
		toRet.addMethod(new MethodSignature("offsetByCodePoints", 
			new String[] {"C", ClassMethods.INT, ClassMethods.INT, ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("isValidCodePoint", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isMirrored", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("codePointAt", 
			new String[] {"C", ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("isMirrored", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("getClass", 
			JSE_Lang.CLASS));
		toRet.addMethod(new MethodSignature("codePointBefore", 
			new String[] {JSE_Lang.CHAR_SEQUENCE, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("isAlphabetic", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("codePointAt", 
			new String[] {"C", ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("isDefined", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isDefined", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Lang.CHARACTER}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("wait"));
		toRet.addMethod(new MethodSignature("isISOControl", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isISOControl", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("hashCode", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("digit", 
			new String[] {ClassMethods.CHAR, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("highSurrogate", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("isLetterOrDigit", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isLetterOrDigit", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("codePointCount", 
			new String[] {"C", ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("codePointCount", 
			new String[] {JSE_Lang.CHAR_SEQUENCE, ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("lowSurrogate", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("isBmpCodePoint", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isSpaceChar", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isUnicodeIdentifierStart", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isSpaceChar", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isSurrogate", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isUnicodeIdentifierStart", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("getType", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("toCodePoint", 
			new String[] {ClassMethods.CHAR, ClassMethods.CHAR}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("charValue", 
			ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("getType", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getNumericValue", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("isUnicodeIdentifierPart", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("getNumericValue", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("isUnicodeIdentifierPart", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isDigit", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("codePointAt", 
			new String[] {JSE_Lang.CHAR_SEQUENCE, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("offsetByCodePoints", 
			new String[] {JSE_Lang.CHAR_SEQUENCE, ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("isDigit", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isHighSurrogate", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isIdentifierIgnorable", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isLetter", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isIdentifierIgnorable", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isLetter", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isLowerCase", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isLowerCase", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("compare", 
			new String[] {ClassMethods.CHAR, ClassMethods.CHAR}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("digit", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getName", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("isSpace", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("toString", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toLowerCase", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("isUpperCase", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("toLowerCase", 
			new String[] {ClassMethods.CHAR}, 
			ClassMethods.CHAR));
		toRet.addMethod(new MethodSignature("notify"));
		return new ClassAttributes(toRet);
	}
	
	public static ClassAttributes getEnum() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Lang.ENUM);
		toRet.addMethod(new MethodSignature("<init>", 
				new String[] {JSE_Lang.STRING, ClassMethods.INT}));
		
		addObjectMembers(toRet);
		
		
		addEnumMethods(toRet);
		return new ClassAttributes(toRet);
	}
	public static void addEnumMethods(ClassAttributesMutant toRet) {
		toRet.addField(new FieldSignature("name", JSE_Lang.STRING));
		toRet.addField(new FieldSignature("ordinal", ClassMethods.INT));
		
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
		toRet.addMethod(new MethodSignature("toString", 
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
				new String[] {"java.io.PrintStream"}));
		toRet.addMethod(new MethodSignature("setStackTrace", 
			new String[] {"[" + JSE_Lang.STACK_TRACE_ELEMENT}));
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
	public GWT_2_6_Lang() {
		super(create());
	}
}