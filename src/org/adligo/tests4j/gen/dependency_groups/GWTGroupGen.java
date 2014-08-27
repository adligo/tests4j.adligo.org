package org.adligo.tests4j.gen.dependency_groups;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.dependency_groups.gwt.GWT_2_6_Lang;


public class GWTGroupGen {

	public static void main(String[] args) {
		GroupGen gg = new GroupGen();
		GenDepGroupContext ctx = new GenDepGroupContext();
		ctx.setTrialPackageName("org.adligo.tests4J_tests.models.shared.dependency_group.gwt");
		ctx.setApiVersion("2_6");
		ctx.setGroupFactoryClass(GWT_2_6_Lang.class);
		ctx.setFactoryMethod("getAppendable");
		gg.setCtx(ctx);
		gg.gen(getLang());
	}
	
	public static List<Class<?>> getLang() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		toRet.add(Appendable.class);
		
		return toRet;
	}
}
