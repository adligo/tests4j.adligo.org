package org.adligo.jtests.models.shared.system;

import java.util.ArrayList;
import java.util.List;

import org.adligo.jtests.models.shared.I_AbstractTrial;


public class RunParameters {
	private List<Class<? extends I_AbstractTrial>> tests = 
				new ArrayList<Class<? extends I_AbstractTrial>>();
	/**
	 * this would only be false if running a single
	 * ClassTest instance
	 */
	private boolean packageScope = true;
	private boolean failFast = true;
	private boolean silent = false;
	
	public List<Class<? extends I_AbstractTrial>> getTests() {
		return tests;
	}
	public boolean isPackageScope() {
		return packageScope;
	}
	public void setTests(List<Class<? extends I_AbstractTrial>> p) {
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
}
