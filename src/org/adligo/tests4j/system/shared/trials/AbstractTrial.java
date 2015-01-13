package org.adligo.tests4j.system.shared.trials;

import static org.adligo.tests4j.shared.asserts.common.AssertType.AssertEquals;
import static org.adligo.tests4j.shared.asserts.common.AssertType.AssertFalse;
import static org.adligo.tests4j.shared.asserts.common.AssertType.AssertGreaterThanOrEquals;
import static org.adligo.tests4j.shared.asserts.common.AssertType.AssertNotEquals;
import static org.adligo.tests4j.shared.asserts.common.AssertType.AssertNotNull;
import static org.adligo.tests4j.shared.asserts.common.AssertType.AssertNotSame;
import static org.adligo.tests4j.shared.asserts.common.AssertType.AssertNotUniform;
import static org.adligo.tests4j.shared.asserts.common.AssertType.AssertNull;
import static org.adligo.tests4j.shared.asserts.common.AssertType.AssertSame;
import static org.adligo.tests4j.shared.asserts.common.AssertType.AssertTrue;
import static org.adligo.tests4j.shared.asserts.common.AssertType.AssertUniform;

import org.adligo.tests4j.shared.asserts.AssertionProcessor;
import org.adligo.tests4j.shared.asserts.BooleanAssertCommand;
import org.adligo.tests4j.shared.asserts.ContainsAssertCommand;
import org.adligo.tests4j.shared.asserts.DoubleAssertCommand;
import org.adligo.tests4j.shared.asserts.IdenticalAssertCommand;
import org.adligo.tests4j.shared.asserts.IdenticalStringAssertCommand;
import org.adligo.tests4j.shared.asserts.ThrownAssertCommand;
import org.adligo.tests4j.shared.asserts.UniformAssertCommand;
import org.adligo.tests4j.shared.asserts.UniformThrownAssertCommand;
import org.adligo.tests4j.shared.asserts.common.CompareAssertionData;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowableValidator;
import org.adligo.tests4j.shared.asserts.common.I_AssertListener;
import org.adligo.tests4j.shared.asserts.common.I_ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_SimpleAssertCommand;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.asserts.common.I_ThrownAssertCommand;
import org.adligo.tests4j.shared.asserts.common.I_ThrownAssertionData;
import org.adligo.tests4j.shared.asserts.uniform.EvaluatorLookupMutant;
import org.adligo.tests4j.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.shared.asserts.uniform.I_UniformAssertionEvaluator;
import org.adligo.tests4j.shared.asserts.uniform.I_UniformThrownAssertionEvaluator;
import org.adligo.tests4j.shared.common.I_Platform;
import org.adligo.tests4j.shared.common.I_PlatformContainer;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ResultMessages;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO i18n the assert messages (currently constants).
 * @author scott
 *
 */
public abstract class AbstractTrial implements I_AbstractTrial, I_Trial {
	public static final Set<String> TESTS4J_TRIAL_CLASSES = getTests4J_TrialClasses();
	
	private static Set<String> getTests4J_TrialClasses() {
		Set<String> toRet = new HashSet<String>();
		toRet.add(ApiTrial.class.getName());
		toRet.add(MetaTrial.class.getName());
		toRet.add(SourceFileTrial.class.getName());
		toRet.add(UseCaseTrial.class.getName());
		return Collections.unmodifiableSet(toRet);
		
	}

	public static final String ASSERT_LISTENER_MAY_ONLY_BE_SET_BY = 
				"The assert listener may only be set by a instance of org.adligo.jtests.run.JTestsRunner or org.adligo.jtests.run.client.JTestsGwtRunner.";
	private I_AssertListener listener_;
	private I_Tests4J_Log log_;
	private I_PlatformContainer platform_;
	private I_EvaluatorLookup evaluationLookup_;
	private EvaluatorLookupMutant evaluationLookupOverrides_;
	private I_Tests4J_Constants constants_;
	private I_Tests4J_ResultMessages resultMessages_;
	/**
	 * Set the memory of the AbstractTrial
	 * @param p
	 */
	@Override
	public void setBindings(I_TrialBindings bindings) {
		//throw npe for nulls
		bindings.hashCode();
		platform_ = bindings;
		
		listener_ = bindings.getAssertListener();
		//throw npe for nulls
		listener_.hashCode();
		
		log_ = bindings.getLog();
		//throw npe for nulls
		log_.hashCode();
		constants_ = bindings.getConstants();
		resultMessages_ = constants_.getResultMessages();
		
		evaluationLookupOverrides_ = new EvaluatorLookupMutant(constants_);
		evaluationLookup_ = bindings.getDefalutEvaluatorLookup();
		evaluationLookup_.hashCode();
	}
	
