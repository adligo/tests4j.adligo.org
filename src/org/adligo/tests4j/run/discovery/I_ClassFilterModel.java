package org.adligo.tests4j.run.discovery;

import java.util.Set;

/**
 * a interface to filter class names,
 * based on ignored packages and classes criteria.
 * 
 * Originally designed to filter out java.* and 
 * shared classes from the reference, dependency
 * discovery code in tests4j_4jacoco.
 * 
 * @author scott
 *
 */
public interface I_ClassFilterModel extends I_ClassFilter {
	/**
	 * the partial package names to ignore
	 * ie "java." would ignore the entire 
	 * java.* package set (java.lang.*, java.util.* exc)
	 * package names must end in a . so that 
	 * the completed package is compared against and the following case doesn't occur;
	 * 
	 * java packages
	 * com.example.foo
	 * com.example.bar
	 * com.example.foo_bar
	 * 
	 * com.example.foo would be a bad ignored package
	 * since it matches the first and third java packages;
	 * com.example.foo and com.example.foo_bar
	 * 
	 * @return
	 */
	public Set<String> getIgnoredPackageNames();
	
	/**
	 * a arbitrary list of complete java
	 * class names.
	 * @return
	 */
	public Set<String> getIgnoredClassNames();

}
