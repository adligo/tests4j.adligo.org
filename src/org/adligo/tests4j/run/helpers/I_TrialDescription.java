package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;

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
	public TrialTypeEnum getType();
}
