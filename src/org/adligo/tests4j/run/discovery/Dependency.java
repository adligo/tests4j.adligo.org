package org.adligo.tests4j.run.discovery;



public class Dependency implements I_Dependency {
	private DependencyMutant dm;

	public Dependency(I_Dependency p) {
		String name = p.getClazzName();
		dm = new DependencyMutant(p);
		//throw a IllegalArgumentException if name is empty
		dm.setClazzName(name);
	}
	
	public String getClazzName() {
		return dm.getClazzName();
	}

	public int getReferences() {
		return dm.getReferences();
	}

	public int compareTo(I_Dependency o) {
		return dm.compareTo(o);
	}
	
}