package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.TrialType;
import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4J_TrialDescriptionMessages;

public class TrialTypeFinder {

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
		I_Tests4J_TrialDescriptionMessages messages = 
				Tests4J_Constants.CONSTANTS.getTrialDescriptionMessages();
		
		if (type == null) {
			String resultFailureMessage = messages.getMissingTypeAnnotationPre() + 
						trialClass.getName() + 
					messages.getMissingTypeAnnotationPost();
			throw new IllegalArgumentException(resultFailureMessage);
		}
		TrialTypeEnum typeEnum = type.type();
		return typeEnum;
	}
}
