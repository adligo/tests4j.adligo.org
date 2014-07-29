package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.trials.I_Trial;

public class Tests4J_Selection implements I_Tests4J_Selection {
	private Class<? extends I_Trial> clazz;
	private String testMethod;
	
	
	public Tests4J_Selection(Class<? extends I_Trial> trialClazz,String pTestMethod) {
		clazz = trialClazz;
		testMethod = pTestMethod;
	}

	public Tests4J_Selection(I_Tests4J_Selection sel) {
		clazz = sel.getTrial();
		testMethod = sel.getTestMethodName();
	}


	@Override
	public Class<? extends I_Trial> getTrial() {
		return clazz;
	}


	@Override
	public String getTestMethodName() {
		// TODO Auto-generated method stub
		return testMethod;
	}
}
