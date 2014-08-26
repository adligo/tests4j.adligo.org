package org.adligo.tests4j.models.shared.dependency_groups;

import org.adligo.tests4j.models.shared.dependency.ClassAttributesMutant;
import org.adligo.tests4j.models.shared.dependency.DependencyGroup;
import org.adligo.tests4j.models.shared.dependency.DependencyGroupMutant;
import org.adligo.tests4j.models.shared.dependency.FieldSignature;
import org.adligo.tests4j.models.shared.dependency.MethodSignature;

public class GWT_2_6_Lang extends DependencyGroup {
	
	public static DependencyGroupMutant create() {
		DependencyGroupMutant dgm = new DependencyGroupMutant();
		
		dgm.addClassMethods(getAppendable());
		dgm.addClassMethods(getArithmeticException());
		dgm.addClassMethods(getArrayIndexOutOfBounds());
		dgm.addClassMethods(getArrayStoreException());
		dgm.addClassMethods(getAssertionError());
		dgm.addClassMethods(getAutoCloseable());
		dgm.addClassMethods(getBoolean());
		
		return dgm;
	}

	

	public static ClassAttributesMutant getAutoCloseable() {
		ClassAttributesMutant cam = new ClassAttributesMutant();
		cam.setClassName(JSE_1_7_Lang.AUTO_CLOSEABLE);
		cam.addMethod(new MethodSignature("close"));
		return cam;
	}

	public static ClassAttributesMutant getAssertionError() {
		ClassAttributesMutant cam = new ClassAttributesMutant();
		cam.setClassName(JSE_1_7_Lang.ASSERTION_ERROR);
		
		cam.addMethod(new MethodSignature("<init>", JSE_1_7_Lang.ASSERTION_ERROR));
		cam.addMethod(new MethodSignature("<init>", new String[] {JSE_1_7_Lang.BOOLEAN}));
		cam.addMethod(new MethodSignature("<init>", new String[] {JSE_1_7_Lang.CHARACTER}));
		cam.addMethod(new MethodSignature("<init>", new String[] {JSE_1_7_Lang.DOUBLE}));
		
		cam.addMethod(new MethodSignature("<init>", new String[] {JSE_1_7_Lang.FLOAT}));
		cam.addMethod(new MethodSignature("<init>", new String[] {JSE_1_7_Lang.INTEGER}));
		cam.addMethod(new MethodSignature("<init>", new String[] {JSE_1_7_Lang.LONG}));
		cam.addMethod(new MethodSignature("<init>", new String[] {JSE_1_7_Lang.OBJECT}));
		return cam;
	}

	public static ClassAttributesMutant getArrayStoreException() {
		ClassAttributesMutant cam = new ClassAttributesMutant();
		cam.setClassName(JSE_1_7_Lang.ARRAY_STORE_EXCEPTION);
		cam.addMethod(new MethodSignature("<init>", new String[] {JSE_1_7_Lang.STRING}));
		cam.addMethod(new MethodSignature("<init>"));
		return cam;
	}

	public static ClassAttributesMutant getArrayIndexOutOfBounds() {
		ClassAttributesMutant cam = new ClassAttributesMutant();
		cam.setClassName(JSE_1_7_Lang.ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION);
		cam.addMethod(new MethodSignature("<init>", new String[] {JSE_1_7_Lang.STRING}, JSE_1_7_Lang.ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION));
		cam.addMethod(new MethodSignature("<init>", null, JSE_1_7_Lang.ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION));
		cam.addMethod(new MethodSignature("<init>", new String[] {JSE_1_7_Lang.INTEGER}, JSE_1_7_Lang.ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION));
		return cam;
	}

	public static ClassAttributesMutant getArithmeticException() {
		ClassAttributesMutant cam = new ClassAttributesMutant();
		cam.setClassName(JSE_1_7_Lang.ARITHMETIC_EXCEPTION);
		cam.addMethod(new MethodSignature("<init>", new String[] {JSE_1_7_Lang.STRING}, JSE_1_7_Lang.ARITHMETIC_EXCEPTION));
		cam.addMethod(new MethodSignature("<init>", null, JSE_1_7_Lang.ARITHMETIC_EXCEPTION));
		return cam;
	}

	public static ClassAttributesMutant getAppendable() {
		ClassAttributesMutant cam = new ClassAttributesMutant();
		cam.setClassName(JSE_1_7_Lang.APPENDABLE);
		cam.addMethod(new MethodSignature("append", new String[] {JSE_1_7_Lang.CHARACTER}, JSE_1_7_Lang.APPENDABLE));
		cam.addMethod(new MethodSignature("append", new String[] {JSE_1_7_Lang.CHAR_SEQUENCE}, JSE_1_7_Lang.APPENDABLE));
		cam.addMethod(new MethodSignature("append", 
				new String[] {JSE_1_7_Lang.CHAR_SEQUENCE,
				JSE_1_7_Lang.INTEGER, JSE_1_7_Lang.INTEGER}, 
				JSE_1_7_Lang.APPENDABLE));
		return cam;
	}
	
