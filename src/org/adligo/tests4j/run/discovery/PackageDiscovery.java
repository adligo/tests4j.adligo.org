package org.adligo.tests4j.run.discovery;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.adligo.tests4j.run.helpers.I_CachedClassBytesClassLoader;

/**
 * A immutable class that  discovers .class files
 * that are on the classpath in folders or .jar files.
 * Note .class files may contain java classes, interfaces and enums.
 * Use the SourceFileParser in this package to discover
 * if a particular .java file exists and if it has 
 * class, interface or enum content.
 * 
 * @author scott
 *
 */
public class PackageDiscovery implements I_PackageDiscovery  {
  public static Set<String> findTopPackages(Set<String> names) {
    Set<String> toRet = new HashSet<String>(names);
    
    Iterator<String> it = toRet.iterator();
    while (it.hasNext()) {
      String name = it.next();
      Set<String> copies = new HashSet<String>(names);
      boolean remove = false;
      for (String cn: copies) {
        if (!name.equals(cn)) {
          if (name.indexOf(cn + ".") == 0 ) {
            //cn is top, remove name
            remove = true;
          }
        }
      }
      if (remove) {
        it.remove();
      }
    }
    return toRet;
  }
	private String packageName;
	/**
	 * the full class name including the package
	 * includes all of the $1, $2 inner classes.
	 */
	private List<String> classNames = new ArrayList<String>();
	private List<I_PackageDiscovery> subpackages = new ArrayList<I_PackageDiscovery>();
	private ClassLoader classLoader;
	/**
	 * either loading from a jar or the file system
	 */
	private boolean jar = false;
	
	public PackageDiscovery() {}
	
	public PackageDiscovery(I_PackageDiscovery pkg) {
		setPackageName(pkg.getPackageName());
		setClassNames(pkg.getClassNames());
		setSubpackages(pkg.getSubPackages());
	}
	
	public PackageDiscovery(String pkgName) throws IOException {
		setPackageName(pkgName);
		
		classLoader = getClassLoader(null);
		classNames = getPackageClasses(pkgName);
		
	}
	/**
	 * this should never be using the cached class loader
	 * @param cl
	 * @return
	 */
	public ClassLoader getClassLoader(ClassLoader cl) {
		if (cl == null) { 
			cl = this.getClass().getClassLoader();
		}
		Class<?>[] interfaces = cl.getClass().getInterfaces();
		Set<String> interNames = new HashSet<String>();
		for (int i = 0; i < interfaces.length; i++) {
			interNames.add(interfaces[i].getName());
		}
		if (interNames.contains(I_CachedClassBytesClassLoader.class.getName())) {
			return cl.getParent();
		}
		return cl;
	}
	
	public PackageDiscovery(String pkgName, ClassLoader pClassLoader) throws IOException {
		setPackageName(pkgName);
		classLoader = pClassLoader;
		classNames = getPackageClasses(pkgName);
	}
	
	private List<String> getSubPackages(String pkg, Set<String> pkgs) throws IOException {
		List<String> toRet = new ArrayList<String>();
		
		for (String subPackName: pkgs) {
			if (subPackName.contains(pkg)) {
				if (!subPackName.equals(pkg)) {
				//make sure it's a direct sub package
					String simpleName = subPackName.substring(pkg.length() + 1, subPackName.length());
					if (simpleName.indexOf(".") == -1) {
						toRet.add(subPackName);
					}
				}
			}
		}
		return toRet;
	}
	
