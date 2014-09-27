package org.adligo.tests4j.models.shared.dependency_groups.gwt.v2_6;

import org.adligo.tests4j.models.shared.dependency_groups.jse.JSE_Lang;
import org.adligo.tests4j.models.shared.dependency_groups.jse.JSE_Util;
import org.adligo.tests4j.shared.asserts.dependency.ClassAttributes;
import org.adligo.tests4j.shared.asserts.dependency.ClassAttributesMutant;
import org.adligo.tests4j.shared.asserts.dependency.FieldSignature;
import org.adligo.tests4j.shared.asserts.dependency.MethodSignature;
import org.adligo.tests4j.shared.common.ClassMethods;

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
	
	public static ClassAttributes getDate() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.DATE);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, ClassMethods.INT, ClassMethods.INT, 
				ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, ClassMethods.INT, ClassMethods.INT, 
				ClassMethods.INT, ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.STRING}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.LONG}));

		addDateMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addDateMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addObjectMembers(toRet);
		toRet.addMethod(new MethodSignature("after", 
			new String[] {JSE_Util.DATE}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("before", 
			new String[] {JSE_Util.DATE}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("compareTo", 
			new String[] {JSE_Util.DATE}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getDate", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getDay", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getHours", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getMinutes", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getMonth", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getSeconds", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getTimezoneOffset", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getYear", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("hashCode", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("clone", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("toGMTString", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toLocaleString", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("UTC", 
			new String[] {ClassMethods.INT, ClassMethods.INT, 
				ClassMethods.INT, ClassMethods.INT, ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("getTime", 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("parse", 
			new String[] {JSE_Lang.STRING}, 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("setDate", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("setHours", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("setMinutes", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("setMonth", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("setSeconds", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("setTime", 
			new String[] {ClassMethods.LONG}));
		toRet.addMethod(new MethodSignature("setYear", 
			new String[] {ClassMethods.INT}));
	}
	
	public static ClassAttributes getAbstractCollection() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.ABSTRACT_COLLECTION);

		addAbstractCollectionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addAbstractCollectionMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addObjectMembers(toRet);
		toRet.addMethod(new MethodSignature("toArray", 
			"[" + JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("toArray", 
			new String[] {"[" + JSE_Lang.OBJECT}, 
			"[" + JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("add", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("addAll", 
			new String[] {JSE_Util.COLLECTION}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("contains", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("containsAll", 
			new String[] {JSE_Util.COLLECTION}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isEmpty", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("remove", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("removeAll", 
			new String[] {JSE_Util.COLLECTION}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("retainAll", 
			new String[] {JSE_Util.COLLECTION}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("size", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("iterator", 
			JSE_Util.ITERATOR));
		toRet.addMethod(new MethodSignature("clear"));
	}
	public static ClassAttributes getAbstractList() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.ABSTRACT_LIST);


		addAbstractListMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addAbstractListMembers(ClassAttributesMutant toRet) {
		addAbstractCollectionMembers(toRet);
		toRet.addMethod(new MethodSignature("addAll", 
			new String[] {ClassMethods.INT, JSE_Util.COLLECTION}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("indexOf", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("lastIndexOf", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("get", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("remove", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("set", 
			new String[] {ClassMethods.INT, JSE_Lang.OBJECT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("iterator", 
			JSE_Util.ITERATOR));
		toRet.addMethod(new MethodSignature("subList", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			JSE_Util.LIST));
		toRet.addMethod(new MethodSignature("listIterator", 
			JSE_Util.LIST_ITERATOR));
		toRet.addMethod(new MethodSignature("listIterator", 
			new String[] {ClassMethods.INT}, 
			JSE_Util.LIST_ITERATOR));
		toRet.addMethod(new MethodSignature("add", 
			new String[] {ClassMethods.INT, JSE_Lang.OBJECT}));
	}
	public static ClassAttributes getAbstractMap() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.ABSTRACT_MAP);

		addAbstractMapMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addAbstractMapMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addObjectMembers(toRet);
		toRet.addMethod(new MethodSignature("containsKey", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("containsValue", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isEmpty", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("size", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("get", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("put", 
			new String[] {JSE_Lang.OBJECT, JSE_Lang.OBJECT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("remove", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("values", 
			JSE_Util.COLLECTION));
		toRet.addMethod(new MethodSignature("entrySet", 
			JSE_Util.SET));
		toRet.addMethod(new MethodSignature("keySet", 
			JSE_Util.SET));
		toRet.addMethod(new MethodSignature("clear"));
		toRet.addMethod(new MethodSignature("putAll", 
			new String[] {JSE_Util.MAP}));
	}
	public static ClassAttributes getAbstractQueue() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.ABSTRACT_QUEUE);

		addAbstractQueueMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addAbstractQueueMembers(ClassAttributesMutant toRet) {
		addAbstractCollectionMembers(toRet);
		toRet.addMethod(new MethodSignature("element", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("offer", 
				new String[] {JSE_Lang.OBJECT},
				ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("peek", 
				JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("poll", 
				JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("remove", 
				JSE_Lang.OBJECT));
	}
	
	public static ClassAttributes getAbstractSequentialList() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.ABSTRACT_SEQUENTIAL_LIST);

		//constructors

		addAbstractSequentialListMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addAbstractSequentialListMembers(ClassAttributesMutant toRet) {
		addAbstractListMembers(toRet);
		toRet.addMethod(new MethodSignature("add", 
				new String[] {ClassMethods.INT, JSE_Lang.OBJECT}));
		toRet.addMethod(new MethodSignature("addAll", 
			new String[] {ClassMethods.INT, JSE_Util.COLLECTION}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("get", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("remove", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("set", 
			new String[] {ClassMethods.INT, JSE_Lang.OBJECT}, 
			JSE_Lang.OBJECT));

	}
	public static ClassAttributes getAbstractSet() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.ABSTRACT_SET);

		//constructors
		addAbstractSetMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addAbstractSetMembers(ClassAttributesMutant toRet) {
		addAbstractCollectionMembers(toRet);
	}
	
	public static ClassAttributes getArrayList() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName("java.util.ArrayList");

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.COLLECTION}));

		addArrayListMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addArrayListMembers(ClassAttributesMutant toRet) {
		addAbstractListMembers(toRet);
		toRet.addMethod(new MethodSignature("toArray", 
			"[" + JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("toArray", 
			new String[] {"[" + JSE_Lang.OBJECT}, 
			"[" + JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("clone", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("set", 
			new String[] {ClassMethods.INT, JSE_Lang.OBJECT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("ensureCapacity", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("trimToSize"));
	}
	
	public static ClassAttributes getArrays() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.ARRAYS);


		addArraysMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addArraysMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("deepEquals", 
			new String[] {"[" + JSE_Lang.OBJECT, "[" + JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {"[" + ClassMethods.BYTE, "[" + ClassMethods.BYTE}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {"[" + ClassMethods.CHAR, "[" + ClassMethods.CHAR}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {"[" + ClassMethods.DOUBLE, "[" + ClassMethods.DOUBLE}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {"[" + ClassMethods.FLOAT, "[" + ClassMethods.FLOAT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {"[" + ClassMethods.INT, "[" + ClassMethods.INT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {"[" + ClassMethods.LONG, "[" + ClassMethods.LONG}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {"[" + JSE_Lang.OBJECT, "[" + JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {"[" + ClassMethods.SHORT, "[" + ClassMethods.SHORT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {"[" + ClassMethods.BOOLEAN, "[" + ClassMethods.BOOLEAN}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("binarySearch", 
			new String[] {"[" + ClassMethods.BYTE, ClassMethods.BYTE}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("binarySearch", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.CHAR}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("binarySearch", 
			new String[] {"[" + ClassMethods.DOUBLE, ClassMethods.DOUBLE}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("binarySearch", 
			new String[] {"[" + ClassMethods.FLOAT, ClassMethods.FLOAT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("binarySearch", 
			new String[] {"[" + ClassMethods.INT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("binarySearch", 
			new String[] {"[" + ClassMethods.LONG, ClassMethods.LONG}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("binarySearch", 
			new String[] {"[" + JSE_Lang.OBJECT, JSE_Lang.OBJECT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("binarySearch", 
			new String[] {"[" + JSE_Lang.OBJECT, JSE_Lang.OBJECT, JSE_Util.COMPARATOR}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("binarySearch", 
			new String[] {"[" + ClassMethods.SHORT, ClassMethods.SHORT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("deepHashCode", 
			new String[] {"[" + JSE_Lang.OBJECT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("hashCode", 
			new String[] {"[" + ClassMethods.BYTE}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("hashCode", 
			new String[] {"[" + ClassMethods.CHAR}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("hashCode", 
			new String[] {"[" + ClassMethods.DOUBLE}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("hashCode", 
			new String[] {"[" + ClassMethods.FLOAT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("hashCode", 
			new String[] {"[" + ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("hashCode", 
			new String[] {"[" + ClassMethods.LONG}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("hashCode", 
			new String[] {"[" + JSE_Lang.OBJECT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("hashCode", 
			new String[] {"[" + ClassMethods.SHORT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("hashCode", 
			new String[] {"[" + ClassMethods.BOOLEAN}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("deepToString", 
			new String[] {"[" + JSE_Lang.OBJECT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {"[" + ClassMethods.BYTE}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {"[" + ClassMethods.CHAR}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {"[" + ClassMethods.DOUBLE}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {"[" + ClassMethods.FLOAT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {"[" + ClassMethods.INT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {"[" + ClassMethods.LONG}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {"[" + JSE_Lang.OBJECT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {"[" + ClassMethods.SHORT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {"[" + ClassMethods.BOOLEAN}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("asList", 
			new String[] {"[" + JSE_Lang.OBJECT}, 
			JSE_Util.LIST));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + ClassMethods.BYTE, ClassMethods.BYTE}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + ClassMethods.BYTE, ClassMethods.INT, 
				ClassMethods.INT, ClassMethods.BYTE}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.CHAR}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.INT, 
				ClassMethods.INT, ClassMethods.CHAR}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + ClassMethods.DOUBLE, ClassMethods.DOUBLE}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + ClassMethods.DOUBLE, ClassMethods.INT, 
				ClassMethods.INT, ClassMethods.DOUBLE}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + ClassMethods.FLOAT, ClassMethods.FLOAT}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + ClassMethods.FLOAT, ClassMethods.INT, 
				ClassMethods.INT, ClassMethods.FLOAT}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + ClassMethods.INT, ClassMethods.INT, 
				ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + ClassMethods.LONG, ClassMethods.INT, 
				ClassMethods.INT, ClassMethods.LONG}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + ClassMethods.LONG, ClassMethods.LONG}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + JSE_Lang.OBJECT, ClassMethods.INT, 
				ClassMethods.INT, JSE_Lang.OBJECT}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + JSE_Lang.OBJECT, JSE_Lang.OBJECT}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + ClassMethods.SHORT, ClassMethods.INT, 
				ClassMethods.INT, ClassMethods.SHORT}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + ClassMethods.SHORT, ClassMethods.SHORT}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + ClassMethods.BOOLEAN, ClassMethods.BOOLEAN}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {"[" + ClassMethods.BOOLEAN, ClassMethods.INT, 
				ClassMethods.INT, ClassMethods.BOOLEAN}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + ClassMethods.BYTE}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + ClassMethods.BYTE, ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + ClassMethods.CHAR}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + ClassMethods.CHAR, ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + ClassMethods.DOUBLE}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + ClassMethods.DOUBLE, ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + ClassMethods.FLOAT}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + ClassMethods.FLOAT, ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + ClassMethods.INT, ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + ClassMethods.LONG}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + ClassMethods.LONG, ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + JSE_Lang.OBJECT}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + JSE_Lang.OBJECT, ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + JSE_Lang.OBJECT, ClassMethods.INT, 
				ClassMethods.INT, JSE_Util.COMPARATOR}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + JSE_Lang.OBJECT, JSE_Util.COMPARATOR}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + ClassMethods.SHORT}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {"[" + ClassMethods.SHORT, ClassMethods.INT, ClassMethods.INT}));
	}
	
	public static ClassAttributes getCollections() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.COLLECTIONS);

		addCollectionsMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addCollectionsMembers(ClassAttributesMutant toRet) {
		toRet.addField(new FieldSignature("EMPTY_LIST", JSE_Util.LIST));
		toRet.addField(new FieldSignature("EMPTY_MAP", JSE_Util.MAP));
		toRet.addField(new FieldSignature("EMPTY_SET", JSE_Util.SET));
		toRet.addMethod(new MethodSignature("addAll", 
			new String[] {JSE_Util.COLLECTION, "[" + JSE_Lang.OBJECT }, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("disjoint", 
			new String[] {JSE_Util.COLLECTION, JSE_Util.COLLECTION}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("replaceAll", 
			new String[] {JSE_Util.LIST, JSE_Lang.OBJECT, JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("binarySearch", 
			new String[] {JSE_Util.LIST, JSE_Lang.OBJECT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("binarySearch", 
			new String[] {JSE_Util.LIST, JSE_Lang.OBJECT, JSE_Util.COMPARATOR}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("frequency", 
			new String[] {JSE_Util.COLLECTION, JSE_Lang.OBJECT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("max", 
			new String[] {JSE_Util.COLLECTION}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("max", 
			new String[] {JSE_Util.COLLECTION, JSE_Util.COMPARATOR}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("min", 
			new String[] {JSE_Util.COLLECTION}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("min", 
			new String[] {JSE_Util.COLLECTION, JSE_Util.COMPARATOR}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("list", 
			new String[] {JSE_Util.ENUMERATION}, 
			JSE_Util.ARRAY_LIST));
		toRet.addMethod(new MethodSignature("unmodifiableCollection", 
			new String[] {JSE_Util.COLLECTION}, 
			JSE_Util.COLLECTION));
		toRet.addMethod(new MethodSignature("reverseOrder", 
			JSE_Util.COMPARATOR));
		toRet.addMethod(new MethodSignature("reverseOrder", 
			new String[] {JSE_Util.COMPARATOR}, 
			JSE_Util.COMPARATOR));
		toRet.addMethod(new MethodSignature("enumeration", 
			new String[] {JSE_Util.COLLECTION}, 
			JSE_Util.ENUMERATION));
		toRet.addMethod(new MethodSignature("emptyList", 
			JSE_Util.LIST));
		toRet.addMethod(new MethodSignature("nCopies", 
			new String[] {ClassMethods.INT, JSE_Lang.OBJECT}, 
			JSE_Util.LIST));
		toRet.addMethod(new MethodSignature("singletonList", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Util.LIST));		toRet.addMethod(new MethodSignature("unmodifiableList", 
			new String[] {JSE_Util.LIST}, 
			JSE_Util.LIST));
		toRet.addMethod(new MethodSignature("emptyListIterator", 
			JSE_Util.LIST_ITERATOR));
		toRet.addMethod(new MethodSignature("emptyMap", 
			JSE_Util.MAP));
		toRet.addMethod(new MethodSignature("singletonMap", 
			new String[] {JSE_Lang.OBJECT, JSE_Lang.OBJECT}, 
			JSE_Util.MAP));
		toRet.addMethod(new MethodSignature("unmodifiableMap", 
			new String[] {JSE_Util.MAP}, 
			JSE_Util.MAP));
		toRet.addMethod(new MethodSignature("emptySet", 
			JSE_Util.SET));
		toRet.addMethod(new MethodSignature("singleton", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Util.SET));
		toRet.addMethod(new MethodSignature("unmodifiableSet", 
			new String[] {JSE_Util.SET}, 
			JSE_Util.SET));
		toRet.addMethod(new MethodSignature("unmodifiableSortedMap", 
			new String[] {JSE_Util.SORTED_MAP}, 
			JSE_Util.SORTED_MAP));
		toRet.addMethod(new MethodSignature("unmodifiableSortedSet", 
			new String[] {JSE_Util.SORTED_SET}, 
			JSE_Util.SORTED_SET));
		toRet.addMethod(new MethodSignature("copy", 
			new String[] {JSE_Util.LIST, JSE_Util.LIST}));
		toRet.addMethod(new MethodSignature("fill", 
			new String[] {JSE_Util.LIST, JSE_Lang.OBJECT}));
		toRet.addMethod(new MethodSignature("reverse", 
			new String[] {JSE_Util.LIST}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {JSE_Util.LIST}));
		toRet.addMethod(new MethodSignature("sort", 
			new String[] {JSE_Util.LIST, JSE_Util.COMPARATOR}));
		toRet.addMethod(new MethodSignature("swap", 
			new String[] {JSE_Util.LIST, ClassMethods.INT, ClassMethods.INT}));
	}
	public static ClassAttributes getCollection() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.COLLECTION);


		addCollectionMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addCollectionMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("toArray", 
			"[" + JSE_Lang.OBJECT ));
		toRet.addMethod(new MethodSignature("toArray", 
			new String[] {"[" + JSE_Lang.OBJECT }, 
			"[" + JSE_Lang.OBJECT ));
		toRet.addMethod(new MethodSignature("add", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("equals", 
				new String[] {JSE_Lang.OBJECT}, 
				ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("hashCode", 
				ClassMethods.INT));
		toRet.addMethod(new MethodSignature("addAll", 
			new String[] {JSE_Util.COLLECTION}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("contains", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("containsAll", 
			new String[] {JSE_Util.COLLECTION}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isEmpty", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("remove", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("removeAll", 
			new String[] {JSE_Util.COLLECTION}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("retainAll", 
			new String[] {JSE_Util.COLLECTION}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("iterator", 
			JSE_Util.ITERATOR));
		toRet.addMethod(new MethodSignature("size", 
				ClassMethods.INT));
		toRet.addMethod(new MethodSignature("clear"));
	}
	
	public static ClassAttributes getComparator() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.COMPARATOR);


		addComparatorMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addComparatorMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("compare", 
			new String[] {JSE_Lang.OBJECT, JSE_Lang.OBJECT}, 
			ClassMethods.INT));
	}
	public static ClassAttributes getEnumMap() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.ENUM_MAP);

		//constructors
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.CLASS}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.ENUM_MAP}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.MAP}));

		addEnumMapMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addEnumMapMembers(ClassAttributesMutant toRet) {
		addAbstractMapMembers(toRet);
	
		toRet.addMethod(new MethodSignature("clone", 
			JSE_Util.ENUM_MAP));
		toRet.addMethod(new MethodSignature("put", 
				new String [] {JSE_Lang.ENUM, JSE_Lang.OBJECT},
				JSE_Lang.OBJECT));
	}
	public static ClassAttributes getEnumSet() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.ENUM_SET);

		addEnumSetMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addEnumSetMembers(ClassAttributesMutant toRet) {
		addAbstractSetMembers(toRet);
		toRet.addMethod(new MethodSignature("allOf", 
			new String[] {JSE_Lang.CLASS}, 
			JSE_Util.ENUM_SET));
		toRet.addMethod(new MethodSignature("clone", 
			JSE_Util.ENUM_SET));
		toRet.addMethod(new MethodSignature("complementOf", 
			new String[] {JSE_Util.ENUM_SET}, 
			JSE_Util.ENUM_SET));
		toRet.addMethod(new MethodSignature("copyOf", 
			new String[] {JSE_Util.COLLECTION}, 
			JSE_Util.ENUM_SET));
		toRet.addMethod(new MethodSignature("copyOf", 
			new String[] {JSE_Util.ENUM_SET}, 
			JSE_Util.ENUM_SET));
		toRet.addMethod(new MethodSignature("noneOf", 
			new String[] {JSE_Lang.CLASS}, 
			JSE_Util.ENUM_SET));
		toRet.addMethod(new MethodSignature("of", 
			new String[] {JSE_Lang.ENUM}, 
			JSE_Util.ENUM_SET));
		toRet.addMethod(new MethodSignature("of", 
			new String[] {JSE_Lang.ENUM, "[" + JSE_Lang.ENUM}, 
			JSE_Util.ENUM_SET));
		toRet.addMethod(new MethodSignature("range", 
			new String[] {JSE_Lang.ENUM, JSE_Lang.ENUM}, 
			JSE_Util.ENUM_SET));
	}
	
	public static ClassAttributes getEnumeration() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.ENUMERATION);


		addEnumerationMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addEnumerationMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("hasMoreElements", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("nextElement", 
			JSE_Lang.OBJECT));
	}
	public static ClassAttributes getEventListener() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.EVENT_LISTENER);


		return new ClassAttributes(toRet);
	}

	public static ClassAttributes getEventObject() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.EVENT_OBJECT);

		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Lang.OBJECT}));

		addEventObjectMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addEventObjectMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addObjectMembers(toRet);
		toRet.addMethod(new MethodSignature("getSource", 
			JSE_Lang.OBJECT));
	}
	public static ClassAttributes getHashMap() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.HASH_MAP);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, ClassMethods.FLOAT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.MAP}));

		addHashMapMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addHashMapMembers(ClassAttributesMutant toRet) {
		addAbstractMapMembers(toRet);
		
		toRet.addMethod(new MethodSignature("clone", 
			JSE_Lang.OBJECT));
	}
	
	public static ClassAttributes getHashSet() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.HASH_SET);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, ClassMethods.FLOAT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.COLLECTION}));

		addHashSetMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addHashSetMembers(ClassAttributesMutant toRet) {
		addAbstractSetMembers(toRet);
		toRet.addMethod(new MethodSignature("clone", 
			JSE_Lang.OBJECT));
	}
	public static ClassAttributes getIdentityHashMap() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.IDENTITY_HASH_MAP);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.MAP}));

		addIdentityHashMapMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addIdentityHashMapMembers(ClassAttributesMutant toRet) {
		addAbstractMapMembers(toRet);
		toRet.addMethod(new MethodSignature("clone", 
			JSE_Lang.OBJECT));
	}
	public static ClassAttributes getIterator() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.ITERATOR);


		addIteratorMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addIteratorMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("hasNext", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("next", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("remove"));
	}
	public static ClassAttributes getLinkedHashMap() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.LINKED_HASH_MAP);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, ClassMethods.FLOAT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, ClassMethods.FLOAT, ClassMethods.BOOLEAN}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.MAP}));

		addLinkedHashMapMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addLinkedHashMapMembers(ClassAttributesMutant toRet) {
		addHashMapMembers(toRet);
	}
	
	public static ClassAttributes getLinkedHashSet() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.LINKED_HASH_SET);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, ClassMethods.FLOAT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.COLLECTION}));

		addLinkedHashSetMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addLinkedHashSetMembers(ClassAttributesMutant toRet) {
		addHashSetMembers(toRet);
	}
	public static ClassAttributes getLinkedList() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.LINKED_LIST);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.COLLECTION}));

		addLinkedListMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addLinkedListMembers(ClassAttributesMutant toRet) {
		addAbstractSequentialListMembers(toRet);
		toRet.addMethod(new MethodSignature("addAll", 
			new String[] {ClassMethods.INT, JSE_Util.COLLECTION}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("addFirst", 
				new String[] {JSE_Lang.OBJECT}));
		toRet.addMethod(new MethodSignature("addLast", 
				new String[] {JSE_Lang.OBJECT}));
		toRet.addMethod(new MethodSignature("clone", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("get", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("getFirst", 
				JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("getLast", 
				JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("remove", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("removeFirst", 
				JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("removeLast", 
				JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("remove", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("peek", 
				JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("poll", 
				JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("listIterator", 
			new String[] {ClassMethods.INT}, 
			JSE_Util.LIST_ITERATOR));
		toRet.addMethod(new MethodSignature("add", 
			new String[] {ClassMethods.INT, JSE_Lang.OBJECT}));
	}
	public static ClassAttributes getList() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.LIST);


		addListMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addListMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addIterableMembers(toRet);
		addCollectionMembers(toRet);
		
		toRet.addMethod(new MethodSignature("addAll", 
			new String[] {ClassMethods.INT, JSE_Util.COLLECTION}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("indexOf", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("lastIndexOf", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("subList", 
			new String[] {ClassMethods.INT, ClassMethods.INT}, 
			JSE_Util.LIST));
		toRet.addMethod(new MethodSignature("listIterator", 
			JSE_Util.LIST_ITERATOR));
		toRet.addMethod(new MethodSignature("listIterator", 
			new String[] {ClassMethods.INT}, 
			JSE_Util.LIST_ITERATOR));
		toRet.addMethod(new MethodSignature("add", 
			new String[] {ClassMethods.INT, JSE_Lang.OBJECT}));
		toRet.addMethod(new MethodSignature("get", 
				new String[] {ClassMethods.INT}, JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("remove", 
				new String[] {ClassMethods.INT}, JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("set", 
				new String[] {ClassMethods.INT, JSE_Lang.OBJECT}, 
				JSE_Lang.OBJECT));
	}
	public static ClassAttributes getListIterator() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.LIST_ITERATOR);


		addListIteratorMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addListIteratorMembers(ClassAttributesMutant toRet) {
		addIteratorMembers(toRet);
		
		toRet.addMethod(new MethodSignature("hasNext", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("hasPrevious", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("nextIndex", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("previousIndex", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("next", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("previous", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("add", 
			new String[] {JSE_Lang.OBJECT}));
		toRet.addMethod(new MethodSignature("remove"));
		toRet.addMethod(new MethodSignature("set", 
			new String[] {JSE_Lang.OBJECT}));
	}

	public static ClassAttributes getMap() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.MAP);


		addMapMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addMapMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("containsKey", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("containsValue", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("isEmpty", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("hashCode", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("size", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("get", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("put", 
			new String[] {JSE_Lang.OBJECT, JSE_Lang.OBJECT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("remove", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("values", 
			JSE_Util.COLLECTION));
		toRet.addMethod(new MethodSignature("entrySet", 
			JSE_Util.SET));
		toRet.addMethod(new MethodSignature("keySet", 
			JSE_Util.SET));
		toRet.addMethod(new MethodSignature("clear"));
		toRet.addMethod(new MethodSignature("putAll", 
			new String[] {JSE_Util.MAP}));
	}
	public static ClassAttributes getMapEntry() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.MAP_ENTRY);


		addMapEntryMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addMapEntryMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("hashCode", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("getKey", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("getValue", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("setValue", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Lang.OBJECT));
	}
	public static ClassAttributes getObjects() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.OBJECTS);

		addObjectsMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addObjectsMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("deepEquals", 
			new String[] {JSE_Lang.OBJECT, JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("equals", 
			new String[] {JSE_Lang.OBJECT, JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("compare", 
			new String[] {JSE_Lang.OBJECT, JSE_Lang.OBJECT, JSE_Util.COMPARATOR}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("hash", 
			new String[] {"[" + JSE_Lang.OBJECT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("hashCode", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("requireNonNull", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("requireNonNull", 
			new String[] {JSE_Lang.OBJECT, JSE_Lang.STRING}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("toString", 
			new String[] {JSE_Lang.OBJECT, JSE_Lang.STRING}, 
			JSE_Lang.STRING));
	}
	
	public static ClassAttributes getPriorityQueue() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.PRIORITY_QUEUE);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, JSE_Util.COMPARATOR}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.COLLECTION}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.PRIORITY_QUEUE}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.SORTED_SET}));

		addPriorityQueueMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addPriorityQueueMembers(ClassAttributesMutant toRet) {
		addAbstractQueueMembers(toRet);
		toRet.addMethod(new MethodSignature("add", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("offer", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("peek", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("poll", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("comparator", 
			JSE_Util.COMPARATOR));
		toRet.addMethod(new MethodSignature("clear"));
	}
	public static ClassAttributes getQueue() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.QUEUE);


		addQueueMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addQueueMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("add", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("offer", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("element", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("peek", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("poll", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("remove", 
			JSE_Lang.OBJECT));
	}
	public static ClassAttributes getRandom() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.RANDOM);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.LONG}));

		addRandomMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addRandomMembers(ClassAttributesMutant toRet) {
		GWT_2_6_Lang.addObjectMembers(toRet);
		toRet.addMethod(new MethodSignature("nextBoolean", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("nextDouble", 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("nextGaussian", 
			ClassMethods.DOUBLE));
		toRet.addMethod(new MethodSignature("nextFloat", 
			ClassMethods.FLOAT));
		toRet.addMethod(new MethodSignature("nextInt", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("nextInt", 
			new String[] {ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("nextLong", 
			ClassMethods.LONG));
		toRet.addMethod(new MethodSignature("nextBytes", 
			new String[] {"[" + ClassMethods.BYTE}));
		toRet.addMethod(new MethodSignature("setSeed", 
			new String[] {ClassMethods.LONG}));
	}

	public static ClassAttributes getRandomAccess() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.RANDOM_ACCESS);

		addRandomAccessMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addRandomAccessMembers(ClassAttributesMutant toRet) {
	}
	public static ClassAttributes getSet() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.SET);


		addSetMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addSetMembers(ClassAttributesMutant toRet) {
		addCollectionMembers(toRet);
		GWT_2_6_Lang.addIterableMembers(toRet);
	}
	
	public static ClassAttributes getSortedMap() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.SORTED_MAP);

		addSortedMapMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addSortedMapMembers(ClassAttributesMutant toRet) {
		addMapMembers(toRet);
		toRet.addMethod(new MethodSignature("firstKey", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("lastKey", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("comparator", 
			JSE_Util.COMPARATOR));
		toRet.addMethod(new MethodSignature("headMap", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Util.SORTED_MAP));
		toRet.addMethod(new MethodSignature("subMap", 
			new String[] {JSE_Lang.OBJECT, JSE_Lang.OBJECT}, 
			JSE_Util.SORTED_MAP));
		toRet.addMethod(new MethodSignature("tailMap", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Util.SORTED_MAP));
	}
	
	public static ClassAttributes getSortedSet() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.SORTED_SET);


		addSortedSetMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addSortedSetMembers(ClassAttributesMutant toRet) {
		toRet.addMethod(new MethodSignature("first", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("last", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("comparator", 
			JSE_Util.COMPARATOR));
		toRet.addMethod(new MethodSignature("headSet", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Util.SORTED_SET));
		toRet.addMethod(new MethodSignature("subSet", 
			new String[] {JSE_Lang.OBJECT, JSE_Lang.OBJECT}, 
			JSE_Util.SORTED_SET));
		toRet.addMethod(new MethodSignature("tailSet", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Util.SORTED_SET));
	}
	public static ClassAttributes getStack() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.STACK);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));

		addStackMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addStackMembers(ClassAttributesMutant toRet) {
		addVectorMembers(toRet);
		toRet.addMethod(new MethodSignature("empty", 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("search", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("peek", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("pop", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("push", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Lang.OBJECT));
	}
	public static ClassAttributes getTreeMap() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.TREE_MAP);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.COMPARATOR}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.MAP}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.SORTED_MAP}));

		addTreeMapMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addTreeMapMembers(ClassAttributesMutant toRet) {
		addAbstractMapMembers(toRet);
		toRet.addMethod(new MethodSignature("clone", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("firstKey", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("lastKey", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("comparator", 
			JSE_Util.COMPARATOR));

		toRet.addMethod(new MethodSignature("headMap", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Util.SORTED_MAP));
		toRet.addMethod(new MethodSignature("subMap", 
			new String[] {JSE_Lang.OBJECT, JSE_Lang.OBJECT}, 
			JSE_Util.SORTED_MAP));
		toRet.addMethod(new MethodSignature("tailMap", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Util.SORTED_MAP));
	}
	public static ClassAttributes getTreeSet() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.TREE_SET);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.COLLECTION}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.COMPARATOR}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.SORTED_SET}));

		addTreeSetMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addTreeSetMembers(ClassAttributesMutant toRet) {
		addAbstractSetMembers(toRet);
		toRet.addMethod(new MethodSignature("first", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("last", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("comparator", 
			JSE_Util.COMPARATOR));
		toRet.addMethod(new MethodSignature("clone", 
				JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("headSet", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Util.SORTED_SET));
		toRet.addMethod(new MethodSignature("subSet", 
			new String[] {JSE_Lang.OBJECT, JSE_Lang.OBJECT}, 
			JSE_Util.SORTED_SET));
		toRet.addMethod(new MethodSignature("tailSet", 
			new String[] {JSE_Lang.OBJECT}, 
			JSE_Util.SORTED_SET));
	}
	
	public static ClassAttributes getVector() {
		ClassAttributesMutant toRet = new ClassAttributesMutant();
		toRet.setName(JSE_Util.VECTOR);

		//constructors
		toRet.addMethod(new MethodSignature("<init>"));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {ClassMethods.INT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("<init>", 
			new String[] {JSE_Util.COLLECTION}));

		addVectorMembers(toRet);
		return new ClassAttributes(toRet);
	}

	public static void addVectorMembers(ClassAttributesMutant toRet) {
		addAbstractListMembers(toRet);
		toRet.addMethod(new MethodSignature("removeElement", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.BOOLEAN));
		toRet.addMethod(new MethodSignature("capacity", 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("indexOf", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("indexOf", 
			new String[] {JSE_Lang.OBJECT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("lastIndexOf", 
			new String[] {JSE_Lang.OBJECT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("lastIndexOf", 
			new String[] {JSE_Lang.OBJECT, ClassMethods.INT}, 
			ClassMethods.INT));
		toRet.addMethod(new MethodSignature("clone", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("elementAt", 
			new String[] {ClassMethods.INT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("firstElement", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("lastElement", 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("set", 
			new String[] {ClassMethods.INT, JSE_Lang.OBJECT}, 
			JSE_Lang.OBJECT));
		toRet.addMethod(new MethodSignature("toString", 
			JSE_Lang.STRING));
		toRet.addMethod(new MethodSignature("elements", 
			JSE_Util.ENUMERATION));
		toRet.addMethod(new MethodSignature("addElement", 
			new String[] {JSE_Lang.OBJECT}));
		toRet.addMethod(new MethodSignature("copyInto", 
			new String[] {"[" + JSE_Lang.OBJECT}));
		toRet.addMethod(new MethodSignature("ensureCapacity", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("insertElementAt", 
			new String[] {JSE_Lang.OBJECT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("removeAllElements"));
		toRet.addMethod(new MethodSignature("removeElementAt", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("setElementAt", 
			new String[] {JSE_Lang.OBJECT, ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("setSize", 
			new String[] {ClassMethods.INT}));
		toRet.addMethod(new MethodSignature("trimToSize"));
	}

}
