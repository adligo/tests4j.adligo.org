package org.adligo.tests4j.shared.asserts.reference;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Note the reference groups used here must be thread safe, 
 * as there may be multiple threads querying them.  
 * Also they must discrete (NOT overlap even a single class name).
 * @see GWT_2_6_ReferenceGroup
 * 
 * @author scott
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedReferences {
	/**
	 * please order the reference groups
	 * from most depended on to least
	 * depended on.
	 * 
	 * @return the class names of the referece groups in order
	 * from least dependent to most dependent. 
	 */
	Class<? extends I_ReferenceGroup>[] groups();
}
