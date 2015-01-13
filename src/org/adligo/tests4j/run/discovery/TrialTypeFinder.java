package org.adligo.tests4j.run.discovery;

import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.TrialFailure;
import org.adligo.tests4j.shared.common.I_TrialType;
import org.adligo.tests4j.shared.common.TrialType;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AnnotationMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.system.shared.trials.TrialTypeAnnotation;

import java.util.List;

public class TrialTypeFinder {
	public static I_TrialType getTypeInternal(I_Tests4J_Constants constants, Class<? extends I_AbstractTrial> trialClass,
			List<I_TrialFailure> failures) {
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
			I_Tests4J_AnnotationMessages messages = constants.getAnnotationMessages();
			failures.add(new TrialFailure(messages.getTrialTypeMissing(), trialClass.getName()));
		}
		I_TrialType typeEnum = TrialType.get(type.type());
		return typeEnum;
	}
}
