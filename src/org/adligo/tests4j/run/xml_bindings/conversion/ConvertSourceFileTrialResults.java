package org.adligo.tests4j.run.xml_bindings.conversion;

import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverageBrief;
import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;
import org.adligo.tests4j.run.xml.io.trial_result.CommonResultType;
import org.adligo.tests4j.run.xml.io.trial_result.InnerSourceFileCoverageType;
import org.adligo.tests4j.run.xml.io.trial_result.SourceFileTrialResultType;
import org.adligo.tests4j.run.xml.io.trial_result.TrialResultType;

public class ConvertSourceFileTrialResults {

  public static TrialResultType to(I_SourceFileTrialResult result) {
    TrialResultType toRet = new TrialResultType();
    SourceFileTrialResultType sf = new SourceFileTrialResultType();
    
    CommonResultType cr = ConvertCommonTrialResults.to(result);
    sf.setCommon(cr);
    
    I_SourceFileCoverageBrief brief = result.getSourceFileCoverage();
    if (brief != null) {
      InnerSourceFileCoverageType coverage = ConvertCoverage.toInner(brief);
      sf.setCoverage(coverage);
    }
    
    toRet.setSouceFileTrialResult(sf);
    
    return toRet;
  }


}
