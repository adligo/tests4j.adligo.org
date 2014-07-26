package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_MethodErrors;

public class Tests4J_MethodErrors implements I_Tests4J_MethodErrors {

	
	private static final String AFTER_TRIAL_NOT_PUBLIC = "Methods Annotated with @AfterTrial must be public.";
	private static final String AFTER_TRIAL_NOT_STATIC = "Methods Annotated with @AfterTrial must be static.";
	private static final String AFTER_TRIAL_HAS_PARAMS = "Methods Annotated with @AfterTrial must NOT take any parameters.";
	
	private static final String BEFORE_TRIAL_NOT_PUBLIC = "Methods Annotated with @BeforeTrial must be public.";
	private static final String BEFORE_TRIAL_NOT_STATIC = "Methods Annotated with @BeforeTrial must be static.";
	private static final String BEFORE_TRIAL_HAS_PARAMS = "Methods Annotated with @BeforeTrial must NOT take any parameters.";
	
	Tests4J_MethodErrors() {}
	
	@Override
	public String getAfterTrialHasParams() {
		return AFTER_TRIAL_HAS_PARAMS;
	}

	@Override
	public String getAfterTrialNotStatic() {
		return AFTER_TRIAL_NOT_STATIC;
	}

	@Override
	public String getAfterTrialNotPublic() {
		return AFTER_TRIAL_NOT_PUBLIC;
	}
	
	@Override
	public String getBeforeTrialHasParams() {
		return BEFORE_TRIAL_HAS_PARAMS;
	}

	@Override
	public String getBeforeTrialNotStatic() {
		return BEFORE_TRIAL_NOT_STATIC;
	}

	@Override
	public String getBeforeTrialNotPublic() {
		return BEFORE_TRIAL_NOT_PUBLIC;
	}


}
