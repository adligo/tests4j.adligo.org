package org.adligo.tests4j.run.discovery;


public class Dependency implements I_Dependency {
	private DependencyMutant dm;

	public Dependency(I_Dependency p) {
		dm = new DependencyMutant(p);
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
