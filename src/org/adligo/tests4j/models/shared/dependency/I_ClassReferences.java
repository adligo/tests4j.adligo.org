package org.adligo.tests4j.models.shared.dependency;

import java.util.Set;

/**
 * a interface for a list of references,
 * a complete class references would include
 * any classes that are directly or indirectly referenced
 * in the references field, and any circular reference in 
 * the circularReferences field.
 *    
 * @author scott
 *
 */
public interface I_ClassReferences extends I_ClassCircluarReferences {


	/**
	 * the class names of the referenced classes.
	 * @return
	 */
	public abstract Set<String> getReferences();

}