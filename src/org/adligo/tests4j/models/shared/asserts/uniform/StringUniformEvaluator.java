package org.adligo.tests4j.models.shared.asserts.uniform;

import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.models.shared.asserts.line_text.TextLinesCompare;
import org.adligo.tests4j.models.shared.i18n.asserts.I_Tests4J_AssertionResultMessages;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

public class StringUniformEvaluator implements I_UniformAssertionEvaluator<String> {
	
	

	@Override
	public Class<String> getType() {
		return String.class;
	}

	@Override
	public I_Evaluation isUniform(I_CompareAssertionData<?> p) {
		String expected = (String) p.getExpected();
		String actual = (String) p.getActual();
		
		TextLinesCompare tlc = new TextLinesCompare();
		I_TextLinesCompareResult lineTextResult =  tlc.compare(expected, actual);
		if (lineTextResult.isMatched()) {
		   EvaluationMutant em = new EvaluationMutant();
		   em.setSuccess(true);
		   return new Evaluation(em);
		} else {
			I_Tests4J_AssertionResultMessages messages = Tests4J_Constants.CONSTANTS.getAssertionResultMessages();
			
			 EvaluationMutant em = new EvaluationMutant();
		   em.setSuccess(false);
		   em.addData(I_TextLinesCompareResult.DATA_KEY, lineTextResult);
		   em.setFailureSubMessage(messages.getTheTextWasNOT_Uniform());
		   return new Evaluation(em);
		}
	   
	}

	@Override
	public I_Evaluation isNotUniform(I_CompareAssertionData<?> p) {
		String expected = (String) p.getExpected();
		String actual = (String) p.getActual();
	
		
		TextLinesCompare tlc = new TextLinesCompare();
		I_TextLinesCompareResult lineTextResult =  tlc.compare(expected, actual);
		if (!lineTextResult.isMatched()) {
		   EvaluationMutant em = new EvaluationMutant();
		   em.setSuccess(true);
		   return new Evaluation(em);
		} else {
			I_Tests4J_AssertionResultMessages messages = Tests4J_Constants.CONSTANTS.getAssertionResultMessages();
			
			EvaluationMutant em = new EvaluationMutant();
			em.setSuccess(false);
			em.addData(I_TextLinesCompareResult.DATA_KEY, lineTextResult);
			em.setFailureSubMessage(messages.getTheTextWasUniform());
			return new Evaluation(em);
		}
	}

}
