package org.adligo.jtests.base.shared;

import org.adligo.jtests.base.shared.results.Failure;

public interface I_Runner {
	public void startTest(Class<? extends I_AbstractTest> p);
	public void beforeTestCompleted(String output);
	public void beforeExhibitsCompleted(String output);
	public void exhibitStarting(String exhibitName);
	public void assertCompleted(I_AssertCommand cmd);
	public void assertFailed(Failure failure);
	public void exceptionThrown(Throwable exception);
	public void exhibitCompleted(String output);
	public void afterExhibitsCompleted(String output);
	public void afterTestCompleted(String output);
}
