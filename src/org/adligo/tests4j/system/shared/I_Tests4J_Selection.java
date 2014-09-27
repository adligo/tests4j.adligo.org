package org.adligo.tests4j.system.shared;

import org.adligo.tests4j.models.shared.trials.I_Trial;

/**
 * the selection of a single test
 * (may be in a collection if there were more than one
 * tests selected).
 * 
 * @author scott
 *
 */
public interface I_Tests4J_Selection {
	public Class<? extends I_Trial> getTrial();
	public String getTestMethodName();
}
