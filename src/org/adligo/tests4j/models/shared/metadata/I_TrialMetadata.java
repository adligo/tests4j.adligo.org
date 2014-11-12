package org.adligo.tests4j.models.shared.metadata;

import java.util.List;

import org.adligo.tests4j.shared.common.I_TrialType;
import org.adligo.tests4j.shared.xml.I_XML_Consumer;
import org.adligo.tests4j.shared.xml.I_XML_Producer;

public interface I_TrialMetadata extends I_XML_Producer {
	public static final String TAG_NAME = "trialMetadata";
	public static final String TRIAL_NAME_ATTRIBUTE = "name";
	public static final String TIMEOUT_ATTRIBUTE = "timeout";
	public static final String IGNORED_ATTRIBUTE = "ignored";
	public static final String MIN_CODE_COVERAGE_ATTRIBUTE = "minCodeCoverage";
	public static final String BEFORE_TRIAL_METHOD_NAME_ATTRIBUTE = "beforeTrial";
	public static final String AFTER_TRIAL_METHOD_NAME_ATTRIBUTE = "afterTrial";
	public static final String TESTS_NESTED_TAG_NAME = "tests";
	public static final String TYPE_ATTRIBUTE = "type";
	public static final String TESTED_SOURCE_FILE_ATTRIBUTE = "testedSourceFile";
	public static final String TESTED_PACKAGE_ATTRIBUTE = "testedPackage";
	public static final String TESTED_SYSTEM_ATTRIBUTE = "testedSystem";
	
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
	 * from the @UseCase annotation
	 * @return
	 */
	public I_UseCaseBrief getUseCase();
	/**
	 * this pertains to SourceFileTrials only
	 * for the v0_1 release, but could be
	 * expanded to ApiTrials at some point
	 * 
	 * @return null if there wasn't a setting
	 */
	public Double getMinimumCodeCoverage();
}