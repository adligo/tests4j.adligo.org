package org.adligo.tests4j.models.shared.metadata;

import java.util.List;

import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.xml.I_XML_Consumer;
import org.adligo.tests4j.models.shared.xml.I_XML_Producer;

public interface I_TrialMetadata extends I_XML_Producer, I_XML_Consumer {
	public static final String TAG_NAME = "trialMetadata";
	public static final String TRIAL_NAME_ATTRIBUTE = "name";
	public static final String TIMEOUT_ATTRIBUTE = "timeout";
	public static final String IGNORED_ATTRIBUTE = "ignored";
	public static final String BEFORE_TRIAL_METHOD_NAME_ATTRIBUTE = "beforeTrial";
	public static final String AFTER_TRIAL_METHOD_NAME_ATTRIBUTE = "afterTrial";
	public static final String TESTS_NESTED_TAG_NAME = "tests";
	public static final String TYPE_ATTRIBUTE = "type";
	public static final String TESTED_SOURCE_FILE_ATTRIBUTE = "testedSourceFile";
	public static final String TESTED_PACKAGE_ATTRIBUTE = "testedPackage";
	public static final String TESTED_SYSTEM_ATTRIBUTE = "testedSystem";
	
	public abstract String getTrialName();

	public abstract Long getTimeout();

	public abstract boolean isIgnored();

	public abstract String getBeforeTrialMethodName();

	public abstract String getAfterTrialMethodName();

	public abstract List<I_TestMetadata> getTests();

	public int getTestCount();
	
	public int getIgnoredTestCount();
	
	public TrialType getType();

	public String getTestedSourceFile() ;

	public String getTestedPackage();

	public String getSystem();

	public I_UseCaseMetadata getUseCase();
}