package org.adligo.tests4j.models.shared.association;

import java.util.Set;

/**
 * a immutable class filter implementation.
 * @author scott
 *
 */
public class ClassFilter implements I_ClassFilterModel {
	private final ClassFilterMutant mutant;
	
	public ClassFilter() {
		mutant = new ClassFilterMutant();
	}
	
	public ClassFilter(I_ClassFilterModel other) {
		mutant = new ClassFilterMutant(other);
	}

	public Set<String> getIgnoredPackageNames() {
		return mutant.getIgnoredPackageNames();
	}

	public Set<String> getIgnoredClassNames() {
		return mutant.getIgnoredClassNames();
	}


	public boolean isFiltered(Class<?> clazz) {
		return mutant.isFiltered(clazz);
	}

	public boolean isFiltered(String className) {
		return mutant.isFiltered(className);
	}
}
