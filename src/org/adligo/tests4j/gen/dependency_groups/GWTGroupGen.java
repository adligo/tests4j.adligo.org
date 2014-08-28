package org.adligo.tests4j.gen.dependency_groups;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.dependency_groups.gwt.GWT_2_6_Lang;
import org.adligo.tests4j.models.shared.dependency_groups.jse.JSE_Lang;


public class GWTGroupGen {

	public static void main(String[] args) {
		GroupGen gg = new GroupGen();
		GenDepGroupContext ctx = new GenDepGroupContext();
		ctx.setTrialPackageName("org.adligo.tests4j_tests.models.shared.dependency_groups.gwt.v2_6.lang");
		ctx.setApiVersion("2_6");
		ctx.setGroupFactoryClass(GWT_2_6_Lang.class);
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
		gg.gen(getLang());
	}
	
	public static List<Class<?>> getLang() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		
		//the actual order
		/*
		toRet.add(Appendable.class);
		toRet.add(Object.class);
		toRet.add(Throwable.class);
		toRet.add(Exception.class);
		toRet.add(RuntimeException.class);
		toRet.add(ArithmeticException.class);
		toRet.add(IndexOutOfBoundsException.class);
		toRet.add(ArrayIndexOutOfBoundsException.class);
		toRet.add(ArrayStoreException.class);
		toRet.add(Error.class);
		*/
		toRet.add(AssertionError.class);
		
		return toRet;
	}
}
