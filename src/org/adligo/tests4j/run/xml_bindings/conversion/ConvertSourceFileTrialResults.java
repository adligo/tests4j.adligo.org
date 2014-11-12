package org.adligo.tests4j.run.xml_bindings.conversion;

import org.adligo.tests4j.models.shared.coverage.I_ClassProbes;
import org.adligo.tests4j.models.shared.coverage.I_Probes;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverageBrief;
import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;
import org.adligo.tests4j.run.xml.io.coverage.ClassCoverageType;
import org.adligo.tests4j.run.xml.io.trial_result.CommonResultType;
import org.adligo.tests4j.run.xml.io.trial_result.InnerSourceFileCoverageType;
import org.adligo.tests4j.run.xml.io.trial_result.SourceFileTrialResultType;
import org.adligo.tests4j.run.xml.io.trial_result.TrialResultType;

import java.util.List;

public class ConvertSourceFileTrialResults {

  @SuppressWarnings("boxing")
  public static TrialResultType to(I_SourceFileTrialResult result) {
    TrialResultType toRet = new TrialResultType();
    SourceFileTrialResultType sf = new SourceFileTrialResultType();
    
    CommonResultType cr = new CommonResultType();
    cr.setAfterTrialOutput(result.getAfterTrialOutput());
    cr.setAsserts(result.getAssertionCount());
    cr.setBeforeTrialOutput(result.getBeforeTrialOutput());
    if (!result.hasRecordedCoverage()) {
      cr.setCoverage(false);
    }
    if (result.isIgnored()) {
      cr.setIgnored(true);
    }
    cr.setName(result.getName());
    cr.setPassed(result.isPassed());
    cr.setRun(result.getRunNumber());
    cr.setType("" + result.getType().getId());
    cr.setUniqueAsserts(result.getUniqueAssertionCount());
    sf.setCommon(cr);
    
    I_SourceFileCoverageBrief brief = result.getSourceFileCoverage();
    if (brief != null) {
      I_Probes probes = brief.getProbes();
      InnerSourceFileCoverageType coverage = new InnerSourceFileCoverageType();
      byte [] data = ConvertByteArrays.to(
          brief.getCoveredCoverageUnits(), 
          brief.getCoverageUnits(), 
          probes.toArray());
      coverage.setData(data);
    
      List<I_ClassProbes> classProbes = brief.getClassProbes();
      List<ClassCoverageType> classCover = coverage.getClassProbes();
      if (classProbes.size() >= 1) {
        for (I_ClassProbes cp: classProbes) {
          ClassCoverageType cct = ConvertClassProbes.to(cp);
          classCover.add(cct);
        }
      }
      sf.setCoverage(coverage);
    }
    toRet.setSouceFileTrialResult(sf);
    
    return toRet;
  }
}
