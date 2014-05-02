package org.adligo.tests4j.models.shared.system.i18n.en;

import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4j_AfterTrialErrors;

public class Tests4J_AfterTrialErrors extends AbstractTests4J_MethodErrors 
	implements I_Tests4j_AfterTrialErrors {

	private static final String NOT_PUBLIC = "Methods Annotated with @AfterTrial must be public.";
	private static final String NOT_STATIC = "Methods Annotated with @AfterTrial must be static.";
	public static final String HAS_PARAMS = "Methods Annotated with @AfterTrial must NOT take any parameters.";
	public static final String IS_ABSTRACT = "Methods Annotated with @AfterTrial must NOT be abstract.";

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

	@Override
	public String getIsAbstract() {
		return IS_ABSTRACT;
	}

}
