package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.i18n.trials.I_Tests4J_TrialDescriptionMessages;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.TrialType;

public class TrialTypeFinder {
	private static final I_Tests4J_TrialDescriptionMessages MESSAGES = 
			Tests4J_Constants.CONSTANTS.getTrialDescriptionMessages();
	
	public static TrialTypeEnum getTypeInternal(Class<? extends I_AbstractTrial> trialClass) 
	throws IllegalArgumentException {
		TrialType type = trialClass.getAnnotation(TrialType.class);
		
		if (type == null)  {
			Class<?> checking = trialClass.getSuperclass();
			while (!Object.class.equals(checking)) {
				type = checking.getAnnotation(TrialType.class);
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
		TrialTypeEnum typeEnum = type.type();
		return typeEnum;
	}
}
