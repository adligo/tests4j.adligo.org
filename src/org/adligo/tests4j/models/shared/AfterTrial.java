package org.adligo.tests4j.models.shared;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotate a static method in your test class to run
 * after the test is completed
 * 
 * Note:
 * 
 * UseCaseTrial's should not have any parameter to the 
 * method annotated by this annotation.
 * 
 * SourceFileTrial's should take a I_AfterSourceTrialData
 * as a parameter to the method annotated by this annotation.
 * 
 * ApiTrial's should take a I_AfterApiTrialData
 * as a parameter to the method annotated by this annotation.
 * 
 * @author scott
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AfterTrial {
	
}
