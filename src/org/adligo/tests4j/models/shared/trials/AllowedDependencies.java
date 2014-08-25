package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.models.shared.dependency.I_DependencyGroup;

/**
 * Note the dependency groups must
 * be immutable, as there may be multiple threads
 * querying them.  Also they must discrete (NOT overlap even a single
 * class name).
 * @see GWT_2_6_DependencyGroup
 * 
 * @author scott
 *
 */
public @interface AllowedDependencies {
	Class<? extends I_DependencyGroup>[] groups();
}
