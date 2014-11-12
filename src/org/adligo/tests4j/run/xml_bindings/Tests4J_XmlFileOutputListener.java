package org.adligo.tests4j.run.xml_bindings;

import org.adligo.tests4j.models.shared.metadata.I_TrialMetadata;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_PhaseState;
import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.run.common.I_JavaPackageNode;
import org.adligo.tests4j.run.common.I_JseSystem;
import org.adligo.tests4j.run.common.JavaTree;
import org.adligo.tests4j.run.common.JseSystem;
import org.adligo.tests4j.run.xml.io.trial_result.TrialResultType;
import org.adligo.tests4j.run.xml_bindings.conversion.ConvertSourceFileTrialResults;
import org.adligo.tests4j.run.xml_bindings.io.TrialResultIO;
import org.adligo.tests4j.shared.common.I_TrialType;
import org.adligo.tests4j.shared.common.TrialType;
import org.adligo.tests4j.system.shared.api.I_Tests4J_Listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class writes files out to disk
 * @author scott
 *
 */
public class Tests4J_XmlFileOutputListener implements I_Tests4J_Listener {
  private I_JseSystem system_ = new JseSystem();
  private String results_ = getResultsDir();
  private String trials_ = results_ + File.separator + "trials";
  private AtomicBoolean hasTopPkgs_ = new AtomicBoolean(false);
  private Set<String> topPkgs_ = new HashSet<String>();
  private ConcurrentSkipListSet<String> trialPkgDirs_ = new ConcurrentSkipListSet<String>();
  
  private String getResultsDir() {
    File file = system_.newFile(".");
    String abs = file.getAbsolutePath();
    char c = abs.charAt(abs.length() - 1);
    if (c == '.') {
      abs = abs.substring(0, abs.length() - 1);
    }
    return abs +  "results";
  }

  @Override
  public void onMetadataCalculated(I_TrialRunMetadata metadata) {

    int trials = metadata.getAllTrialsCount();
    if (trials > 100) {
      hasTopPkgs_.set(true);
     
      Set<String> trialNames = new HashSet<String>();
      List<? extends I_TrialMetadata> trialList = metadata.getAllTrialMetadata();
      for (I_TrialMetadata tm: trialList) {
        String name = tm.getTrialName(); 
        trialNames.add(name);
      }
      JavaTree tree = new JavaTree(trialNames);
      List<I_JavaPackageNode> nodes = tree.getNodes();
      
      File resultsFile = system_.newFile(results_);
      if (resultsFile.exists()) {
        try {
          system_.remove(results_);
        } catch (IOException x) {
          throw new RuntimeException("There was a problem deleting the file " + 
              resultsFile.getAbsolutePath(), x);
        }
      }
      system_.mkdir(results_);
      system_.mkdir(trials_);
      findTopPkgs(nodes, new ArrayList<I_JavaPackageNode>(), topPkgs_);
      for (String topPkg: topPkgs_) {
        String rev = JavaTree.reverseJavaName(topPkg);
        trialPkgDirs_.add(rev);
        system_.mkdir(trials_ + File.separator + rev);
      }
    } 
  }

  private void findTopPkgs(List<I_JavaPackageNode> nodes, 
      List<I_JavaPackageNode> parentNodes, Set<String> topPkgs) {
    for (I_JavaPackageNode node: nodes) {
      String name = null;
      if (parentNodes != null) {
        for (I_JavaPackageNode parentNode: parentNodes) {
          if (name == null) {
            name = parentNode.getName();
          } else {
            name = name + "." + parentNode.getName(); 
          }
        }
      }
      if (name == null) {
        name = node.getName();
      } else {
        name = name + "." + node.getName(); 
      }
      if (node.countClasses() > 100) {
        List<String> classNames = node.getClassNames();
        if (classNames.size() >= 1) {
          //there are trial classes in this package, so we can't use sub packages
          topPkgs.add(name);
        } else {
          List<I_JavaPackageNode> parents = new ArrayList<I_JavaPackageNode>(parentNodes);
          parents.add(node);
          findTopPkgs(node.getChildNodes(), parents, topPkgs);
        }
      } else {
        topPkgs.add(name);
      }
    }
  }
  
  @Override
  public void onStartingTrial(String trialName) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onStartingTest(String trialName, String testName) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onTestCompleted(String trialName, String testName, boolean passed) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onTrialCompleted(I_TrialResult result) {
    String fullFileName = identifyFileNameAndCreateParentDirs(result);
    I_TrialType tt = result.getType();
    TrialType type = TrialType.get(tt);
    switch (type) {
      case SourceFileTrial:
          TrialResultType rt = ConvertSourceFileTrialResults.to(
              (I_SourceFileTrialResult) result);
        try {
          TrialResultIO.write(fullFileName, rt);
        } catch (IOException e) {
          e.printStackTrace(system_.getOut());
        }
        break;
      default:
        //donothing
    }
  }

  public String identifyFileNameAndCreateParentDirs(I_TrialResult result) {
    String fileName = result.getName();
    fileName = JavaTree.reverseJavaName(fileName);
    String fullFileName = null;
    
    if (!hasTopPkgs_.get()) {
      //write file 
      fullFileName = trials_ + File.separator + fileName  + ".xml";
    } else {
      //trialPkgDirs_.
      Set<String> topDirs = new HashSet<String>(trialPkgDirs_);
      for (String topDir: topDirs) {
        int index = fileName.indexOf(topDir);
        if (index != -1) {
          String baseDir = trials_ + File.separator + topDir;
          
          fullFileName = baseDir + File.separator + fileName  + ".xml";
          break;
        }
      }
    }
    if (fullFileName == null) {
      new Exception("Unable to identify package for " + fileName).printStackTrace(system_.getOut());
    }
    return fullFileName;
  }

  @Override
  public void onRunCompleted(I_TrialRunResult result) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onProgress(I_PhaseState info) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onProccessStateChange(I_PhaseState info) {
    // TODO Auto-generated method stub
    
  }
}
