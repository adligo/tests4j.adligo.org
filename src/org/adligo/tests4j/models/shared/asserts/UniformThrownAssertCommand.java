package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.models.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.models.shared.asserts.uniform.Evaluation;
import org.adligo.tests4j.models.shared.asserts.uniform.EvaluationMutant;
import org.adligo.tests4j.models.shared.asserts.uniform.I_Evaluation;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionEvaluator;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformThrownAssertionCommand;
import org.adligo.tests4j.models.shared.i18n.asserts.I_Tests4J_AssertionResultMessages;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

public class UniformThrownAssertCommand extends AbstractAssertCommand 
	implements I_UniformThrownAssertionCommand {
	
	public static final String NULL_DATA = "UniformAssertCommand requires non null CompareAssertionData.";
	public static final String BAD_TYPE = 
			"UniformAssertCommand requires it's type to be one of AssertType.UNIFORM_TYPES";
	private I_ExpectedThrownData data;
	private AssertType type;
	private I_Evaluation result;
	private Throwable actual;
	
	public UniformThrownAssertCommand(I_AssertType pType, String failureMessage, I_ExpectedThrownData pData) {
		super(pType, failureMessage);
		if (AssertType.AssertThrownUniform != pType) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
		type = (AssertType) pType;
		data = pData;
		if (data == null) {
			throw new IllegalArgumentException(NULL_DATA);
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
		return new CompareAssertionData<>(data,actual);
	}

	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionCommand#evaluate(org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionEvaluator)
	 */
	@Override
	public boolean evaluate(I_Thrower thrower, I_UniformAssertionEvaluator<?> e) {
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
		switch (type) {
			case AssertThrownUniform:
					result = e.isUniform(new CompareAssertionData<>(data.getInstance(),actual));
					if (result.isSuccess()) {
						return true;
					} 
				break;
		}
		
		return false;
	}

	public String getFailureSubMessage() {
		if (result == null) {
			return  "";
		}
		return result.getFailureSubMessage();
	}
	
	public I_Evaluation getResult() {
		return result;
	}
}
