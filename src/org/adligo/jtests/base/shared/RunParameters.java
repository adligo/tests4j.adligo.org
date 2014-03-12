package org.adligo.jtests.base.shared;

import java.util.ArrayList;
import java.util.List;


public class RunParameters {
	private List<I_AbstractTest> tests = new ArrayList<I_AbstractTest>();
	/**
	 * this would only be false if running a single
	 * ClassTest instance
	 */
	private boolean packageScope = true;
	public List<? extends I_AbstractTest> getTests() {
		return tests;
	}
	public boolean isPackageScope() {
		return packageScope;
	}
	public void setTests(List<? extends I_AbstractTest> p) {
		tests.clear();
		tests.addAll(p);
	}
	
	public void setPackageScope(boolean packageScope) {
		this.packageScope = packageScope;
	}
	
	
}
