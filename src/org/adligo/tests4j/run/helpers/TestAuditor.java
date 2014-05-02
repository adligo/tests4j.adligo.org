package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.adligo.tests4j.models.shared.IgnoreTest;
import org.adligo.tests4j.models.shared.Test;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4J_TestMethodErrors;

/**
 * audits a method to make sure it conforms to the @Test annotation.
 * 
 * @author scott
 *
 */
public class TestAuditor {


	/**
	 * returns a non null TestDecription if 
	 * this method is a @Test method.
	 * 
	 * @param trialDesc
	 * @param failures
	 * @param method
	 * @return
	 */
	public static TestDescription audit(I_TrialDescription trialDesc,
			List<TrialVerificationFailure> failures,
			Method method) {
		Test test = method.getAnnotation(Test.class);
		if (test != null) {
			I_Tests4J_TestMethodErrors errors = Tests4J_Constants.CONSTANTS
			.getTrialDescriptionMessages().getTestMethodErrors();
			String trialName = trialDesc.getTrialName();
			Class<?> [] params = method.getParameterTypes();
			if (params.length != 0) {
				failures.add(new TrialVerificationFailure(
						errors.getHasParams(),
						new IllegalArgumentException(trialName + "." + method.getName() + 
								errors.getWasAnnotatedIncorrectly())));
			}
			if (Modifier.isAbstract(method.getModifiers())) {
				failures.add(new TrialVerificationFailure(
						errors.getIsAbstract(),
						new IllegalArgumentException(trialName + "." + method.getName() + 
								errors.getWasAnnotatedIncorrectly())));
			}
			if (Modifier.isStatic(method.getModifiers())) {
				failures.add(new TrialVerificationFailure(
						errors.getIsStatic(),
						new IllegalArgumentException(trialName + "." + method.getName() + 
								errors.getWasAnnotatedIncorrectly())));
			}
			if (!Modifier.isPublic(method.getModifiers())) {
				failures.add(new TrialVerificationFailure(
						errors.getIsNotPublic(),
						new IllegalArgumentException(trialName + "." + method.getName() + 
								errors.getWasAnnotatedIncorrectly())));
			}
			long timeout = test.timeout();
			if (timeout < 0) {
				failures.add(new TrialVerificationFailure(
						errors.getHasNegativeTimeout(),
						new IllegalArgumentException(trialName + "." + method.getName() + 
								errors.getWasAnnotatedIncorrectly())));
			}
			IgnoreTest it = method.getAnnotation(IgnoreTest.class);
			return new TestDescription(method, timeout, it != null);
		}
		return null;
	}
}
