package org.adligo.tests4j.models.shared.trials;

import static org.adligo.tests4j.models.shared.asserts.common.AssertType.AssertEquals;
import static org.adligo.tests4j.models.shared.asserts.common.AssertType.AssertFalse;
import static org.adligo.tests4j.models.shared.asserts.common.AssertType.AssertGreaterThanOrEquals;
import static org.adligo.tests4j.models.shared.asserts.common.AssertType.AssertNotEquals;
import static org.adligo.tests4j.models.shared.asserts.common.AssertType.AssertNotNull;
import static org.adligo.tests4j.models.shared.asserts.common.AssertType.AssertNotSame;
import static org.adligo.tests4j.models.shared.asserts.common.AssertType.AssertNotUniform;
import static org.adligo.tests4j.models.shared.asserts.common.AssertType.AssertNull;
import static org.adligo.tests4j.models.shared.asserts.common.AssertType.AssertSame;
import static org.adligo.tests4j.models.shared.asserts.common.AssertType.AssertTrue;
import static org.adligo.tests4j.models.shared.asserts.common.AssertType.AssertUniform;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.AssertionProcessor;
import org.adligo.tests4j.models.shared.asserts.BooleanAssertCommand;
import org.adligo.tests4j.models.shared.asserts.ContainsAssertCommand;
import org.adligo.tests4j.models.shared.asserts.DoubleAssertCommand;
import org.adligo.tests4j.models.shared.asserts.IdenticalAssertCommand;
import org.adligo.tests4j.models.shared.asserts.IdenticalStringAssertCommand;
import org.adligo.tests4j.models.shared.asserts.ThrownAssertCommand;
import org.adligo.tests4j.models.shared.asserts.UniformAssertCommand;
import org.adligo.tests4j.models.shared.asserts.UniformThrownAssertCommand;
import org.adligo.tests4j.models.shared.asserts.common.CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.models.shared.asserts.common.I_SimpleAssertCommand;
import org.adligo.tests4j.models.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.models.shared.asserts.common.I_ThrownAssertCommand;
import org.adligo.tests4j.models.shared.asserts.common.I_ThrownAssertionData;
import org.adligo.tests4j.models.shared.asserts.uniform.EvaluatorLookupMutant;
import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionEvaluator;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformThrownAssertionEvaluator;
import org.adligo.tests4j.models.shared.common.I_Platform;
import org.adligo.tests4j.models.shared.common.I_PlatformContainer;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ResultMessages;
import org.adligo.tests4j.models.shared.system.I_Tests4J_AssertListener;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

/**
 * TODO i18n the assert messages (currently constants).
 * @author scott
 *
 */
public abstract class AbstractTrial implements I_AbstractTrial, I_Trial {
	private static final I_Tests4J_ResultMessages MESSAGES = 
			Tests4J_Constants.CONSTANTS.getResultMessages();
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
	private I_Tests4J_AssertListener listener;
	private I_Tests4J_Log log;
	private I_PlatformContainer platform;
	private I_EvaluatorLookup evaluationLookup;
	private EvaluatorLookupMutant evaluationLookupOverrides = new EvaluatorLookupMutant();
	/**
	 * Set the memory of the AbstractTrial
	 * @param p
	 */
	@Override
	public void setBindings(I_TrialBindings bindings) {
		//throw npe for nulls
		bindings.hashCode();
		platform = bindings;
		
		listener = bindings.getAssertListener();
		//throw npe for nulls
		listener.hashCode();
		
		log = bindings.getLog();
		//throw npe for nulls
		log.hashCode();
		
		evaluationLookup = bindings.getDefalutEvaluatorLookup();
		evaluationLookup.hashCode();
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
		evaluationLookupOverrides.setEvaluator(clazz, evaluator);
	}
	/**
	 * @param cmd
	 */
	public void evaluate(I_SimpleAssertCommand cmd) {
		AssertionProcessor.evaluate(listener, cmd);
	}
	
	public void evaluate(I_ThrownAssertCommand cmd, I_Thrower p) {
		AssertionProcessor.evaluate(listener, cmd, p);
	}
	
