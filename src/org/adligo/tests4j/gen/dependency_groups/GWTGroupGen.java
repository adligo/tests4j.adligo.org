package org.adligo.tests4j.gen.dependency_groups;

import java.util.ArrayList;
import java.util.List;


public class GWTGroupGen {

	public static void main(String[] args) {
		GroupGen gg = new GroupGen();
		GenDepGroupContext ctx = new GenDepGroupContext();
		ctx.setTrialPackageName("org.adligo.tests4J_tests.models.shared.dependency_group.gwt");
		gg.setCtx(ctx);
		gg.gen(getLang());
	}
	
	public static List<Class<?>> getLang() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		toRet.add(Appendable.class);
		
		return toRet;
	}
}
