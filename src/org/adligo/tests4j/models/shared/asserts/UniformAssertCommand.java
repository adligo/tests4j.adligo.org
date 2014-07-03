package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.uniform.I_Evaluation;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionCommand;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionEvaluator;
import org.adligo.tests4j.models.shared.i18n.asserts.I_Tests4J_AssertionResultMessages;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

public class UniformAssertCommand extends AbstractAssertCommand 
	implements I_UniformAssertionCommand {
	
	public static final String NULL_DATA = "UniformAssertCommand requires non null CompareAssertionData.";
	public static final String BAD_TYPE = 
			"UniformAssertCommand requires it's type to be one of AssertType.UNIFORM_TYPES";
	private I_CompareAssertionData<?> data;
	private AssertType type;
	private I_Evaluation result;
	
	public UniformAssertCommand(I_AssertType pType, String failureMessage, I_CompareAssertionData<?> pData) {
		super(pType, failureMessage);
		if (!AssertType.UNIFORM_TYPES.contains(pType)) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
		type = (AssertType) pType;
		data = pData;
		if (data == null) {
			throw new IllegalArgumentException(NULL_DATA);
		}
		if (data.getExpected() == null) {
			I_Tests4J_AssertionResultMessages messages = Tests4J_Constants.CONSTANTS.getAssertionResultMessages();
			throw new IllegalArgumentException(messages.getTheExpectedValueShouldNeverBeNull());
		}
	}

	@Override
	public Object getExpected() {
		return data.getExpected();
	}

	@Override
	public Object getActual() {
		return data.getActual();
	}

	@Override
	public I_AssertionData getData() {
		return data;
	}

	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionCommand#evaluate(org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionEvaluator)
	 */
	@Override
	public boolean evaluate(I_UniformAssertionEvaluator<?> e) {
		switch (type) {
			case AssertUniform:
					result = e.isUniform((I_CompareAssertionData<?>) data);
					if (result.isSuccess()) {
						return true;
					} 
				break;
			case AssertNotUniform:
				result = e.isNotUniform((I_CompareAssertionData<?>) data);
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
