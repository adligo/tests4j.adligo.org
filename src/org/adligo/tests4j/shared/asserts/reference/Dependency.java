package org.adligo.tests4j.shared.asserts.reference;




public class Dependency implements I_Dependency {
	private DependencyMutant dm;

	public Dependency(I_Dependency p) {
		I_ClassAlias name = p.getAlias();
		dm = new DependencyMutant(p);
		//throw a IllegalArgumentException if name is empty
		dm.setAlias(name);
	}
	
	public I_ClassAlias getAlias() {
		return dm.getAlias();
	}

	public int getReferences() {
		return dm.getReferences();
	}

	public int compareTo(I_Dependency o) {
		return dm.compareTo(o);
	}
	@Override
	public String toString() {
		return dm.toString(Dependency.class, this);
	}
}
