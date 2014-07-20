package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;

import org.adligo.tests4j.models.shared.i18n.I_Tests4j_AfterTrialErrors;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.trials.AfterTrial;

/**
 * audits a method to see if it conforms to @AfterTrial
 * 
 * @author scott
 *
 */
public class AfterTrialAuditor {

	
	/**
	 * @param trialDescription
	 * @param method
	 * @return true if the method is annotated with AfterTrial
	 */
	public static boolean audit(I_TrialDescription trialDescription, 
			Collection<TrialVerificationFailure> verificationFailures, Method method) {
		AfterTrial at = method.getAnnotation(AfterTrial.class);
		String trialName = trialDescription.getTrialName();
		if (at != null) {
			I_Tests4j_AfterTrialErrors errors = 
					Tests4J_Constants.CONSTANTS
					.getTrialDescriptionMessages().getAfterTrialErrors();
			
			if (!Modifier.isStatic(method.getModifiers())) {
				verificationFailures.add(new TrialVerificationFailure(
						errors.getNotStatic(),
						new IllegalArgumentException(trialName + 
								errors.getWasAnnotatedIncorrectly())));
			}
			if (!Modifier.isPublic(method.getModifiers())) {
				verificationFailures.add(new TrialVerificationFailure(
						errors.getNotPublic(),
						new IllegalArgumentException(trialName + 
								errors.getWasAnnotatedIncorrectly())));
			}
			Class<?> [] params = method.getParameterTypes();
			if (params.length != 0) {
				verificationFailures.add(new TrialVerificationFailure(
								errors.getHasParams(),
								new IllegalArgumentException(trialName + 
										errors.getWasAnnotatedIncorrectly())));
			
			}
			return true;
		}
		return false;
	}
}
