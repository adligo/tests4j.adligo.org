package org.adligo.tests4j.models.shared.results;

import java.util.List;

import org.adligo.tests4j.models.shared.asserts.common.I_TestFailure;
import org.adligo.tests4j.models.shared.dependency.I_FieldSignature;
import org.adligo.tests4j.models.shared.dependency.I_MethodSignature;

public interface I_DependencyTestFailure extends I_TestFailure {

	public abstract List<String> getGroupNames();

	public abstract Class<?> getSourceClass();

	public abstract String getCalledClass();

	public abstract I_FieldSignature getField();

	public abstract I_MethodSignature getMethod();

}