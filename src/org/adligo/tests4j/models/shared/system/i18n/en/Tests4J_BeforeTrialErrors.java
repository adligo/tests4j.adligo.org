package org.adligo.tests4j.models.shared.system.i18n.en;

import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4j_BeforeTrialErrors;

public class Tests4J_BeforeTrialErrors extends AbstractTests4J_MethodErrors 
	implements I_Tests4j_BeforeTrialErrors {

	private static final String NOT_PUBLIC = "Methods Annotated with @BeforeTrial must be public.";
	private static final String NOT_STATIC = "Methods Annotated with @BeforeTrial must be static.";
	public static final String HAS_PARAMS = "Methods Annotated with @BeforeTrial must NOT take any parameters.";
	
	@Override
	public String getHasParams() {
		return HAS_PARAMS;
	}

	@Override
	public String getNotStatic() {
		return NOT_STATIC;
	}

	@Override
	public String getNotPublic() {
		return NOT_PUBLIC;
	}


}
