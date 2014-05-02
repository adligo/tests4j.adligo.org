package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.adligo.tests4j.models.shared.AfterTrialTests;
import org.adligo.tests4j.models.shared.I_AfterApiTrialCoverage;
import org.adligo.tests4j.models.shared.I_AfterSourceFileTrialCoverage;
import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4J_AfterTrialTestsErrors;

public class AfterTrialTestsAuditor {

	

	public static boolean audit(I_TrialDescription trialDesc,
			List<TrialVerificationFailure> failures,
			Method method) {
		AfterTrialTests att = method.getAnnotation(AfterTrialTests.class);
		if (att != null) {
			String trialName = trialDesc.getTrialName();
			TrialTypeEnum type = trialDesc.getType();
			I_Tests4J_AfterTrialTestsErrors errors = Tests4J_Constants.CONSTANTS
			.getTrialDescriptionMessages().getAfterTrialTestsErrors();
			if (Modifier.isStatic(method.getModifiers())) {
				failures.add(new TrialVerificationFailure(
						errors.getIsStatic(),
						new IllegalArgumentException(trialName + 
								errors.getWasAnnotatedIncorrectly())));
			}
			if (Modifier.isAbstract(method.getModifiers())) {
				failures.add(new TrialVerificationFailure(
						errors.getIsAbstract(),
						new IllegalArgumentException(trialName + 
								errors.getWasAnnotatedIncorrectly())));
			}
			if (!Modifier.isPublic(method.getModifiers())) {
				failures.add(new TrialVerificationFailure(
						errors.getIsNotPublic(),
						new IllegalArgumentException(trialName + 
								errors.getWasAnnotatedIncorrectly())));
			}
			Class<?> [] params = method.getParameterTypes();
			switch (type) {
			case SourceFileTrial:
				if (params.length != 1) {
					failures.add(new TrialVerificationFailure(
							errors.getSourceFileTrialHasWrongParams(),
							new IllegalArgumentException(trialName + 
									errors.getWasAnnotatedIncorrectly())));
				} else {
					Class<?> sfParamClass = params[0];
					if ( !I_AfterSourceFileTrialCoverage.class.isAssignableFrom(sfParamClass)) {
						failures.add(new TrialVerificationFailure(
								errors.getSourceFileTrialHasWrongParams(),
								new IllegalArgumentException(trialName + 
										errors.getWasAnnotatedIncorrectly())));
					}
				}
				break;

			case ApiTrial:
				if (params.length != 1) {
					failures.add(new TrialVerificationFailure(
							errors.getApiTrialTestsHasWrongParams(),
							new IllegalArgumentException(trialName + 
									errors.getWasAnnotatedIncorrectly())));
				} else {
					
					Class<?> apiParamClass = params[0];
					if ( !I_AfterApiTrialCoverage.class.isAssignableFrom(apiParamClass)) {
						failures.add(new TrialVerificationFailure(
								errors.getApiTrialTestsHasWrongParams(),
								new IllegalArgumentException(trialName + 
										errors.getWasAnnotatedIncorrectly())));
					}
				}
				break;
			default:
				failures.add(new TrialVerificationFailure(
						errors.getUseCaseTrialHasAfterTrialTests(),
						new IllegalArgumentException(trialName + 
								errors.getWasAnnotatedIncorrectly())));
			}
			return true;
		}
		return false;
	}
}
