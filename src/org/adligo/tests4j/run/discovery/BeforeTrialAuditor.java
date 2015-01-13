package org.adligo.tests4j.run.discovery;

import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.TrialFailure;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AnnotationMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.system.shared.trials.BeforeTrial;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
/**
 * audits a method to see if it conforms to @BeforeTrial
 * 
 * @author scott
 *
 */
public class BeforeTrialAuditor {

	
	public static boolean audit(I_Tests4J_Constants constants,  I_TrialDescription trialDesc,
			List<I_TrialFailure> failures,
			Method method) {
		
	  int initalFailuresSize = failures.size();
		String trialName = trialDesc.getTrialName();
		BeforeTrial bt = method.getAnnotation(BeforeTrial.class);
		if (bt != null) {
				
			if (!Modifier.isStatic(method.getModifiers())) {
				I_Tests4J_AnnotationMessages messages = constants.getAnnotationMessages();
			
				failures.add(new TrialFailure(
						messages.getBeforeTrialNotStatic(),
						trialName + messages.getWasAnnotatedIncorrectly()));

			}
			if (!Modifier.isPublic(method.getModifiers())) {
				I_Tests4J_AnnotationMessages messages = constants.getAnnotationMessages();
			
				failures.add(new TrialFailure(
						messages.getBeforeTrialNotPublic(),
						trialName + messages.getWasAnnotatedIncorrectly()));

			}
			Class<?> [] params = method.getParameterTypes();
			if (params.length != 1) {
				addIncorrectAnnotationParams(constants, failures, trialName);
			} else {
  			Class<?> param = params[0];
  			if ( !Map.class.isAssignableFrom(param)) {
  			  addIncorrectAnnotationParams(constants, failures, trialName);
  			}
			}
		}
		if (bt == null || failures.size() != initalFailuresSize) {
		  return false;
		}
		return true;
	}

  public static void addIncorrectAnnotationParams(I_Tests4J_Constants constants, List<I_TrialFailure> failures, 
      String trialName) {
    I_Tests4J_AnnotationMessages messages = constants.getAnnotationMessages();

    failures.add(new TrialFailure(
    		messages.getBeforeTrialHasWrongParams(),
    		trialName + messages.getWasAnnotatedIncorrectly()));
  }
}