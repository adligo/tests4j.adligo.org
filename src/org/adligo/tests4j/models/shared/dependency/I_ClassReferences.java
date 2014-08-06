package org.adligo.tests4j.models.shared.dependency;

import java.util.Set;

/**
 * a interface for a list of references,
 * a complete class references would include
 * any classes that are directly or indirectly referenced.
 * 
 * @author scott
 *
 */
public interface I_ClassReferences {

	/**
	 * the name of the class which references other classes
	 * @return
	 */
	public abstract String getClassName();
	/**
	 * the class names of the referenced classes.
	 * @return
	 */
	public abstract Set<String> getReferences();

	

}