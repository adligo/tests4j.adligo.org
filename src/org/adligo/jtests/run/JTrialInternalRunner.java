package org.adligo.jtests.run;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.adligo.jtests.models.shared.AfterTrial;
import org.adligo.jtests.models.shared.BeforeTrial;
import org.adligo.jtests.models.shared.ClassScope;
import org.adligo.jtests.models.shared.I_AbstractTrial;
import org.adligo.jtests.models.shared.JTrialType;
import org.adligo.jtests.models.shared.PackageScope;
import org.adligo.jtests.models.shared.Test;
import org.adligo.jtests.models.shared.asserts.I_AssertCommand;
import org.adligo.jtests.models.shared.common.IsEmpty;
import org.adligo.jtests.models.shared.common.TrialType;
import org.adligo.jtests.models.shared.results.I_TestFailure;
import org.adligo.jtests.models.shared.results.TestResult;
import org.adligo.jtests.models.shared.results.TestResultMutant;
import org.adligo.jtests.models.shared.results.TestRunResult;
import org.adligo.jtests.models.shared.results.TestRunResultMutant;
import org.adligo.jtests.models.shared.results.TrialFailure;
import org.adligo.jtests.models.shared.results.TrialResult;
import org.adligo.jtests.models.shared.results.TrialResultMutant;
import org.adligo.jtests.models.shared.system.ByteListOutputStream;
import org.adligo.jtests.models.shared.system.I_AssertListener;
import org.adligo.jtests.models.shared.system.I_TestFinishedListener;
import org.adligo.jtests.models.shared.system.I_TestRunListener;

public class JTrialInternalRunner implements Runnable, I_TestFinishedListener {
	public static final String UNEXPECTED_EXCEPTION_THROWN_FROM = 
				"Unexpected exception thrown from ";
	public static final String J_TEST_INTERNAL_RUNNER_REQUIRES_A_TEST_COMLETED_LISTENER = "JTestInternalRunner requires a test comleted listener";
	public static final String REFERS_TO_A_NULL_J_TEST_TYPE_TYPE = " refers to a null TestType type.";
	public static final String IS_MISSING_A_J_TEST_TYPE_ANNOTATION = " is missing a @JTestType annotation.";
	public static final String CLASS_TRIALS_MUST_BE_ANNOTATED_WITH_CLASS_TEST_SCOPE = "ClassTrials must be annotated with ClassScope.";
	public static final String IS_MISSING_A_TRIAL_TYPE = " is missing a TrialType.";
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
	public static final String WAS_NOT_ANNOTATED_CORRECTLY = " was not annotated correctly.";

	public static final String J_TEST_INTERNAL_RUNNER_REQUIRES_A_I_RUN_DONE_LISTENER = "JTestInternalRunner requires a I_RunDoneListener.";
	public static final String SIMPLE_RUNNER_REQUIRES_A_I_TEST_RESULTS_PROCESSOR = "SimpleRunner requires a I_TestResultsProcessor.";
	public static final String CLASSES_WHICH_IMPLEMENT_I_ABSTRACT_TRIAL_MUST_HAVE_A_ZERO_ARGUMENT_CONSTRUCTOR = "Classes which implement I_AbstractTrial must have a zero argument constructor.";
	private List<Class<? extends I_AbstractTrial>> tests = new ArrayList<Class<? extends I_AbstractTrial>>();
	private Class<? extends I_AbstractTrial> testClass;
	private I_AbstractTrial trial;
	private I_TestRunListener listener;
	private boolean silent = false;
	private ByteListOutputStream blos = new ByteListOutputStream(64);
	private PrintStream originalOut;
	private PrintStream originalErr;
	private PrintStream captureOut;
	private PrintStream captureErr;
	private Method beforeTrial;
	private Method afterTrial;
	private ArrayBlockingQueue<Method> testMethods;
	
	private TrialResultMutant trialResultMutant;
	private TestResult testResult;
	private TestRunResultMutant testRun = new TestRunResultMutant();
	private JTestsInternalRunner testsRunner = new JTestsInternalRunner();
	private Thread testRunnerThread = new Thread(testsRunner);
	
	public JTrialInternalRunner(List<Class<? extends I_AbstractTrial>> pTests, 
			I_TestRunListener pTestCompletedLister) {
		
		testRun.setStartTime(System.currentTimeMillis());
		tests.addAll(pTests);
		originalOut = System.out;
		originalErr = System.err;
		
		captureOut = new PrintStream(blos);
		System.setOut(captureOut);
		
		captureErr = new PrintStream(blos);
		System.setErr(captureErr);
		
		listener = pTestCompletedLister;
		if (listener == null) {
			throw new IllegalArgumentException(J_TEST_INTERNAL_RUNNER_REQUIRES_A_TEST_COMLETED_LISTENER);
		}
	}
	
