package org.adligo.tests4j.models.shared.trials;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotate a static method in your test class to run
 * after the @Test methods and @AfterTrialTests method are completed.
 * 
 * @author scott
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AfterTrial {
	
}
