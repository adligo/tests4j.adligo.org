package org.adligo.tests4j.run.xml_bindings.conversion;

import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.run.common.I_JavaPackageNode;
import org.adligo.tests4j.run.common.JavaTree;
import org.adligo.tests4j.run.xml.io.trial_run_result.v1_0.TrialGroupType;
import org.adligo.tests4j.run.xml.io.trial_run_result.v1_0.TrialRunResultType;
import org.adligo.tests4j.run.xml.io.trial_run_result.v1_0.TrialTreeType;

import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

public class ConvertRunResults {
  /**
   * a List<String> for the passing results
   */
  public static final String PASSES = "passes";
  /**
   * a List<String> for the failing results
   */
  public static final String FAILURES = "failures";
  /**
   * a List<String> for the ignored trials
   */
  public static final String IGNORES = "ignroes";
  /**
   * a List<String> of the directories where the trial files are stored
   */
  public static final String GROUPS = "groups";
  
  /**
   * 
   * @param result
   * @param extras see the constants in this class for 
   *   a description of the values
   * @return
   */
  @SuppressWarnings({"boxing", "unchecked"})
  public static TrialRunResultType to(I_TrialRunResult result, Map<String,Object> extras) {
    TrialRunResultType ret = new TrialRunResultType();
    
    List<String> resultFolder = ret.getResultFolder();
    List<String> groups = (List<String>) extras.get(GROUPS);
    resultFolder.addAll(groups);
    
    ret.setAsserts(result.getAsserts());
    ret.setUniqueAsserts(result.getUniqueAsserts());
    /*
    List<I_PackageCoverageBrief> coverage = result.getCoverage();
    if (coverage != null) {
      if (coverage.size() >= 1) {
        List<PackageCoverageType> cover = ConvertCoverage.to(coverage);
        PackagesCoverageType pkgs = new PackagesCoverageType();
        pkgs.getPackage().addAll(cover);
        ret.setCoverage(pkgs);
      }
    }
    */
    ret.setCoveragePercentage((float) result.getCoveragePercentage()); 
    
    //passes
    List<String> passes = (List<String>) extras.get(PASSES);
    ret.setTrialsPassed(passes.size());
    JavaTree jt = new JavaTree(passes);
    List<I_JavaPackageNode> nodes = jt.getNodes();
    List<TrialTreeType> jtree = ConvertTrialTree.to(nodes);
    
    TrialGroupType passGroup = new TrialGroupType();
    List<TrialTreeType> pkgs = passGroup.getPackage();
    pkgs.addAll(jtree);
    ret.setPassingTrials(passGroup);
    
    //passes
    List<String> failures = (List<String>) extras.get(FAILURES);
    ret.setTrialsFailed(failures.size());
    JavaTree jtFail = new JavaTree(failures);
    List<I_JavaPackageNode> nodesFail = jtFail.getNodes();
    List<TrialTreeType> jtreeFail = ConvertTrialTree.to(nodesFail);
    
    TrialGroupType failGroup = new TrialGroupType();
    List<TrialTreeType> failPkgs = failGroup.getPackage();
    failPkgs.addAll(jtreeFail);
    ret.setFailingTrials(failGroup);
    
    //passes
    List<String> ignores = (List<String>) extras.get(IGNORES);
    ret.setTrialsIgnored(ignores.size());
    JavaTree jtIgnores = new JavaTree(ignores);
    List<I_JavaPackageNode> nodesIgnored = jtIgnores.getNodes();
    List<TrialTreeType> jtreeIgnored = ConvertTrialTree.to(nodesIgnored);
    
    TrialGroupType ignoredGroup = new TrialGroupType();
    List<TrialTreeType> pkgsIgnored = ignoredGroup.getPackage();
    pkgsIgnored.addAll(jtreeIgnored);
    ret.setIgnoredTrials(ignoredGroup);
    
    ret.setTrials(passes.size() + failures.size() + ignores.size());
    ret.setTests(result.getTests());
    ret.setTestsPassed(result.getTestsPassed());
    ret.setTestsFailed(result.getTestsFailed());
    ret.setTestsIgnored(result.getTestsIgnored());
    
    DatatypeFactory df;
    try {
      df = DatatypeFactory.newInstance();
      Duration duration = df.newDuration(result.getRunTime());
      ret.setTime(duration);
      
    } catch (DatatypeConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return ret;
  }


}
