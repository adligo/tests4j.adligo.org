package org.adligo.tests4j.models.shared.dependency_groups.adligo;

import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.models.shared.dependency.ClassDependenciesLocal;
import org.adligo.tests4j.models.shared.dependency.ClassDependenciesLocalMutant;
import org.adligo.tests4j.models.shared.dependency.ClassFilter;
import org.adligo.tests4j.models.shared.dependency.ClassFilterMutant;
import org.adligo.tests4j.models.shared.dependency.ClassParentsLocal;
import org.adligo.tests4j.models.shared.dependency.ClassParentsLocalMutant;
import org.adligo.tests4j.models.shared.dependency.I_ClassDependencies;
import org.adligo.tests4j.models.shared.dependency.I_ClassDependenciesCache;
import org.adligo.tests4j.models.shared.dependency.I_ClassDependenciesLocal;
import org.adligo.tests4j.models.shared.dependency.I_ClassFilter;
import org.adligo.tests4j.models.shared.dependency.I_ClassFilterModel;
import org.adligo.tests4j.models.shared.dependency.I_ClassParents;
import org.adligo.tests4j.models.shared.dependency.I_ClassParentsCache;
import org.adligo.tests4j.models.shared.dependency.I_ClassParentsLocal;
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
import org.adligo.tests4j.shared.asserts.dependency.I_ClassAlias;
import org.adligo.tests4j.shared.asserts.dependency.I_ClassAliasLocal;
import org.adligo.tests4j.shared.asserts.dependency.I_ClassAttributes;
import org.adligo.tests4j.shared.asserts.dependency.I_Dependency;
import org.adligo.tests4j.shared.asserts.dependency.I_DependencyGroup;
import org.adligo.tests4j.shared.asserts.dependency.I_FieldSignature;
import org.adligo.tests4j.shared.asserts.dependency.I_MethodSignature;
import org.adligo.tests4j.shared.asserts.dependency.MethodSignature;
import org.adligo.tests4j.shared.asserts.dependency.NameOnlyDependencyGroup;

public class Tests4J_Dependency_DependencyGroup extends Tests4J_DependencyGroup {

	public Tests4J_Dependency_DependencyGroup() {
		Set<String> names = new HashSet<String>();
		
		
		add(names, ClassDependenciesLocal.class);
		add(names, ClassDependenciesLocalMutant.class);
		add(names, ClassFilter.class);
		add(names, ClassFilterMutant.class);
		add(names, ClassParentsLocal.class);
		add(names, ClassParentsLocalMutant.class);
		
		add(names, I_ClassDependencies.class);
		add(names, I_ClassDependenciesCache.class);
		add(names, I_ClassDependenciesLocal.class);
		add(names, I_ClassFilter.class);
		add(names, I_ClassFilterModel.class);
		add(names, I_ClassParents.class);
		add(names, I_ClassParentsCache.class);
		add(names, I_ClassParentsLocal.class);
		
		
		Tests4J_Common_DependencyGroup dg = new Tests4J_Common_DependencyGroup();
		names.addAll(dg.getClassNames());
		
		Tests4J_AssertsDependency_DependencyGroup ad = new Tests4J_AssertsDependency_DependencyGroup();
		names.addAll(ad.getClassNames());
		
		setupDelegates(names);
	}
	
	private void add(Set<String> names, Class<?> c) {
		names.add(c.getName());
	}
}
