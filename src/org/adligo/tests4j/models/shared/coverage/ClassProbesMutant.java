package org.adligo.tests4j.models.shared.coverage;


public class ClassProbesMutant implements I_ClassProbesMutant {

	private ProbesMutant probes_;
	private long classId_;
	private String className_;
	
	public ClassProbesMutant() {}
	
	public ClassProbesMutant(I_ClassProbes other) {
		probes_ = new ProbesMutant(other.getProbes());
		classId_ = other.getClassId();
		className_ = other.getClassName();
	}

	public ProbesMutant getProbes() {
		return probes_;
	}

	public long getClassId() {
		return classId_;
	}

	public String getClassName() {
		return className_;
	}

	public void setProbes(I_Probes p) {
	  try {
	    probes_ = (ProbesMutant) p;
	  } catch (ClassCastException x) {
	    probes_ = new ProbesMutant(p);
	  }
	}

	public void setClassId(long classId) {
		this.classId_ = classId;
	}

	public void setClassName(String className) {
		this.className_ = className;
	}

	@Override
	public boolean[] getProbesArray() {
		return probes_.getArray();
	}

  public int getCoverageUnits() {
    return probes_.getCoverageUnits();
  }

  public int getCoveredCoverageUnits() {
    return probes_.getCoveredCoverageUnits();
  }
}
