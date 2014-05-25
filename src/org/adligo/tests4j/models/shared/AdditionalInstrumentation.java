package org.adligo.tests4j.models.shared;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * a marker to the code coverage plugin 
 * that additional instrumentation is required
 * to correctly calculate code coverage.
 * This occurs when the following happens;
 *    Trial -> Trial Helper Class ->  
 * 
 * @author scott 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AdditionalInstrumentation {
	/**
	 * a comma delimited list of java package names,
	 * all of the classes in the java packages
	 * will be instrumented by the coverage recorder plug-in.
	 * @return
	 */
	String javaPackages();
}
