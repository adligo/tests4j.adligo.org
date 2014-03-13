package org.adligo.jtests.models.shared.run;

import java.util.ArrayList;
import java.util.List;

import org.adligo.jtests.base.shared.I_AbstractTest;


public class RunParameters {
	private List<Class<? extends I_AbstractTest>> tests = 
				new ArrayList<Class<? extends I_AbstractTest>>();
	/**
	 * this would only be false if running a single
	 * ClassTest instance
	 */
	private boolean packageScope = true;
	private boolean failFast = true;
	private boolean silent = false;
	private I_AllTestsDoneListener allTestsDoneListener;
	
	public List<Class<? extends I_AbstractTest>> getTests() {
		return tests;
	}
	public boolean isPackageScope() {
		return packageScope;
	}
	public void setTests(List<Class<? extends I_AbstractTest>> p) {
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
	public boolean isSilent() {
		return silent;
	}
	public void setSilent(boolean silent) {
		this.silent = silent;
	}
	public I_AllTestsDoneListener getAllTestsDoneListener() {
		return allTestsDoneListener;
	}
	public void setAllTestsDoneListener(I_AllTestsDoneListener runDoneListener) {
		this.allTestsDoneListener = runDoneListener;
	}
}
