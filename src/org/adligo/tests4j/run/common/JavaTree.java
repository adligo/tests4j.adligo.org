package org.adligo.tests4j.run.common;

import org.adligo.tests4j.shared.asserts.reference.ClassAliasLocal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * This class identifies a tree structure which mimics
 * .java src folders, except that 
 * common java package names are aggregated into
 * package nodes.
 * i.e.
 *    org.adligo.gwt_refs
 *    org.adligo.tests4j.models
 *    
 *   will turn into
 *    org.adligo
 *      |-gwt_refs
 *      |-tests4j.models
 *      
 * and 
 *    org.adligo.gwt_refs
 *    org.adligo.tests4j.models.shared.association
 *    org.adligo.tests4j.models.shared.coverage
 *    
 *   will turn into
 *    org.adligo
 *      |-gwt_refs
 *      |-tests4j.models.shared
 *          |-association
 *          |-coverage
 *          
 * The tops of the trees must also be either;
 * A the parent package of a java class used to create a JavaTree instance.
 * or
 * A java package name used to create a JavaTree instance.
 * 
 *    Also note that this class uses the left two package
 *    names if at all possible for the tops of the trees,
 *    since it is a enterprise test tool, it expects
 *    it is testing code which follows;
 *    https://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html
 * @author scott
 *
 */
public class JavaTree {
  private I_Classes classes_ = new ClassesDelegate();
  private ClassLoader classLoader_ = new ClassLoader(ClassLoader.getSystemClassLoader()) {};
  private Set<String> all_ = new HashSet<String>();
  private Set<String> classNames_ = new HashSet<String>();
  private Set<ClassAliasLocal> classAliases_ = new HashSet<ClassAliasLocal>();
  private Set<String> packages_ = new TreeSet<String>();
  private Map<String, JavaPackageNodeMutant> top_ = new HashMap<String, JavaPackageNodeMutant>();
  
  public JavaTree(Collection<String> javaPackagesOrClasses) {
    all_.addAll(javaPackagesOrClasses);
    for (String poc: javaPackagesOrClasses) {
      try {
        Class<?> clazz = classes_.forName(poc, true, classLoader_);
        Package pkg = clazz.getPackage();
        classNames_.add(poc);
        classAliases_.add(new ClassAliasLocal(clazz));
        packages_.add(pkg.getName());
      } catch (ClassNotFoundException x) {
        //guess it's a package
        packages_.add(poc);
      }
    }
    addTopsByTransversingDownLengthTree();
  }
  
  private void addTopsByTransversingDownLengthTree() {
    int nextLength = getNextLength(0);
    while (nextLength != -1) {
      for (String pkg: packages_) {
        if (pkg.length() == nextLength) {
          if (!containsParentPackage(pkg)) {
            top_.put(pkg, new JavaPackageNodeMutant(pkg));
          }
        } 
      }
      if (finished()) {
        break;
      } else {
        nextLength = getNextLength(nextLength);
        if (nextLength == -1) {
          break;
        }
      }
    }
    //ok transverse values, adding all matches 
    Collection<JavaPackageNodeMutant> nodes = top_.values();
    for (JavaPackageNodeMutant jpnm: nodes) {
      String name = jpnm.getName();
      Set<String> classNames = getPackageClasses(name);
      jpnm.setClassNames(classNames);
      //add the package name to the classes for the getChildren method
      Set<String> packageClasses = new HashSet<String>();
      if (classNames != null) {
        for (String cn: classNames) {
          packageClasses.add(name + "." + cn);
        }
      }
      Set<String> children = getChildren(name, packageClasses);
      if (children.size() >= 1) {
        JavaTree childTree = new JavaTree(children);
        Collection<JavaPackageNodeMutant> jpnmChildren = childTree.top_.values();
        for (JavaPackageNodeMutant c: jpnmChildren) {
          String pkgName = c.getName();
          pkgName = pkgName.substring(name.length() + 1, pkgName.length());
          c.setName(pkgName);
        }
        
        jpnm.setChildNodes(jpnmChildren);
      }
    }
  }
  
  private boolean containsParentPackage(String packageName) {
    Set<String> keys = top_.keySet();
    for (String key: keys) {
      if (packageName.indexOf(key) == 0) {
        if (packageName.length() > key.length() + 1) {
          char c = packageName.charAt(key.length());
          if (c == '.') {
            return true;
          }
        }
      }
    }
    return false;
  }
  private Set<String> getPackageClasses(String packageName) {
    Set<String> toRet = new HashSet<String>();
    for (ClassAliasLocal cal: classAliases_) {
      String name = cal.getName();
      if (name.indexOf(packageName) == 0) {
        if (!name.equals(packageName)) {
          if (name.length() >= packageName.length() + 1) {
            char c = name.charAt(packageName.length());
            if (c == '.') {
              String extra = name.substring(packageName.length() + 1, name.length());
              if (extra.indexOf(".") == -1) {
                Class<?> clazz = cal.getTarget();
                toRet.add(clazz.getSimpleName());
              }
            }
          }
        }
      }
    }
    return toRet;
  }
  
  private Set<String> getChildren(String filter, Set<String> packageClasses) {
    Set<String> toRet = new HashSet<String>();
    for (String e: all_) {
      if (!packageClasses.contains(e)) {
        if (e.indexOf(filter) == 0) {
          if (!e.equals(filter)) {
            if (e.charAt(filter.length()) == '.') {
              toRet.add(e);
            }
          }
        }
      } 
    }
    return toRet;
  }
  
 @SuppressWarnings("boxing")
private int getNextLength(int lastLength) {
    TreeSet<Integer> lengths = new TreeSet<Integer>();
    
    for (String pkg: packages_) {
      if (pkg.length() > lastLength) {
        lengths.add(pkg.length());
      }
    }
    Integer toRet =  -1;
    if ( !lengths.isEmpty()) {
      toRet = lengths.first();
      if (toRet != null) {
        return toRet;
      }
    }
    return toRet;
  }
 
  private boolean finished() {
    Set<String> subPackages = new HashSet<String>();
    Set<String> topKeys = top_.keySet();
    for (String topName: topKeys) {
      for (String pkg: packages_) {
        if (pkg.indexOf(topName) == 0) {
          if (pkg.equals(topName)) {
            subPackages.add(pkg);
          } else {
            if (pkg.length() > topName.length() + 1) {
              char c = pkg.charAt(topName.length() + 1);
              if (c == '.') {
                subPackages.add(pkg);
              }
            }
          }
        }
      }
    }
    boolean result = subPackages.containsAll(packages_);
    return result;
  }
  public List<I_JavaPackageNode> getNodes() {
    return new ArrayList<I_JavaPackageNode>(top_.values());
  }
  
  public static String reverseJavaName(String topPkg) {
    StringTokenizer st = new StringTokenizer(topPkg,".");
    List<String> tokens = new ArrayList<String>();
    while (st.hasMoreTokens()) {
      tokens.add(st.nextToken());
    }
    StringBuilder sb = new StringBuilder();
    for (int i = tokens.size() - 1; i >= 0; i--) {
      String token = tokens.get(i);
      if (i != tokens.size() - 1) {
        sb.append(".");
      }
      sb.append(token);
    }
    return sb.toString();
  }
}
