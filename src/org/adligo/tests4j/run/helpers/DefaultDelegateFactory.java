package org.adligo.tests4j.run.helpers;

import java.io.PrintStream;

import org.adligo.tests4j.models.shared.system.I_Tests4J_Delegate;
import org.adligo.tests4j.models.shared.system.I_Tests4J_DelegateFactory;

public class DefaultDelegateFactory implements I_Tests4J_DelegateFactory {
	private static final PrintStream ORIGINAL_OUTPUT = System.out;
	
	@Override
	public I_Tests4J_Delegate create() {
		return new Tests4J_Processor(ORIGINAL_OUTPUT);
	}

}
