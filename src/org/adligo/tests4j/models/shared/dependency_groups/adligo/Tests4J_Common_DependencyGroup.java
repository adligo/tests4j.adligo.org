package org.adligo.tests4j.models.shared.dependency_groups.adligo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.dependency.DependencyGroupAggregate;
import org.adligo.tests4j.models.shared.dependency.DependencyGroupBaseDelegate;
import org.adligo.tests4j.models.shared.dependency.I_DependencyGroup;
import org.adligo.tests4j.models.shared.dependency.NameOnlyDependencyGroup;
import org.adligo.tests4j.shared.common.CacheControl;
import org.adligo.tests4j.shared.common.ClassMethods;
import org.adligo.tests4j.shared.common.DefaultSystem;
import org.adligo.tests4j.shared.common.DelegateSystem;
import org.adligo.tests4j.shared.common.I_CacheControl;
import org.adligo.tests4j.shared.common.I_Immutable;
import org.adligo.tests4j.shared.common.I_Platform;
import org.adligo.tests4j.shared.common.I_PlatformContainer;
import org.adligo.tests4j.shared.common.I_System;
import org.adligo.tests4j.shared.common.I_TrialType;
import org.adligo.tests4j.shared.common.JavaAPIVersion;
import org.adligo.tests4j.shared.common.LegacyAPI_Issues;
import org.adligo.tests4j.shared.common.MethodBlocker;
import org.adligo.tests4j.shared.common.Platform;
import org.adligo.tests4j.shared.common.StackTraceBuilder;
import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.common.SystemWithPrintStreamDelegate;
import org.adligo.tests4j.shared.common.Tests4J_Constants;
import org.adligo.tests4j.shared.common.Tests4J_ConstantsWrapper;
import org.adligo.tests4j.shared.common.Tests4J_System;
import org.adligo.tests4j.shared.common.TrialType;

public class Tests4J_Common_DependencyGroup extends Tests4J_DependencyGroup {

	public Tests4J_Common_DependencyGroup() {
		Set<String> names = new HashSet<String>();
		
		add(names, CacheControl.class);
		add(names, ClassMethods.class);
		
		add(names, DefaultSystem.class);
		add(names, DelegateSystem.class);
		
		add(names, I_CacheControl.class);
		add(names, I_Immutable.class);
		add(names, I_Platform.class);
		add(names, I_PlatformContainer.class);
		add(names, I_System.class);
		add(names, I_TrialType.class);
		
		add(names, JavaAPIVersion.class);
		
		add(names, LegacyAPI_Issues.class);
		
		add(names, MethodBlocker.class);
		
		add(names, Platform.class);
		
		add(names, StackTraceBuilder.class);
		add(names, StringMethods.class);
		add(names, SystemWithPrintStreamDelegate.class);
		
		add(names, Tests4J_Constants.class);
		add(names, Tests4J_ConstantsWrapper.class);
		add(names, Tests4J_System.class);
		add(names, TrialType.class);
		
		Tests4J_EN_DependencyGroup dg = new Tests4J_EN_DependencyGroup();
		names.addAll(dg.getClassNames());
		
		setupDelegates(names);
	}
	
	private void add(Set<String> names, Class<?> c) {
		names.add(c.getName());
	}
}