	/**
	 * This method generally called from a tests constructor,
	 * may be used to insert custom evaluators into the tests4j framework
	 * for a specific trial.  To insert one for the entire
	 * run of trial use the Tests4j_Params.
	 * 
	 * @param clazz
	 * @param evaluator
	 */
	protected void setEvaluatorLookup(Class<?> clazz, I_UniformAssertionEvaluator<?, ?> evaluator) {
		evaluationLookupOverrides_.setEvaluator(clazz, evaluator);
	}
	/**
	 * @param cmd
	 */
	public void evaluate(I_SimpleAssertCommand cmd) {
		AssertionProcessor.evaluate(listener_, cmd);
	}
	
	public void evaluate(I_ThrownAssertCommand cmd, I_Thrower p) {
		AssertionProcessor.evaluate(listener_, cmd, p);
	}
	
	public void evaluateForNullExpected(Object expected) {
		if (expected == null) {
			I_Tests4J_AssertionInputMessages messages = constants_.getAssertionInputMessages();
			
			AssertionProcessor.onAssertionFailure(listener_, null, messages.getTheExpectedValueShouldNeverBeNull());
		}
		
	}

	public void evaluateForNullThrower(I_Thrower thrower) {
		if (thrower == null) {
			I_Tests4J_AssertionInputMessages messages = constants_.getAssertionInputMessages();
			AssertionProcessor.onAssertionFailure(listener_, null, messages.getIThrowerIsRequired());
		}
	}
	
	@Override
	public void assertEquals(Object p, Object a) {
		assertEquals(resultMessages_.getTheObjectsShouldBeEqual(), p, a);
	}

	@Override
	public void assertEquals(String message, Object p, Object a) {
		evaluateForNullExpected(p);
		evaluate(new IdenticalAssertCommand(constants_,  message, 
				new CompareAssertionData<Object>(p, a, AssertEquals)));
	}
	
	@Override
	public void assertEquals(String p, String a) {
		assertEquals(resultMessages_.getTheObjectsShouldBeEqual(), p, a);
	}

	@Override
	public void assertEquals(String message, String p, String a) {
		evaluateForNullExpected(p);
		evaluate(new IdenticalStringAssertCommand(constants_, message, 
				new CompareAssertionData<String>(p, a, AssertEquals)));
	}
	
	@Override
	public void assertTrue(boolean p) {
		assertTrue(resultMessages_.getTheValueShouldBeTrue(), p);
	}

	@Override
	public void assertTrue(String message, boolean p) {
		evaluate(new BooleanAssertCommand(AssertTrue, message, p));
	}

	@Override
	public void assertFalse(boolean p) {
		assertFalse(resultMessages_.getTheValueShouldBeFalse(), p);
	}

	@Override
	public void assertFalse(String message, boolean p) {
		evaluate(new BooleanAssertCommand(AssertFalse, message, p));
	}
	
	@Override
	public void assertNull(Object p) {
		assertNull(resultMessages_.getTheValueShouldBeNull(), p);
	}

	@Override
	public void assertNull(String message, Object p) {
		evaluate(new BooleanAssertCommand(AssertNull, message, p));
	}

	@Override
	public void assertNotNull(Object p) {
		assertNotNull(resultMessages_.getTheValueShould_NOT_BeNull(), p);
	}

	@Override
	public void assertNotNull(String message, Object p) {
		evaluate(new BooleanAssertCommand(AssertNotNull, message, p));
	}


	/**
	 * this should be called by sub classes
	 * which override this method by super.beforeTests();
	 */
	public void beforeTests() {
	}

	public void afterTests() {
		//do nothing, allow overrides
	}

	
	@Override
	public void assertNotEquals(Object p, Object a) {
		assertNotEquals(resultMessages_.getTheObjectsShould_NOT_BeEqual(),p, a);
	}