	public void evaluateForNullExpected(Object expected) {
		if (expected == null) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			
			AssertionProcessor.onAssertionFailure(listener, null, messages.getTheExpectedValueShouldNeverBeNull());
		}
	}

	public void evaluateForNullThrower(I_Thrower thrower) {
		if (thrower == null) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			AssertionProcessor.onAssertionFailure(listener, null, messages.getIThrowerIsRequired());
		}
	}
	
	@Override
	public void assertEquals(Object p, Object a) {
		assertEquals(MESSAGES.getTheObjectsShouldBeEqual(), p, a);
	}

	@Override
	public void assertEquals(String message, Object p, Object a) {
		evaluateForNullExpected(p);
		evaluate(new IdenticalAssertCommand( message, 
				new CompareAssertionData<Object>(p, a, AssertEquals)));
	}
	
	@Override
	public void assertEquals(String p, String a) {
		assertEquals(MESSAGES.getTheObjectsShouldBeEqual(), p, a);
	}

	@Override
	public void assertEquals(String message, String p, String a) {
		evaluateForNullExpected(p);
		evaluate(new IdenticalStringAssertCommand(message, 
				new CompareAssertionData<String>(p, a, AssertEquals)));
	}
	
	@Override
	public void assertTrue(boolean p) {
		assertTrue(MESSAGES.getTheValueShouldBeTrue(), p);
	}

	@Override
	public void assertTrue(String message, boolean p) {
		evaluate(new BooleanAssertCommand(AssertTrue, message, p));
	}

	@Override
	public void assertFalse(boolean p) {
		assertFalse(MESSAGES.getTheValueShouldBeFalse(), p);
	}

	@Override
	public void assertFalse(String message, boolean p) {
		evaluate(new BooleanAssertCommand(AssertFalse, message, p));
	}
	
	@Override
	public void assertNull(Object p) {
		assertNull(MESSAGES.getTheValueShouldBeNull(), p);
	}

	@Override
	public void assertNull(String message, Object p) {
		evaluate(new BooleanAssertCommand(AssertNull, message, p));
	}

	@Override
	public void assertNotNull(Object p) {
		assertNotNull(MESSAGES.getTheValueShould_NOT_BeNull(), p);
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
		assertNotEquals(MESSAGES.getTheObjectsShould_NOT_BeEqual(),p, a);
	}

	@Override
	public void assertNotEquals(String message, Object p, Object a) {
		evaluateForNullExpected(p);
		evaluate(new IdenticalAssertCommand(message, 
				new CompareAssertionData<Object>(p, a, AssertNotEquals)));
	}
	
	@Override
	public void assertNotEquals(String p, String a) {
		assertNotEquals(MESSAGES.getTheObjectsShould_NOT_BeEqual(),p, a);
	}

	@Override
	public void assertNotEquals(String message, String p, String a) {
		evaluateForNullExpected(p);
		evaluate(new IdenticalStringAssertCommand( message, 
				new CompareAssertionData<String>(p, a, AssertNotEquals)));
	}
	
	@Override
	public void assertSame(Object p, Object a) {
		assertSame(MESSAGES.getTheObjectsShouldBeTheSame(), p,  a);
	}

	@Override
	public void assertSame(String message, Object p, Object a) {
		evaluateForNullExpected(p);
		evaluate(new IdenticalAssertCommand(message, 
				new CompareAssertionData<Object>(p, a, AssertSame)));
	}

	@Override
	public void assertNotUniform(Object p, Object a) {
		assertNotUniform(MESSAGES.getTheObjectsShould_NOT_BeUniform(), p, a);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void assertNotUniform(String message, Object expected, Object actual) {
		evaluateForNullExpected(expected);
		I_UniformAssertionEvaluator<?, ?> eval = getEvaluator(expected);
		if (eval != null) {
			evaluate(new UniformAssertCommand(
					message, 
					new CompareAssertionData<Object>(expected, actual, AssertNotUniform), eval));
		} else {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			AssertionProcessor.onAssertionFailure(listener, null, messages.getNoEvaluatorFoundForClass());
		}
	}
				
	@Override
	public void assertNotSame(Object p, Object a) {
		assertNotSame(MESSAGES.getTheObjectsShould_NOT_BeTheSame(), p,  a);
	}

	@Override
	public void assertNotSame(String message, Object p, Object a) {
		evaluateForNullExpected(p);
		evaluate(new IdenticalAssertCommand(message, 
				new CompareAssertionData<Object>(p, a, AssertNotSame)));
	}
	
	@Override
	public void assertThrown(I_ExpectedThrownData pData, I_Thrower pThrower) {
		evaluateForNullExpected(pData);
		evaluateForNullThrower(pThrower);
		assertThrown(MESSAGES.getTheExpectedThrowableDidNotMatch(), pData, pThrower);
	}

	@Override
	public void assertThrown(String pMessage, I_ExpectedThrownData pData, I_Thrower pThrower) {
		evaluateForNullExpected(pData);
		evaluateForNullThrower(pThrower);
		evaluate(new ThrownAssertCommand(pMessage, pData), pThrower);
	}
	
	@Override
	public void assertThrownUniform(I_ExpectedThrownData pData, I_Thrower pThrower) {
		evaluateForNullExpected(pData);
		evaluateForNullThrower(pThrower);
		assertThrownUniform(MESSAGES.getTheExpectedThrowableDataWasNotUniformTheActual(), pData, pThrower);
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public void assertThrownUniform(String pMessage, I_ExpectedThrownData pData, I_Thrower pThrower) {
		evaluateForNullExpected(pData);
		evaluateForNullThrower(pThrower);
		I_UniformThrownAssertionEvaluator<I_ThrownAssertionData> eval = evaluationLookup.getThrownEvaulator();
		if (eval == null) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			AssertionProcessor.onAssertionFailure(listener, null, messages.getTheUniformThrownEvaluatorIsNull());
		} else {
			evaluate(new UniformThrownAssertCommand(pMessage, pData, eval), pThrower);
		}
	}
	
	public void assertUniform(Object expected, Object actual) {
		assertUniform(MESSAGES.getTheObjectsShouldBeUniform(), expected, actual);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void assertUniform(String message, Object expected, Object actual) {
		evaluateForNullExpected(expected);
		I_UniformAssertionEvaluator<?, ?> evaluator = getEvaluator(expected);
		if (evaluator != null) {
			evaluate(new UniformAssertCommand(message, 
					new CompareAssertionData<Object>(expected, actual,AssertUniform), evaluator));
		} else {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			AssertionProcessor.onAssertionFailure(listener, null, messages.getNoEvaluatorFoundForClass());
		}
	}

	

	private <D> I_UniformAssertionEvaluator<?, D> getEvaluator(Object expectedInstance) {
		if (expectedInstance == null) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			throw new IllegalStateException(messages.getNoEvaluatorFoundForClass() + expectedInstance);
		}
		
		Class<?> expectedClass = expectedInstance.getClass();
		return getEvaluator(expectedClass);
	}

	@SuppressWarnings("unchecked")
	private <D> I_UniformAssertionEvaluator<?, D> getEvaluator(Class<?> expectedClass) {
		I_UniformAssertionEvaluator<?, ?> eval = evaluationLookupOverrides.findEvaluator(expectedClass);
		if (eval == null) {
			eval = evaluationLookup.findEvaluator(expectedClass);
		}
		return (I_UniformAssertionEvaluator<?, D>) eval;
	}
	
	@Override
	public void assertGreaterThanOrEquals(double expected, double actual) {
		evaluate(new DoubleAssertCommand(MESSAGES.getTheActualShouldBeGreaterThanOrEqualToTheExpected(), 
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
		evaluate(new ContainsAssertCommand(
				MESSAGES.getTheCollectionShouldContainTheValue(), p, a));
	}

	@Override
	public void assertContains(String message, Collection<?> p, Object a) {
		evaluateForNullExpected(p);
		evaluate(new ContainsAssertCommand(
				message, p, a));
	}
	
	public void log(String p) {
		log.log(p);
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
		return platform.getPlatform();
	}

	public I_Tests4J_Log getLog() {
		return log;
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