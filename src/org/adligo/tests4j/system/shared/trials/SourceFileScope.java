package org.adligo.tests4j.system.shared.trials;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.adligo.tests4j.shared.asserts.reference.CircularDependencies;

/**
 * Note this was originally ClassScope,
 * however since java can have anonymous inner classes,
 * named inner classes exc, I changed the name to 
 * make sure the class returned from testedClass
 * pertains to a class which matches 
 * one to one with a 
 * java source file.
 * @author scott
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SourceFileScope {
	Class<?> sourceClass();
	/**
	 * This is the minimum code coverage expected
	 * on the sourceClass by the trial with this annotation.
	 * This will only work when a coverage plug-in is present.
	 * 
	 * @return a percentage 0.0 - 100.0 representing the minimum 
	 * code coverage expected on the sourceClass by the trial 
	 * with this annotation.
	 */
	 double minCoverage() default 100.0;
	 
	 /**
	  * if this is set to true,
	  * classes that depend on the sourceClass
	  * may also be depended on by the sourceClass.
	  * Dependency is provided by the coverage plug-in,
	  * so this will only work when a coverage plug-in is present.
	  * @return
	  */
	 int allowedCircularDependencies() default CircularDependencies.Na;
}
