package org.adligo.tests4j.models.shared.dependency;

import java.util.Collections;
import java.util.List;

/**
 * for class entries which are shared between threads,
 * to keep the instrumentation work down.
 * 
 * @author scott
 *
 */
public class ClassDependencies extends ClassCircularReferences implements I_ClassDependencies {
	private List<I_Dependency> dependencies;
	
	public ClassDependencies(I_ClassDependencies p) {
		super(p);
		List<I_Dependency>  deps = p.getDependencies();
		if (deps == null) {
			 dependencies = Collections.emptyList();
		} else {
			 dependencies = Collections.unmodifiableList(deps);
		}
	}
	
	public List<I_Dependency> getDependencies() {
		return dependencies;
	}
}
