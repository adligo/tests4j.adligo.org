package org.adligo.tests4j.models.shared.trials;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * this annotation will tell tests4j to 
 * to generate a test that is runnable on the specified platform.
 * JSE is the default when there isn't a annotation,
 * and if another platform is specified JSE is removed from 
 * the list of specified platforms.
 * 
 * More than one annotation can be added for instance if you 
 * want to support all platforms, add a annotation for each one.
 * @author scott
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TargetedPlatform {
	PlatformType[] value();
}
