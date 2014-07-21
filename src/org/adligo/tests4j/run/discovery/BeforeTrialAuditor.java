package org.adligo.tests4j.run.discovery;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.adligo.tests4j.models.shared.i18n.I_Tests4j_BeforeTrialErrors;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.trials.BeforeTrial;
/**
 * audits a method to see if it conforms to @BeforeTrial
 * 
 * @author scott
 *
 */
public class BeforeTrialAuditor {

	
	public static boolean audit(I_TrialDescription trialDesc,
			List<TrialVerificationFailure> failures,
			Method method) {
		
		String trialName = trialDesc.getTrialName();
		BeforeTrial bt = method.getAnnotation(BeforeTrial.class);
		if (bt != null) {
			I_Tests4j_BeforeTrialErrors errors = 
					Tests4J_Constants.CONSTANTS
					.getTrialDescriptionMessages().getBeforeTrialErrors();
			
			if (!Modifier.isStatic(method.getModifiers())) {
				failures.add(new TrialVerificationFailure(
						errors.getNotStatic(),
						new IllegalArgumentException(trialName + 
								errors.getWasAnnotatedIncorrectly())));

			}
			if (!Modifier.isPublic(method.getModifiers())) {
				failures.add(new TrialVerificationFailure(
						errors.getNotPublic(),
						new IllegalArgumentException(trialName + 
								errors.getWasAnnotatedIncorrectly())));

			}
			Class<?> [] params = method.getParameterTypes();
			if (params.length != 0) {
				failures.add(new TrialVerificationFailure(
						errors.getHasParams(),
						new IllegalArgumentException(trialName + 
								errors.getWasAnnotatedIncorrectly())));
			}
			return true;
		}
		return false;
	}
}
