package org.adligo.tests4j.models.shared.dependency;

import org.adligo.tests4j.models.shared.common.StringMethods;


/**
 * a simple mutable class that represents 
 * a dependency one java class references another java class.
 * a String is used to represent the class name so it 
 * can be passed between classloaders.
 * 
 * @author scott
 *
 */
public class DependencyMutant implements I_Dependency {
	public static final String CLASS_NAME_MAY_NOT_BE_SET_TO_A_EMPTY_VALUE = 
			"Dependency requires a non empty class name.";
	
	private String clazzName;
	private int references;
	
	public DependencyMutant() {}
	
	public DependencyMutant(I_Dependency p) {
		clazzName = p.getClassName();
		references = p.getReferences();
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.common.I_DependencyMutant#getClazzName()
	 */
	@Override
	public String getClassName() {
		return clazzName;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.common.I_DependencyMutant#getReferences()
	 */
	@Override
	public int getReferences() {
		return references;
	}
	public void setClazzName(String pName) {
		if (StringMethods.isEmpty(pName)) {
			throw new IllegalArgumentException(CLASS_NAME_MAY_NOT_BE_SET_TO_A_EMPTY_VALUE);
		}
		this.clazzName = pName;
	}
	
	public void addReference() {
		references++;
	}
	
	@Override
	public int compareTo(I_Dependency o) {
		//higher references mean lower order
		int negFlip = -1 * (references - o.getReferences());
		return negFlip;
	}
	@Override
	public String toString() {
		return toString(DependencyMutant.class, this);
	}
	
	String toString(Class<?> c, I_Dependency p) {
		return c.getSimpleName() + " [clazzName=" + p.getClassName() + ", references="
				+ p.getReferences() + "]";
	}
	
}
