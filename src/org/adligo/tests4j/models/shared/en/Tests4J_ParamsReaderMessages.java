package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ParamsReaderMessages;

public class Tests4J_ParamsReaderMessages implements I_Tests4J_ParamsReaderMessages {


	private static final String TEST_SELETIONS_PASSED_TO_TESTS4J_MUST_HAVE_A_CORRESPONDING_TRIAL = "Test seletions passed to tests4j MUST have a corresponding trial.";
	private static final String THE_EVALUATORS_ARE_EXPECTED_TO_AT_LEAST_CONTAIN_THE_DEFAULT_EVALUATOR_LOOKUPS = "The evaluators are expected to at least contain the default evaluator lookups.";
	private static final String THERE_WERE_NOT_ANY_TRIALS_OR_REMOTES_TO_RUN = "There were NO trials or remotes to run.";
	private static final String AUTH_CODE_OR_AUTH_CODE_DEFAULT_REQUIRED = "A auth code, or auth code default is required.";
	private static final String HOST_REQUIRED = "A requires a non empty host name/ip address is required.";
	
	Tests4J_ParamsReaderMessages() {}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.en.I_Tests4J_ParamReaderConstants#getNoTrialsToRun()
	 */
	@Override
	public String getNoTrialsOrRemotesToRun() {
		return THERE_WERE_NOT_ANY_TRIALS_OR_REMOTES_TO_RUN;
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
	
	public String getTestSelectionsMustHaveACorrespondingTrial() {
		return TEST_SELETIONS_PASSED_TO_TESTS4J_MUST_HAVE_A_CORRESPONDING_TRIAL;
	}
}
