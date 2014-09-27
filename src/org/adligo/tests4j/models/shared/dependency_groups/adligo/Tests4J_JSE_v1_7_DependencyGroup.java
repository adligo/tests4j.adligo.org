package org.adligo.tests4j.models.shared.dependency_groups.adligo;

import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.models.shared.dependency_groups.jse.v1_7.I_JSE_1_7_IO;
import org.adligo.tests4j.models.shared.dependency_groups.jse.v1_7.I_JSE_1_7_Lang;
import org.adligo.tests4j.models.shared.dependency_groups.jse.v1_7.I_JSE_1_7_LangAnnot;
import org.adligo.tests4j.models.shared.dependency_groups.jse.v1_7.I_JSE_1_7_Log;
import org.adligo.tests4j.models.shared.dependency_groups.jse.v1_7.I_JSE_1_7_Math;
import org.adligo.tests4j.models.shared.dependency_groups.jse.v1_7.I_JSE_1_7_Sql;
import org.adligo.tests4j.models.shared.dependency_groups.jse.v1_7.I_JSE_1_7_Util;

public class Tests4J_JSE_v1_7_DependencyGroup extends Tests4J_DependencyGroup {

	public Tests4J_JSE_v1_7_DependencyGroup() {
		Set<String> names = new HashSet<String>();
		
		add(names, I_JSE_1_7_IO.class);
		add(names, I_JSE_1_7_Lang.class);
		add(names, I_JSE_1_7_LangAnnot.class);
		add(names, I_JSE_1_7_Log.class);
		add(names, I_JSE_1_7_Math.class);
		add(names, I_JSE_1_7_Sql.class);
		add(names, I_JSE_1_7_Util.class);
		
		Tests4J_JSE_v1_6_DependencyGroup dg = new Tests4J_JSE_v1_6_DependencyGroup();
		names.addAll(dg.getClassNames());
		
		super.setupDelegates(names);
	}
	
	private void add(Set<String> names, Class<?> c) {
		names.add(c.getName());
	}
}
