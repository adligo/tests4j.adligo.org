package org.adligo.tests4j.system.shared.api;

import org.adligo.tests4j.models.shared.results.I_TestResult;

public interface I_Tests4J_TestFinishedListener {
	public void testFinished(I_TestResult p);
}
