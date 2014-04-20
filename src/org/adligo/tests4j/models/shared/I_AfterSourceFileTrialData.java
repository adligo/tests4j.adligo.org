package org.adligo.tests4j.models.shared;

import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.system.I_AssertListener;

public interface I_AfterSourceFileTrialData {
	public I_AssertListener getListener();
	public I_SourceFileCoverage getSourceFileCoverage();
}
