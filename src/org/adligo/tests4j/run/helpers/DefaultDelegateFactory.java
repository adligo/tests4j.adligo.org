package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.shared.common.I_System;
import org.adligo.tests4j.system.shared.I_Tests4J_Delegate;
import org.adligo.tests4j.system.shared.I_Tests4J_DelegateFactory;

public class DefaultDelegateFactory implements I_Tests4J_DelegateFactory {
	
	@Override
	public I_Tests4J_Delegate create(I_System system) {
		return new Tests4J_Processor(system);
	}

}
