package org.adligo.jtests.models.shared;

import org.adligo.jtests.models.shared.system.I_AssertListener;


public interface I_AbstractTrial {
	public void beforeTests();
	public void afterTests();
	public void setListener(I_AssertListener p);
}
