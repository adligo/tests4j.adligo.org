package org.adligo.tests4j.models.shared.dependency;

import org.adligo.tests4j.shared.common.ClassMethods;


/**
 * a interface to filter class names,
 * based on any criteria.
 * 
 * Originally designed to filter out java.* and 
 * shared classes from the reference, dependency
 * discovery code in tests4j_4jacoco.
 * 
 * A class filter should always filter classes which are primitives
 * @see ClassMethods#isPrimitive(Class)
 * @author scott
 *
 */
public interface I_ClassFilter {


	/**
	 * @param className
	 * @return true if the the class is allowed through the filter
	 *    false if it isn't.
	 */
	public boolean isFiltered(Class<?> clazz);
	
	
	/**
	 * performs a Class.forName(className)
	 * and then checks the isFiltered(Class<?> method.
	 * 
	 */
	public boolean isFiltered(String className);
}
