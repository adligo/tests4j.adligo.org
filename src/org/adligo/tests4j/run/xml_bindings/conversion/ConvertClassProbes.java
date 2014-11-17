package org.adligo.tests4j.run.xml_bindings.conversion;

import org.adligo.tests4j.models.shared.coverage.I_ClassProbes;
import org.adligo.tests4j.models.shared.coverage.I_Probes;
import org.adligo.tests4j.run.xml.io.coverage.v1_0.ClassCoverageType;

public class ConvertClassProbes {

  public static ClassCoverageType to(I_ClassProbes classProbes) {
    ClassCoverageType toRet = new ClassCoverageType();
    toRet.setClassName(classProbes.getClassName());
    I_Probes probes = classProbes.getProbes();
    boolean [] probesArray =  probes.toArray();

    byte [] probeBytes = ConvertCoverageByteArrays.to(probesArray);
    toRet.setProbes(probeBytes);
    return toRet;
  }
}
