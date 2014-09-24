package org.adligo.tests4j.models.shared.dependency.asserts;

import java.util.List;

import org.adligo.tests4j.models.shared.trials.I_CircularDependencies;
import org.adligo.tests4j.shared.asserts.common.I_SourceTestFailure;
import org.adligo.tests4j.shared.asserts.common.I_TestFailure;

public interface I_CircularDependencyFailure extends I_SourceTestFailure {
	public I_CircularDependencies getAllowedType();
	public List<String> getClassesOutOfBounds();
}