	@Override
	public void assertNotEquals(String message, Object p, Object a) {
		evaluateForNullExpected(p);
		evaluate(new IdenticalAssertCommand(constants_, message, 
				new CompareAssertionData<Object>(p, a, AssertNotEquals)));
	}
	
	@Override
	public void assertNotEquals(String p, String a) {
		assertNotEquals(resultMessages_.getTheObjectsShould_NOT_BeEqual(),p, a);
	}

	@Override
	public void assertNotEquals(String message, String p, String a) {
		evaluateForNullExpected(p);
		evaluate(new IdenticalStringAssertCommand(constants_,  message, 
				new CompareAssertionData<String>(p, a, AssertNotEquals)));
	}
	
	@Override
	public void assertSame(Object p, Object a) {
		assertSame(resultMessages_.getTheObjectsShouldBeTheSame(), p,  a);
	}

	@Override
	public void assertSame(String message, Object p, Object a) {
		evaluateForNullExpected(p);
		evaluate(new IdenticalAssertCommand(constants_, message, 
				new CompareAssertionData<Object>(p, a, AssertSame)));
	}

	@Override
	public void assertNotUniform(Object p, Object a) {
		assertNotUniform(resultMessages_.getTheObjectsShould_NOT_BeUniform(), p, a);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void assertNotUniform(String message, Object expected, Object actual) {
		evaluateForNullExpected(expected);
		I_UniformAssertionEvaluator<?, ?> eval = getEvaluator(expected);
		if (eval != null) {
			evaluate(new UniformAssertCommand(constants_, message, 
					new CompareAssertionData<Object>(expected, actual, AssertNotUniform), eval));
		} else {
			I_Tests4J_AssertionInputMessages messages = constants_.getAssertionInputMessages();
			AssertionProcessor.onAssertionFailure(listener_, null, messages.getNoEvaluatorFoundForClass());
		}
	}
				
	@Override
	public void assertNotSame(Object p, Object a) {
		assertNotSame(resultMessages_.getTheObjectsShould_NOT_BeTheSame(), p,  a);
	}

	@Override
	public void assertNotSame(String message, Object p, Object a) {
		evaluateForNullExpected(p);
		evaluate(new IdenticalAssertCommand(constants_, message, 
				new CompareAssertionData<Object>(p, a, AssertNotSame)));
	}
	
	@Override
	public void assertThrown(I_ExpectedThrowable data, I_Thrower thrower) {
		evaluateForNullExpected(data);
		evaluateForNullThrower(thrower);
		assertThrown(resultMessages_.getTheExpectedThrowableDidNotMatch(), 
		    new ExpectedThrowableValidator(constants_, data), thrower);
	}

	@Override
	public void assertThrown(String message, I_ExpectedThrowable data, I_Thrower thrower) {
		evaluateForNullExpected(data);
		evaluateForNullThrower(thrower);
		evaluate(new ThrownAssertCommand(constants_, message, 
		    new ExpectedThrowableValidator(constants_, data)), thrower);
	}
	
	@Override
	public void assertThrownUniform(I_ExpectedThrowable data, I_Thrower thrower) {
		evaluateForNullExpected(data);
		evaluateForNullThrower(thrower);
		assertThrownUniform(resultMessages_.getTheExpectedThrowableDataWasNotUniformTheActual(), 
		    new ExpectedThrowableValidator(constants_, data), thrower);
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public void assertThrownUniform(String message, I_ExpectedThrowable data, I_Thrower thrower) {
		evaluateForNullExpected(data);
		evaluateForNullThrower(thrower);
		I_UniformThrownAssertionEvaluator<I_ThrownAssertionData> eval = evaluationLookup_.getThrownEvaulator();
		if (eval == null) {
			I_Tests4J_AssertionInputMessages messages = constants_.getAssertionInputMessages();
			AssertionProcessor.onAssertionFailure(listener_, null, messages.getTheUniformThrownEvaluatorIsNull());
		} else {
			evaluate(new UniformThrownAssertCommand(constants_, message, 
			    new ExpectedThrowableValidator(constants_, data), eval), thrower);
		}
	}
	
	public void assertUniform(Object expected, Object actual) {
		assertUniform(resultMessages_.getTheObjectsShouldBeUniform(), expected, actual);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void assertUniform(String message, Object expected, Object actual) {
		evaluateForNullExpected(expected);
		I_UniformAssertionEvaluator<?, ?> evaluator = getEvaluator(expected);
		if (evaluator != null) {
			evaluate(new UniformAssertCommand(constants_, message, 
					new CompareAssertionData<Object>(expected, actual,AssertUniform), evaluator));
		} else {
			I_Tests4J_AssertionInputMessages messages = constants_.getAssertionInputMessages();
			AssertionProcessor.onAssertionFailure(listener_, null, messages.getNoEvaluatorFoundForClass());
		}
	}

	

	private <D> I_UniformAssertionEvaluator<?, D> getEvaluator(Object expectedInstance) {
		if (expectedInstance == null) {
			I_Tests4J_AssertionInputMessages messages = constants_.getAssertionInputMessages();
			throw new IllegalStateException(messages.getNoEvaluatorFoundForClass() + expectedInstance);
		}
		
		Class<?> expectedClass = expectedInstance.getClass();
		return getEvaluator(expectedClass);
	}

	@SuppressWarnings("unchecked")
	private <D> I_UniformAssertionEvaluator<?, D> getEvaluator(Class<?> expectedClass) {
		I_UniformAssertionEvaluator<?, ?> eval = evaluationLookupOverrides_.findEvaluator(expectedClass);
		if (eval == null) {
			eval = evaluationLookup_.findEvaluator(expectedClass);
		}
		return (I_UniformAssertionEvaluator<?, D>) eval;
	}
	
	@Override
	public void assertGreaterThanOrEquals(double expected, double actual) {
		evaluate(new DoubleAssertCommand(resultMessages_.getTheActualShouldBeGreaterThanOrEqualToTheExpected(), 
				new CompareAssertionData<Double>(expected, actual, AssertGreaterThanOrEquals)));
	}

	@Override
	public void assertGreaterThanOrEquals(String message, double expected, double actual) {
		evaluate(new DoubleAssertCommand( message, 
				new CompareAssertionData<Double>(expected, actual, AssertGreaterThanOrEquals)));
	}

	@Override
	public void assertContains(Collection<?> p, Object a) {
		evaluateForNullExpected(p);
		evaluate(new ContainsAssertCommand(constants_, 
				resultMessages_.getTheCollectionShouldContainTheValue(), p, a));
	}

	@Override
	public void assertContains(String message, Collection<?> p, Object a) {
		evaluateForNullExpected(p);
		evaluate(new ContainsAssertCommand(constants_, 
				message, p, a));
	}
	
	public void log(String p) {
		log_.log(p);
	}

	public void log(Throwable t) {
    log_.onThrowable(t);
  }
	
	@Override
	public void assertGreaterThanOrEquals(double p, float a) {
		assertGreaterThanOrEquals(p, 0.0 + a);
	}

	@Override
	public void assertGreaterThanOrEquals(String message, double p, float a) {
		assertGreaterThanOrEquals(message, p, 0.0 + a);
	}

	@Override
	public void assertGreaterThanOrEquals(double p, int a) {
		assertGreaterThanOrEquals(p, 0.0 + a);
	}

	@Override
	public void assertGreaterThanOrEquals(String message, double p, int a) {
		assertGreaterThanOrEquals(message, p, 0.0 + a);
	}

	@Override
	public void assertGreaterThanOrEquals(double p, long a) {
		assertGreaterThanOrEquals(p, 0.0 + a);
	}

	@Override
	public void assertGreaterThanOrEquals(String message, double p, long a) {
		assertGreaterThanOrEquals(message, p, 0.0 + a);
	}

	@Override
	public void assertGreaterThanOrEquals(double p, short a) {
		assertGreaterThanOrEquals(p, 0.0 + a);
	}

	@Override
	public void assertGreaterThanOrEquals(String message, double p, short a) {
		assertGreaterThanOrEquals(message, p, 0.0 + a);
	}
	
	public I_Platform getPlatform() {
		return platform_.getPlatform();
	}

	public I_Tests4J_Log getLog() {
		return log_;
	}
	
	public void showWidget(Object o) {
		throw new IllegalStateException("This feature has not yet been implemented");
	}

	/**
	 * you may override this to provide notification for long running tests
	 * @param testName
	 * @return
	 */
	public synchronized double getPctDone(String testName) {
		return 0.0;
	}
}