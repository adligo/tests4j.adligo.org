package org.adligo.tests4j.models.shared.metadata;

import java.util.List;

import org.adligo.tests4j.shared.common.I_TrialType;
import org.adligo.tests4j.shared.xml.I_XML_Consumer;
import org.adligo.tests4j.shared.xml.I_XML_Producer;

public interface I_TrialMetadata {
	
	/**
	 * the class name of the trial
	 * @return
	 */
	public abstract String getTrialName();

	/**
	 * the trial time out annotation setting or
	 * null
	 * @return
	 */
	public abstract Long getTimeout();

	/**
	 * if the IgnoreTrial annotation is present
	 * @return
	 */
	public abstract boolean isIgnored();

	/**
	 * the name of the method annotated with @BeforeTrial
	 * or null
	 * @return
	 */
	public abstract String getBeforeTrialMethodName();
	/**
	 * the name of the method annotated with @AfterTrial
	 * or null
	 * @return
	 */
	public abstract String getAfterTrialMethodName();

	/**
	 * the test metadata, in order of execution.
	 * @return
	 */
	public abstract List<I_TestMetadata> getTests();

	/**
	 * the number of tests
	 * @return
	 */
	public int getTestCount();
	/**
	 * the number of tests with the @IgnoreTest annotation
	 * @return
	 */
	public int getIgnoredTestCount();
	
	/**
	 * the type of trial
	 * @return
	 */
	public I_TrialType getType();

	/**
	 * from the SourceFileTrial annotation sourceClass
	 * @return
	 */
	public String getTestedSourceFile() ;

	/**
	 * from the PackageScope annotation
	 * @return
	 */
	public String getTestedPackage();

	/**
	 * this pertains to SourceFileTrials only
	 * for the v0_1 release, but could be
	 * expanded to ApiTrials at some point
	 * 
	 * @return null if there wasn't a setting
	 */
	public Double getMinimumCodeCoverage();
}