package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.trials.SourceFileScope;
import org.adligo.tests4j.models.shared.xml.I_XML_Consumer;
import org.adligo.tests4j.models.shared.xml.I_XML_Producer;

/**
 * parameters for the coverage plug-in,
 * so the I_Tests4J_Params can be a central point
 * of configuration for the entire process.
 * @author scott
 *
 */
public interface I_Tests4J_CoveragePluginParams extends I_XML_Consumer, I_XML_Producer {
	/**
	 * if true a new I_Tests4J_CoverageRecorder
	 * is created for each trial/test thread combination
	 * to record code coverage for those specific trials/tests.
	 * This allows tests4j a isolated code coverage result
	 * which is different than the aggergate result
	 * and can be asserted on.
	 * @see {@link SourceFileScope#minCoverage()}
	 * @return
	 */
	public boolean isCanThreadLocalGroupRecord();
	/**
	 * this is mostly for diagnosing problems with
	 * the plug-ins instrumentation code.
	 * @return
	 */
	public boolean isWriteOutInstrumentedClasses();
	/**
	 * a relative path from the current runtime
	 * where the instrumented classes are to be written
	 * if isWriteOutInstrumentedClasses is true,
	 * defaults to 'instrumentedClasses'
	 * @return
	 */
	public String getInstrumentedClassOutputFolder();
}
