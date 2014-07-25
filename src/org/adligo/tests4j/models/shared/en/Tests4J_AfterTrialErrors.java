package org.adligo.tests4j.models.shared.en;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AfterTrialErrors;

public class Tests4J_AfterTrialErrors extends AbstractTests4J_MethodErrors 
	implements I_Tests4J_AfterTrialErrors {

	private static final String NOT_PUBLIC = "Methods Annotated with @AfterTrial must be public.";
	private static final String NOT_STATIC = "Methods Annotated with @AfterTrial must be static.";
	public static final String HAS_PARAMS = "Methods Annotated with @AfterTrial must NOT take any parameters.";
	
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
