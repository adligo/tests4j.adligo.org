package org.adligo.tests4j.models.shared.dependency;

import java.util.List;

import org.adligo.tests4j.shared.asserts.dependency.I_ClassAlias;

/**
 * this interface is used to track which 
 * classes bytes have been cached in memory.
 * It also tracks information about the class,
 * so that the order of class instrumentation
 * can be kept clean (to make sure all parents
 * are instrumented before the current class).
 * @author scott
 *
 */
public interface I_ClassParents extends I_ClassAlias {

	
	/**
	 * a ordered list of the classes parents
	 * from interfaces to super classes;
	 * This should include all parent classes,
	 * but not the class from getInitalClass/getClassName.
	 * 
	 * @return
	 */
	public List<I_ClassParents> getParents();
	/**
	 * a ordered list of the classes parents
	 * from interfaces to super classes;
	 * This should include all parent classes,
	 * but not the class from getInitalClass/getClassName.
	 * @return
	 */
	public List<String> getParentNames();
	
	
}
