package org.adligo.jtests.run;

import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.adligo.jtests.base.shared.AfterTest;
import org.adligo.jtests.base.shared.BeforeTest;
import org.adligo.jtests.base.shared.ClassTestScope;
import org.adligo.jtests.base.shared.Exhibit;
import org.adligo.jtests.base.shared.I_AbstractTest;
import org.adligo.jtests.base.shared.JTestType;
import org.adligo.jtests.base.shared.PackageTestScope;
import org.adligo.jtests.base.shared.asserts.I_AssertCommand;
import org.adligo.jtests.models.shared.common.TestType;
import org.adligo.jtests.models.shared.results.ExhibitResultMutant;
import org.adligo.jtests.models.shared.results.I_TestFailure;
import org.adligo.jtests.models.shared.results.TestFailureMutant;
import org.adligo.jtests.models.shared.results.TestResult;
import org.adligo.jtests.models.shared.results.TestResultMutant;
import org.adligo.jtests.models.shared.run.AssertionFailureException;
import org.adligo.jtests.models.shared.run.ByteListOutputStream;
import org.adligo.jtests.models.shared.run.I_AllTestsDoneListener;
import org.adligo.jtests.models.shared.run.I_AssertListener;
import org.adligo.jtests.models.shared.run.I_TestCompleteListener;

public class JTestInternalRunner implements I_AssertListener, Runnable {
	public static final String UNEXPECTED_EXCEPTION_THROWN_FROM = "Unexpected exception thrown from ";
	public static final String J_TEST_INTERNAL_RUNNER_REQUIRES_A_TEST_COMLETED_LISTENER = "JTestInternalRunner requires a test comleted listener";
	public static final String THROWN_EXCEPTION = "Thrown Exception.";
	public static final String NO_THROWN_EXCEPTION = "No Thrown Exception.";
	public static final String REFERS_TO_A_NULL_J_TEST_TYPE_TYPE = " refers to a null @JTestType type.";
	public static final String IS_MISSING_A_J_TEST_TYPE_ANNOTATION = " is missing a @JTestType annotation.";
	public static final String UNEXPECTED_EXCEPTION_WAS_THROWN = "Unexpected Exception was thrown.";
	public static final String TEST_CLASSES_MUST_HAVE_AT_LEAST_ONE_METHOD_ANNOTATED_WITH_EXHIBIT = "Test Classes must have at least one method annotated with @Exhibit.";
	public static final String METHODS_ANNOTATED_WITH_EXHIBIT_MUST_NOT_BE_STATIC = "Methods Annotated with @Exhibit must NOT be static.";
	public static final String METHODS_ANNOTATED_WITH_EXHIBIT_MUST_NOT_BE_ABSTRACT = "Methods Annotated with @Exhibit must NOT be abstract.";
	public static final String METHODS_ANNOTATED_WITH_EXHIBIT_MUST_BE_PUBLIC = "Methods Annotated with @Exhibit must be public.";
	public static final String METHODS_ANNOTATED_WITH_EXHIBIT_MUST_NOT_TAKE_ANY_PARAMETERS = "Methods Annotated with @Exhibit must not take any parameters";
	private static final String METHODS_ANNOTATED_WITH_AFTER_TEST_MUST_NOT_TAKE_ANY_PARAMETERS = 
				"Methods Annotated with @AfterTest must not take any parameters.";
	public static final String METHODS_ANNOTATED_WITH_AFTER_TEST_MUST_BE_STATIC = 
			"Methods Annotated with @AfterTest must be static.";
	public static final String METHODS_ANNOTATED_WITH_BEFORE_TEST_MUST_NOT_TAKE_ANY_PARAMETERS = 
				"Methods Annotated with @BeforeTest must not take any parameters.";
	public static final String METHODS_ANNOTATED_WITH_BEFORE_TEST_MUST_BE_STATIC = 
				"Methods annotated with BeforeTest must be static.";
	public static final String PACKAGE_TESTS_MUST_BE_ANNOTATED_WITH_A_PACKAGE_TEST_SCOPE_ANNOTATION = 
				"PackageTests must be annotated with a PackageTestScope annotation.";
	public static final String WAS_NOT_ANNOTATED_CORRECTLY = " was not annotated correctly.";
	public static final String CLASS_TESTS_MUST_BE_ANNOTATED_WITH_CLASS_TEST_SCOPE = "ClassTests must be annotated with ClassTestScope.";
	public static final String IS_MISSING_A_TEST_TYPE = " is missing a TestType.";
	public static final String THE_TEST = "The test ";
	public static final String J_TEST_INTERNAL_RUNNER_REQUIRES_A_I_RUN_DONE_LISTENER = "JTestInternalRunner requires a I_RunDoneListener.";
	public static final String SIMPLE_RUNNER_REQUIRES_A_I_TEST_RESULTS_PROCESSOR = "SimpleRunner requires a I_TestResultsProcessor.";
	public static final String CLASSES_WHICH_IMPLEMENT_I_ABSTRACT_TEST_MUST_HAVE_A_ZERO_ARGUMENT_CONSTRUCTOR = "Classes which implement I_AbstractTest must have a zero argument constructor.";
	private List<Class<? extends I_AbstractTest>> tests = new ArrayList<Class<? extends I_AbstractTest>>();
	private Class<? extends I_AbstractTest> testClass;
	private I_AbstractTest test;
	private I_TestCompleteListener listener;
	private boolean silent = false;
	private ByteListOutputStream blos = new ByteListOutputStream(64);
	private PrintStream originalOut;
	private PrintStream originalErr;
	private PrintStream captureOut;
	private PrintStream captureErr;
	private I_AllTestsDoneListener runDoneListener;
	private Method beforeTest;
	private Method afterTest;
	private TestResultMutant testResultMutant;
	private ExhibitResultMutant exhibitResultMutant;
	private List<Method> exhibitMethods;
	
