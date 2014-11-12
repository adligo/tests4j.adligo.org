package org.adligo.tests4j.run.xml_bindings.conversion;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverageBrief;
import org.adligo.tests4j.models.shared.results.I_ApiTrialResult;
import org.adligo.tests4j.run.xml.io.coverage.PackageCoverageType;
import org.adligo.tests4j.run.xml.io.trial_result.ApiTrialResultType;
import org.adligo.tests4j.run.xml.io.trial_result.CommonResultType;
import org.adligo.tests4j.run.xml.io.trial_result.TrialResultType;

public class ConvertApiTrialResults {

  @SuppressWarnings("boxing")
  public static TrialResultType to(I_ApiTrialResult result) {
    TrialResultType toRet = new TrialResultType();
    ApiTrialResultType sf = new ApiTrialResultType();
    
    CommonResultType cr = ConvertCommonTrialResults.to(result);
    sf.setCommon(cr);
    
    I_PackageCoverageBrief pkgCover = result.getPackageCoverage();
    if (pkgCover != null) {
      PackageCoverageType pkg = ConvertCoverage.to(pkgCover);
    }
    toRet.setApiTrialResult(sf);
    
    return toRet;
  }

}
