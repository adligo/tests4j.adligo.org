package org.adligo.tests4j.run.discovery;

import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AnnotationErrors;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.TrialTypeAnnotation;

public class TrialTypeFinder {
	public static TrialType getTypeInternal(Class<? extends I_AbstractTrial> trialClass) 
	throws IllegalArgumentException {
		TrialTypeAnnotation type = trialClass.getAnnotation(TrialTypeAnnotation.class);
		
		if (type == null)  {
			Class<?> checking = trialClass.getSuperclass();
			while (!Object.class.equals(checking)) {
				type = checking.getAnnotation(TrialTypeAnnotation.class);
				if (type != null) {
					break;
				}
				checking = checking.getSuperclass();
			}
		}
		
		if (type == null) {
			I_Tests4J_AnnotationErrors messages = Tests4J_Constants.CONSTANTS.getAnnotationErrors();
			String resultFailureMessage = messages.getMissingTypeAnnotationPre() + 
						trialClass.getName() + 
					messages.getMissingTypeAnnotationPost();
			throw new IllegalArgumentException(resultFailureMessage);
		}
		TrialType typeEnum = type.type();
		return typeEnum;
	}
}
