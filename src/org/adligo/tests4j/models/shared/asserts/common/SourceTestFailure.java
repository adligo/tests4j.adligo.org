package org.adligo.tests4j.models.shared.asserts.common;

public class SourceTestFailure extends TestFailure implements I_SourceTestFailure {
	private Class<?> sourceClass;
	
	public SourceTestFailure() {}
	
	public SourceTestFailure(I_SourceTestFailure failure) {
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
