package org.adligo.tests4j.shared.asserts.reference;

import java.util.Collections;
import java.util.List;

import org.adligo.tests4j.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.shared.asserts.common.I_TestFailureType;
import org.adligo.tests4j.shared.asserts.common.SourceTestFailure;

public class AllowedReferencesFailure extends SourceTestFailure 
	implements I_AllowedReferencesFailure {
	private AllowedReferencesFailureMutant mutant_;
	private List<String> groupNames_;
	
	public AllowedReferencesFailure() {
		mutant_ = new AllowedReferencesFailureMutant();
		groupNames_ = Collections.emptyList();
	}
	public AllowedReferencesFailure(I_AllowedReferencesFailure failure) {
		super(failure);
		mutant_ = new AllowedReferencesFailureMutant(failure);
		List<String> ogn = failure.getGroupNames();
		if (ogn != null) {
			groupNames_ = Collections.unmodifiableList(ogn);
		} else {
			groupNames_ = Collections.emptyList();
		}
	}

	public List<String> getGroupNames() {
		return groupNames_;
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
