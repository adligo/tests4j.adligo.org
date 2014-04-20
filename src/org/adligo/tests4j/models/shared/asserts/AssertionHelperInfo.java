package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.system.I_AssertListener;
import org.adligo.tests4j.models.shared.system.I_CoveragePlugin;

public class AssertionHelperInfo implements I_AssertionHelperInfo {
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
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.I_AbstractTrialMemory#getListener()
	 */
	@Override
	public I_AssertListener getListener() {
		return listener;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.I_AbstractTrialMemory#getCoveragePlugin()
	 */
	@Override
	public I_CoveragePlugin getCoveragePlugin() {
		return coveragePlugin;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.I_AbstractTrialMemory#getInstanceClass()
	 */
	@Override
	public Class<?> getInstanceClass() {
		return instanceClass;
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
	
}
