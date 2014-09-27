package org.adligo.tests4j.models.shared.dependency_groups.adligo;

import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.models.shared.dependency_groups.jse.v1_6.I_JSE_1_6_Lang;

public class Tests4J_JSE_v1_6_DependencyGroup extends Tests4J_DependencyGroup {

	public Tests4J_JSE_v1_6_DependencyGroup() {
		Set<String> names = new HashSet<String>();
		
		add(names, I_JSE_1_6_Lang.class);
		super.setupDelegates(names);
	}
	
	private void add(Set<String> names, Class<?> c) {
		names.add(c.getName());
	}
}
