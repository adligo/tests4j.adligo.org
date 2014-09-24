package org.adligo.tests4j.models.shared.dependency_groups.adligo;

import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.common.AssertCompareFailure;
import org.adligo.tests4j.models.shared.asserts.common.AssertCompareFailureMutant;
import org.adligo.tests4j.models.shared.asserts.common.AssertThrownFailure;
import org.adligo.tests4j.models.shared.asserts.common.AssertThrownFailureMutant;
import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.ExpectedThrownData;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertCommand;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertCompareFailure;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertListener;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertThrownFailure;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_CollectionAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_CollectionContainsAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertCommand;
import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.models.shared.asserts.common.I_SimpleAssertCommand;
import org.adligo.tests4j.models.shared.asserts.common.I_SimpleCompareAssertCommand;
import org.adligo.tests4j.models.shared.asserts.common.I_SourceTestFailure;
import org.adligo.tests4j.models.shared.asserts.common.I_TestFailure;
import org.adligo.tests4j.models.shared.asserts.common.I_TestFailureType;
import org.adligo.tests4j.models.shared.asserts.common.I_ThrowableInfo;
import org.adligo.tests4j.models.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.models.shared.asserts.common.I_ThrownAssertCommand;
import org.adligo.tests4j.models.shared.asserts.common.I_ThrownAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.SourceTestFailure;
import org.adligo.tests4j.models.shared.asserts.common.SourceTestFailureMutant;
import org.adligo.tests4j.models.shared.asserts.common.TestFailure;
import org.adligo.tests4j.models.shared.asserts.common.TestFailureBuilder;
import org.adligo.tests4j.models.shared.asserts.common.TestFailureMutant;
import org.adligo.tests4j.models.shared.asserts.common.TestFailureType;
import org.adligo.tests4j.models.shared.asserts.common.ThrowableInfo;
import org.adligo.tests4j.models.shared.asserts.common.ThrowableInfoMutant;
import org.adligo.tests4j.models.shared.asserts.common.ThrownAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.ThrownAssertionDataMutant;

public class Tests4J_AssertsCommon_DependencyGroup extends Tests4J_DependencyGroup {

	public Tests4J_AssertsCommon_DependencyGroup() {
		Set<String> names = new HashSet<String>();
		
		add(names, AssertCompareFailure.class);
		add(names, AssertCompareFailureMutant.class);
		add(names, AssertThrownFailure.class);
		add(names, AssertThrownFailureMutant.class);
		
		add(names, AssertType.class);
		add(names, CompareAssertionData.class);
		add(names, ExpectedThrownData.class);
		
		add(names, I_AssertCommand.class);
		add(names, I_AssertCompareFailure.class);
		add(names, I_AssertionData.class);
		add(names, I_AssertListener.class);
		add(names, I_AssertThrownFailure.class);
		add(names, I_AssertType.class);
		
		add(names, I_CollectionAssertionData.class);
		add(names, I_CollectionContainsAssertionData.class);
		add(names, I_CompareAssertCommand.class);
		add(names, I_CompareAssertionData.class);
		
		add(names, I_ExpectedThrownData.class);
		
		add(names, I_SimpleAssertCommand.class);
		add(names, I_SimpleCompareAssertCommand.class);
		add(names, I_SourceTestFailure.class);
		
		add(names, I_TestFailure.class);
		add(names, I_TestFailureType.class);
		add(names, I_ThrowableInfo.class);
		add(names, I_Thrower.class);
		add(names, I_ThrownAssertCommand.class);
		add(names, I_ThrownAssertionData.class);
		
		add(names, SourceTestFailure.class);
		add(names, SourceTestFailureMutant.class);
		
		add(names, TestFailure.class);
		add(names, TestFailureBuilder.class);
		add(names, TestFailureMutant.class);
		add(names, TestFailureType.class);
		
		add(names, ThrowableInfo.class);
		add(names, ThrowableInfoMutant.class);
		add(names, ThrownAssertionData.class);
		add(names, ThrownAssertionDataMutant.class);
		
		Tests4J_XML_DependencyGroup dg = new Tests4J_XML_DependencyGroup();
		names.addAll(dg.getClassNames());
		
		setupDelegates(names);
	}
	
	private void add(Set<String> names, Class<?> c) {
		names.add(c.getName());
	}
}
