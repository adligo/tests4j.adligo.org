package org.adligo.tests4j.run;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adligo.tests4j.models.shared.AfterTrial;
import org.adligo.tests4j.models.shared.BeforeTrial;
import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.I_AfterSourceFileTrialData;
import org.adligo.tests4j.models.shared.IgnoreTest;
import org.adligo.tests4j.models.shared.IgnoreTrial;
import org.adligo.tests4j.models.shared.PackageScope;
import org.adligo.tests4j.models.shared.SourceFileScope;
import org.adligo.tests4j.models.shared.Test;
import org.adligo.tests4j.models.shared.TrialTimeout;
import org.adligo.tests4j.models.shared.TrialType;
import org.adligo.tests4j.models.shared.common.IsEmpty;
import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.results.TestFailure;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Logger;

public class TrialDescription {
	public static final String THE_TEST_METHOD_MAY_NOT_HAVE_A_NEGATIVE_OR = 
				"The test method may not have a negative.";
	public static final String REFERS_TO_A_NULL_J_TEST_TYPE_TYPE = 
				" refers to a null TestType type.";
	public static final String IS_MISSING_TRIAL_TYPE_ANNOTATION = 
				" is missing a @TrialType annotation.";
	public static final String CLASS_TRIALS_MUST_BE_ANNOTATED_WITH_CLASS_TEST_SCOPE = 
				"SourceFileTrials must be annotated with SourceFileScope.";
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
	private static final String AFTER_USE_CASE_TRIAL_HAS_PARAMS = 
			"Use Case Trial Methods Annotated with @AfterTrial must not take any parameters.";
	private static final String AFTER_SOURCE_FILE_TRIAL_HAS_WRONG_PARAMS = 
			"Source File Trial Methods Annotated with @AfterTrial must take a single parameter I_AfterSourceFileTrialData.";
	private static final String AFTER_API_TRIAL_HAS_WRONG_PARAMS = 
			"Api Trial Methods Annotated with @AfterTrial must take a single parameter I_AfterApiTrialData.";
	
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

	public static final String CLASSES_WHICH_IMPLEMENT_I_ABSTRACT_TRIAL_MUST_HAVE_A_ZERO_ARGUMENT_CONSTRUCTOR = 
			"Classes which implement I_AbstractTrial must have a zero argument constructor.";
	private Class<? extends I_AbstractTrial> trialClass;
	
	private I_AbstractTrial trial;
	
	private Method beforeTrialMethod;
	private Method afterTrialMethod;
	private final List<TestDescription> testMethods = new CopyOnWriteArrayList<TestDescription>();
	private TrialTypeEnum type;
	private boolean trialCanRun = false;
	private String resultFailureMessage;
	private Exception resultException;
	private boolean ignored;
	private long duration;
	private long timeout;
	private I_Tests4J_Logger log;
	
	public TrialDescription(Class<? extends I_AbstractTrial> pTrialClass,
			I_Tests4J_Logger pLog) {
		long start = System.currentTimeMillis();
		log = pLog;
		
		trialClass = pTrialClass;
		
		trialCanRun = checkTestClass();
		long end = System.currentTimeMillis();
		duration = end - start;
	}




	private boolean checkTestClass() {
		
		type = getTypeInternal();
		if (!checkTypeAnnotations()) {
			return false;
		}
		IgnoreTrial ignoredTrial = trialClass.getAnnotation(IgnoreTrial.class);
		if (ignoredTrial != null) {
			ignored = true;
			return false;
		}
		TrialTimeout trialTimeout = trialClass.getAnnotation(TrialTimeout.class);
		if (trialTimeout != null) {
			timeout = trialTimeout.timeout();
		} else {
			timeout = 0;
		}
		
		List<TrialVerificationFailure> failures = locateTestMethods();
		if (failures.size() >= 1) {
			TrialVerificationFailure topFailure = failures.get(0);
			resultFailureMessage = topFailure.getFailureMessage();
			resultException = topFailure.getException();
			return false;
		}
		if (!createInstance()) {
			return false;
		}
		return true;
	}

