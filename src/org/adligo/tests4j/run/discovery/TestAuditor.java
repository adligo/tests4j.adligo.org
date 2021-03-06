package org.adligo.tests4j.run.discovery;

import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.TrialFailure;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AnnotationMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.system.shared.trials.IgnoreTest;
import org.adligo.tests4j.system.shared.trials.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

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
	public static TestDescription audit(I_Tests4J_Constants constants, I_TrialDescription trialDesc,
			List<I_TrialFailure> failures,
			Method method) {
		Test test = method.getAnnotation(Test.class);
		if (test != null) {
			String trialName = trialDesc.getTrialName();
			Class<?> [] params = method.getParameterTypes();
			if (params.length != 0) {
				I_Tests4J_AnnotationMessages errors = constants.getAnnotationMessages();
				
				failures.add(new TrialFailure(
						errors.getHasParams(),
						trialName + "." + method.getName() + 
								errors.getWasAnnotatedIncorrectly()));
			}
			if (Modifier.isAbstract(method.getModifiers())) {
				I_Tests4J_AnnotationMessages errors = constants.getAnnotationMessages();
				
				failures.add(new TrialFailure(
						errors.getIsAbstract(),
						trialName + "." + method.getName() + 
								errors.getWasAnnotatedIncorrectly()));
			}
			if (Modifier.isStatic(method.getModifiers())) {
				I_Tests4J_AnnotationMessages errors = constants.getAnnotationMessages();
				
				failures.add(new TrialFailure(
						errors.getIsStatic(),
						trialName + "." + method.getName() + 
								errors.getWasAnnotatedIncorrectly()));
			}
			if (!Modifier.isPublic(method.getModifiers())) {
				I_Tests4J_AnnotationMessages errors = constants.getAnnotationMessages();
				
				failures.add(new TrialFailure(
						errors.getIsNotPublic(),
						trialName + "." + method.getName() + 
								errors.getWasAnnotatedIncorrectly()));
			}
			long timeout = test.timeout();
			if (timeout < 0) {
				I_Tests4J_AnnotationMessages errors = constants.getAnnotationMessages();
				
				failures.add(new TrialFailure(
						errors.getHasNegativeTimeout(),
						trialName + "." + method.getName() + 
								errors.getWasAnnotatedIncorrectly()));
			}
			IgnoreTest it = method.getAnnotation(IgnoreTest.class);
			return new TestDescription(method, timeout, it != null);
		}
		return null;
	}
}
