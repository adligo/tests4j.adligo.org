package org.adligo.tests4j.models.shared.coverage;

import org.adligo.tests4j.shared.common.ClassMethods;

import java.util.Arrays;


public class ProbesMutant implements I_Probes {
	private final boolean[] probes_;

	public ProbesMutant() {
		probes_ = new boolean[]{};
	}
	
	public ProbesMutant(boolean [] pProbes) {
		probes_ = new boolean[pProbes.length];
		for (int i = 0; i < pProbes.length; i++) {
			probes_[i] = pProbes[i];
		}
	}
	
	public ProbesMutant(I_Probes other) {
		probes_ = new boolean[other.size()];
		for (int i = 0; i < other.size(); i++) {
			probes_[i] = other.get(i);
		}
	}
	
	@Override
	public boolean get(int p) {
		if (p >= probes_.length || p < 0) {
			return false;
		}
		return probes_[p];
	}
	
	public void set(int i, boolean p) {
		if (i >= 0 && i < probes_.length) {
			probes_[i] = p;
		}
	}
	
	@Override
	public int size() {
		return probes_.length;
	}
	
	public boolean[] getArray() {
		return probes_;
	}

	public boolean[] toArray() {
	  return probes_;
	}
	
	
  @Override
  public int hashCode() {
    return Arrays.hashCode(probes_);
  }

  @Override
  public boolean equals(Object obj) {
    return equals(this, obj);
  }

  protected static boolean equals(I_Probes me, Object obj) {
    try {
      I_Probes op = (I_Probes) obj;
      boolean [] asArray = op.toArray();
      boolean [] probes = me.toArray();
      return Arrays.equals(probes, asArray);
    } catch (ClassCastException x) {
      //it wasn't a I_Probes
    }
    return false;
  }
  
  @Override
  public int getCoverageUnits() {
    return probes_.length;
  }
  @Override
  public int getCoveredCoverageUnits() {
    return getCoveredCoverageUnits(probes_);
  }
  
  public static int getCoveredCoverageUnits(boolean [] in) {
    int toRet = 0;
    for (int i = 0; i < in.length; i++) {
      if (in[i]) {
        toRet++;
      }
    }
    return toRet;
  }
  
  public String toString() {
    return toString(this);
  }
  
  public static String toString(I_Probes p) {
    StringBuilder sb = new StringBuilder();
    sb.append(ClassMethods.getSimpleName(p)+ " [");
    
    int counter = 0;
    for (int i = 0; i < p.size(); i++) {
      if (i != 0 && counter == 0) {
        sb.append(",");
      }
      if (p.get(i)) {
        sb.append("t");
      } else {
        sb.append("f");
      }
      
      counter++;
      if (counter == 3) {
        counter = 0;
      }
    }
    sb.append("]");
    return sb.toString();
  }
}
