package org.adligo.jtests.run;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.adligo.jtests.base.shared.asserts.I_AbstractTest;
import org.adligo.jtests.base.shared.asserts.I_AssertCommand;
import org.adligo.jtests.base.shared.results.I_Failure;
import org.adligo.jtests.base.shared.results.TestResultMutant;
import org.adligo.jtests.models.shared.run.I_AssertListener;
import org.adligo.jtests.models.shared.run.I_TestCompleteListener;

public class SimpleRunner implements I_AssertListener {
	public static final String SIMPLE_RUNNER_REQUIRES_A_I_TEST_RESULTS_PROCESSOR = "SimpleRunner requires a I_TestResultsProcessor.";
	public static final String CLASSES_WHICH_IMPLEMENT_I_ABSTRACT_TEST_MUST_HAVE_A_ZERO_ARGUMENT_CONSTRUCTOR = "Classes which implement I_AbstractTest must have a zero argument constructor.";
	private List<Class<? extends I_AbstractTest>> tests = new ArrayList<Class<? extends I_AbstractTest>>();
	private Class<? extends I_AbstractTest> testClass;
	private I_AbstractTest test;
	private I_TestCompleteListener listener;
	
	protected SimpleRunner(List<Class<? extends I_AbstractTest>> pTests) {
		tests.addAll(pTests);
	}
	
	protected void runTests() {
		for (Class<? extends I_AbstractTest> testClass: tests) {
			if (startTest(testClass)) {
				
			}
		}
	}
	
	protected boolean startTest(Class<? extends I_AbstractTest> p) {
		testClass = p;
		try {
			Constructor<? extends I_AbstractTest> constructor =
					testClass.getConstructor(new Class[] {});
			test = constructor.newInstance();
			
		} catch (NoSuchMethodException x) {
			failTestOnException(p);
			throw new IllegalArgumentException(
					CLASSES_WHICH_IMPLEMENT_I_ABSTRACT_TEST_MUST_HAVE_A_ZERO_ARGUMENT_CONSTRUCTOR);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private void failTestOnException() {
		TestResultMutant trm = new TestResultMutant();
		trm.set
		listener.onTestCompleted(testClass, test, result);
	}

	public void beforeTestCompleted(String output) {
		// TODO Auto-generated method stub
		
	}

	public void beforeExhibitsCompleted(String output) {
		// TODO Auto-generated method stub
		
	}

	public void exhibitStarting(String exhibitName) {
		// TODO Auto-generated method stub
		
	}

	public void assertCompleted(I_AssertCommand cmd) {
		// TODO Auto-generated method stub
		
	}

	public void assertFailed(I_Failure failure) {
		// TODO Auto-generated method stub
		
	}

	public void exceptionThrown(Throwable exception) {
		// TODO Auto-generated method stub
		
	}

	public void exhibitCompleted(String output) {
		// TODO Auto-generated method stub
		
	}

	public void afterExhibitsCompleted(String output) {
		// TODO Auto-generated method stub
		
	}

	public void afterTestCompleted(String output) {
		// TODO Auto-generated method stub
		
	}

	public void setTestCompletedListener(I_TestCompleteListener pListener) {
		listener = pListener;
	}

	public I_TestCompleteListener getTestCompletedListener() {
		// TODO Auto-generated method stub
		return listener;
	}

}
