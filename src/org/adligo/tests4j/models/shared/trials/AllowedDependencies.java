package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.models.shared.dependency.I_DependencyGroup;

public @interface AllowedDependencies {
	Class<? extends I_DependencyGroup>[] groups();
}
