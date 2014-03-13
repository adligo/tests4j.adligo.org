package org.adligo.jtests.base.shared.asserts;

import org.adligo.jtests.models.shared.common.TestType;

public interface I_AbstractTest {
	public TestType getType();
	public void beforeExhibits();
	public void afterExhibits();
}
