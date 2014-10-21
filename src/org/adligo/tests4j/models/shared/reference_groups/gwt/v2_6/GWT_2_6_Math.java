package org.adligo.tests4j.models.shared.reference_groups.gwt.v2_6;

import org.adligo.tests4j.models.shared.reference_groups.jse.JSE_Lang;
import org.adligo.tests4j.models.shared.reference_groups.jse.JSE_Math;
import org.adligo.tests4j.models.shared.reference_groups.jse.JSE_Util;
import org.adligo.tests4j.shared.asserts.reference.ClassAttributes;
import org.adligo.tests4j.shared.asserts.reference.ClassAttributesMutant;
import org.adligo.tests4j.shared.asserts.reference.FieldSignature;
import org.adligo.tests4j.shared.asserts.reference.MethodSignature;
import org.adligo.tests4j.shared.common.ClassMethods;

public class GWT_2_6_Math {

	public static ClassAttributes getBigDecimal() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName("java.math.BigDecimal");

		//constructors

		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {"[" + ClassMethods.CHAR}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.INT, ClassMethods.INT, JSE_Math.MATH_CONTEXT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {"[" + ClassMethods.CHAR, JSE_Math.MATH_CONTEXT}));
		
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.DOUBLE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.DOUBLE, JSE_Math.MATH_CONTEXT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, JSE_Math.MATH_CONTEXT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING, JSE_Math.MATH_CONTEXT}));
		
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Math.BIG_INTEGER}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Math.BIG_INTEGER, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Math.BIG_INTEGER, ClassMethods.INT, JSE_Math.MATH_CONTEXT}));
		
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Math.BIG_INTEGER, JSE_Math.MATH_CONTEXT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.LONG}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.LONG, JSE_Math.MATH_CONTEXT}));

		addBigDecimalMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addBigDecimalMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addNumberMembers(toRet);
		toRet.addField(new FieldSignature("ONE", JSE_Math.BIG_DECIMAL));
		toRet.addField(new FieldSignature("TEN", JSE_Math.BIG_DECIMAL));
		toRet.addField(new FieldSignature("ZERO", JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("divideAndRemainder", 
			new String[] {JSE_Math.BIG_DECIMAL}, 
			"[" + JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("divideAndRemainder", 
			new String[] {JSE_Math.BIG_DECIMAL, JSE_Math.MATH_CONTEXT}, 
			"[" + JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("byteValueExact", 
			ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("doubleValue", 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("floatValue", 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Math.BIG_DECIMAL}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("intValue", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("intValueExact", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("precision", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("scale", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("signum", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("toEngineeringString", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toPlainString", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("abs", 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("abs", 
			new String[] {JSE_Math.MATH_CONTEXT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("add", 
			new String[] {JSE_Math.BIG_DECIMAL}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("add", 
			new String[] {JSE_Math.BIG_DECIMAL, JSE_Math.MATH_CONTEXT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("divide", 
			new String[] {JSE_Math.BIG_DECIMAL}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("divide", 
			new String[] {JSE_Math.BIG_DECIMAL, ClassMethods.INT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("divide", 
			new String[] {JSE_Math.BIG_DECIMAL, ClassMethods.INT, ClassMethods.INT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("divide", 
			new String[] {JSE_Math.BIG_DECIMAL, ClassMethods.INT, JSE_Math.ROUNDING_MODE}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("divide", 
			new String[] {JSE_Math.BIG_DECIMAL, JSE_Math.MATH_CONTEXT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("divide", 
			new String[] {JSE_Math.BIG_DECIMAL, JSE_Math.ROUNDING_MODE}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("divideToIntegralValue", 
			new String[] {JSE_Math.BIG_DECIMAL}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("divideToIntegralValue", 
			new String[] {JSE_Math.BIG_DECIMAL, JSE_Math.MATH_CONTEXT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("max", 
			new String[] {JSE_Math.BIG_DECIMAL}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("min", 
			new String[] {JSE_Math.BIG_DECIMAL}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("movePointLeft", 
			new String[] {ClassMethods.INT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("movePointRight", 
			new String[] {ClassMethods.INT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("multiply", 
			new String[] {JSE_Math.BIG_DECIMAL}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("multiply", 
			new String[] {JSE_Math.BIG_DECIMAL, JSE_Math.MATH_CONTEXT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("negate", 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("negate", 
			new String[] {JSE_Math.MATH_CONTEXT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("plus", 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("plus", 
			new String[] {JSE_Math.MATH_CONTEXT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("pow", 
			new String[] {ClassMethods.INT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("pow", 
			new String[] {ClassMethods.INT, JSE_Math.MATH_CONTEXT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("remainder", 
			new String[] {JSE_Math.BIG_DECIMAL}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("remainder", 
			new String[] {JSE_Math.BIG_DECIMAL, JSE_Math.MATH_CONTEXT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("round", 
			new String[] {JSE_Math.MATH_CONTEXT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("scaleByPowerOfTen", 
			new String[] {ClassMethods.INT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("setScale", 
			new String[] {ClassMethods.INT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("setScale", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("setScale", 
			new String[] {ClassMethods.INT, JSE_Math.ROUNDING_MODE}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("stripTrailingZeros", 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("subtract", 
			new String[] {JSE_Math.BIG_DECIMAL}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("subtract", 
			new String[] {JSE_Math.BIG_DECIMAL, JSE_Math.MATH_CONTEXT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("ulp", 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.DOUBLE}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.LONG}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.LONG, ClassMethods.INT}, 
			JSE_Math.BIG_DECIMAL));
		toRet.addMethod(new MethodSignature("toBigInteger", 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("toBigIntegerExact", 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("unscaledValue", 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("longValue", 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("longValueExact", 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("shortValueExact", 
			ClassMethods.SHORT));
	}
	
	public static ClassAttributes getBigInteger() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Math.BIG_INTEGER);

		//constructors
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {"[" + ClassMethods.BYTE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, "[" + ClassMethods.BYTE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, ClassMethods.INT, JSE_Util.RANDOM}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, JSE_Util.RANDOM}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING, ClassMethods.INT}));

		addBigIntegerMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addBigIntegerMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addNumberMembers(toRet);
		toRet.addField(new FieldSignature("ONE", JSE_Math.BIG_INTEGER));
		toRet.addField(new FieldSignature("TEN", JSE_Math.BIG_INTEGER));
		toRet.addField(new FieldSignature("ZERO", JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("toByteArray", 
			"[" + ClassMethods.BYTE));
		toRet.addMethod(new MethodSignature("divideAndRemainder", 
			new String[] {JSE_Math.BIG_INTEGER}, 
			"[" + JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("isProbablePrime", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("testBit", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("bitCount", 
				ClassMethods.INT));
		toRet.addMethod(new MethodSignature("bitLength", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Math.BIG_INTEGER}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getLowestSetBit", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("intValue", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("signum", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("abs", 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("add", 
			new String[] {JSE_Math.BIG_INTEGER}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("and", 
			new String[] {JSE_Math.BIG_INTEGER}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("andNot", 
			new String[] {JSE_Math.BIG_INTEGER}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("clearBit", 
			new String[] {ClassMethods.INT}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("divide", 
			new String[] {JSE_Math.BIG_INTEGER}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("flipBit", 
			new String[] {ClassMethods.INT}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("gcd", 
			new String[] {JSE_Math.BIG_INTEGER}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("max", 
			new String[] {JSE_Math.BIG_INTEGER}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("min", 
			new String[] {JSE_Math.BIG_INTEGER}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("mod", 
			new String[] {JSE_Math.BIG_INTEGER}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("modInverse", 
			new String[] {JSE_Math.BIG_INTEGER}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("modPow", 
			new String[] {JSE_Math.BIG_INTEGER, JSE_Math.BIG_INTEGER}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("multiply", 
			new String[] {JSE_Math.BIG_INTEGER}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("negate", 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("nextProbablePrime", 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("not", 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("or", 
			new String[] {JSE_Math.BIG_INTEGER}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("pow", 
			new String[] {ClassMethods.INT}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("probablePrime", 
			new String[] {ClassMethods.INT, JSE_Util.RANDOM}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("remainder", 
			new String[] {JSE_Math.BIG_INTEGER}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("setBit", 
			new String[] {ClassMethods.INT}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("shiftLeft", 
			new String[] {ClassMethods.INT}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("shiftRight", 
			new String[] {ClassMethods.INT}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("subtract", 
			new String[] {JSE_Math.BIG_INTEGER}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.LONG}, 
			JSE_Math.BIG_INTEGER));
		toRet.addMethod(new MethodSignature("xor", 
			new String[] {JSE_Math.BIG_INTEGER}, 
			JSE_Math.BIG_INTEGER));
	}
	
	public static ClassAttributes getMathContext() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Math.MATH_CONTEXT);

		//constructors
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, JSE_Math.ROUNDING_MODE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));

		addMathContextMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addMathContextMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addObjectMembers(toRet);
		toRet.addField(new FieldSignature("DECIMAL128", JSE_Math.MATH_CONTEXT));
		toRet.addField(new FieldSignature("DECIMAL32", JSE_Math.MATH_CONTEXT));
		toRet.addField(new FieldSignature("DECIMAL64", JSE_Math.MATH_CONTEXT));
		toRet.addField(new FieldSignature("UNLIMITED", JSE_Math.MATH_CONTEXT));

		toRet.addMethod(new MethodSignature("getPrecision", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getRoundingMode", 
			JSE_Math.ROUNDING_MODE));
	}
	public static ClassAttributes getRoundingMode() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Math.ROUNDING_MODE);


		addRoundingModeMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addRoundingModeMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addEnumMembers(toRet);
		toRet.addField(new FieldSignature("CEILING", JSE_Math.ROUNDING_MODE));
		toRet.addField(new FieldSignature("DOWN", JSE_Math.ROUNDING_MODE));
		toRet.addField(new FieldSignature("FLOOR", JSE_Math.ROUNDING_MODE));
		toRet.addField(new FieldSignature("HALF_DOWN", JSE_Math.ROUNDING_MODE));
		toRet.addField(new FieldSignature("HALF_EVEN", JSE_Math.ROUNDING_MODE));
		toRet.addField(new FieldSignature("HALF_UP", JSE_Math.ROUNDING_MODE));
		toRet.addField(new FieldSignature("UNNECESSARY", JSE_Math.ROUNDING_MODE));
		toRet.addField(new FieldSignature("UP", JSE_Math.ROUNDING_MODE));
		toRet.addMethod(new MethodSignature("values", 
			"[" + JSE_Math.ROUNDING_MODE));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {ClassMethods.INT}, 
			JSE_Math.ROUNDING_MODE));
		toRet.addMethod(new MethodSignature("valueOf", 
			new String[] {JSE_Lang.STRING}, 
			JSE_Math.ROUNDING_MODE));
	}
}
