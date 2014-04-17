package org.adligo.tests4j.models.shared.metadata;

public interface I_TestMetadata {

	public abstract String getTestName();

	public abstract Long getTimeout();

	public abstract boolean isSkipped();

}