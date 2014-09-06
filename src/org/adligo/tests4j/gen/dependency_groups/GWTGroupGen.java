package org.adligo.tests4j.gen.dependency_groups;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.annotation.AnnotationFormatError;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.EmptyStackException;
import java.util.List;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.TooManyListenersException;

import org.adligo.tests4j.models.dependency_groups.gwt.GWT_2_6_Lang;
import org.adligo.tests4j.models.dependency_groups.jse.JSE_Lang;
import org.adligo.tests4j.models.shared.common.Tests4J_System;


public class GWTGroupGen {

	public static void main(String[] args) {
		GroupGen gg = new GroupGen();
		GenDepGroupContext ctx = new GenDepGroupContext();
		ctx.setTrialPackageName("org.adligo.tests4j_v1_tests.models.dependency_groups.gwt.v2_6.lang");
		ctx.setApiVersion("2_6");
		ctx.setGroupFactoryClass(GWT_2_6_Lang.class);
		//ctx.setExtraTrialAnnotations("@SuppressOutput");
		ctx.setTrialClass("org.adligo.tests4j_tests.base_trials.SourceFileCountingTrial");
		ctx.setTrialClassSimpleName("SourceFileCountingTrial");
		ctx.setExtraTrialContent("" + Tests4J_System.lineSeperator() +
			"\t@Override" + Tests4J_System.lineSeperator() +
			"\tpublic int getTests(I_CountType type) {"+ Tests4J_System.lineSeperator() +
			"\t\treturn super.getTests(type, 1);"+ Tests4J_System.lineSeperator() +
			"\t}"+ Tests4J_System.lineSeperator() +
			"" + Tests4J_System.lineSeperator() +
			"\t@Override "+ Tests4J_System.lineSeperator() +
			"\tpublic int getAsserts(I_CountType type) { "+ Tests4J_System.lineSeperator() +
			"\t\tint thisAsserts = 1;"+ Tests4J_System.lineSeperator() +
			"\t\t//code coverage and circular dependencies +"+ Tests4J_System.lineSeperator() +
			"\t\t//custom afterTrialTests"+ Tests4J_System.lineSeperator() +
			"\t\t//+ see above"+ Tests4J_System.lineSeperator() +
			"\t\tint thisAfterAsserts = 24;"+ Tests4J_System.lineSeperator() +
			"\t\tif (type.isFromMetaWithCoverage()) {"+ Tests4J_System.lineSeperator() +
			"\t\t\treturn super.getAsserts(type, thisAsserts + thisAfterAsserts);"+ Tests4J_System.lineSeperator() +
			"\t\t} else {"+ Tests4J_System.lineSeperator() +
			"\t\t\treturn super.getAsserts(type, thisAsserts);"+ Tests4J_System.lineSeperator() +
			"\t\t}"+ Tests4J_System.lineSeperator() +
			"\t}"+ Tests4J_System.lineSeperator() +
			"" + Tests4J_System.lineSeperator() +
			"\t@Override"+ Tests4J_System.lineSeperator() +
			"\tpublic int getUniqueAsserts(I_CountType type) {"+ Tests4J_System.lineSeperator() +
			"\t\tint uAsserts = 1;"+ Tests4J_System.lineSeperator() +
			"\t\t//code coverage and circular dependencies +"+ Tests4J_System.lineSeperator() +
			"\t\t//custom afterTrialTests"+ Tests4J_System.lineSeperator() +
			"\t\t//+ see above"+ Tests4J_System.lineSeperator() +
			"\t\tint uAfterAsserts = 24;"+ Tests4J_System.lineSeperator() +
			"\t\tif (type.isFromMetaWithCoverage()) {"+ Tests4J_System.lineSeperator() +
			"\t\t\treturn super.getAsserts(type, uAsserts + uAfterAsserts);"+ Tests4J_System.lineSeperator() +
			"\t\t} else {"+ Tests4J_System.lineSeperator() +
			"\t\t\treturn super.getAsserts(type, uAsserts);"+ Tests4J_System.lineSeperator() +
			"\t\t}"+ Tests4J_System.lineSeperator() +
			"\t}"); 
		
		ConstantLookup cl = ctx.getConstantLookup();
		cl.addLookups(JSE_Lang.INSTANCE);
		
		gg.setCtx(ctx);
		//ctx.setRunConstantGen(false);
		//ctx.setRunConstantTrialGen(false);
		gg.gen(getLang());
	}
	
	public static List<Class<?>> getLang() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		
		//the actual order
		/*
		toRet.add(Appendable.class);
		toRet.add(Object.class);
		toRet.add(StackTraceElement.class);
		toRet.add(Throwable.class);
		toRet.add(Exception.class);
		toRet.add(RuntimeException.class);
		toRet.add(ArithmeticException.class);
		toRet.add(IndexOutOfBoundsException.class);
		toRet.add(ArrayIndexOutOfBoundsException.class);
		toRet.add(ArrayStoreException.class);
		toRet.add(Error.class);
		toRet.add(AssertionError.class);
		toRet.add(ClassCastException.class);
		toRet.add(IllegalArgumentException.class);
		toRet.add(IllegalStateException.class);
		toRet.add(NegativeArraySizeException.class);
		//hmm gwt is still on 1.6?
		toRet.add(ReflectiveOperationException.class);
		
		
		toRet.add(NoSuchMethodException.class);
		toRet.add(NullPointerException.class);
		toRet.add(NumberFormatException.class);
		toRet.add(StringIndexOutOfBoundsException.class);
		toRet.add(UnsupportedOperationException.class);
		//all exceptions done
		toRet.add(Appendable.class);
		toRet.add(AutoCloseable.class);
		
		toRet.add(CharSequence.class);
		
		toRet.add(Cloneable.class);
		toRet.add(Comparable.class);
		toRet.add(Deprecated.class);
		toRet.add(Override.class);
		toRet.add(SuppressWarnings.class);
		
		toRet.add(Boolean.class);
		toRet.add(Enum.class);
		toRet.add(Number.class);
		toRet.add(Byte.class);
		toRet.add(Character.class);
		toRet.add(Character.class);
		toRet.add(Class.class);
		toRet.add(Double.class);
		toRet.add(Float.class);
		toRet.add(Integer.class);
		toRet.add(Iterable.class);
		toRet.add(Long.class);
		toRet.add(Math.class);
		toRet.add(Short.class);
		toRet.add(String.class);
		toRet.add(StringBuffer.class);
		*/
		
		toRet.add(StringBuilder.class);
		
		//toRet.add(Double.class);
		return toRet;
	}
	
	public static List<Class<?>> getAnnot() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		toRet.add(Annotation.class);
		toRet.add(Documented.class); 
		toRet.add(AnnotationFormatError.class);
		toRet.add(AnnotationTypeMismatchException.class);
		toRet.add(ElementType.class); 
		
		toRet.add(Inherited.class); 
		toRet.add(Retention.class); 
		toRet.add(RetentionPolicy.class); 
		toRet.add(Target.class); 
		return toRet;
	}
	
	public static List<Class<?>> getIO() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		
		toRet.add(IOException.class);
		toRet.add(UnsupportedEncodingException.class);
		return toRet;
	}
	
	public static List<Class<?>> getUtil() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		
		toRet.add(ConcurrentModificationException.class);
		toRet.add(EmptyStackException.class);
		toRet.add(MissingResourceException.class);
		toRet.add(NoSuchElementException.class);
		toRet.add(TooManyListenersException.class);
		
		return toRet;
	}
}