	private boolean checkTypeAnnotations() {
		switch(type) {
			case SourceFileTrial:
					SourceFileScope scope = trialClass.getAnnotation(SourceFileScope.class);
					if (scope == null) {
						resultFailureMessage = 
								CLASS_TRIALS_MUST_BE_ANNOTATED_WITH_CLASS_TEST_SCOPE;
						resultException	 =
								new IllegalArgumentException(trialClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY);
						return false;
					} else {
						Class<?> clazz = scope.sourceClass();
						if (clazz == null) {
							resultFailureMessage = 
									CLASS_TRIALS_MUST_BE_ANNOTATED_WITH_CLASS_TEST_SCOPE;
							resultException	 =
									new IllegalArgumentException(trialClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY);
							return false;
						}
					}
				break;
			case ApiTrial:
				PackageScope pkgScope = trialClass.getAnnotation(PackageScope.class);
				if (pkgScope == null) {
					resultFailureMessage = 
							PACKAGE_TRIALS_MUST_BE_ANNOTATED_WITH_A_PACKAGE_TEST_SCOPE_ANNOTATION;
					resultException	 =
							new IllegalArgumentException(trialClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY);
					return false;
				}
				String testedPackageName = pkgScope.packageName();
				if (IsEmpty.isEmpty(testedPackageName)) {
					resultFailureMessage = 
							PACKAGE_TRIALS_MUST_BE_ANNOTATED_WITH_A_PACKAGE_TEST_SCOPE_ANNOTATION;
					resultException	 =
							new IllegalArgumentException(trialClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY);
					return false;
				} 
				
				break;
			default:
				//do nothing, functional tests don't require annotations
		}
		return true;
	}

	private List<TrialVerificationFailure> locateTestMethods() {
		List<TrialVerificationFailure> failures = new ArrayList<TrialVerificationFailure>();
		Method [] methods = trialClass.getMethods();
		for (Method method: methods) {
			BeforeTrial bt = method.getAnnotation(BeforeTrial.class);
			if (bt != null) {
				if (!Modifier.isStatic(method.getModifiers())) {
					failures.add(new TrialVerificationFailure(
							BEFORE_TRIAL_NOT_STATIC,
							new IllegalArgumentException(trialClass.getName() + 
									WAS_NOT_ANNOTATED_CORRECTLY)));

				}
				Class<?> [] params = method.getParameterTypes();
				if (params.length != 0) {
					failures.add(new TrialVerificationFailure(
							BEFORE_TRIAL_HAS_PARAMS,
							new IllegalArgumentException(trialClass.getName() + 
									WAS_NOT_ANNOTATED_CORRECTLY)));
				}
				beforeTrialMethod = method;
			}
			AfterTrial at = method.getAnnotation(AfterTrial.class);
			if (at != null) {
				if (!Modifier.isStatic(method.getModifiers())) {
					failures.add(new TrialVerificationFailure(
							AFTER_TRIAL_NOT_STATIC,
							new IllegalArgumentException(trialClass.getName() + 
									WAS_NOT_ANNOTATED_CORRECTLY)));
				}
				Class<?> [] params = method.getParameterTypes();
				switch (type) {
					case SourceFileTrial:
						if (params.length != 1) {
							failures.add(new TrialVerificationFailure(
									AFTER_SOURCE_FILE_TRIAL_HAS_WRONG_PARAMS,
									new IllegalArgumentException(trialClass.getName() + 
											WAS_NOT_ANNOTATED_CORRECTLY)));
						} else {
							Class<?> param = params[0];
							if ( !I_AfterSourceFileTrialData.class.isAssignableFrom(param)) {
								failures.add(new TrialVerificationFailure(
										AFTER_SOURCE_FILE_TRIAL_HAS_WRONG_PARAMS,
										new IllegalArgumentException(trialClass.getName() + 
												WAS_NOT_ANNOTATED_CORRECTLY)));
							}
						}
					case ApiTrial:
						if (params.length != 1) {
							failures.add(new TrialVerificationFailure(
									AFTER_API_TRIAL_HAS_WRONG_PARAMS,
									new IllegalArgumentException(trialClass.getName() + 
											WAS_NOT_ANNOTATED_CORRECTLY)));
						} else {
							Class<?> param = params[0];
							if ( !I_AfterSourceFileTrialData.class.isAssignableFrom(param)) {
								failures.add(new TrialVerificationFailure(
										AFTER_API_TRIAL_HAS_WRONG_PARAMS,
										new IllegalArgumentException(trialClass.getName() +
												WAS_NOT_ANNOTATED_CORRECTLY)));
							}
						}
					default:
						if (params.length != 0) {
							failures.add(new TrialVerificationFailure(
									AFTER_USE_CASE_TRIAL_HAS_PARAMS,
									new IllegalArgumentException(trialClass.getName() + 
											WAS_NOT_ANNOTATED_CORRECTLY)));
						}
				}
				afterTrialMethod = method;
			}
			Test test = method.getAnnotation(Test.class);
			if (test != null) {
				Class<?> [] params = method.getParameterTypes();
				if (params.length != 0) {
					failures.add(new TrialVerificationFailure(
							TEST_HAS_PARAMS,
							new IllegalArgumentException(trialClass.getName() + "." + method.getName() + 
									WAS_NOT_ANNOTATED_CORRECTLY)));
				}
				if (Modifier.isAbstract(method.getModifiers())) {
					failures.add(new TrialVerificationFailure(
							TEST_IS_ABSTRACT,
							new IllegalArgumentException(trialClass.getName() + "." + method.getName() + 
									WAS_NOT_ANNOTATED_CORRECTLY)));
				}
				if (Modifier.isStatic(method.getModifiers())) {
					failures.add(new TrialVerificationFailure(
							TEST_NOT_STATIC,
							new IllegalArgumentException(trialClass.getName() + "." + method.getName() + 
									WAS_NOT_ANNOTATED_CORRECTLY)));
				}
				long timeout = test.timeout();
				if (timeout < 0) {
					failures.add(new TrialVerificationFailure(
							THE_TEST_METHOD_MAY_NOT_HAVE_A_NEGATIVE_OR,
							new IllegalArgumentException(trialClass.getName() + "." + method.getName() + 
									WAS_NOT_ANNOTATED_CORRECTLY)));
				}
				IgnoreTest it = method.getAnnotation(IgnoreTest.class);
				testMethods.add(new TestDescription(method, timeout, it != null));
			}
		}
		if (testMethods.size() == 0) {
			failures.add(new TrialVerificationFailure(
					TRIAL_NO_TEST,
					new IllegalArgumentException(trialClass.getName() + 
							WAS_NOT_ANNOTATED_CORRECTLY)));
		}
		return failures;
	}

