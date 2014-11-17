package org.adligo.tests4j.run.xml_bindings.conversion;

import org.adligo.tests4j.models.shared.coverage.I_ClassProbes;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverageBrief;
import org.adligo.tests4j.models.shared.coverage.I_Probes;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverageBrief;
import org.adligo.tests4j.run.xml.io.coverage.v1_0.ClassCoverageType;
import org.adligo.tests4j.run.xml.io.coverage.v1_0.PackageCoverageType;
import org.adligo.tests4j.run.xml.io.coverage.v1_0.SourceFileCoverageType;
import org.adligo.tests4j.run.xml.io.trial_result.v1_0.InnerSourceFileCoverageType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ConvertCoverage {

  public static InnerSourceFileCoverageType toInner(I_SourceFileCoverageBrief brief) {
    I_Probes probes = brief.getProbes();
    InnerSourceFileCoverageType coverage = new InnerSourceFileCoverageType();
    byte [] data = ConvertCoverageByteArrays.to(
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
    return coverage;
  }
  
  public static SourceFileCoverageType to(I_SourceFileCoverageBrief brief) {
    I_Probes probes = brief.getProbes();
    SourceFileCoverageType coverage = new SourceFileCoverageType();
    String className = brief.getClassName();
    coverage.setName(className);
    byte [] data = ConvertCoverageByteArrays.to(
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
    return coverage;
  }
  
  public static PackageCoverageType to(I_PackageCoverageBrief brief) {
    PackageCoverageType coverage = new PackageCoverageType();
    List<SourceFileCoverageType> sfCoverToRet = coverage.getFile();
    List<PackageCoverageType> pkgCoverToRet = coverage.getPackage();
    
    Set<String> names = brief.getSourceFileNames();
    for (String name: names) {
      I_SourceFileCoverageBrief sfBrief = brief.getCoverage(name);
      if (sfBrief != null) {
        SourceFileCoverageType sfCover = to(sfBrief);
        sfCoverToRet.add(sfCover);
      }
    }
    List<I_PackageCoverageBrief> children = brief.getChildPackageCoverage();
    for (I_PackageCoverageBrief pkg: children) {
      PackageCoverageType pkgCover = to(pkg);
      pkgCoverToRet.add(pkgCover);
    }
    return coverage;
  }
  
  public static List<PackageCoverageType> to(Collection<I_PackageCoverageBrief> briefs) {
    List<PackageCoverageType> toRet = new ArrayList<PackageCoverageType>();
    for (I_PackageCoverageBrief brief: briefs) {
      PackageCoverageType pkg = to(brief);
      toRet.add(pkg);
    }
    
    return toRet;
  }
}
