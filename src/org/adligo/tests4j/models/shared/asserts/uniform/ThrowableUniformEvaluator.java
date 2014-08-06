package org.adligo.tests4j.models.shared.asserts.uniform;

import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.models.shared.asserts.line_text.TextLinesCompare;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ResultMessages;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

public class ThrowableUniformEvaluator implements I_UniformAssertionEvaluator<Throwable, I_TextLinesCompareResult> {
	@Override
	public Class<Throwable> getType() {
		return Throwable.class;
	}

	@Override
	public I_Evaluation<I_TextLinesCompareResult> isUniform(I_CompareAssertionData<Throwable> p) {
		
		EvaluationMutant<I_TextLinesCompareResult> result = new EvaluationMutant<I_TextLinesCompareResult>();
		Throwable expected = (Throwable) p.getExpected();
		Throwable actual = (Throwable) p.getActual();
		if (expected == null) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			
			result.setSuccess(false);
			result.setFailureReason(messages.getTheExpectedValueShouldNeverBeNull());
			return new Evaluation<I_TextLinesCompareResult>(result);
		}
		if (actual == null) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			result.setSuccess(false);
			result.setFailureReason(messages.getTheActualValueIsNull());
			return new Evaluation<I_TextLinesCompareResult>(result);
		}
		Class<? extends Throwable> eClazz = expected.getClass();
		Class<? extends Throwable> aClazz = actual.getClass();
		
		if (!eClazz.isAssignableFrom(aClazz)) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			result.setSuccess(false);
			result.setFailureReason(messages.getTheActualClassIsNotAssignableFromTheExpectedClass());
			return new Evaluation<I_TextLinesCompareResult>(result);
		}
		TextLinesCompare tlc = new TextLinesCompare();
		I_TextLinesCompareResult lineTextResult = 
				tlc.compare(expected.getMessage(), actual.getMessage(), true);
		if (!lineTextResult.isMatched()) {
			I_Tests4J_ResultMessages messages = Tests4J_Constants.CONSTANTS.getResultMessages();
			
			result.setSuccess(false);
			result.setFailureReason(messages.getTheTextWasNOT_Uniform());
			result.setData(lineTextResult);
			return new Evaluation<I_TextLinesCompareResult>(result);
		}
		result.setSuccess(true);
		return new Evaluation<I_TextLinesCompareResult>(result);
	}

	@Override
	public I_Evaluation<I_TextLinesCompareResult> isNotUniform(I_CompareAssertionData<Throwable> p) {
		
		EvaluationMutant<I_TextLinesCompareResult> result = new EvaluationMutant<I_TextLinesCompareResult>();
		Throwable expected = (Throwable) p.getExpected();
		Throwable actual = (Throwable) p.getActual();
		if (expected == null) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			
			result.setSuccess(false);
			result.setFailureReason(messages.getTheExpectedValueShouldNeverBeNull());
			return new Evaluation<I_TextLinesCompareResult>(result);
		}
		if (actual == null) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			result.setSuccess(false);
			result.setFailureReason(messages.getTheActualValueIsNull());
			return new Evaluation<I_TextLinesCompareResult>(result);
		}
		Class<? extends Throwable> eClazz = expected.getClass();
		Class<? extends Throwable> aClazz = actual.getClass();
		
		if (!eClazz.isAssignableFrom(aClazz)) {
			result.setSuccess(true);
			return new Evaluation<I_TextLinesCompareResult>(result);
		}
		TextLinesCompare tlc = new TextLinesCompare();
		I_TextLinesCompareResult lineTextResult = 
				tlc.compare(expected.getMessage(), actual.getMessage(), true);
		if (lineTextResult.isMatched()) {
			I_Tests4J_ResultMessages messages = Tests4J_Constants.CONSTANTS.getResultMessages();
			
			result.setSuccess(false);
			result.setFailureReason(messages.getTheTextWasUniform());
			result.setData(lineTextResult);
			return new Evaluation<I_TextLinesCompareResult>(result);
		}
		result.setSuccess(true);
		return new Evaluation<I_TextLinesCompareResult>(result);
	}

}
