package org.adligo.tests4j.models.shared.dependency;

import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.shared.common.ClassMethods;

/**
 * add ignroed packages like "java."
 * the trailing dot indicates .*!
 * 
 * @author scott
 *
 */
public class ClassFilterMutant implements I_ClassFilterModel {
	private Set<String> ignoredPackageNames = new HashSet<String>();
	private Set<String> ignoredClassNames = new HashSet<String>();
	private Set<String> learnedFilteredClasses = new HashSet<String>();
	
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
			learnedFilteredClasses.add(className);
			return true;
		}
		if (ignoredPackageNames != null) {
			for (String pkg: ignoredPackageNames) {
				if (className.indexOf(pkg) == 0) {
					learnedFilteredClasses.add(className);
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
		if (learnedFilteredClasses.contains(clazz.getName())) {
			return true;
		}
		if (clazz.isPrimitive()) {
			learnedFilteredClasses.add(clazz.getName());
			return true;
		}
		if (clazz.isArray()) {
			return isFilteredIn(clazz.getComponentType().getName());
		}
		String className = clazz.getName();
		if (Void.TYPE == clazz || Void.class == clazz) {
			learnedFilteredClasses.add(clazz.getName());
			return true;
		}
		return isFilteredIn(className);
	}
	
	/**
	 * {@link I_ClassFilter#isFiltered(String)}
	 */
	@Override 
	public boolean isFiltered(String className) {
		if (className == null) {
			return true;
		}
		if (learnedFilteredClasses.contains(className)) {
			return true;
		}
		
		
		if ("null".equals(className) || ClassMethods.isPrimitive(className)) {
			//visitTryCatchBlock(ReferenceTrackingMethodVisitor.java:114)
			learnedFilteredClasses.add(className);
			return true;
		}
	
		if (ClassMethods.isArray(className)) {
			className = ClassMethods.getArrayType(className);
			return isFiltered(className);
		}
		try {
			Class<?> c = Class.forName(className);
			return isFiltered(c);
		} catch (ClassNotFoundException x) {
			throw new RuntimeException("Class " + className + " not found.", x);
		}
	}
	
}
