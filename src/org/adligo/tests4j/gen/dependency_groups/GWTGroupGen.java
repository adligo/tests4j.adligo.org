package org.adligo.tests4j.gen.dependency_groups;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.AnnotationFormatError;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.annotation.IncompleteAnnotationException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.EmptyStackException;
import java.util.List;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.TooManyListenersException;

import org.adligo.tests4j.models.shared.dependency_groups.gwt.GWT_2_6_Util;
import org.adligo.tests4j.models.shared.dependency_groups.jse.JSE_Lang;


public class GWTGroupGen {

	public static void main(String[] args) {
		GroupGen gg = new GroupGen();
		GenDepGroupContext ctx = new GenDepGroupContext();
		ctx.setTrialPackageName("org.adligo.tests4j_tests.models.shared.dependency_groups.gwt.v2_6.util");
		ctx.setApiVersion("2_6");
		ctx.setGroupFactoryClass(GWT_2_6_Util.class);
		ctx.setExtraTrialAnnotations("@SuppressOutput");
		ctx.setTrialClass("org.adligo.tests4j_tests.base_trials.SourceFileCountingTrial");
		ctx.setTrialClassSimpleName("SourceFileCountingTrial");
		ctx.setExtraTrialContent("" + System.lineSeparator() +
			"\t@Override" + System.lineSeparator() +
			"\tpublic int getTests() {"+ System.lineSeparator() +
			"\t\treturn 1;"+ System.lineSeparator() +
			"\t}"+ System.lineSeparator() +
			"" + System.lineSeparator() +
			"\t@Override "+ System.lineSeparator() +
			"\tpublic int getAsserts() { "+ System.lineSeparator() +
			"\t\treturn 1; "+ System.lineSeparator() +
			"\t}"+ System.lineSeparator() +
			"" + System.lineSeparator() +
			"\t@Override"+ System.lineSeparator() +
			"\tpublic int getUniqueAsserts() {"+ System.lineSeparator() +
			"\t\treturn 1;"+ System.lineSeparator() +
			"\t}"); 
		
		ConstantLookup cl = ctx.getConstantLookup();
		cl.addLookups(JSE_Lang.INSTANCE);
		
		gg.setCtx(ctx);
		ctx.setRunConstantGen(false);
		ctx.setRunConstantTrialGen(false);
		gg.gen(getUtil());
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
		*/
		/*
		
		
		*/
		return toRet;
	}
	
	public static List<Class<?>> getAnnot() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		
		toRet.add(AnnotationFormatError.class);
		toRet.add(AnnotationTypeMismatchException.class);
		toRet.add(IncompleteAnnotationException.class);
		
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
