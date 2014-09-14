package org.adligo.tests4j.run.discovery;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;

import org.adligo.tests4j.models.shared.common.Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AnnotationErrors;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.TrialFailure;
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
			Collection<I_TrialFailure> verificationFailures, Method method) {
		AfterTrial at = method.getAnnotation(AfterTrial.class);
		String trialName = trialDescription.getTrialName();
		if (at != null) {
			
			if (!Modifier.isStatic(method.getModifiers())) {
				I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
				I_Tests4J_AnnotationErrors messages = consts.getAnnotationErrors();
				
				verificationFailures.add(new TrialFailure(
						messages.getAfterTrialNotStatic(),
						trialName + messages.getWasAnnotatedIncorrectly()));
			}
			if (!Modifier.isPublic(method.getModifiers())) {
				I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
				I_Tests4J_AnnotationErrors messages = consts.getAnnotationErrors();
				
				verificationFailures.add(new TrialFailure(
						messages.getAfterTrialNotPublic(),
						trialName +  messages.getWasAnnotatedIncorrectly()));
			}
			Class<?> [] params = method.getParameterTypes();
			if (params.length != 0) {
				I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
				I_Tests4J_AnnotationErrors messages = consts.getAnnotationErrors();
				
				verificationFailures.add(new TrialFailure(
								messages.getAfterTrialHasParams(),
								trialName + messages.getWasAnnotatedIncorrectly()));
			
			}
			return true;
		}
		return false;
	}
}
