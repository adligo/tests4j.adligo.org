package org.adligo.tests4j.system.shared.trials;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * helps computers read the use case trial.
 * @author scott
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UseCaseScope {
	/**
	 * the software system this use case is testing (ie tests4j.adligo.org)
	 * @return
	 */
	String system();
	/**
	 * the verb from the use case (ie 'run' from the 'run trial' use case)
	 * @return
	 */
	String verb();
	/**
	 * the nown from the use case (ie 'trial' from the 'run trial' use case)
	 * @return
	 */
	String nown();
}
