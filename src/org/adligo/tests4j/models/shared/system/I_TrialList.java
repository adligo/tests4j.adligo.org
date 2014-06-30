package org.adligo.tests4j.models.shared.system;

import java.util.List;

import org.adligo.tests4j.models.shared.trials.AbstractTrial;

public interface I_TrialList {
	public List<Class<? extends AbstractTrial>> getTrials();
}
