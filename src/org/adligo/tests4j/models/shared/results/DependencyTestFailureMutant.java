package org.adligo.tests4j.models.shared.results;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_TestFailureType;
import org.adligo.tests4j.models.shared.asserts.common.TestFailureType;
import org.adligo.tests4j.models.shared.common.Tests4J_Constants;
import org.adligo.tests4j.models.shared.dependency.I_FieldSignature;
import org.adligo.tests4j.models.shared.dependency.I_MethodSignature;

import com.sun.scenario.effect.Merge;

public class DependencyTestFailureMutant implements I_DependencyTestFailure {
	private List<String> groupNames_ = new ArrayList<String>();
	private Class<?> sourceClass;
	private String calledClass;
	private I_FieldSignature field;
	private I_MethodSignature method;
	private String message = Tests4J_Constants.CONSTANTS
				.getResultMessages().getCalledMethodOrFieldsOutsideOfAllowedDepenencies();
	
	public DependencyTestFailureMutant() {
	}
	
	public DependencyTestFailureMutant(I_DependencyTestFailure other){
		groupNames_.addAll(other.getGroupNames());
		sourceClass = other.getSourceClass();
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
	 * @see org.adligo.tests4j.models.shared.results.I_DependencyTestFailure#getSourceClass()
	 */
	@Override
	public Class<?> getSourceClass() {
		return sourceClass;
	}
	public void setSourceClass(Class<?> sourceClass) {
		this.sourceClass = sourceClass;
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
		return TestFailureType.TestFailure;
	}
	@Override
	public I_AssertType getAssertType() {
		return AssertType.AssertDependency;
	}
	@Override
	public String getFailureMessage() {
		return message;
	}
	@Override
	public String getFailureDetail() {
		return null;
	}
	
}
