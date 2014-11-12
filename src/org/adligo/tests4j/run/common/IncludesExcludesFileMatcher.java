package org.adligo.tests4j.run.common;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class IncludesExcludesFileMatcher implements I_FileMatcher {
  private List<I_FileMatcher> exclueds_ = new ArrayList<I_FileMatcher>();
  private List<I_FileMatcher> includes_ = new ArrayList<I_FileMatcher>();
  
  /**
   * Create a complex IncludesExcludesFileMatcher
   * with multiple pattern matchers
   * @param includes if null use the includesDefault
   * @param includesDefault if used and null, nothing is excluded
   * @param excludes if null use the excludesDefault
   * @param excludesDefault if used and null, nothing is excluded
   */
  public IncludesExcludesFileMatcher(String includes, 
      String includesDefault, String excludes, String excludesDefault) {
      if (includes != null) {
        addIncludes(includes);
      } else if (includesDefault != null) {
        addIncludes(includesDefault);
      }
      if (excludes != null) {
        addExcludes(excludes);
      } else if (excludesDefault != null) {
        addExcludes(excludesDefault);
      }
  }

  
  
  public IncludesExcludesFileMatcher(List<I_FileMatcher> includes, List<I_FileMatcher> excludes_ ) {
    includes_.addAll(includes);
    excludes_.addAll(excludes_);
  }

  @Override
  public boolean isMatch(String path) {
    for (I_FileMatcher e: exclueds_) {
      if (e.isMatch(path)) {
        return false;
      }
    }
    for (I_FileMatcher i: includes_) {
      if (i.isMatch(path)) {
        return true;
      }
    }
    return false;
  }
  
  private void addIncludes(String includes) {
    StringTokenizer tokens = new StringTokenizer(includes, ",");
    while (tokens.hasMoreTokens()) {
      String pattern = tokens.nextToken();
      includes_.add(new PatternFileMatcher(pattern, true));
    }
  }
  
  private void addExcludes(String excludes) {
    StringTokenizer tokens = new StringTokenizer(excludes, ",");
    while (tokens.hasMoreTokens()) {
      String pattern = tokens.nextToken();
      exclueds_.add(new PatternFileMatcher(pattern, false));
    }
  }
}
