package org.adligo.jtests.base.shared;

import org.adligo.jtests.models.shared.run.I_AssertListener;


public interface I_AbstractTest {
	public void beforeExhibits();
	public void afterExhibits();
	public void setListener(I_AssertListener p);
}
