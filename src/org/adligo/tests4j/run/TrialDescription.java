package org.adligo.tests4j.run;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.AfterTrial;
import org.adligo.tests4j.models.shared.BeforeTrial;
import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.IgnoreTest;
import org.adligo.tests4j.models.shared.IgnoreTrial;
import org.adligo.tests4j.models.shared.PackageScope;
import org.adligo.tests4j.models.shared.SourceFileScope;
import org.adligo.tests4j.models.shared.Test;
import org.adligo.tests4j.models.shared.TrialType;
import org.adligo.tests4j.models.shared.common.IsEmpty;
import org.adligo.tests4j.models.shared.common.TrialTypeEnum;

public class TrialDescription {
	public static final String THE_TEST_METHOD_MAY_NOT_HAVE_A_NEGATIVE_OR_ZERO_TIMEOUT = 
				"The test method may not have a negative or zero timeout.";
	public static final String UNEXPECTED_EXCEPTION_THROWN_FROM = 
				"Unexpected exception thrown from ";
	public static final String J_TEST_INTERNAL_RUNNER_REQUIRES_A_TEST_COMLETED_LISTENER = 
				"JTestInternalRunner requires a test comleted listener";
	public static final String REFERS_TO_A_NULL_J_TEST_TYPE_TYPE = 
				" refers to a null TestType type.";
	public static final String IS_MISSING_TRIAL_TYPE_ANNOTATION = 
				" is missing a @TrialType annotation.";
	public static final String CLASS_TRIALS_MUST_BE_ANNOTATED_WITH_CLASS_TEST_SCOPE = 
				"SourceFilesTrials must be annotated with SourceFileScope.";
	public static final String IS_MISSING_A_TRIAL_TYPE = " is missing a TrialTypeEnum.";
	public static final String THE_TRIAL = "The trail ";
	public static final String TRIAL_NO_TEST = 
			"Trial Classes must have at least one method annotated with @Test.";
	public static final String TEST_NOT_STATIC = 
			"Methods Annotated with @Test must NOT be static.";
	public static final String TEST_IS_ABSTRACT = 
			"Methods Annotated with @Test must NOT be abstract.";
	public static final String TEST_NOT_PUBLIC = 
			"Methods Annotated with @Test must be public.";
	public static final String TEST_HAS_PARAMS = 
			"Methods Annotated with @Test must not take any parameters";
	private static final String AFTER_TRIAL_HAS_PARAMS = 
			"Methods Annotated with @AfterTrial must not take any parameters.";
	public static final String AFTER_TRIAL_NOT_STATIC = 
			"Methods Annotated with @AfterTrial must be static.";
	public static final String BEFORE_TRIAL_HAS_PARAMS = 
			"Methods Annotated with @BeforeTrial must not take any parameters.";
	public static final String BEFORE_TRIAL_NOT_STATIC = 
			"Methods Annotated with @BeforeTrial must be static.";
	public static final String PACKAGE_TRIALS_MUST_BE_ANNOTATED_WITH_A_PACKAGE_TEST_SCOPE_ANNOTATION = 
			"PackageTrials must be annotated with a PackageScope annotation.";
	public static final String WAS_NOT_ANNOTATED_CORRECTLY = 
			" was not annotated correctly.";

	public static final String J_TEST_INTERNAL_RUNNER_REQUIRES_A_I_RUN_DONE_LISTENER = 
			"JTestInternalRunner requires a I_RunDoneListener.";
	public static final String SIMPLE_RUNNER_REQUIRES_A_I_TEST_RESULTS_PROCESSOR = 
			"SimpleRunner requires a I_TestResultsProcessor.";
	public static final String CLASSES_WHICH_IMPLEMENT_I_ABSTRACT_TRIAL_MUST_HAVE_A_ZERO_ARGUMENT_CONSTRUCTOR = 
			"Classes which implement I_AbstractTrial must have a zero argument constructor.";
	private List<Class<? extends I_AbstractTrial>> tests = new ArrayList<Class<? extends I_AbstractTrial>>();
	private Class<? extends I_AbstractTrial> testClass;
	private I_AbstractTrial trial;
	
	private Method beforeTrial;
	private Method afterTrial;
	private List<TestMethod> testMethods;
	private TrialTypeEnum type;
	private boolean trialCanRun = false;
	private String resultFailureMessage;
	private IllegalArgumentException resultException;
	private boolean ignored;
	private long duration;
	
	public TrialDescription(Class<? extends I_AbstractTrial> pTrial) {
		long start = System.currentTimeMillis();
		
		beforeTrial = null;
		afterTrial = null;
		trial = null;
		
		testClass = pTrial;
		trialCanRun = checkTestClass();
		long end = System.currentTimeMillis();
		duration = end - start;
	}




	



