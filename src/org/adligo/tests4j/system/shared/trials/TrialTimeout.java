package org.adligo.tests4j.system.shared.trials;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * if a trial should timeout after a certain number of milliseconds
 * @author scott
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TrialTimeout {
	/**
	 * defaults to 0 (never timeout)
	 * @return
	 */
	long timeout();
}
