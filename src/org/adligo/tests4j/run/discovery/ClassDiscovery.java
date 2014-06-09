package org.adligo.tests4j.run.discovery;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class ClassDiscovery  {
	private String name;
	private List<String> classNames = new ArrayList<String>();
	private List<ClassDiscovery> subpackages = new ArrayList<ClassDiscovery>();
	/**
	 * either loading from a jar or the file system
	 */
	private boolean jar = false;
	
	public ClassDiscovery() {}
	
	public ClassDiscovery(ClassDiscovery pkg) {
		setName(pkg.getName());
		setClassNames(pkg.getClassNames());
		setSubpackages(pkg.getSubPackages());
	}
	
	public ClassDiscovery(String pkg) throws IOException {
		setName(pkg);
		classNames = getPackageClasses(pkg);
		
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
	            				Class<?> c = Class.forName(entryName);
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
	    
	    List<String> topSubs = getSubPackages(name, subPackages);
	    for (String topSub: topSubs) {
	    	this.subpackages.add(new ClassDiscovery(topSub));
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
						Class<?> c = Class.forName(className);
						Package pkg = c.getPackage();
						addPackages(subPackages, pkg.getName());
					} catch (ClassNotFoundException e) {
						throw new IOException("problem loading file " + 
								file.getAbsolutePath() + File.separator + fileName + " with name " + className,e);
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
    
	public String getName() {
		return name;
	}
	public List<String> getClassNames() {
		return classNames;
	}
	public List<ClassDiscovery> getSubPackages() {
		return subpackages;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setClassNames(List<String> p) {
		classNames.clear();
		classNames.addAll(p);
	}
	
	public void setSubpackages(List<? extends ClassDiscovery> p) {
		subpackages.clear();
		for (ClassDiscovery pkg: p) {
			subpackages.add(new ClassDiscovery(pkg));
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ClassDiscovery other = (ClassDiscovery) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public boolean isJar() {
		return jar;
	}
}
