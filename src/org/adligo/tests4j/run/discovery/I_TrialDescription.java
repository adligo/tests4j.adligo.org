package org.adligo.tests4j.run.discovery;

import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.shared.common.I_TrialType;

/**
 * this interface was added to provide easy mocking
 * for BeforeTrialAuditor, TestsAuditor, 
 * AfterTrialAuditor, AfterTrialTestsAuditor
 * so that they could be extracted from TrialDescription.
 * 
 * @author scott
 *
 */
public interface I_TrialDescription {
	public String getTrialName();
	public I_TrialType getType();
	public Class<? extends I_AbstractTrial> getTrialClass();
}
