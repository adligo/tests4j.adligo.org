package org.adligo.tests4j.models.shared.system;

import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;

public class SynchronizedCoverageRecorder implements I_CoverageRecorder {
	private I_CoverageRecorder delegate;

	public SynchronizedCoverageRecorder(I_CoverageRecorder p) {
		delegate = p;
	}
	
	public String getScope() {
		return delegate.getScope();
	}

	public void startRecording() {
		delegate.startRecording();
	}

	public void pauseRecording() {
		delegate.pauseRecording();
	}

	public List<I_PackageCoverage> endRecording() {
		return delegate.endRecording();
	}
	
}