	private List<String> getPackageClasses(String packageName) throws IOException {
	    ClassLoader classLoader =  Thread.currentThread().getContextClassLoader();
	    ArrayList<String> names = new ArrayList<String>();
	    Set<String> subPackages = new HashSet<String>();
	    
	    String packageNameAsFile = packageName.replace(".", "/");
	    URL packageURL = classLoader.getResource(packageNameAsFile);
	    if (packageURL == null) {
	    	//could be a jse package?
	    	//although it has no way to look up the classes either....
	    	return names;
	    }
	    if ("file".equals(packageURL.getProtocol())) {
	    	File file = new File(packageURL.getFile());
	    	File [] files = file.listFiles();
	    	for (File f: files) {
	    		if (f.isDirectory()) {
	    			findSubPackages(packageName, subPackages, f);
	    		} else {
		    		String fileName = f.getName();
		    		int classIndex = fileName.indexOf(".class");
		    		if (classIndex != -1) {
		    			fileName = fileName.substring(0, classIndex);
		    			names.add(packageName + "." + fileName);
		    		}
	    		}
	    	}
	    } else if ("jar".equals(packageURL.getProtocol())) {
	    	jar = true;
	    	
	    	String jarFileName;
	        Enumeration<JarEntry> jarEntries;
	        String entryName;

	        // build jar file name, then loop through zipped entries
	        jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
	        jarFileName = jarFileName.substring(5,jarFileName.indexOf("!"));
	        
	        //hmm eclipse is flagging this next line but
	        // I do close jf after the while block
	        JarFile jf = new JarFile(jarFileName);
	        jarEntries = jf.entries();
	        while(jarEntries.hasMoreElements()){
	            entryName = jarEntries.nextElement().getName();
	            if (entryName.contains(".class")) {
	            	if(entryName.startsWith(packageNameAsFile) && entryName.length()>packageNameAsFile.length()+5){
	            		entryName = entryName.replace("/", ".");
	            		entryName = entryName.substring(0, entryName.length() - 6);
	            		String className = entryName.substring(packageName.length() + 1, entryName.length());
	            		if (!className.contains(".")) {
	            			try {
	            				Class<?> c = Class.forName(entryName, true, classLoader);
								Package pkg = c.getPackage();
								addPackages(subPackages, pkg.getName());
	            				names.add(entryName);
	            			} catch (ClassNotFoundException x) {
	            				throw new IOException(x);
	            			}
	            			
	            		}
		            }
	            }
	        }
	        jf.close();
	    }
	    
	    List<String> topSubs = getSubPackages(packageName, subPackages);
	    for (String topSub: topSubs) {
	    	this.subpackages.add(new PackageDiscovery(topSub));
	    }
	    return names;
	}

	private void findSubPackages(String packageName, Set<String> subPackages,
			File file) throws IOException {
		int classIndex;
		File [] files = file.listFiles();
		for (File j: files) {
			if (j.isDirectory()) {
				findSubPackages(packageName + "." + file.getName(), subPackages, j);
			} else {
				String fileName = j.getName();
				classIndex = fileName.indexOf(".class");
				if (classIndex != -1) {
					String className = packageName + "." + file.getName() + "." + 
								fileName.substring(0, classIndex);
					try {
						Class<?> c = Class.forName(className, true, classLoader);
						Package pkg = c.getPackage();
						addPackages(subPackages, pkg.getName());
					} catch (ClassNotFoundException e) {
						throw new IOException("problem loading file " + 
								file.getAbsolutePath() + File.separator + fileName + " with name " + className +
								" and class laoder " + classLoader,e);
					}
				}
			}
		}
	}

	private void addPackages(Set<String> subPackages, String pkgName) {
		if (!subPackages.contains(pkgName)) {
			subPackages.add(pkgName);
			int lastDot = pkgName.lastIndexOf(".");
			if (lastDot != -1) {
				pkgName = pkgName.substring(0, lastDot);
				addPackages(subPackages, pkgName);
			}
		}
	}
    
	/* (non-Javadoc)
   * @see org.adligo.tests4j.run.discovery.I_PackageDiscovery#getPackageName()
   */
	@Override
  public String getPackageName() {
		return packageName;
	}
	/* (non-Javadoc)
   * @see org.adligo.tests4j.run.discovery.I_PackageDiscovery#getClassNames()
   */
	@Override
  public List<String> getClassNames() {
		return classNames;
	}
	/* (non-Javadoc)
   * @see org.adligo.tests4j.run.discovery.I_PackageDiscovery#getSubPackages()
   */
	@Override
  public List<I_PackageDiscovery> getSubPackages() {
		return subpackages;
	}
	private void setPackageName(String name) {
		this.packageName = name;
	}
	private void setClassNames(List<String> p) {
		classNames.clear();
		classNames.addAll(p);
	}
	
	private void setSubpackages(List<? extends I_PackageDiscovery> p) {
		subpackages.clear();
		for (I_PackageDiscovery pkg: p) {
			subpackages.add(new PackageDiscovery(pkg));
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PackageDiscovery other = (PackageDiscovery) obj;
		if (packageName == null) {
			if (other.packageName != null)
				return false;
		} else if (!packageName.equals(other.packageName))
			return false;
		return true;
	}

	public boolean isJar() {
		return jar;
	}
	
	/* (non-Javadoc)
   * @see org.adligo.tests4j.run.discovery.I_PackageDiscovery#getClassCount()
   */
	@Override
  public int getClassCount() {
		int toRet = 0;
		for (I_PackageDiscovery pd: subpackages) {
			toRet = toRet + pd.getClassCount();
		}
		toRet = toRet + classNames.size();
		return toRet;
	}
}
