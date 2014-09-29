package org.adligo.tests4j.run.discovery;

import java.lang.reflect.Method;

import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;
import org.adligo.tests4j.system.shared.trials.AbstractTrial;
import org.adligo.tests4j.system.shared.trials.I_SourceFileTrial;

/**
 * TODO extract the method 
 * private SourceFileTrialResultMutant afterSourceFileTrialTests(TrialType type) {
 * from TrialInstancesProcessor
 * 
 * This class should behave like a model (not thread safe)
 * 
 * @author scott
 *
 */
public class NonTests4jMethodDiscovery  {
	
	/**
	 * @param the trial class
	 * @param the signature of the method (java reflection)
	 * @param the parameters to the method
	 * 
	 * @return
	 */
	public static Method findNonTests4jMethod(Class<?> trialClass, String methoName, Class<?>[] types) {
		
		
		boolean working = true;
		Class<?> parentClass = trialClass;
		Method clazzMethod = null;
		while (working) {
			try {
				clazzMethod = parentClass.getDeclaredMethod(
						methoName, types);
				//clazzMethod = trialClass.getDeclaredMethod(AFTER_TRIAL_TESTS, I_SourceFileTrialResult.class);
			} catch (NoSuchMethodException e) {
				//do nothing
			} catch (SecurityException e) {
				//do nothing
			}
			if (clazzMethod != null) {
				working = false;
			} else {
				parentClass = parentClass.getSuperclass();
				if (
					AbstractTrial.TESTS4J_TRIAL_CLASSES.contains(parentClass.getName())) {
					//no method here
					break;
				}
			} 
		}
		return clazzMethod;
	}
	
	
}
