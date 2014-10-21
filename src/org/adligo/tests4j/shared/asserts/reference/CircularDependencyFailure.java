package org.adligo.tests4j.shared.asserts.reference;

import java.util.Collections;
import java.util.List;

import org.adligo.tests4j.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.shared.asserts.common.I_TestFailureType;
import org.adligo.tests4j.shared.asserts.common.SourceTestFailure;

public class CircularDependencyFailure extends SourceTestFailure implements I_CircularDependencyFailure {
	private List<String> classesOutOfBounds_;
	private CircularDependencyFailureMutant mutant_;
	
	public CircularDependencyFailure() {
		classesOutOfBounds_ = Collections.emptyList();
		mutant_ = new CircularDependencyFailureMutant();
	}
	
	public CircularDependencyFailure(I_CircularDependencyFailure p) {
		super(p);
		List<String> cob = p.getClassesOutOfBounds();
		if (cob != null) {
			classesOutOfBounds_ = Collections.unmodifiableList(cob);
		} else {
			classesOutOfBounds_ = Collections.emptyList();
		}
		mutant_ = new CircularDependencyFailureMutant(p, null);
	}

	public String getFailureMessage() {
		return mutant_.getFailureMessage();
	}

	public String getFailureDetail() {
		return mutant_.getFailureDetail();
	}

	public I_TestFailureType getType() {
		return mutant_.getType();
	}

	public I_CircularDependencies getAllowedType() {
		return mutant_.getAllowedType();
	}

	public List<String> getClassesOutOfBounds() {
		return classesOutOfBounds_;
	}

	public int hashCode() {
		return mutant_.hashCode();
	}

	public I_AssertType getAssertType() {
		return mutant_.getAssertType();
	}

	public boolean equals(Object obj) {
		return mutant_.equals(obj);
	}
}
