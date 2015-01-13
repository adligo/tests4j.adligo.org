package org.adligo.tests4j.shared.asserts.uniform;

import org.adligo.tests4j.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.shared.asserts.line_text.TextLinesCompare;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ResultMessages;

public class StringUniformEvaluator implements I_UniformAssertionEvaluator<String, I_TextLinesCompareResult> {
	 private I_Tests4J_Constants constants_;
	 
	 public StringUniformEvaluator(I_Tests4J_Constants constants) {
	   constants_ = constants;
   }
	

	@Override
	public Class<String> getType() {
		return String.class;
	}

	@Override
	public I_Evaluation<I_TextLinesCompareResult> isUniform(I_CompareAssertionData<String> p) {
		String expected = (String) p.getExpected();
		String actual = (String) p.getActual();
		
		TextLinesCompare tlc = new TextLinesCompare();
		I_TextLinesCompareResult lineTextResult =  tlc.compare(constants_, expected, actual, true);
		if (lineTextResult.isMatched()) {
		   EvaluationMutant<I_TextLinesCompareResult> em = new EvaluationMutant<I_TextLinesCompareResult>();
		   em.setSuccess(true);
		   return new Evaluation<I_TextLinesCompareResult>(em);
		} else {
			I_Tests4J_ResultMessages messages = constants_.getResultMessages();
			
			 EvaluationMutant<I_TextLinesCompareResult> em = new EvaluationMutant<I_TextLinesCompareResult>();
		   em.setSuccess(false);
		   em.setData(lineTextResult);
		   em.setFailureReason(messages.getTheTextWasNOT_Uniform());
		   return new Evaluation<I_TextLinesCompareResult>(em);
		}
	   
	}

	@Override
	public I_Evaluation<I_TextLinesCompareResult> isNotUniform(I_CompareAssertionData<String> p) {
		String expected = (String) p.getExpected();
		String actual = (String) p.getActual();
	
		
		TextLinesCompare tlc = new TextLinesCompare();
		I_TextLinesCompareResult lineTextResult =  tlc.compare(constants_, expected, actual, true);
		if (!lineTextResult.isMatched()) {
		   EvaluationMutant<I_TextLinesCompareResult> em = new EvaluationMutant<I_TextLinesCompareResult>();
		   em.setSuccess(true);
		   return new Evaluation<I_TextLinesCompareResult>(em);
		} else {
			I_Tests4J_ResultMessages messages = constants_.getResultMessages();
			
			EvaluationMutant<I_TextLinesCompareResult> em = new EvaluationMutant<I_TextLinesCompareResult>();
			em.setSuccess(false);
			em.setData(lineTextResult);
			em.setFailureReason(messages.getTheTextWasUniform());
			return new Evaluation<I_TextLinesCompareResult>(em);
		}
	}

}
