package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ParamReaderConstants;

public class Tests4J_ParamsReaderConstants implements I_Tests4J_ParamReaderConstants {


	public static final String THE_EVALUATORS_ARE_EXPECTED_TO_AT_LEAST_CONTAIN_THE_DEFAULT_EVALUATOR_LOOKUPS = "The evaluators are expected to at least contain the default evaluator lookups.";
	public static final String THERE_WERE_NOT_ANY_TRIALS_TO_RUN = "There were NOT any trials to run.";
	public static final String AUTH_CODE_OR_AUTH_CODE_DEFAULT_REQUIRED = "A auth code, or auth code default is required.";
	public static final String HOST_REQUIRED = "A requires a non empty host name/ip address is required.";
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ParamReaderConstants#getNoTrialsToRun()
	 */
	@Override
	public String getNoTrialsToRun() {
		return THERE_WERE_NOT_ANY_TRIALS_TO_RUN;
	}


	public String getTheEvaluatorsAreExpectedToContainTheDefaultKeys() {
		return THE_EVALUATORS_ARE_EXPECTED_TO_AT_LEAST_CONTAIN_THE_DEFAULT_EVALUATOR_LOOKUPS;
	}
	
	
	public String getAuthCodeOrAuthCodeDefaultRequired() {
		return AUTH_CODE_OR_AUTH_CODE_DEFAULT_REQUIRED;
	}
	public String getHostRequired() {
		return HOST_REQUIRED;
	}
	
}
