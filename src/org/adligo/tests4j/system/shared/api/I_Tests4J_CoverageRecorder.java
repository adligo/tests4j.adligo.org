package org.adligo.tests4j.system.shared.api;

import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileProbes;

/**
 * a coverage recorder that 
 * can record all of the code coverage, 
 * or the code coverage specific to a thread (ie a trial thread).
 * 
 * @author scott
 *
 */
public interface I_Tests4J_CoverageRecorder {
	/**
	 * this start recording for the context of the current recorder
	 */
	public void startRecording();
	/**
	 * this ends recording for the context of the current record.
	 * 
	 * Note the items in the I_PackageCoverage
	 * are should only be packages that actually have classes,
	 * with all child packages rolled in.
	 * @param the names of the classes for 
	 *    which coverage info is requested.
	 */
	public List<I_PackageCoverage> endRecording(Set<String> classNames);
	
	/**
	 * for source file trials return the probes;
	 * @return
	 */
	public I_SourceFileProbes getSourceFileCoverage();
}
