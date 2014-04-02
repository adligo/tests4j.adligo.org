package org.adligo.jtests.run;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.adligo.jtests.models.shared.AfterTrial;
import org.adligo.jtests.models.shared.BeforeTrial;
import org.adligo.jtests.models.shared.ClassScope;
import org.adligo.jtests.models.shared.I_AbstractTrial;
import org.adligo.jtests.models.shared.IgnoreTest;
import org.adligo.jtests.models.shared.IgnoreTrial;
import org.adligo.jtests.models.shared.JTrialType;
import org.adligo.jtests.models.shared.PackageScope;
import org.adligo.jtests.models.shared.Test;
import org.adligo.jtests.models.shared.common.IsEmpty;
import org.adligo.jtests.models.shared.common.TrialType;
import org.adligo.jtests.models.shared.results.TestFailure;
import org.adligo.jtests.models.shared.results.TestFailureMutant;
import org.adligo.jtests.models.shared.results.TestResult;
import org.adligo.jtests.models.shared.results.TestResultMutant;
import org.adligo.jtests.models.shared.results.TrialRunResult;
import org.adligo.jtests.models.shared.results.TrialRunResultMutant;
import org.adligo.jtests.models.shared.results.TrialFailure;
import org.adligo.jtests.models.shared.results.TrialResult;
import org.adligo.jtests.models.shared.results.TrialResultMutant;
import org.adligo.jtests.models.shared.system.ByteListOutputStream;
import org.adligo.jtests.models.shared.system.I_TestFinishedListener;
import org.adligo.jtests.models.shared.system.I_TestRunListener;

public class TrialProcessor implements Runnable, I_TestFinishedListener {
	public static final String THE_TEST_METHOD_MAY_NOT_HAVE_A_NEGATIVE_OR_ZERO_TIMEOUT = "The test method may not have a negative or zero timeout.";
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
	private List<TestMethod> testMethods;
	
	private TrialResultMutant trialResultMutant;
	private TrialRunResultMutant testRun = new TrialRunResultMutant();
	private TestRunable testsRunner = new TestRunable();
	private ExecutorService testRunService;
	private ArrayBlockingQueue<Boolean> blocking = new ArrayBlockingQueue<Boolean>(1);
	
	public TrialProcessor(List<Class<? extends I_AbstractTrial>> pTests, 
			I_TestRunListener pTestCompletedLister) {
		
		testsRunner.setListener(this);
		testRunService = Executors.newSingleThreadExecutor();
		
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
		
		
		for (Class<? extends I_AbstractTrial> clazz: tests) {
			runTrial(clazz);
			
		}
		System.setOut(originalOut);
		System.setErr(originalErr);
		long end = System.currentTimeMillis();
		long duration = end - testRun.getStartTime();
		testRun.setRunTime(duration);
		listener.onRunCompleted(new TrialRunResult(testRun));
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
			if (startTrial(testClass)) {
				testsRunner.setTrial(trial);
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
		Iterator<TestMethod> methods = testMethods.iterator();
		
		
		while (methods.hasNext()) {
			
			TestMethod tm = methods.next();
			
			runTest(tm);
		}
		
	}

	private void runTest(TestMethod tm) {
		Method method = tm.getMethod();
		blocking.clear();
		if (tm.isIgnore()) {
			TestResultMutant trm = new TestResultMutant();
			trm.setName(method.getName());
			trm.setIgnored(true);
			trialResultMutant.addResult(trm);
		} else {
			trial.beforeTests();
			testsRunner.setTestMethod(method);
			testRunService.execute(testsRunner);
			
			try {
				Boolean result = blocking.poll(tm.getTimeoutMillis(), TimeUnit.MILLISECONDS);
				if (result == null) {
					TestResultMutant trm = new TestResultMutant(
							testsRunner.getTestResultMutant());
					TestFailureMutant tfm = new TestFailureMutant();
					String message = "Test Timedout at " + tm.getTimeoutMillis() + " milliseconds.";
					tfm.setMessage(message);
					tfm.setException(new IllegalStateException(message));
					trm.setFailure(new TestFailure(tfm));
					trialResultMutant.addResult(new TestResult(trm));
				}
			} catch (InterruptedException x) {
				//do nothing
			}
			trial.afterTests();
		}
	}
	
	private boolean startTrial(Class<? extends I_AbstractTrial> p) {
		testClass = p;
		try {
			if (!silent) {
				originalOut.println("");
				originalOut.println("Creating Trial Instance for " + testClass);
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
			case API_Trial:
				PackageScope pkgScope = testClass.getAnnotation(PackageScope.class);
				if (pkgScope == null) {
					failTestOnException(PACKAGE_TRIALS_MUST_BE_ANNOTATED_WITH_A_PACKAGE_TEST_SCOPE_ANNOTATION, 
							new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY),
							type);
					return false;
				}
				String testedPackageName = pkgScope.packageName();
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
		IgnoreTrial ignored = testClass.getAnnotation(IgnoreTrial.class);
		if (ignored != null) {
			trialResultMutant.setIgnored(true);
			listener.onTestCompleted(testClass, null, 
					new TrialResult(trialResultMutant));
			return false;
		}
		Method [] methods = testClass.getMethods();
		testMethods = new ArrayList<TestMethod>();
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
			Test test = method.getAnnotation(Test.class);
			if (test != null) {
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
				long timeout = test.timout();
				if (timeout <= 0) {
					failTestOnException(THE_TEST_METHOD_MAY_NOT_HAVE_A_NEGATIVE_OR_ZERO_TIMEOUT, 
							new IllegalArgumentException(testClass.getName() + "." + method.getName() + 
									WAS_NOT_ANNOTATED_CORRECTLY), type);
				}
				IgnoreTest it = method.getAnnotation(IgnoreTest.class);
				testMethods.add(new TestMethod(method, timeout, it != null));
			}
		}
		if (testMethods.size() == 0) {
			failTestOnException(TRIAL_NO_TEST, 
					new IllegalArgumentException(testClass.getName() + 
							WAS_NOT_ANNOTATED_CORRECTLY), type);
			return false;
		}
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
	public synchronized void testFinished(TestResult p) {
		TestResultMutant forOut = new TestResultMutant(p);
		forOut.setOutput(blos.toString());
		trialResultMutant.addResult(new TestResult(forOut));
		try {
			blocking.put(true);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
