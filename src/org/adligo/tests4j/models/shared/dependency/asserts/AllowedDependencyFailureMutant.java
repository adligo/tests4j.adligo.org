package org.adligo.tests4j.models.shared.dependency.asserts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adligo.tests4j.models.shared.dependency.I_FieldSignature;
import org.adligo.tests4j.models.shared.dependency.I_MethodSignature;
import org.adligo.tests4j.shared.asserts.common.AssertType;
import org.adligo.tests4j.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.shared.asserts.common.I_TestFailureType;
import org.adligo.tests4j.shared.asserts.common.SourceTestFailureMutant;
import org.adligo.tests4j.shared.asserts.common.TestFailureType;
import org.adligo.tests4j.shared.common.Tests4J_Constants;

public class AllowedDependencyFailureMutant extends SourceTestFailureMutant 
	implements I_AllowedDependencyFailure {
	private List<String> groupNames_ = new ArrayList<String>();
	private String calledClass;
	private I_FieldSignature field;
	private I_MethodSignature method;
	private String message = Tests4J_Constants.CONSTANTS
				.getResultMessages().getCalledMethodOrFieldsOutsideOfAllowedDepenencies();
	
	public AllowedDependencyFailureMutant() {
	}
	
	public AllowedDependencyFailureMutant(I_AllowedDependencyFailure other){
		super(other);
		groupNames_.addAll(other.getGroupNames());
		calledClass = other.getCalledClass();
		field = other.getField();
		method = other.getMethod();
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.results.I_DependencyTestFailure#getGroupNames()
	 */
	@Override
	public List<String> getGroupNames() {
		return groupNames_;
	}
	public void setGroupNames(Collection<String> groupNames) {
		if (groupNames != null) {
			groupNames_.clear();
			if (groupNames.size() >= 1) {
				groupNames_.addAll(groupNames);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.results.I_DependencyTestFailure#getCalledClass()
	 */
	@Override
	public String getCalledClass() {
		return calledClass;
	}
	public void setCalledClass(String calledClass) {
		this.calledClass = calledClass;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.results.I_DependencyTestFailure#getField()
	 */
	@Override
	public I_FieldSignature getField() {
		return field;
	}
	public void setField(I_FieldSignature field) {
		this.field = field;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.results.I_DependencyTestFailure#getMethod()
	 */
	@Override
	public I_MethodSignature getMethod() {
		return method;
	}
	public void setMethod(I_MethodSignature method) {
		this.method = method;
	}
	@Override
	public I_TestFailureType getType() {
		return TestFailureType.AssertDependencyFailure;
	}
	@Override
	public I_AssertType getAssertType() {
		return AssertType.AssertDependency;
	}
	@Override
	public String getFailureMessage() {
		return message;
	}
	
}
