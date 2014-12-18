package org.adligo.tests4j.system.shared.trials;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This helps tests4j read the use case trial,
 * and match it up to a use case using the 
 * use case xml files defined in requirements.xml
 * file.  
 * 
 * @author scott
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UseCaseScope {
	/**
	 * This is the unique name from the 
	 * use cases in the use case paths 
	 * defined in requirements.xml.
	 * @return
	 */
	String name();
}
