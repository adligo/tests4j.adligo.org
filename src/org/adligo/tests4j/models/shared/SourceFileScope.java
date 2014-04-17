package org.adligo.tests4j.models.shared;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
}
