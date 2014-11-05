package org.adligo.tests4j.models.shared.coverage;


public interface I_ClassProbesMutant extends I_ClassProbes {
	public void setClassName(String p);
	public void setClassId(long p);
	public void setProbes(I_Probes p);
	public boolean[] getProbesArray();
}