	public JTestInternalRunner(List<Class<? extends I_AbstractTest>> pTests, 
			I_AllTestsDoneListener pRunDoneListener, I_TestCompleteListener pTestCompletedLister) {
		
		tests.addAll(pTests);
		originalOut = System.out;
		originalErr = System.err;
		
		captureOut = new PrintStream(blos);
		System.setOut(captureOut);
		
		captureErr = new PrintStream(blos);
		System.setErr(captureErr);
		
		runDoneListener = pRunDoneListener;
		if (runDoneListener == null) {
			throw new IllegalArgumentException(J_TEST_INTERNAL_RUNNER_REQUIRES_A_I_RUN_DONE_LISTENER);
		}
		
		listener = pTestCompletedLister;
		if (runDoneListener == null) {
			throw new IllegalArgumentException(J_TEST_INTERNAL_RUNNER_REQUIRES_A_TEST_COMLETED_LISTENER);
		}
	}
	
	@Override
	public void run() {
		Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				e.printStackTrace(originalErr);
			}
		});
		for (Class<? extends I_AbstractTest> clazz: tests) {
			beforeTest = null;
			afterTest = null;
			test = null;
			
			testClass = clazz;
			testResultMutant = new TestResultMutant();
			testResultMutant.setTestName(testClass.getName());
			if (checkTestClass()) {
				
				runBeforeTest();
				if (startTest(testClass)) {
					runExhibits();
					runAfterTest();
				} 
			} 
			listener.onTestCompleted(testClass, test, new TestResult(testResultMutant));
			testResultMutant = null;
		}
		System.setOut(originalOut);
		System.setErr(originalErr);
		runDoneListener.whenFinished();
	}

	private void runAfterTest() {
		if (afterTest != null) {
			try {
				afterTest.invoke(test, new Object[] {});
			} catch (Exception e) {
				failTestOnException(UNEXPECTED_EXCEPTION_THROWN_FROM + testClass + 
						"." + afterTest.getName(), e);
			}
			testResultMutant.setAfterTestOutput(blos.toString());
		}
	}

	private void runBeforeTest()  {
		if (beforeTest != null) {
			try {
				beforeTest.invoke(test, new Object[] {});
			} catch (Exception e) {
				failTestOnException(UNEXPECTED_EXCEPTION_THROWN_FROM + testClass + 
						"." + beforeTest.getName(), e);
			}
			testResultMutant.setBeforeTestOutput(blos.toString());
		}
	}

	private boolean checkTestClass() {
		
		TestType type = getType();
		testResultMutant.setTestType(type);
		switch(type) {
			case ClassTest:
					ClassTestScope scope = testClass.getAnnotation(ClassTestScope.class);
					if (scope == null) {
						failTestOnException(CLASS_TESTS_MUST_BE_ANNOTATED_WITH_CLASS_TEST_SCOPE, 
								new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY));
						return false;
					}
				break;
			case PackageTest:
				PackageTestScope pkgScope = testClass.getAnnotation(PackageTestScope.class);
				if (pkgScope == null) {
					failTestOnException(PACKAGE_TESTS_MUST_BE_ANNOTATED_WITH_A_PACKAGE_TEST_SCOPE_ANNOTATION, 
							new IllegalArgumentException(testClass.getName() + WAS_NOT_ANNOTATED_CORRECTLY));
					return false;
				}
				break;
			default:
				//do nothing, functional tests don't require annotations
		}
		exhibitMethods = new ArrayList<Method>();
		Method [] methods = testClass.getMethods();
		for (Method method: methods) {
			BeforeTest bt = method.getAnnotation(BeforeTest.class);
			if (bt != null) {
				if (!Modifier.isStatic(method.getModifiers())) {
					failTestOnException(METHODS_ANNOTATED_WITH_BEFORE_TEST_MUST_BE_STATIC, 
							new IllegalArgumentException(testClass + WAS_NOT_ANNOTATED_CORRECTLY));
					return false;
				}
				Class<?> [] params = method.getParameterTypes();
				if (params.length != 0) {
					failTestOnException(METHODS_ANNOTATED_WITH_BEFORE_TEST_MUST_NOT_TAKE_ANY_PARAMETERS, 
							new IllegalArgumentException(testClass + WAS_NOT_ANNOTATED_CORRECTLY));
					return false;
				}
				beforeTest = method;
			}
			AfterTest at = method.getAnnotation(AfterTest.class);
			if (at != null) {
				if (!Modifier.isStatic(method.getModifiers())) {
					failTestOnException(METHODS_ANNOTATED_WITH_AFTER_TEST_MUST_BE_STATIC, 
							new IllegalArgumentException(testClass + WAS_NOT_ANNOTATED_CORRECTLY));
					return false;
				}
				Class<?> [] params = method.getParameterTypes();
				if (params.length != 0) {
					failTestOnException(METHODS_ANNOTATED_WITH_AFTER_TEST_MUST_NOT_TAKE_ANY_PARAMETERS, 
							new IllegalArgumentException(testClass + WAS_NOT_ANNOTATED_CORRECTLY));
					return false;
				}
				afterTest = method;
			}
			Exhibit exhibit = method.getAnnotation(Exhibit.class);
			if (exhibit != null) {
				Class<?> [] params = method.getParameterTypes();
				if (params.length != 0) {
					failTestOnException(METHODS_ANNOTATED_WITH_EXHIBIT_MUST_NOT_TAKE_ANY_PARAMETERS, 
							new IllegalArgumentException(testClass + "." + method.getName() + WAS_NOT_ANNOTATED_CORRECTLY));
					return false;
				}
				if (!Modifier.isPublic(method.getModifiers())) {
					failTestOnException(METHODS_ANNOTATED_WITH_EXHIBIT_MUST_BE_PUBLIC, 
							new IllegalArgumentException(testClass + "." + method.getName() + WAS_NOT_ANNOTATED_CORRECTLY));
					return false;
				}
				if (Modifier.isAbstract(method.getModifiers())) {
					failTestOnException(METHODS_ANNOTATED_WITH_EXHIBIT_MUST_NOT_BE_ABSTRACT, 
							new IllegalArgumentException(testClass + "." + method.getName() + WAS_NOT_ANNOTATED_CORRECTLY));
					return false;
				}
				if (Modifier.isStatic(method.getModifiers())) {
					failTestOnException(METHODS_ANNOTATED_WITH_EXHIBIT_MUST_NOT_BE_STATIC, 
							new IllegalArgumentException(testClass + "." + method.getName() + WAS_NOT_ANNOTATED_CORRECTLY));
					return false;
				}
				exhibitMethods.add(method);
			}
		}
		if (exhibitMethods.size() == 0) {
			failTestOnException(TEST_CLASSES_MUST_HAVE_AT_LEAST_ONE_METHOD_ANNOTATED_WITH_EXHIBIT, 
					new IllegalArgumentException(testClass + WAS_NOT_ANNOTATED_CORRECTLY));
			return false;
		}
		return true;
	}

	private TestType getType() {
		JTestType jtype = testClass.getAnnotation(JTestType.class);
		if (jtype == null)  {
			Class<?> checking = testClass.getSuperclass();
			while (!Object.class.equals(checking)) {
				jtype = checking.getAnnotation(JTestType.class);
				if (jtype != null) {
					break;
				}
				checking = checking.getSuperclass();
			}
		}
		if (jtype == null) {
			failTestOnException(THE_TEST + testClass + IS_MISSING_A_J_TEST_TYPE_ANNOTATION, 
					new IllegalArgumentException());
		}
		TestType type = jtype.getType();
		return type;
	}

	/**
	 * 
	 * @return all suceeded
	 */
	private void runExhibits() {
		for (Method method: exhibitMethods) {
			exhibitResultMutant = new ExhibitResultMutant();
			test.beforeExhibits();
			exhibitResultMutant.setBeforeOutput(blos.toString());
			
			Exception unexpected = null;
			try {
				exhibitResultMutant.setExhibitName(method.getName());
				
				method.invoke(test, new Object[] {});
				exhibitResultMutant.setPassed(true);
				
			} catch (AssertionFailureException a) {
				exhibitResultMutant.setPassed(false);
			} catch (Exception e) {
				unexpected = e;
			}
			exhibitResultMutant.setOutput(blos.toString());
			test.afterExhibits();
			exhibitResultMutant.setAfterOutput(blos.toString());
			if (unexpected != null) {
				TestFailureMutant failure = new TestFailureMutant();
				failure.setException(unexpected);
				failure.setMessage(UNEXPECTED_EXCEPTION_WAS_THROWN);
				failure.setExpected(true);
				failure.setActual(false);
				exhibitResultMutant.setFailure(failure);
			}
			testResultMutant.addExhibitResult(exhibitResultMutant);
			exhibitResultMutant = null;
		}
	}
	
	private boolean startTest(Class<? extends I_AbstractTest> p) {
		testClass = p;
		try {
			if (!silent) {
				originalOut.println("");
				originalOut.println("Creating Test Instance for " + testClass);
			}
			Constructor<? extends I_AbstractTest> constructor =
					testClass.getConstructor(new Class[] {});
			test = constructor.newInstance();
			test.setListener(this);
		} catch (Exception x) {
			failTestOnException(
					CLASSES_WHICH_IMPLEMENT_I_ABSTRACT_TEST_MUST_HAVE_A_ZERO_ARGUMENT_CONSTRUCTOR, x);
		} 
		return true;
	}


	public void assertCompleted(I_AssertCommand cmd) {
		exhibitResultMutant.incrementAssertionCount();
		exhibitResultMutant.addUniqueAsserts(cmd.hashCode());
	}

	public void assertFailed(I_TestFailure failure) {
		exhibitResultMutant.setFailure(failure);
		throw new AssertionFailureException();
	}



	public I_TestCompleteListener getTestCompletedListener() {
		return listener;
	}


	private void failTestOnException(String message, Throwable p) {
		testResultMutant.setPassed(false);
		TestFailureMutant failure = new TestFailureMutant();
		failure.setMessage(message);
		failure.setException(p);
		failure.setExpected(NO_THROWN_EXCEPTION);
		failure.setActual(THROWN_EXCEPTION);
		testResultMutant.setFailure(failure);
		testResultMutant.setTestType(TestType.UnknownTestType);
	}
	
	public boolean isSilent() {
		return silent;
	}

	public void setSilent(boolean silent) {
		this.silent = silent;
	}

}