	private boolean checkTestClass() {
		
		type = getType();
		switch(type) {
			case SourceFileTrial:
					SourceFileScope scope = testClass.getAnnotation(SourceFileScope.class);
					if (scope == null) {
						resultFailureMessage = 
								CLASS_TRIALS_MUST_BE_ANNOTATED_WITH_CLASS_TEST_SCOPE;
						resultException	 =
								new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY);
						return false;
					} else {
						Class<?> clazz = scope.sourceClass();
						if (clazz == null) {
							resultFailureMessage = 
									CLASS_TRIALS_MUST_BE_ANNOTATED_WITH_CLASS_TEST_SCOPE;
							resultException	 =
									new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY);
							return false;
						}
					}
				break;
			case API_Trial:
				PackageScope pkgScope = testClass.getAnnotation(PackageScope.class);
				if (pkgScope == null) {
					resultFailureMessage = 
							PACKAGE_TRIALS_MUST_BE_ANNOTATED_WITH_A_PACKAGE_TEST_SCOPE_ANNOTATION;
					resultException	 =
							new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY);
					return false;
				}
				String testedPackageName = pkgScope.packageName();
				if (IsEmpty.isEmpty(testedPackageName)) {
					resultFailureMessage = 
							PACKAGE_TRIALS_MUST_BE_ANNOTATED_WITH_A_PACKAGE_TEST_SCOPE_ANNOTATION;
					resultException	 =
							new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY);
					return false;
				} 
				
				break;
			default:
				//do nothing, functional tests don't require annotations
		}
		IgnoreTrial ignoredTrial = testClass.getAnnotation(IgnoreTrial.class);
		if (ignoredTrial != null) {
			ignored = true;
			return false;
		}
		Method [] methods = testClass.getMethods();
		testMethods = new ArrayList<TestMethod>();
		for (Method method: methods) {
			BeforeTrial bt = method.getAnnotation(BeforeTrial.class);
			if (bt != null) {
				if (!Modifier.isStatic(method.getModifiers())) {
					resultFailureMessage = BEFORE_TRIAL_NOT_STATIC;
					resultException	 =
							new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY);
					return false;
				}
				Class<?> [] params = method.getParameterTypes();
				if (params.length != 0) {
					resultFailureMessage = BEFORE_TRIAL_HAS_PARAMS;
					resultException	 =
							new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY);
					return false;
				}
				beforeTrial = method;
			}
			AfterTrial at = method.getAnnotation(AfterTrial.class);
			if (at != null) {
				if (!Modifier.isStatic(method.getModifiers())) {
					resultFailureMessage = AFTER_TRIAL_NOT_STATIC; 
					resultException	 =
							new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY);
					return false;
				}
				Class<?> [] params = method.getParameterTypes();
				if (params.length != 0) {
					resultFailureMessage = AFTER_TRIAL_HAS_PARAMS;
					resultException	 =
							new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY);
					return false;
				}
				afterTrial = method;
			}
			Test test = method.getAnnotation(Test.class);
			if (test != null) {
				Class<?> [] params = method.getParameterTypes();
				if (params.length != 0) {
					resultFailureMessage = TEST_HAS_PARAMS;
					resultException	= new IllegalArgumentException(testClass.getName() + "." + method.getName() + 
									WAS_NOT_ANNOTATED_CORRECTLY);
					return false;
				}
				if (Modifier.isAbstract(method.getModifiers())) {
					resultFailureMessage = TEST_IS_ABSTRACT;
					resultException	= new IllegalArgumentException(testClass.getName() + "." + method.getName() + 
									WAS_NOT_ANNOTATED_CORRECTLY);
					return false;
				}
				if (Modifier.isStatic(method.getModifiers())) {
					resultFailureMessage = TEST_NOT_STATIC;
					resultException	= new IllegalArgumentException(testClass.getName() + "." + method.getName() + 
									WAS_NOT_ANNOTATED_CORRECTLY);
					return false;
				}
				long timeout = test.timout();
				if (timeout <= 0) {
					resultFailureMessage = THE_TEST_METHOD_MAY_NOT_HAVE_A_NEGATIVE_OR_ZERO_TIMEOUT;
					resultException	= new IllegalArgumentException(testClass.getName() + "." + method.getName() + 
									WAS_NOT_ANNOTATED_CORRECTLY);
				}
				IgnoreTest it = method.getAnnotation(IgnoreTest.class);
				testMethods.add(new TestMethod(method, timeout, it != null));
			}
		}
		if (testMethods.size() == 0) {
			resultFailureMessage = TRIAL_NO_TEST;
			resultException	= new IllegalArgumentException(testClass.getName() + 
							WAS_NOT_ANNOTATED_CORRECTLY);
			return false;
		}
		return true;
	}

	private TrialTypeEnum getType() {
		TrialType type = testClass.getAnnotation(TrialType.class);
		if (type == null)  {
			Class<?> checking = testClass.getSuperclass();
			while (!Object.class.equals(checking)) {
				type = checking.getAnnotation(TrialType.class);
				if (type != null) {
					break;
				}
				checking = checking.getSuperclass();
			}
		}
		if (type == null) {
			resultFailureMessage = THE_TRIAL + testClass.getName() + 
					IS_MISSING_TRIAL_TYPE_ANNOTATION;
			resultException	= new IllegalArgumentException(resultFailureMessage);
			return null;
		}
		TrialTypeEnum typeEnum = type.type();
		return typeEnum;
	}
}
