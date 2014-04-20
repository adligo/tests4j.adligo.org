package org.adligo.tests4j.models.shared;

import org.adligo.tests4j.models.shared.asserts.I_AssertionHelperInfo;
import org.adligo.tests4j.models.shared.system.I_AssertListener;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;

public class AbstractTrialMemory implements I_AssertionHelperInfo {
	/**
	 * the assertion listener used to notify
	 * the TrialInstanceProcessor that a 
	 * assertion has failed or passed.
	 */
	private I_AssertListener listener;
	/**
	 * may be null if one wasn't passed in
	 */
	private I_CoveragePlugin coveragePlugin;
	/**
	 * the instance of the trial class getting run
	 */
	private Class<?> instanceClass;
	/**
	 * may be null if the byte code wasn't instrumented for
	 * source code coverage.
	 */
	private Class<?> nonInstrumentedInstanceClass;
	
	public I_AssertListener getListener() {
		return listener;
	}
	public I_CoveragePlugin getCoveragePlugin() {
		return coveragePlugin;
	}
	public Class<?> getInstanceClass() {
		return instanceClass;
	}
	public Class<?> getNonInstrumentedInstanceClass() {
		return nonInstrumentedInstanceClass;
	}
	public void setListener(I_AssertListener listener) {
		this.listener = listener;
	}
	public void setCoveragePlugin(I_CoveragePlugin coveragePlugin) {
		this.coveragePlugin = coveragePlugin;
	}
	public void setInstanceClass(Class<?> instanceClass) {
		this.instanceClass = instanceClass;
	}
	public void setNonInstrumentedInstanceClass(
			Class<?> nonInstrumentedInstanceClass) {
		this.nonInstrumentedInstanceClass = nonInstrumentedInstanceClass;
	}
	
}
