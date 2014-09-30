package org.adligo.tests4j.system.shared.api;

import java.util.List;

import org.adligo.tests4j.system.shared.trials.I_Trial;

public interface I_Tests4J_TrialList {
	public List<Class<? extends I_Trial>> getTrials();
}
