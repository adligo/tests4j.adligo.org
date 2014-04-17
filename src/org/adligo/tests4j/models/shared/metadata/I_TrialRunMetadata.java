package org.adligo.tests4j.models.shared.metadata;

import java.util.List;

public interface I_TrialRunMetadata {

	public abstract List<? extends I_TrialMetadata> getTrials();

}