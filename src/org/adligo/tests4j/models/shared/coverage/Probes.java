package org.adligo.tests4j.models.shared.coverage;

import org.adligo.tests4j.shared.common.ArrayUtils;

import java.util.Arrays;


/**
 * a immutable representation of probes
 * @author scott
 *
 */
public class Probes implements I_Probes {
	private final boolean[] probes_;
	
	
	public Probes(I_Probes other) {
		probes_ = ArrayUtils.copyOf(other.toArray());
	}
	
	public Probes(boolean [] pProbes) {
		probes_ = ArrayUtils.copyOf(pProbes);
	}
	
	@Override
	public boolean get(int p) {
		if (p >= probes_.length) {
			return false;
		}
		return probes_[p];
	}

	@Override
	public int size() {
		return probes_.length;
	}

  @Override
  public boolean[] toArray() {
    return ArrayUtils.copyOf(probes_);
  }

  @Override
  public String toString() {
    return ProbesMutant.toString(this);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(probes_);
  }

  @Override
  public boolean equals(Object obj) {
    return ProbesMutant.equals(this, obj);
  }
}
