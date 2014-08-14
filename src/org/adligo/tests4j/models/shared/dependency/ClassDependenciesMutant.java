package org.adligo.tests4j.models.shared.dependency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * a model like class for accumulating class dependencies
 * @author scott
 *
 */
public class ClassDependenciesMutant extends ClassCircularReferencesMutant implements I_ClassDependencies {
	private Map<String,DependencyMutant> dependencies = new HashMap<String, DependencyMutant>();
	
	public ClassDependenciesMutant() {}
	
	public ClassDependenciesMutant(I_ClassDependencies c) {
		super(c);
		List<I_Dependency> deps = c.getDependencies();
		if (deps != null) {
			for (I_Dependency dep: deps) {
				dependencies.put(dep.getClassName(), new DependencyMutant(dep));
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.discovery.I_ClassDependencies#getDependency(java.lang.String)
	 */
	public DependencyMutant getDependency(String clazzName) {
		return dependencies.get(clazzName);
	}
	
	public void putDependency(DependencyMutant dm) {
		dependencies.put(dm.getClassName(), dm);
	}
	
	public void addDependency(I_Dependency dm) {
		String name = dm.getClassName();
		DependencyMutant mdm =  dependencies.get(name);
		
		
		if (mdm == null) {
			mdm = new DependencyMutant(dm);
			dependencies.put(name, mdm);
		} else {
			String sourceFileClassName = name;
			int di = sourceFileClassName.indexOf("$");
			if (di != -1) {
				sourceFileClassName = sourceFileClassName.substring(0, di);
			}
			String className = super.getClassName();
			if (className.indexOf(sourceFileClassName) != -1) {
				//its a reference from a innner class back to the parent source file class
				// or vice versa, so ignore it
			} else {
				int refs = dm.getReferences();
				
				for (int i = 0; i < refs; i++) {
					mdm.addReference();
				}
			}
		}
	}
	
	@Override
	public List<I_Dependency> getDependencies() {
		Collection<DependencyMutant> dms = dependencies.values();
		List<I_Dependency> toRet = new ArrayList<I_Dependency>(dms);
		Collections.sort(toRet);
		return toRet;
	}
	/**
	 * add a dependencies 
	 * from another class, to this one.
	 * 
	 * @param other
	 */
	public void add(I_ClassDependencies other) {
		String clazzName = other.getClassName();
		DependencyMutant dm = new DependencyMutant();
		dm.setClassName(clazzName);
		dm.addReference();
		addDependency(dm);
		
		List<I_Dependency> ods =  other.getDependencies();
		for (I_Dependency od: ods) {
			addDependency(od);
		}
	}
}
