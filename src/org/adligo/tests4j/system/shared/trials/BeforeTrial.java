package org.adligo.tests4j.system.shared.trials;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotate a static method in your trial class to run
 * before the trial instance is created.
 * It must take a Map&lt;String,Object&gt; parameters parameter
 * and use the provided thread factory for new
 * threads, if code coverage collection is expected on those
 * threads.
 * 
 * @author scott
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BeforeTrial {
  /**
   * key to the parameter map which will return
   * a ThreadFactory which can be use to gather code
   * coverage on a new thread.
   */
	public static final String THREAD_FACTORY = "threadFactory";
}
