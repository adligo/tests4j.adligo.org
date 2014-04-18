package org.adligo.tests4j.models.shared.asserts;

public interface I_CompareAssertionData<T> extends I_AssertionData {
	public T getExpected();
	public T getActual();
}
