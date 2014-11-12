package org.adligo.tests4j.run.xml_bindings.conversion;

import org.adligo.tests4j.models.shared.results.I_UseCaseTrialResult;
import org.adligo.tests4j.run.xml.io.trial_result.CommonResultType;
import org.adligo.tests4j.run.xml.io.trial_result.TrialResultType;
import org.adligo.tests4j.run.xml.io.trial_result.UseCaseTrialResultType;
import org.adligo.tests4j.run.xml.io.use_cases.UseCaseBriefType;

public class ConvertUseCaseTrialResults {

  public static TrialResultType to(I_UseCaseTrialResult result) {
    TrialResultType ret = new TrialResultType();
    UseCaseTrialResultType trial = new UseCaseTrialResultType();
    
    UseCaseBriefType uc = ConvertUseCases.to(result.getUseCase());
    trial.setUseCase(uc);
    CommonResultType cr = ConvertCommonTrialResults.to(result);
    trial.setCommon(cr);
    ret.setUseCaseTrialResult(trial);
    return ret;
  }

}
