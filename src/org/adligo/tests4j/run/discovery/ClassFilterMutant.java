package org.adligo.tests4j.run.discovery;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ClassFilterMutant implements I_ClassFilterModel {
	private Set<String> ignoredPackageNames = new HashSet<String>(Collections.singleton("java."));
	private Set<String> ignoredClassNames = new HashSet<String>();
	
	public ClassFilterMutant() {}
	
	public ClassFilterMutant(I_ClassFilterModel other) {
		setIgnoredClassNames(other.getIgnoredClassNames());
		setIgnoredPackageNames(other.getIgnoredPackageNames());
	}
	
	public Set<String> getIgnoredPackageNames() {
		return ignoredPackageNames;
	}
	
	public Set<String> getIgnoredClassNames() {
		return ignoredClassNames;
	}
	
	public void setIgnoredPackageNames(Set<String> pIgnoredPackageNames) {
		if (pIgnoredPackageNames != null) {
			ignoredPackageNames.clear();
			ignoredPackageNames.addAll(pIgnoredPackageNames);
		}
	}
	
	public void setIgnoredClassNames(Set<String> pIgnoredClassNames) {
		if (pIgnoredClassNames != null) {
			ignoredClassNames.clear();
			ignoredClassNames.addAll(pIgnoredClassNames);
		}
	}

	private boolean isFilteredIn(String className) {
		int di = className.indexOf("$");
		if (di != -1) {
			//normalize to outer class from inner class
			className = className.substring(0, di);
		}
		if (ignoredClassNames.contains(className)) {
			return true;
		}
		if (ignoredPackageNames != null) {
			for (String pkg: ignoredPackageNames) {
				if (className.indexOf(pkg) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * {@link I_ClassFilter#isFiltered(Class<?>)}
	 */
	@Override
	public boolean isFiltered(Class<?> clazz) {
		if (clazz == null) {
			return true;
		}
		if (clazz.isPrimitive()) {
			return true;
		}
		if (clazz.isArray()) {
			return isFilteredIn(clazz.getComponentType().getName());
		}
		String className = clazz.getName();
		if (Void.TYPE == clazz || Void.class == clazz) {
			return true;
		}
		return isFilteredIn(className);
	}
	
	/**
	 * {@link I_ClassFilter#isFiltered(String)}
	 */
	@Override 
	public boolean isFiltered(String className) {
		if (className.length() == 1) {
			//ASM has a desc 'D' and 'V'
			return true;
		}
		if (className.indexOf("(") != -1) {
			//ASM has desc sometimes like ()
			// is NOT a letter or digit http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.8
			return true;
		}
		if (className.indexOf(")") != -1) {
			//ASM has desc sometimes like ()
			// is NOT a letter or digit http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.8
			return true;
		}
		try {
			Class<?> c = Class.forName(className);
			return isFiltered(c);
		} catch (ClassNotFoundException x) {
			throw new RuntimeException("Class " + className + " not found.", x);
		}
	}
	
}
