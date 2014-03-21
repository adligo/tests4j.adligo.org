package org.adligo.jtests.models.shared;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * this marks a method as a runnable exibit 
 * (like a junit @Test annotation)
 * @author scott
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
	/**
	 * in seconds defaults to 5 minute's for debugging
	 * @return
	 */
	long timout() default 300000;
}
