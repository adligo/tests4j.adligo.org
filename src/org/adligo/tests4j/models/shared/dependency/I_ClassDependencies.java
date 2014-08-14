package org.adligo.tests4j.models.shared.dependency;

import java.util.List;
/**
 * represents all of the branch/leaf references
 * from a class with a accurate count
 * of how many references (directly or indirectly)
 * there are to a class from the class that this instance represents.
 * This helps determine which classes to instrument 
 * first, second, third exc.
 * 
 * @author scott
 *
 */
public interface I_ClassDependencies extends I_ClassCircluarReferences {

	/**
	 * this list of I_Dependency
	 * should be ordered in most referenced to least
	 * referenced.
	 * @return
	 */
	public abstract List<I_Dependency> getDependencies();


}