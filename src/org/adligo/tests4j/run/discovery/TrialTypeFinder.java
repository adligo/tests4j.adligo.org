package org.adligo.tests4j.run.discovery;

import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_TrialDescriptionMessages;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.TrialTypeAnnotation;

public class TrialTypeFinder {
	private static final I_Tests4J_TrialDescriptionMessages MESSAGES = 
			Tests4J_Constants.CONSTANTS.getTrialDescriptionMessages();
	
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
			String resultFailureMessage = MESSAGES.getMissingTypeAnnotationPre() + 
						trialClass.getName() + 
					MESSAGES.getMissingTypeAnnotationPost();
			throw new IllegalArgumentException(resultFailureMessage);
		}
		TrialType typeEnum = type.type();
		return typeEnum;
	}
}
