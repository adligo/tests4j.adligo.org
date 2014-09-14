package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.models.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.models.shared.asserts.common.I_ThrownAssertionData;
import org.adligo.tests4j.models.shared.asserts.uniform.I_Evaluation;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformThrownAssertionCommand;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformThrownAssertionEvaluator;
import org.adligo.tests4j.models.shared.common.Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionInputMessages;

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
	implements I_UniformThrownAssertionCommand<I_ThrownAssertionData> {
	
	public static final String UNIFORM_THROWN_ASSERT_COMMAND_REQUIRES_A_EVALUATOR = 
			"UniformThrownAssertCommand requires a evaluator.";
	private I_ExpectedThrownData expected;
	private I_UniformThrownAssertionEvaluator<I_ThrownAssertionData> evaluator;
	private I_Evaluation<I_ThrownAssertionData> result;
	private Throwable actual;
	/**
	 * which exception in the chain 
	 * didn't match, one based (not zero based).
	 */
	private Integer failureThrowable;
	/**
	 * the reason the throwable wasn't a match.
	 */
	private String failureReason;
	
	public UniformThrownAssertCommand(String failureMessage, I_ExpectedThrownData pData,  
			I_UniformThrownAssertionEvaluator<I_ThrownAssertionData> pEvaluator) {
		super(AssertType.AssertThrownUniform, failureMessage);
		expected = pData;
		if (expected == null) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			throw new IllegalArgumentException(messages.getExpectedThrownDataRequiresThrowable());
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
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			throw new IllegalArgumentException(messages.getIThrowerIsRequired());
		}
		try {
			thrower.run();
		} catch (Throwable t) {
			actual = t;
		}
		result = evaluator.isUniform(expected, actual);
		if (result.isSuccess()) {
			return true;
		} 
		return false;
	}
	
	public I_Evaluation<I_ThrownAssertionData> getEvaluation() {
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((actual == null) ? 0 : actual.hashCode());
		result = prime * result + ((expected == null) ? 0 : expected.hashCode());
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
		UniformThrownAssertCommand other = (UniformThrownAssertCommand) obj;
		if (actual == null) {
			if (other.actual != null)
				return false;
		} else if (!actual.equals(other.actual))
			return false;
		if (expected == null) {
			if (other.expected != null)
				return false;
		} else if (!expected.equals(other.expected))
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
		return (I_AssertionData) result.getData();
	}

	public Integer getFailureThrowable() {
		return failureThrowable;
	}

	public String getFailureReason() {
		return failureReason;
	}

}
