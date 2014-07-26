package org.adligo.tests4j.run.discovery;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AnnotationErrors;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.trials.IgnoreTest;
import org.adligo.tests4j.models.shared.trials.Test;

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
			String trialName = trialDesc.getTrialName();
			Class<?> [] params = method.getParameterTypes();
			if (params.length != 0) {
				I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
				I_Tests4J_AnnotationErrors errors = consts.getAnnotationErrors();
				
				failures.add(new TrialVerificationFailure(
						errors.getHasParams(),
						new IllegalArgumentException(trialName + "." + method.getName() + 
								errors.getWasAnnotatedIncorrectly())));
			}
			if (Modifier.isAbstract(method.getModifiers())) {
				I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
				I_Tests4J_AnnotationErrors errors = consts.getAnnotationErrors();
				
				failures.add(new TrialVerificationFailure(
						errors.getIsAbstract(),
						new IllegalArgumentException(trialName + "." + method.getName() + 
								errors.getWasAnnotatedIncorrectly())));
			}
			if (Modifier.isStatic(method.getModifiers())) {
				I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
				I_Tests4J_AnnotationErrors errors = consts.getAnnotationErrors();
				
				failures.add(new TrialVerificationFailure(
						errors.getIsStatic(),
						new IllegalArgumentException(trialName + "." + method.getName() + 
								errors.getWasAnnotatedIncorrectly())));
			}
			if (!Modifier.isPublic(method.getModifiers())) {
				I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
				I_Tests4J_AnnotationErrors errors = consts.getAnnotationErrors();
				
				failures.add(new TrialVerificationFailure(
						errors.getIsNotPublic(),
						new IllegalArgumentException(trialName + "." + method.getName() + 
								errors.getWasAnnotatedIncorrectly())));
			}
			long timeout = test.timeout();
			if (timeout < 0) {
				I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
				I_Tests4J_AnnotationErrors errors = consts.getAnnotationErrors();
				
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
