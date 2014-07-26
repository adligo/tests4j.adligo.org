package org.adligo.tests4j.run.discovery;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AnnotationErrors;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_MethodErrors;
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
			I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
				
			if (!Modifier.isStatic(method.getModifiers())) {
				I_Tests4J_MethodErrors mErrors =  consts.getMethodErrors();
				I_Tests4J_AnnotationErrors annoErrors = consts.getAnnotationErrors();
			
				failures.add(new TrialVerificationFailure(
						mErrors.getBeforeTrialNotStatic(),
						new IllegalArgumentException(trialName + 
								annoErrors.getWasAnnotatedIncorrectly())));

			}
			if (!Modifier.isPublic(method.getModifiers())) {
				I_Tests4J_MethodErrors mErrors =  consts.getMethodErrors();
				I_Tests4J_AnnotationErrors annoErrors = consts.getAnnotationErrors();
			
				failures.add(new TrialVerificationFailure(
						mErrors.getBeforeTrialNotPublic(),
						new IllegalArgumentException(trialName + 
								annoErrors.getWasAnnotatedIncorrectly())));

			}
			Class<?> [] params = method.getParameterTypes();
			if (params.length != 0) {
				I_Tests4J_MethodErrors mErrors =  consts.getMethodErrors();
				I_Tests4J_AnnotationErrors annoErrors = consts.getAnnotationErrors();
			
				failures.add(new TrialVerificationFailure(
						mErrors.getBeforeTrialHasParams(),
						new IllegalArgumentException(trialName + 
								annoErrors.getWasAnnotatedIncorrectly())));
			}
			return true;
		}
		return false;
	}
}
