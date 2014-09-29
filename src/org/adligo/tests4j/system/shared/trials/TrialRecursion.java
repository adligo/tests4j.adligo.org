package org.adligo.tests4j.system.shared.trials;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * this annotation is just so that
 * a test runner can identify trials that should run
 * which arn't IgnoreTrial.
 * Or in other words so you can run the tests4j_4eclipse plugin
 * on tests4j_tests.
 * 
 * @author scott
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TrialRecursion {

}