	@Override
	public void run() {
		Thread.currentThread().setUncaughtExceptionHandler(JTestUncaughtExceptionHandler.HANDLER);
		for (Class<? extends I_AbstractTrial> clazz: tests) {
			runTrial(clazz);
			
		}
		System.setOut(originalOut);
		System.setErr(originalErr);
		long end = System.currentTimeMillis();
		long duration = end - testRun.getStartTime();
		testRun.setRunTime(duration);
		listener.onRunCompleted(new TestRunResult(testRun));
	}

	private void runTrial(final Class<? extends I_AbstractTrial> clazz) {

		beforeTrial = null;
		afterTrial = null;
		trial = null;
		
		testClass = clazz;
		trialResultMutant = new TrialResultMutant();
		trialResultMutant.setTestName(testClass.getName());
		if (checkTestClass()) {
			
			runBeforeTrial();
			if (startTest(testClass)) {
				runTests();
				runAfterTrial();
			} 
		} 
		listener.onTestCompleted(testClass, trial, new TrialResult(trialResultMutant));
		trialResultMutant = null;
	}


	private void runAfterTrial() {
		if (afterTrial != null) {
			try {
				afterTrial.invoke(trial, new Object[] {});
			} catch (Exception e) {
				failTestOnException(UNEXPECTED_EXCEPTION_THROWN_FROM + testClass + 
						"." + afterTrial.getName(), e, null);
			}
			trialResultMutant.setAfterTestOutput(blos.toString());
		}
	}

	private void runBeforeTrial()  {
		if (beforeTrial != null) {
			try {
				beforeTrial.invoke(trial, new Object[] {});
			} catch (Exception e) {
				failTestOnException(UNEXPECTED_EXCEPTION_THROWN_FROM + testClass + 
						"." + beforeTrial.getName(), e, null);
			}
			trialResultMutant.setBeforeTestOutput(blos.toString());
		}
	}

	/**
	 * 
	 * @return all suceeded
	 */
	private void runTests() {
		testsRunner.setListener(this);
		testsRunner.setTrial(trial);
		Iterator<Method> methods = testMethods.iterator();
		while (methods.hasNext()) {
			testsRunner.setTestMethod(methods.next());
			testResult = null;
			
			try {
				testRunnerThread.start();
				while(testResult == null) {
					Thread.sleep(1);
				}
			} catch (InterruptedException x) {
				x.printStackTrace(originalOut);
			}
		}
		
	}
	
	private boolean startTest(Class<? extends I_AbstractTrial> p) {
		testClass = p;
		try {
			if (!silent) {
				originalOut.println("");
				originalOut.println("Creating Test Instance for " + testClass);
			}
			Constructor<? extends I_AbstractTrial> constructor =
					testClass.getConstructor(new Class[] {});
			trial = constructor.newInstance();
			trial.setListener(testsRunner);
		} catch (Exception x) {
			failTestOnException(
					CLASSES_WHICH_IMPLEMENT_I_ABSTRACT_TRIAL_MUST_HAVE_A_ZERO_ARGUMENT_CONSTRUCTOR, 
					x, null);
			return false;
		} 
		return true;
	}


	



	public I_TestRunListener getTestCompletedListener() {
		return listener;
	}


	private void failTestOnException(String message, Throwable p, TrialType type) {
		trialResultMutant.setPassed(false);
		TrialFailure failure = new TrialFailure(message, p);
		trialResultMutant.setFailure(failure);
		if (type != null) {
			trialResultMutant.setType(type);
		}
	}
	
	public boolean isSilent() {
		return silent;
	}

	public void setSilent(boolean silent) {
		this.silent = silent;
	}


