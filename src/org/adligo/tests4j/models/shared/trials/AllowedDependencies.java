package org.adligo.tests4j.models.shared.trials;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.adligo.tests4j.models.shared.dependency.I_DependencyGroup;

/**
 * Note the dependency groups used here must be thread safe, 
 * as there may be multiple threads querying them.  
 * Also they must discrete (NOT overlap even a single class name).
 * @see GWT_2_6_DependencyGroup
 * 
 * @author scott
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedDependencies {
	/**
	 * please order the dependencies
	 * from most depended on to least
	 * depended on.
	 * @return
	 */
	Class<? extends I_DependencyGroup>[] groups();
}
