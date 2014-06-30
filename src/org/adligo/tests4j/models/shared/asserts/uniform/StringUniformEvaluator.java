package org.adligo.tests4j.models.shared.asserts.uniform;

import java.util.Collections;

import org.adligo.tests4j.models.shared.asserts.CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.line_text.I_LineTextCompareResult;
import org.adligo.tests4j.models.shared.asserts.line_text.LineTextCompare;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.i18n.trials.asserts.I_Tests4J_AssertionResultMessages;

public class StringUniformEvaluator implements I_UniformAssertionEvaluator<String> {
	
	

	@Override
	public Class<String> getType() {
		return String.class;
	}

	@Override
	public I_Evaluation isUniform(I_CompareAssertionData<?> p) {
		String expected = (String) p.getExpected();
		String actual = (String) p.getActual();
		
		I_LineTextCompareResult lineTextResult =  LineTextCompare.compare(expected, actual);
		if (lineTextResult.isMatched()) {
		   EvaluationMutant em = new EvaluationMutant();
		   em.setSuccess(true);
		   return new Evaluation(em);
		} else {
			I_Tests4J_AssertionResultMessages messages = Tests4J_Constants.CONSTANTS.getAssertionResultMessages();
			
			 EvaluationMutant em = new EvaluationMutant();
		   em.setSuccess(false);
		   em.addData(I_LineTextCompareResult.DATA_KEY, lineTextResult);
		   em.setFailureSubMessage(messages.getTheTextWasNOT_Uniform());
		   return new Evaluation(em);
		}
	   
	}

	@Override
	public I_Evaluation isNotUniform(I_CompareAssertionData<?> p) {
		String expected = (String) p.getExpected();
		String actual = (String) p.getActual();
		
		I_LineTextCompareResult lineTextResult =  LineTextCompare.compare(expected, actual);
		if (!lineTextResult.isMatched()) {
		   EvaluationMutant em = new EvaluationMutant();
		   em.setSuccess(true);
		   return new Evaluation(em);
		} else {
			I_Tests4J_AssertionResultMessages messages = Tests4J_Constants.CONSTANTS.getAssertionResultMessages();
			
			EvaluationMutant em = new EvaluationMutant();
			em.setSuccess(false);
			em.addData(I_LineTextCompareResult.DATA_KEY, lineTextResult);
			em.setFailureSubMessage(messages.getTheTextWasUniform());
			return new Evaluation(em);
		}
	}

}
