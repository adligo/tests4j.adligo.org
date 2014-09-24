package org.adligo.tests4j.shared.asserts.common;

public class SourceTestFailureMutant extends TestFailureMutant implements I_SourceTestFailure {
	private Class<?> sourceClass;
	
	public SourceTestFailureMutant() {}
	
	public SourceTestFailureMutant(I_SourceTestFailure failure) {
		super(failure);
		sourceClass = failure.getSourceClass();
	}

	public Class<?> getSourceClass() {
		return sourceClass;
	}

	public void setSourceClass(Class<?> sourceClass) {
		this.sourceClass = sourceClass;
	}
}
