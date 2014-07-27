package org.adligo.tests4j.run.discovery;


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
	private String clazzName;
	private int references;
	
	public DependencyMutant() {}
	
	public DependencyMutant(I_Dependency p) {
		clazzName = p.getClazzName();
		references = p.getReferences();
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.common.I_DependencyMutant#getClazzName()
	 */
	@Override
	public String getClazzName() {
		return clazzName;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.common.I_DependencyMutant#getReferences()
	 */
	@Override
	public int getReferences() {
		return references;
	}
	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
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
	
}
