package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.run.common.I_JseSystem;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Delegate;
import org.adligo.tests4j.system.shared.api.I_Tests4J_DelegateFactory;

public class DefaultDelegateFactory implements I_Tests4J_DelegateFactory {
	
	@Override
	public I_Tests4J_Delegate create(I_JseSystem system) {
		return new Tests4J_Processor(system);
	}

}
