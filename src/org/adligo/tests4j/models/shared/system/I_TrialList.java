package org.adligo.tests4j.models.shared.system;

import java.util.List;

import org.adligo.tests4j.models.shared.I_AbstractTrial;

public interface I_TrialList {
	public List<Class<? extends I_AbstractTrial>> getTrials();
	public int getMinTests();
	public int getMinAsserts();
	public int getMinUniqueAssertions();
}
