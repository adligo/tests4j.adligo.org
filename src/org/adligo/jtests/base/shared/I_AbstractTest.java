package org.adligo.jtests.base.shared;

import org.adligo.jtests.base.shared.common.TestType;

public interface I_AbstractTest {
	public TestType getType();
	public void beforeExhibits();
	public void afterExhibits();
}
