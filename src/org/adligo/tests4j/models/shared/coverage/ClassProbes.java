package org.adligo.tests4j.models.shared.coverage;

/**
 * the probes for just one class, 
 * which could be a inner class.
 * 
 * @author scott
 *
 */
public class ClassProbes implements I_ClassProbes {
	private Probes probes_;
	private long classId_;
	private String className_;
	
	public ClassProbes(I_ClassProbes other) {
		probes_ = new Probes(other.getProbes());
		classId_ = other.getClassId();
		className_ = other.getClassName();
	}
	
	@Override
	public I_Probes getProbes() {
		return probes_;
	}
	
	@Override
	public long getClassId() {
		return classId_;
	}
	
	@Override
	public String getClassName() {
		return className_;
	}
	
  public int getCoverageUnits() {
    return probes_.getCoverageUnits();
  }

  public int getCoveredCoverageUnits() {
    return probes_.getCoveredCoverageUnits();
  }
}
