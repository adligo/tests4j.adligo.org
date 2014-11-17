package org.adligo.tests4j.run.xml_bindings.conversion;

import org.adligo.tests4j.models.shared.metadata.I_UseCaseBrief;
import org.adligo.tests4j.run.xml.io.use_cases.v1_0.UseCaseBriefType;

public class ConvertUseCases {

  public static UseCaseBriefType to(I_UseCaseBrief useCase) {
    UseCaseBriefType toRet = new UseCaseBriefType();
    toRet.setNown(useCase.getNown());
    toRet.setVerb(useCase.getVerb());
    return toRet;
  }
}