	private boolean checkTestClass() {
		
		TrialType type = getType();
		trialResultMutant.setType(type);
		switch(type) {
			case ClassTrial:
					ClassScope scope = testClass.getAnnotation(ClassScope.class);
					if (scope == null) {
						failTestOnException(CLASS_TRIALS_MUST_BE_ANNOTATED_WITH_CLASS_TEST_SCOPE, 
								new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY),
								type);
						return false;
					} else {
						Class<?> clazz = scope.testedClass();
						if (clazz == null) {
							failTestOnException(CLASS_TRIALS_MUST_BE_ANNOTATED_WITH_CLASS_TEST_SCOPE, 
									new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY),
									type);
							return false;
						}
						trialResultMutant.setTestedClassName(clazz.getName());
					}
				break;
			case PackageTrial:
				PackageScope pkgScope = testClass.getAnnotation(PackageScope.class);
				if (pkgScope == null) {
					failTestOnException(PACKAGE_TRIALS_MUST_BE_ANNOTATED_WITH_A_PACKAGE_TEST_SCOPE_ANNOTATION, 
							new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY),
							type);
					return false;
				}
				String testedPackageName = pkgScope.testedPackageName();
				if (IsEmpty.isEmpty(testedPackageName)) {
					failTestOnException(PACKAGE_TRIALS_MUST_BE_ANNOTATED_WITH_A_PACKAGE_TEST_SCOPE_ANNOTATION, 
							new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY),
							type);
					return false;
				} else {
					trialResultMutant.setTestedClassName(testedPackageName);
				}
				
				break;
			default:
				//do nothing, functional tests don't require annotations
		}
		Method [] methods = testClass.getMethods();
		List<Method> testMethodsLocal = new ArrayList<Method>();
		for (Method method: methods) {
			BeforeTrial bt = method.getAnnotation(BeforeTrial.class);
			if (bt != null) {
				if (!Modifier.isStatic(method.getModifiers())) {
					failTestOnException(BEFORE_TRIAL_NOT_STATIC, 
							new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY),
							type);
					return false;
				}
				Class<?> [] params = method.getParameterTypes();
				if (params.length != 0) {
					failTestOnException(BEFORE_TRIAL_HAS_PARAMS, 
							new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY),
							type);
					return false;
				}
				beforeTrial = method;
			}
			AfterTrial at = method.getAnnotation(AfterTrial.class);
			if (at != null) {
				if (!Modifier.isStatic(method.getModifiers())) {
					failTestOnException(AFTER_TRIAL_NOT_STATIC, 
							new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY),
							type);
					return false;
				}
				Class<?> [] params = method.getParameterTypes();
				if (params.length != 0) {
					failTestOnException(AFTER_TRIAL_HAS_PARAMS, 
							new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY),
							type);
					return false;
				}
				afterTrial = method;
			}
			Test exhibit = method.getAnnotation(Test.class);
			if (exhibit != null) {
				Class<?> [] params = method.getParameterTypes();
				if (params.length != 0) {
					failTestOnException(TEST_HAS_PARAMS, 
							new IllegalArgumentException(testClass.getName() + "." + method.getName() + 
									WAS_NOT_ANNOTATED_CORRECTLY), type);
					return false;
				}
				if (Modifier.isAbstract(method.getModifiers())) {
					failTestOnException(TEST_IS_ABSTRACT, 
							new IllegalArgumentException(testClass.getName() + "." + method.getName() + 
									WAS_NOT_ANNOTATED_CORRECTLY), type);
					return false;
				}
				if (Modifier.isStatic(method.getModifiers())) {
					failTestOnException(TEST_NOT_STATIC, 
							new IllegalArgumentException(testClass.getName() + "." + method.getName() + 
									WAS_NOT_ANNOTATED_CORRECTLY), type);
					return false;
				}
				testMethodsLocal.add(method);
			}
		}
		if (testMethodsLocal.size() == 0) {
			failTestOnException(TRIAL_NO_TEST, 
					new IllegalArgumentException(testClass.getName() + 
							WAS_NOT_ANNOTATED_CORRECTLY), type);
			return false;
		}
		testMethods = new ArrayBlockingQueue<Method>(testMethodsLocal.size());
		
		return true;
	}

	private TrialType getType() {
		JTrialType jtype = testClass.getAnnotation(JTrialType.class);
		if (jtype == null)  {
			Class<?> checking = testClass.getSuperclass();
			while (!Object.class.equals(checking)) {
				jtype = checking.getAnnotation(JTrialType.class);
				if (jtype != null) {
					break;
				}
				checking = checking.getSuperclass();
			}
		}
		if (jtype == null) {
			failTestOnException(THE_TRIAL + testClass.getName() + 
					IS_MISSING_A_J_TEST_TYPE_ANNOTATION, 
					new IllegalArgumentException(), null);
		}
		TrialType type = jtype.getType();
		return type;
	}

	@Override
	public void testFinished(TestResult p) {
		TestResultMutant forOut = new TestResultMutant(p);
		forOut.setOutput(blos.toString());
		testResult = new TestResult(forOut);
	}
}
