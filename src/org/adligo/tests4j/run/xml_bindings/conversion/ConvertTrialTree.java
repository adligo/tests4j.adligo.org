package org.adligo.tests4j.run.xml_bindings.conversion;

import org.adligo.tests4j.run.common.I_JavaPackageNode;
import org.adligo.tests4j.run.xml.io.trial_run_result.v1_0.TrialTreeType;

import java.util.ArrayList;
import java.util.List;

public class ConvertTrialTree {

  public static TrialTreeType to(I_JavaPackageNode node) {
    TrialTreeType ret = new TrialTreeType();
    ret.setName(node.getName());
    List<String> trials = ret.getTrial();
    List<String> classNames = node.getClassNames();
    if (classNames != null) {
      if (classNames.size() >= 1) {
        trials.addAll(node.getClassNames());
      }
    }
    List<TrialTreeType> packages = ret.getPackage();
    List<I_JavaPackageNode> children = node.getChildNodes();
    List<TrialTreeType> childPkgs = to(children);
    if (childPkgs.size() >= 1) {
      packages.addAll(childPkgs);
    }
    return ret;
  }

  public static List<TrialTreeType> to(List<I_JavaPackageNode> children) {
    List<TrialTreeType> ret = new ArrayList<TrialTreeType>();
    
    if (children != null) {
      if (children.size() >= 1) {
        for (I_JavaPackageNode child: children) {
          TrialTreeType type = to(child);
          ret.add(type);
        }
      }
    }
    return ret;
  }
}
