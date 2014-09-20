package org.adligo.tests4j.models.shared.dependency.asserts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_TestFailureType;
import org.adligo.tests4j.models.shared.asserts.common.SourceTestFailureMutant;
import org.adligo.tests4j.models.shared.asserts.common.TestFailureType;
import org.adligo.tests4j.models.shared.trials.I_CircularDependencies;

public class CircularDependencyFailureMutant  extends SourceTestFailureMutant implements I_CircularDependencyFailure {
	private I_CircularDependencies type_;
	private List<String> classesOutOfBounds_ = new ArrayList<String>();
	
	public CircularDependencyFailureMutant() {}
	
	public CircularDependencyFailureMutant(I_CircularDependencyFailure p) {
		super(p);
		type_ = p.getAllowedType();
		List<String> oob = p.getClassesOutOfBounds();
		if (oob != null) {
			classesOutOfBounds_.addAll(oob);
		}
	}

	public CircularDependencyFailureMutant(I_CircularDependencyFailure p, List<String> classesOb) {
		super(p);
		type_ = p.getAllowedType();
		if (classesOb != null) {
			classesOutOfBounds_.addAll(classesOb);
		}
	}
	
	public I_CircularDependencies getAllowedType() {
		return type_;
	}

	public void setAllowedType(I_CircularDependencies type) {
		this.type_ = type;
	}

	public List<String> getClassesOutOfBounds() {
		return classesOutOfBounds_;
	}

	public void setClassesOutOfBounds(Collection<String> classesOutOfBounds) {
		classesOutOfBounds_.clear();
		if (classesOutOfBounds != null) {
			classesOutOfBounds_.addAll(classesOutOfBounds);
		}
	}
	
	public void addClassOutOfBounds(String classOutOfBounds) {
		classesOutOfBounds_.add(classOutOfBounds);
	}

	@Override
	public I_AssertType getAssertType() {
		return AssertType.AssertCircularDependency;
	}

	@Override
	public I_TestFailureType getType() {
		return TestFailureType.AssertDependencyFailure;
	}

}