	public static ClassAttributesMutant getBoolean() {
		ClassAttributesMutant cam = new ClassAttributesMutant();
		cam.setClassName(JSE_1_7_Lang.BOOLEAN);
		cam.addField(new FieldSignature("FALSE", JSE_1_7_Lang.BOOLEAN));
		cam.addField(new FieldSignature("TRUE", JSE_1_7_Lang.BOOLEAN));
		cam.addField(new FieldSignature("TYPE", JSE_1_7_Lang.CLASS));
		
		cam.addMethod(new MethodSignature("<init>", new String[] {JSE_1_7_Lang.BOOLEAN}));
		cam.addMethod(new MethodSignature("<init>", new String[] {JSE_1_7_Lang.STRING}));
		
		cam.addMethod(new MethodSignature("compare", 
				new String[] {JSE_1_7_Lang.BOOLEAN, JSE_1_7_Lang.BOOLEAN}, 
				JSE_1_7_Lang.INTEGER));
		cam.addMethod(new MethodSignature("parseBoolean", 
				new String[] {JSE_1_7_Lang.STRING}, 
				JSE_1_7_Lang.BOOLEAN));
		cam.addMethod(new MethodSignature("toString", 
				new String[] {JSE_1_7_Lang.BOOLEAN}, 
				JSE_1_7_Lang.STRING));
		cam.addMethod(new MethodSignature("valueOf", new String[] {JSE_1_7_Lang.BOOLEAN}, JSE_1_7_Lang.BOOLEAN));
		cam.addMethod(new MethodSignature("valueOf", new String[] {JSE_1_7_Lang.STRING}, JSE_1_7_Lang.BOOLEAN));
		cam.addMethod(new MethodSignature("booleanValue", JSE_1_7_Lang.BOOLEAN));
		cam.addMethod(new MethodSignature("compareTo", 
				new String[] {JSE_1_7_Lang.BOOLEAN}, 
				JSE_1_7_Lang.INTEGER));
		cam.addMethod(new MethodSignature("equals", 
				new String[] {JSE_1_7_Lang.OBJECT}, 
				JSE_1_7_Lang.BOOLEAN));
		cam.addMethod(new MethodSignature("hashCode", JSE_1_7_Lang.INTEGER));
		cam.addMethod(new MethodSignature("toString", JSE_1_7_Lang.STRING));
		return cam;
	}
	
	public static ClassAttributesMutant getByte() {
		ClassAttributesMutant cam = new ClassAttributesMutant();
		cam.setClassName(JSE_1_7_Lang.BYTE);
		cam.addField(new FieldSignature("MIN_VALUE", JSE_1_7_Lang.BYTE));
		cam.addField(new FieldSignature("MAX_VALUE", JSE_1_7_Lang.BYTE));
		cam.addField(new FieldSignature("SIZE", JSE_1_7_Lang.INTEGER));
		cam.addField(new FieldSignature("TYPE", JSE_1_7_Lang.CLASS));
		
		cam.addMethod(new MethodSignature("<init>", new String[] {JSE_1_7_Lang.BYTE}));
		cam.addMethod(new MethodSignature("<init>", new String[] {JSE_1_7_Lang.STRING}));
		
		cam.addMethod(new MethodSignature("compare", 
				new String[] {JSE_1_7_Lang.BYTE, JSE_1_7_Lang.BYTE}, 
				JSE_1_7_Lang.INTEGER));
		cam.addMethod(new MethodSignature("decode", 
				new String[] {JSE_1_7_Lang.STRING}, 
				JSE_1_7_Lang.BYTE));
		cam.addMethod(new MethodSignature("hashCode", 
				new String[] {JSE_1_7_Lang.BYTE}, 
				JSE_1_7_Lang.INTEGER));
		cam.addMethod(new MethodSignature("parseByte", 
				new String[] {JSE_1_7_Lang.STRING}, 
				JSE_1_7_Lang.BYTE));
		//row 2
		cam.addMethod(new MethodSignature("parseByte", 
				new String[] {JSE_1_7_Lang.STRING, JSE_1_7_Lang.INTEGER}, 
				JSE_1_7_Lang.BYTE));
		cam.addMethod(new MethodSignature("toString", 
				new String[] {JSE_1_7_Lang.BYTE}, 
				JSE_1_7_Lang.STRING));
		cam.addMethod(new MethodSignature("valueOf", 
				new String[] {JSE_1_7_Lang.BYTE}, 
				JSE_1_7_Lang.BYTE));
		cam.addMethod(new MethodSignature("valueOf", 
				new String[] {JSE_1_7_Lang.STRING}, 
				JSE_1_7_Lang.BYTE));
		cam.addMethod(new MethodSignature("valueOf", 
				new String[] {JSE_1_7_Lang.STRING, JSE_1_7_Lang.INTEGER}, 
				JSE_1_7_Lang.BYTE));
		cam.addMethod(new MethodSignature("byteValue", JSE_1_7_Lang.BYTE));
		//3rd row
		cam.addMethod(new MethodSignature("compareTo", 
				new String[] {JSE_1_7_Lang.BYTE}, 
				JSE_1_7_Lang.INTEGER));
		cam.addMethod(new MethodSignature("doubleValue", 
				JSE_1_7_Lang.DOUBLE));
		cam.addMethod(new MethodSignature("equals", 
				new String[] {JSE_1_7_Lang.OBJECT}, 
				JSE_1_7_Lang.BOOLEAN));
		cam.addMethod(new MethodSignature("floatValue", 
				JSE_1_7_Lang.FLOAT));
		cam.addMethod(new MethodSignature("hashCode", 
				JSE_1_7_Lang.INTEGER));
		cam.addMethod(new MethodSignature("inValue", 
				JSE_1_7_Lang.INTEGER));
		cam.addMethod(new MethodSignature("longValue", 
				JSE_1_7_Lang.LONG));
		cam.addMethod(new MethodSignature("longValue", 
				JSE_1_7_Lang.LONG));
		cam.addMethod(new MethodSignature("shortValue", 
				JSE_1_7_Lang.SHORT));
		cam.addMethod(new MethodSignature("toString", 
				JSE_1_7_Lang.STRING));
		return cam;
	}
	
	public GWT_2_6_Lang() {
		super(create());
	}
}
