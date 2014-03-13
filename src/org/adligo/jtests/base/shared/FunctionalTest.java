package org.adligo.jtests.base.shared;

import org.adligo.jtests.models.shared.common.TestType;

public class FunctionalTest extends AbstractTest {

	@Override
	public TestType getType() {
		return TestType.FunctionalTest;
	}

}