	private boolean createInstance() {
		try {
			Constructor<? extends I_AbstractTrial> constructor =
					trialClass.getConstructor(new Class[] {});
			trial = constructor.newInstance();
		} catch (Exception x) {
			resultFailureMessage = CLASSES_WHICH_IMPLEMENT_I_ABSTRACT_TRIAL_MUST_HAVE_A_ZERO_ARGUMENT_CONSTRUCTOR; 
			resultException	= x;
			if (log.isEnabled()) {
				x.printStackTrace(log.getOutput());
				Throwable cause = x.getCause();
				while (cause != null) {
					cause.printStackTrace(log.getOutput());
					cause = cause.getCause();
				}
			}
			return false;
		}
		return true;
	}

	public TrialTypeEnum getType() {
		if (type == null) {
			return TrialTypeEnum.UnknownTrialType;
		}
		return type;
	}
	
	private TrialTypeEnum getTypeInternal() {
		TrialType type = trialClass.getAnnotation(TrialType.class);
		if (type == null)  {
			Class<?> checking = trialClass.getSuperclass();
			while (!Object.class.equals(checking)) {
				type = checking.getAnnotation(TrialType.class);
				if (type != null) {
					break;
				}
				checking = checking.getSuperclass();
			}
		}
		if (type == null) {
			resultFailureMessage = THE_TRIAL + trialClass.getName() + 
					IS_MISSING_TRIAL_TYPE_ANNOTATION;
			resultException	= new IllegalArgumentException(resultFailureMessage);
			return null;
		}
		TrialTypeEnum typeEnum = type.type();
		return typeEnum;
	}

	public boolean isTrialCanRun() {
		return trialCanRun;
	}

	public String getResultFailureMessage() {
		return resultFailureMessage;
	}

	public Exception getResultException() {
		return resultException;
	}

	public void setTrialCanRun(boolean trialCanRun) {
		this.trialCanRun = trialCanRun;
	}

	public void setResultFailureMessage(String resultFailureMessage) {
		this.resultFailureMessage = resultFailureMessage;
	}

	public void setResultException(IllegalArgumentException resultException) {
		this.resultException = resultException;
	}

	public boolean isIgnored() {
		return ignored;
	}

	public long getDuration() {
		return duration;
	}

	public String getTrialName() {
		return trialClass.getName();
	}
	public I_AbstractTrial getTrial() {
		return trial;
	}

	public Method getBeforeTrialMethod() {
		return beforeTrialMethod;
	}

	public Method getAfterTrialMethod() {
		return afterTrialMethod;
	}

	public int getTestMethodsSize() {
		return testMethods.size();
	}
	
	public Iterator<TestDescription> getTestMethods() {
		return testMethods.iterator();
	}

	public Class<? extends I_AbstractTrial> getTrialClass() {
		return trialClass;
	}

	public long getTimeout() {
		return timeout;
	}
}
