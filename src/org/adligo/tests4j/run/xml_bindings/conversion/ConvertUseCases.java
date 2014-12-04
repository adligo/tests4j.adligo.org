package org.adligo.tests4j.run.xml_bindings.conversion;

import org.adligo.tests4j.models.shared.metadata.I_UseCaseBrief;
import org.adligo.tests4j.run.xml.io.use_cases.v1_0.UseCaseNameType;

public class ConvertUseCases {

  public static UseCaseNameType to(I_UseCaseBrief useCase) {
    UseCaseNameType toRet = new UseCaseNameType();
    //toRet.setNown(useCase.getNown());
    //toRet.setVerb(useCase.getVerb());
    return toRet;
  }
}
