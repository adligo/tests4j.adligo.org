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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((dm == null) ? 0 : dm.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Dependency other = (Dependency) obj;
    if (dm == null) {
      if (other.dm != null)
        return false;
    } else if (!dm.equals(other.dm))
      return false;
    return true;
  }
}
