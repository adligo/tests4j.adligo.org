package org.adligo.tests4j.shared.asserts.reference;

import org.adligo.tests4j.shared.asserts.common.AssertType;
import org.adligo.tests4j.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.shared.asserts.common.I_TestFailureType;
import org.adligo.tests4j.shared.asserts.common.SourceTestFailureMutant;
import org.adligo.tests4j.shared.asserts.common.TestFailureType;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AllowedReferencesFailureMutant extends SourceTestFailureMutant 
	implements I_AllowedReferencesFailure {
	private List<String> groupNames_ = new ArrayList<String>();
	private String calledClass;
	private I_FieldSignature field;
	private I_MethodSignature method;
	private String message;
	
	public AllowedReferencesFailureMutant(I_Tests4J_Constants constants) {
	  message = constants.getResultMessages().getCalledMethodOrFieldsOutsideOfAllowedDepenencies();
	}
	
	public AllowedReferencesFailureMutant(I_AllowedReferencesFailure other){
		super(other);
		groupNames_.addAll(other.getGroupNames());
		calledClass = other.getCalledClass();
		field = other.getField();
		method = other.getMethod();
		message = other.getFailureMessage();
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
		return TestFailureType.AssertReferencesFailure;
	}
	@Override
	public I_AssertType getAssertType() {
		return AssertType.AssertReferences;
	}
	@Override
	public String getFailureMessage() {
		return message;
	}
	
}
