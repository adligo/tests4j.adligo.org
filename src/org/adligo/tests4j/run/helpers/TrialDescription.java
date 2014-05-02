package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adligo.tests4j.models.shared.AfterTrialTests;
import org.adligo.tests4j.models.shared.BeforeTrial;
import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.IgnoreTest;
import org.adligo.tests4j.models.shared.IgnoreTrial;
import org.adligo.tests4j.models.shared.PackageScope;
import org.adligo.tests4j.models.shared.SourceFileScope;
import org.adligo.tests4j.models.shared.Test;
import org.adligo.tests4j.models.shared.TrialTimeout;
import org.adligo.tests4j.models.shared.TrialType;
import org.adligo.tests4j.models.shared.common.IsEmpty;
import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;

/**
 * a generally immutable class that represents/wrapps
 * a Trial class adding the reflection Methods exc
 * so that it can be run.
 * 
 * @author scott
 *
 */
public class TrialDescription implements I_TrialDescription {

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



	
	public static final String PACKAGE_TRIALS_MUST_BE_ANNOTATED_WITH_A_PACKAGE_TEST_SCOPE_ANNOTATION = 
			"PackageTrials must be annotated with a PackageScope annotation.";
	public static final String WAS_NOT_ANNOTATED_CORRECTLY = 
			" was not annotated correctly.";

	public static final String CLASSES_WHICH_IMPLEMENT_I_ABSTRACT_TRIAL_MUST_HAVE_A_ZERO_ARGUMENT_CONSTRUCTOR = 
			"Classes which implement I_AbstractTrial must have a zero argument constructor.";
	private Class<? extends I_AbstractTrial> trialClass;
	
	private I_AbstractTrial trial;
	
	private Method beforeTrialMethod;
	private Method afterTrialTestsMethod;
	private Method afterTrialMethod;
	private final List<TestDescription> testMethods = new CopyOnWriteArrayList<TestDescription>();
	private TrialTypeEnum type;
	private boolean trialCanRun = false;
	private String resultFailureMessage;
	private Exception resultException;
	private boolean ignored;
	private long duration;
	private long timeout;
	private I_Tests4J_Reporter reporter;
	
	public TrialDescription(Class<? extends I_AbstractTrial> pTrialClass,
			I_Tests4J_Reporter pLog) {
		long start = System.currentTimeMillis();
		reporter = pLog;
		
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
			if (BeforeTrialAuditor.audit(this, failures, method)) {
				beforeTrialMethod = method;
			}
			TestDescription testDesc = TestAuditor.audit(this, failures, method);
			if (testDesc != null) {
				testMethods.add(testDesc);
			}
			if (AfterTrialTestsAuditor.audit(this, failures, method)) {
				afterTrialTestsMethod = method;
			}
			if (AfterTrialAuditor.audit(this, failures, method)) {
				afterTrialMethod = method;
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
			Object o = constructor.newInstance();
			trial = (I_AbstractTrial) o;
		} catch (Exception x) {
			resultFailureMessage = CLASSES_WHICH_IMPLEMENT_I_ABSTRACT_TRIAL_MUST_HAVE_A_ZERO_ARGUMENT_CONSTRUCTOR; 
			resultException	= x;
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

	public Method getAfterTrialTestsMethod() {
		return afterTrialTestsMethod;
	}
}
