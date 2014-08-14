package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;

public interface I_Tests4J_Memory {

	public abstract I_Tests4J_Log getLogger();

	public abstract I_EvaluatorLookup getEvaluationLookup();

}