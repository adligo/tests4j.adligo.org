package org.adligo.tests4j.system.shared.trials;

/**
 * The data for IgnoreTest and IgnoreTrial, to make ignoring tests and trials
 * harder to do, and to provide more information about a test or trial
 * which is in progress.
 * @author scott
 *
 */
public @interface Ignored {
  /**
   * The name of the person who ignored the
   * test.
   * TODO make this required when reading annotations.
   * @return
   */
  String ignoredBy();
  /**
   * The date of when the test was ignored
   * in the format YYYY-MM-DD.
   * TODO make this required when reading annotations.
   * @return
   */
  String ignoredOn();
  /**
   * The name of the person who is working on the
   * test, this is a optional value and defaluts
   * to the ignroedBy value.
   * @return
   */
  String developer() default "default";
  /**
   * The date of when the test was is expected to be completed
   * in the format YYYY-MM-DD. This is a optional value.
   * TODO have test4j print a warning list of ignored items that 
   * are expected to be completed in 3 days.
   * Also have tests4j fail the test or trial when the current date is past
   * the expectedOn value.
   * @return
   */
  String expectedOn() default "default";
}
