package org.adligo.tests4j.models.shared.asserts.uniform;

import org.adligo.tests4j.models.shared.asserts.AbstractAssertCommand;
import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertCommand;
import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;

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
