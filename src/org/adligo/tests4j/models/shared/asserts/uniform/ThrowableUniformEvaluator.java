package org.adligo.tests4j.models.shared.asserts.uniform;

import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.line_text.I_LineTextCompareResult;
import org.adligo.tests4j.models.shared.asserts.line_text.LineTextCompare;
import org.adligo.tests4j.models.shared.i18n.asserts.I_Tests4J_AssertionResultMessages;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

public class ThrowableUniformEvaluator implements I_UniformAssertionEvaluator<Throwable> {
	@Override
	public Class<Throwable> getType() {
		return Throwable.class;
	}

	@Override
	public I_Evaluation isUniform(I_CompareAssertionData<?> p) {
		I_Tests4J_AssertionResultMessages messages = Tests4J_Constants.CONSTANTS.getAssertionResultMessages();
		
		EvaluationMutant result = new EvaluationMutant();
		Throwable expected = (Throwable) p.getExpected();
		Throwable actual = (Throwable) p.getActual();
		if (expected == null) {
			result.setSuccess(false);
			result.setFailureSubMessage(messages.getTheExpectedValueShouldNeverBeNull());
			return new Evaluation(result);
		}
		if (actual == null) {
			result.setSuccess(false);
			result.setFailureSubMessage(messages.getTheActualValueIsNull());
			return new Evaluation(result);
		}
		Class<? extends Throwable> eClazz = expected.getClass();
		Class<? extends Throwable> aClazz = actual.getClass();
		
		if (!eClazz.isAssignableFrom(aClazz)) {
			result.setSuccess(false);
			result.setFailureSubMessage(messages.getTheActualClassIsNotAssignableFromTheExpectedClass());
			return new Evaluation(result);
		}
		I_LineTextCompareResult lineTextResult = 
				LineTextCompare.compare(expected.getMessage(), actual.getMessage());
		if (!lineTextResult.isMatched()) {
			result.setSuccess(false);
			result.setFailureSubMessage(messages.getTheTextWasNOT_Uniform());
			result.addData(I_LineTextCompareResult.DATA_KEY, lineTextResult);
			return new Evaluation(result);
		}
		result.setSuccess(true);
		return new Evaluation(result);
	}

	@Override
	public I_Evaluation isNotUniform(I_CompareAssertionData<?> p) {
		I_Tests4J_AssertionResultMessages messages = Tests4J_Constants.CONSTANTS.getAssertionResultMessages();
		
		EvaluationMutant result = new EvaluationMutant();
		Throwable expected = (Throwable) p.getExpected();
		Throwable actual = (Throwable) p.getActual();
		if (expected == null) {
			result.setSuccess(false);
			result.setFailureSubMessage(messages.getTheExpectedValueShouldNeverBeNull());
			return new Evaluation(result);
		}
		if (actual == null) {
			result.setSuccess(true);
			return new Evaluation(result);
		}
		Class<? extends Throwable> eClazz = expected.getClass();
		Class<? extends Throwable> aClazz = actual.getClass();
		
		if (!eClazz.isAssignableFrom(aClazz)) {
			result.setSuccess(true);
			return new Evaluation(result);
		}
		I_LineTextCompareResult lineTextResult = 
				LineTextCompare.compare(expected.getMessage(), actual.getMessage());
		if (lineTextResult.isMatched()) {
			result.setSuccess(false);
			result.setFailureSubMessage(messages.getTheTextWasUniform());
			result.addData(I_LineTextCompareResult.DATA_KEY, lineTextResult);
			return new Evaluation(result);
		}
		result.setSuccess(false);
		return new Evaluation(result);
	}

}
