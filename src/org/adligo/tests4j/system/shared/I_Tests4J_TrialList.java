package org.adligo.tests4j.system.shared;

import java.util.List;

import org.adligo.tests4j.models.shared.trials.I_Trial;

public interface I_Tests4J_TrialList {
	public List<Class<? extends I_Trial>> getTrials();
}
