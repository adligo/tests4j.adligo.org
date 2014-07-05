package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.models.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.models.shared.asserts.uniform.Evaluation;
import org.adligo.tests4j.models.shared.asserts.uniform.EvaluationMutant;
import org.adligo.tests4j.models.shared.asserts.uniform.I_Evaluation;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionEvaluator;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformThrownAssertionCommand;
import org.adligo.tests4j.models.shared.i18n.asserts.I_Tests4J_AssertionResultMessages;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

/**
 * A class to represent a assertThrownUniform 
 * assertion.
 * Note this class is NOT in the .uniform package
 *  so that it can extend AbstractAssertCommand with out
 *  creating a circular package dependency.  
 *  (.asserts - - depends on - - > .asserts.uniform)
 * @author scott
 *
 */
public class UniformThrownAssertCommand extends AbstractAssertCommand 
	implements I_UniformThrownAssertionCommand {
	
	private I_ExpectedThrownData data;
	private I_Evaluation result;
	private Throwable actual;
	
	public UniformThrownAssertCommand(String failureMessage, I_ExpectedThrownData pData) {
		super(AssertType.AssertThrownUniform, failureMessage);
		data = pData;
		if (data == null) {
			I_Tests4J_AssertionResultMessages messages = Tests4J_Constants.CONSTANTS.getAssertionResultMessages();
			throw new IllegalArgumentException(messages.getTheExpectedValueShouldNeverBeNull());
		}
		
	}

	@Override
	public Object getExpected() {
		return data.getInstance();
	}

	@Override
	public Object getActual() {
		return actual;
	}

	@Override
	public I_AssertionData getData() {
		ThrownAssertionDataMutant tadm = new ThrownAssertionDataMutant();
		tadm.setExpectedMessage(data.getMessage());
		tadm.setExpectedThrowable(data.getThrowableClass());
		
		//there may not have been a caught exception
		if (actual != null) {
			tadm.setActualThrowable(actual.getClass());
			tadm.setActualMessage(actual.getMessage());
		}
		return new ThrownAssertionData(tadm);
	}

	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionCommand#evaluate(org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionEvaluator)
	 */
	@Override
	public boolean evaluate(I_Thrower thrower, I_UniformAssertionEvaluator<?> e) {
		if (thrower == null) {
			I_Tests4J_AssertionResultMessages messages = Tests4J_Constants.CONSTANTS.getAssertionResultMessages();
			throw new IllegalArgumentException(messages.getIThrowerIsRequired());
		}
		try {
			thrower.run();
		} catch (Throwable t) {
			actual = t;
		}
		I_Tests4J_AssertionResultMessages messages = Tests4J_Constants.CONSTANTS.getAssertionResultMessages();
		
		EvaluationMutant em = new EvaluationMutant();
		if (actual == null) {
			em.setSuccess(false);
			em.setFailureSubMessage(messages.getNothingWasThrown());
			result = new Evaluation(em);
			return false;
		}
		result = e.isUniform(new CompareAssertionData<>(data.getInstance(),actual));
		if (result.isSuccess()) {
			return true;
		} 
		return false;
	}
	
	public I_Evaluation getResult() {
		return result;
	}
}
