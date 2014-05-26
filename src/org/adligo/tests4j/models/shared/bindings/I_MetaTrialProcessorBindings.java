package org.adligo.tests4j.models.shared.bindings;

import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;

public interface I_MetaTrialProcessorBindings extends I_TrialProcessorBindings {
	public I_TrialRunMetadata getMetadata();
}
