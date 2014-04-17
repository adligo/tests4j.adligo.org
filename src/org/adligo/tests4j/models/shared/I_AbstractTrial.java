package org.adligo.tests4j.models.shared;

import org.adligo.tests4j.models.shared.system.I_AssertListener;


public interface I_AbstractTrial {
	public void beforeTests();
	public void afterTests();
	public void setListener(I_AssertListener p);
}
