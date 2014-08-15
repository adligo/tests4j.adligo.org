package org.adligo.tests4j.models.shared.dependency;



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
	public static final String CLASS_ALIAS_MAY_NOT_BE_SET_TO_A_NULL_VALUE = 
			"DependencyMutant requires a non null class alias.";
	
	private I_ClassAlias alias;
	private int references;
	
	public DependencyMutant() {}
	
	public DependencyMutant(I_Dependency p) {
		alias = p.getAlias();
		references = p.getReferences();
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.common.I_Dependenc#getAlias()
	 */
	@Override
	public I_ClassAlias getAlias() {
		return alias;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.common.I_DependencyMutant#getReferences()
	 */
	@Override
	public int getReferences() {
		return references;
	}
	public void setAlias(I_ClassAlias pAlias) {
		if (pAlias == null) {
			throw new IllegalArgumentException(CLASS_ALIAS_MAY_NOT_BE_SET_TO_A_NULL_VALUE);
		}
		this.alias = pAlias;
	}
	
	public void addReference() {
		references++;
	}
	
	@Override
	public int compareTo(I_Dependency o) {
		//higher references mean lower order
		int negFlip = -1 * (references - o.getReferences());
		if (negFlip == 0) {
			I_ClassAlias otherClassName = o.getAlias();
			return alias.compareTo(otherClassName);
		}
		return negFlip;
	}
	@Override
	public String toString() {
		return toString(DependencyMutant.class, this);
	}
	
	String toString(Class<?> c, I_Dependency p) {
		return c.getSimpleName() + " [alias=" + p.getAlias() + ", references="
				+ p.getReferences() + "]";
	}
	
}
