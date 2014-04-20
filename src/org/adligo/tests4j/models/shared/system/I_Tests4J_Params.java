package org.adligo.tests4j.models.shared.system;

import java.util.List;

import org.adligo.tests4j.models.shared.AbstractTrial;

public interface I_Tests4J_Params {
	public List<Class<? extends AbstractTrial>> getTrials();
	public I_Tests4J_Logger getLog();
}
