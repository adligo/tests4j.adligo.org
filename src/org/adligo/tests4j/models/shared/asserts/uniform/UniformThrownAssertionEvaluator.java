package org.adligo.tests4j.models.shared.asserts.uniform;

import org.adligo.tests4j.models.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.models.shared.asserts.common.I_ThrownAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.ThrownAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.ThrownAssertionDataMutant;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.models.shared.asserts.line_text.TextLinesCompare;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ResultMessages;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

public class UniformThrownAssertionEvaluator implements I_UniformThrownAssertionEvaluator<I_ThrownAssertionData> {

	@Override
	public I_Evaluation<I_ThrownAssertionData> isUniform(
			I_ExpectedThrownData expected, Throwable actual) {
		
		EvaluationMutant<I_ThrownAssertionData> evaluation = 
				new EvaluationMutant<I_ThrownAssertionData>();
		
		int throwableDiff = 1;
		
		ThrownAssertionDataMutant failureMutant = new ThrownAssertionDataMutant();
		failureMutant.setFailureThrowable(throwableDiff);
		failureMutant.setExpected(expected);
		
		if (actual == null) {
			I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
			return createFailure(evaluation, failureMutant, messages.getNothingWasThrown());
		}
		failureMutant.setActual(actual);
		Class<?> throwableClazz = expected.getThrowableClass();
		Class<?> caughtClass = actual.getClass();
		if ( !throwableClazz.equals(caughtClass)) {
			I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
			return createFailure(evaluation, failureMutant, messages.getThrowableClassMismatch());
		}
		String expectedMessage = expected.getMessage();
		String caughtMessage = actual.getMessage();
		if (expectedMessage == null) {
			if (caughtMessage != null) {
				I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
				return createFailure(evaluation, failureMutant, messages.getThrowableMessageNotUniform());
			}
		} else if (caughtMessage == null) {
			I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
			return createFailure(evaluation, failureMutant, messages.getThrowableMessageNotUniform());
		} else {
			TextLinesCompare tlc = new TextLinesCompare();
			I_TextLinesCompareResult result = tlc.compare(expectedMessage, caughtMessage, true);
			if ( !result.isMatched()) {
				I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
				return createFailure(evaluation, failureMutant, messages.getThrowableMessageNotUniform());
			}
		}
		
		Throwable cause = actual.getCause();
		I_ExpectedThrownData ec = expected.getExpectedCause();
		while (ec != null) {
			throwableDiff++;
			failureMutant.setFailureThrowable(throwableDiff);
			Class<? extends Throwable> expectedCauseClass = ec.getThrowableClass();
			if (cause == null) {
				I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
				return createFailure(evaluation, failureMutant, messages.getThrowableClassMismatch());
			}
			
			if (!cause.getClass().equals(expectedCauseClass)) {
				I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
				return createFailure(evaluation, failureMutant, messages.getThrowableClassMismatch());
			}
			expectedMessage = ec.getMessage();
			caughtMessage = cause.getMessage();
			if (expectedMessage == null) {
				if (caughtMessage != null) {
					I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
					return createFailure(evaluation, failureMutant, messages.getThrowableMessageNotUniform());
				}
			} else if (caughtMessage == null) {
				I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
				return createFailure(evaluation, failureMutant, messages.getThrowableMessageNotUniform());
			} else {
				TextLinesCompare tlc = new TextLinesCompare();
				I_TextLinesCompareResult result = tlc.compare(expectedMessage, caughtMessage, true);
				if ( !result.isMatched()) {
					I_Tests4J_ResultMessages messages =  Tests4J_Constants.CONSTANTS.getResultMessages();
					return createFailure(evaluation, failureMutant, messages.getThrowableMessageNotUniform());
				}
			}
			ec = ec.getExpectedCause();
			cause = cause.getCause();
		}
		evaluation.setSuccess(true);
		return new Evaluation<I_ThrownAssertionData>(evaluation);
	}

	private I_Evaluation<I_ThrownAssertionData> createFailure(
			EvaluationMutant<I_ThrownAssertionData> evaluation,
			ThrownAssertionDataMutant failureMutant, String message) {
		failureMutant.setFailureReason(message);
		evaluation.setFailureReason(message);
		ThrownAssertionData failure = new ThrownAssertionData(failureMutant);
		evaluation.setSuccess(false);
		evaluation.setData(failure);
		return new Evaluation<I_ThrownAssertionData>(evaluation);
	}

}
