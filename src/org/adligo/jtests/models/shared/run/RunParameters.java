package org.adligo.jtests.models.shared.run;

import java.util.ArrayList;
import java.util.List;

import org.adligo.jtests.base.shared.asserts.I_AbstractTest;


public class RunParameters {
	private List<Class<I_AbstractTest>> tests = new ArrayList<Class<I_AbstractTest>>();
	/**
	 * this would only be false if running a single
	 * ClassTest instance
	 */
	private boolean packageScope = true;
	private boolean failFast = true;
	public List<Class<I_AbstractTest>> getTests() {
		return tests;
	}
	public boolean isPackageScope() {
		return packageScope;
	}
	public void setTests(List<Class<I_AbstractTest>> p) {
		tests.clear();
		tests.addAll(p);
	}
	
	public void setPackageScope(boolean packageScope) {
		this.packageScope = packageScope;
	}
	public boolean isFailFast() {
		return failFast;
	}
	public void setFailFast(boolean failFast) {
		this.failFast = failFast;
	}
}
