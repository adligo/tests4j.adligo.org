package org.adligo.tests4j.shared.asserts.dependency;

import java.util.List;

import org.adligo.tests4j.shared.asserts.common.I_SourceTestFailure;

public interface I_AllowedDependencyFailure extends I_SourceTestFailure {

	public abstract List<String> getGroupNames();

	public abstract String getCalledClass();

	public abstract I_FieldSignature getField();

	public abstract I_MethodSignature getMethod();

}