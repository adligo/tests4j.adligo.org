package org.adligo.tests4j.models.shared.dependency;

import java.util.List;
/**
 * represents all of the branch/leaf references
 * from a class, mostly for caching of this information.
 * 
 * @author scott
 *
 */
public interface I_ClassDependencies {

	/**
	 * The name of the class which depends on 
	 * things
	 * @return
	 */
	public abstract String getClassName();

	/**
	 * this list of I_Dependency
	 * should be ordered in most referenced to least
	 * referenced.
	 * @return
	 */
	public abstract List<I_Dependency> getDependencies();

}