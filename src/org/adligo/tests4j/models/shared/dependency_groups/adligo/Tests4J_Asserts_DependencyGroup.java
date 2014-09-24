package org.adligo.tests4j.models.shared.dependency_groups.adligo;

import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.shared.asserts.AbstractAssertCommand;
import org.adligo.tests4j.shared.asserts.AbstractCompareAssertCommand;
import org.adligo.tests4j.shared.asserts.AssertionFailureLocation;
import org.adligo.tests4j.shared.asserts.AssertionProcessor;
import org.adligo.tests4j.shared.asserts.BooleanAssertCommand;
import org.adligo.tests4j.shared.asserts.ContainsAssertCommand;
import org.adligo.tests4j.shared.asserts.DoubleAssertCommand;
import org.adligo.tests4j.shared.asserts.IdenticalAssertCommand;
import org.adligo.tests4j.shared.asserts.IdenticalStringAssertCommand;
import org.adligo.tests4j.shared.asserts.ThrownAssertCommand;
import org.adligo.tests4j.shared.asserts.UniformAssertCommand;
import org.adligo.tests4j.shared.asserts.UniformThrownAssertCommand;

public class Tests4J_Asserts_DependencyGroup extends Tests4J_DependencyGroup {

	public Tests4J_Asserts_DependencyGroup() {
		Set<String> names = new HashSet<String>();
		
		add(names, AbstractAssertCommand.class);
		add(names, AbstractCompareAssertCommand.class);
		add(names, AssertionFailureLocation.class);
		add(names, AssertionProcessor.class);
		
		add(names, BooleanAssertCommand.class);
		
		add(names, ContainsAssertCommand.class);
		
		add(names, DoubleAssertCommand.class);
		
		add(names, IdenticalAssertCommand.class);
		add(names, IdenticalStringAssertCommand.class);
		
		add(names, ThrownAssertCommand.class);
		
		add(names, UniformAssertCommand.class);
		add(names, UniformThrownAssertCommand.class);
		
		Tests4J_AssertsUniform_DependencyGroup dg = new Tests4J_AssertsUniform_DependencyGroup();
		names.addAll(dg.getClassNames());
		
		setupDelegates(names);
	}
	
	private void add(Set<String> names, Class<?> c) {
		names.add(c.getName());
	}
}
