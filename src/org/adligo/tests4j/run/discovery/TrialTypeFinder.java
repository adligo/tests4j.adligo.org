package org.adligo.tests4j.run.discovery;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.adligo.tests4j.models.shared.common.I_TrialType;
import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AnnotationErrors;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.TrialTypeAnnotation;

public class TrialTypeFinder {
	private static final String CLASS_NAME = TrialTypeAnnotation.class.getName();
	
	public static I_TrialType getTypeInternal(Class<? extends I_AbstractTrial> trialClass) 
	throws IllegalArgumentException {
		Object annotation = getTrialTypeAnnotation(trialClass);
		
		if (annotation == null) {
			I_Tests4J_AnnotationErrors messages = Tests4J_Constants.CONSTANTS.getAnnotationErrors();
			String resultFailureMessage = messages.getMissingTypeAnnotationPre() + 
						trialClass.getName() + 
					messages.getMissingTypeAnnotationPost();
			throw new IllegalArgumentException(resultFailureMessage);
		}
		
		Method typeM;
		try {
			typeM = annotation.getClass().getMethod("type", new Class[] {});
			Object result = typeM.invoke(annotation, new Object[] {});
			I_TrialType typeEnum = TrialType.get((Integer) result);
			return typeEnum;
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
			throw new IllegalArgumentException(e);
		}
		
	}
	
	private static Object getTrialTypeAnnotation(Class<?> trialClass) {
		Annotation [] annos = trialClass.getAnnotations();
		for (int i = 0; i < annos.length; i++) {
			Annotation ann = annos[0];
			String annonName = ann.annotationType().getName();
			if (CLASS_NAME.equals(annonName)) {
				return ann;
			}
		}
		Class<?> checking = trialClass.getSuperclass();
		if (checking == null) {
			return null;
		}
		return getTrialTypeAnnotation(checking);
	}
}
