package org.adligo.tests4j.models.shared.dependency_groups.adligo;

import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.shared.asserts.dependency.AllowedDependencies;
import org.adligo.tests4j.shared.asserts.dependency.AllowedDependencyFailure;
import org.adligo.tests4j.shared.asserts.dependency.AllowedDependencyFailureMutant;
import org.adligo.tests4j.shared.asserts.dependency.CircularDependencies;
import org.adligo.tests4j.shared.asserts.dependency.CircularDependencyFailure;
import org.adligo.tests4j.shared.asserts.dependency.CircularDependencyFailureMutant;
import org.adligo.tests4j.shared.asserts.dependency.ClassAlias;
import org.adligo.tests4j.shared.asserts.dependency.ClassAliasLocal;
import org.adligo.tests4j.shared.asserts.dependency.ClassAttributes;
import org.adligo.tests4j.shared.asserts.dependency.ClassAttributesMutant;
import org.adligo.tests4j.shared.asserts.dependency.Dependency;
import org.adligo.tests4j.shared.asserts.dependency.DependencyGroup;
import org.adligo.tests4j.shared.asserts.dependency.DependencyGroupAggregate;
import org.adligo.tests4j.shared.asserts.dependency.DependencyGroupBaseDelegate;
import org.adligo.tests4j.shared.asserts.dependency.DependencyGroupMutant;
import org.adligo.tests4j.shared.asserts.dependency.DependencyMutant;
import org.adligo.tests4j.shared.asserts.dependency.FieldSignature;
import org.adligo.tests4j.shared.asserts.dependency.I_AllowedDependencyFailure;
import org.adligo.tests4j.shared.asserts.dependency.I_CircularDependencies;
import org.adligo.tests4j.shared.asserts.dependency.I_CircularDependencyFailure;
import org.adligo.tests4j.shared.asserts.dependency.I_ClassAlias;
import org.adligo.tests4j.shared.asserts.dependency.I_ClassAliasLocal;
import org.adligo.tests4j.shared.asserts.dependency.I_ClassAttributes;
import org.adligo.tests4j.shared.asserts.dependency.I_Dependency;
import org.adligo.tests4j.shared.asserts.dependency.I_DependencyGroup;
import org.adligo.tests4j.shared.asserts.dependency.I_FieldSignature;
import org.adligo.tests4j.shared.asserts.dependency.I_MethodSignature;
import org.adligo.tests4j.shared.asserts.dependency.MethodSignature;
import org.adligo.tests4j.shared.asserts.dependency.NameOnlyDependencyGroup;

public class Tests4J_AssertsDependency_DependencyGroup extends Tests4J_DependencyGroup {

	public Tests4J_AssertsDependency_DependencyGroup() {
		Set<String> names = new HashSet<String>();
		
		add(names, AllowedDependencies.class);
		add(names, AllowedDependencyFailure.class);
		add(names, AllowedDependencyFailureMutant.class);
		
		
		add(names, CircularDependencies.class);
		add(names, CircularDependencyFailure.class);
		add(names, CircularDependencyFailureMutant.class);
		
		add(names, ClassAlias.class);
		add(names, ClassAliasLocal.class);
		
		add(names, ClassAttributes.class);
		add(names, ClassAttributesMutant.class);
		
		add(names, Dependency.class);
		add(names, DependencyMutant.class);
		
		add(names, DependencyGroup.class);
		add(names, DependencyGroupAggregate.class);
		add(names, DependencyGroupBaseDelegate.class);
		add(names, DependencyGroupMutant.class);
		
		add(names, FieldSignature.class);
		
		add(names, I_AllowedDependencyFailure.class);
		add(names, I_CircularDependencies.class);
		add(names, I_CircularDependencyFailure.class);
		
		add(names, I_ClassAlias.class);
		add(names, I_ClassAliasLocal.class);
		
		add(names, I_ClassAttributes.class);
		
		add(names, I_Dependency.class);
		add(names, I_DependencyGroup.class);
		
		add(names, I_FieldSignature.class);
		add(names, I_MethodSignature.class);
		
		add(names, MethodSignature.class);
		add(names, NameOnlyDependencyGroup.class);
		
		Tests4J_AssertsLineText_DependencyGroup dg = new Tests4J_AssertsLineText_DependencyGroup();
		names.addAll(dg.getClassNames());
		
		setupDelegates(names);
	}
	
	private void add(Set<String> names, Class<?> c) {
		names.add(c.getName());
	}
}
