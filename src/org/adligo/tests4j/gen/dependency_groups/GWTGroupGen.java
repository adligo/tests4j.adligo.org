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
		ConstantLookup cl = ctx.getConstantLookup();
		cl.addLookups(JSE_Lang.INSTANCE);
		
		gg.setCtx(ctx);
		gg.gen(getLang());
	}
	
	public static List<Class<?>> getLang() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		/*
		toRet.add(Appendable.class);
		toRet.add(ArithmeticException.class);
		toRet.add(ArrayIndexOutOfBoundsException.class);
		toRet.add(ArrayStoreException.class);
		toRet.add(AssertionError.class);
		toRet.add(AutoCloseable.class);
		
		toRet.add(Boolean.class);
		toRet.add(Byte.class);
		
		toRet.add(CharSequence.class);
		toRet.add(Character.class);
		*/
		//toRet.add(Object.class);
		//toRet.add(Throwable.class);
		toRet.add(Exception.class);
		//toRet.add(RuntimeException.class);
		//toRet.add(ArithmeticException.class);
		
		/*
		toRet.add(Throwable.class);
		toRet.add(Enum.class);
		*/
		return toRet;
	}
}
