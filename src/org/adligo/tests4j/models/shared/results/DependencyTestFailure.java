package org.adligo.tests4j.models.shared.results;

import java.util.List;

import org.adligo.tests4j.models.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_TestFailureType;
import org.adligo.tests4j.models.shared.dependency.I_FieldSignature;
import org.adligo.tests4j.models.shared.dependency.I_MethodSignature;

public class DependencyTestFailure implements I_DependencyTestFailure {
	private DependencyTestFailureMutant mutant_;
	
	public DependencyTestFailure(I_DependencyTestFailure failure) {
		mutant_ = new DependencyTestFailureMutant(failure);
	}

	public List<String> getGroupNames() {
		return mutant_.getGroupNames();
	}

	public Class<?> getSourceClass() {
		return mutant_.getSourceClass();
	}

	public int hashCode() {
		return mutant_.hashCode();
	}

	public String getCalledClass() {
		return mutant_.getCalledClass();
	}

	public I_FieldSignature getField() {
		return mutant_.getField();
	}

	public I_MethodSignature getMethod() {
		return mutant_.getMethod();
	}

	public I_TestFailureType getType() {
		return mutant_.getType();
	}

	public I_AssertType getAssertType() {
		return mutant_.getAssertType();
	}

	public String getFailureMessage() {
		return mutant_.getFailureMessage();
	}

	public String getFailureDetail() {
		return mutant_.getFailureDetail();
	}

	public boolean equals(Object obj) {
		return mutant_.equals(obj);
	}
}
