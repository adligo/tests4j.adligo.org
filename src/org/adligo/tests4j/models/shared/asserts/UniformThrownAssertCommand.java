package org.adligo.tests4j.models.shared.asserts;

import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.models.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.models.shared.asserts.uniform.Evaluation;
import org.adligo.tests4j.models.shared.asserts.uniform.EvaluationMutant;
import org.adligo.tests4j.models.shared.asserts.uniform.I_Evaluation;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionEvaluator;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformThrownAssertionCommand;
import org.adligo.tests4j.models.shared.common.StringMethods;
import org.adligo.tests4j.models.shared.i18n.asserts.I_Tests4J_AssertionInputMessages;
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
public class UniformThrownAssertCommand<D> extends AbstractAssertCommand 
	implements I_UniformThrownAssertionCommand<D> {
	
	public static final String UNIFORM_THROWN_ASSERT_COMMAND_REQUIRES_A_EVALUATOR = 
			"UniformThrownAssertCommand requires a evaluator.";
	private I_ExpectedThrownData data;
	private I_UniformAssertionEvaluator<?, D> evaluator;
	private I_Evaluation<D> result;
	private Throwable actual;
	
	public UniformThrownAssertCommand(String failureMessage, I_ExpectedThrownData pData,  I_UniformAssertionEvaluator<?, D> pEvaluator) {
		super(AssertType.AssertThrownUniform, failureMessage);
		data = pData;
		if (data == null) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			throw new IllegalArgumentException(messages.getExpectedThrownDataRequiresThrowable());
		}
		String exceptionMessage = data.getMessage();
		if (StringMethods.isEmpty(exceptionMessage)) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			throw new IllegalArgumentException(messages.getExpectedThrownDataRequiresMessage());
		}
		if (pEvaluator == null) {
			throw new IllegalArgumentException(UNIFORM_THROWN_ASSERT_COMMAND_REQUIRES_A_EVALUATOR);
		}
		evaluator = pEvaluator;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionCommand#evaluate(org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionEvaluator)
	 */
	@Override
	public boolean evaluate(I_Thrower thrower) {
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
		
		EvaluationMutant<D> em = new EvaluationMutant<D>();
		if (actual == null) {
			em.setSuccess(false);
			em.setFailureReason(messages.getNothingWasThrown());
			result = new Evaluation<D>(em);
			return false;
		}
		result = evaluator.isUniform(new CompareAssertionData<Throwable>(data.getInstance(),actual));
		if (result.isSuccess()) {
			return true;
		} 
		return false;
	}
	
	public I_Evaluation<D> getEvaluation() {
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((actual == null) ? 0 : actual.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((this.result == null) ? 0 : this.result.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		@SuppressWarnings("unchecked")
		UniformThrownAssertCommand<D> other = (UniformThrownAssertCommand<D>) obj;
		if (actual == null) {
			if (other.actual != null)
				return false;
		} else if (!actual.equals(other.actual))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		return true;
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

}
