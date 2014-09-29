package org.adligo.tests4j.system.shared.trials;

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
	 * in seconds defaults to 0 which is no timeout
	 * @return
	 */
	long timeout() default 0;
}
