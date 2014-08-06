package org.adligo.tests4j.models.shared.dependency;

import java.util.Collections;
import java.util.List;

import org.adligo.tests4j.models.shared.common.StringMethods;

/**
 * for class entries which are shared between threads,
 * to keep the instrumentation work down.
 * 
 * @author scott
 *
 */
public class ClassDependencies implements I_ClassDependencies {
	public static final String NO_NAME = "ClassDependencies requires a className";
	private String className;
	private List<I_Dependency> dependencies;
	
	public ClassDependencies(I_ClassDependencies p) {
		className = p.getClassName();
		if (StringMethods.isEmpty(className)) {
			throw new IllegalArgumentException(NO_NAME);
		}
		List<I_Dependency>  deps = p.getDependencies();
		if (deps == null) {
			 dependencies = Collections.emptyList();
		} else {
			 dependencies = Collections.unmodifiableList(deps);
		}
	}
	
	public String getClassName() {
		return className;
	}

	
	public List<I_Dependency> getDependencies() {
		return dependencies;
	}
}
