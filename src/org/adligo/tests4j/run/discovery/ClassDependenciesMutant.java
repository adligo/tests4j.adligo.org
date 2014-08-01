package org.adligo.tests4j.run.discovery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * a model like class for accumulating class dependencies
 * @author scott
 *
 */
public class ClassDependenciesMutant implements I_ClassDependencies {
	private String clazzName;
	private Map<String,DependencyMutant> dependencies = new HashMap<String, DependencyMutant>();
	
	public ClassDependenciesMutant() {}
	
	public ClassDependenciesMutant(I_ClassDependencies c) {
		clazzName = c.getClassName();
		List<I_Dependency> deps = c.getDependencies();
		if (deps != null) {
			for (I_Dependency dep: deps) {
				dependencies.put(dep.getClassName(), new DependencyMutant(dep));
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.discovery.I_ClassDependencies#getClazzName()
	 */
	@Override
	public String getClassName() {
		return clazzName;
	}
	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
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
			if (clazzName.indexOf(sourceFileClassName) != -1) {
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
		int maxRefs = 0;
		for (DependencyMutant dm: dms) {
			if (dm.getReferences() > maxRefs) {
				maxRefs = dm.getReferences();
			}
		}
		List<I_Dependency> toRet = new ArrayList<I_Dependency>();
		addByLevel(maxRefs, new ArrayList<DependencyMutant>(dms), toRet);
		return toRet;
	}
	
	protected void addByLevel(int level, List<DependencyMutant> clone, List<I_Dependency> toRet) {
		Iterator<DependencyMutant> it =  clone.iterator();
		while (it.hasNext()) {
			DependencyMutant dm = it.next();
			if (dm.getReferences() == level) {
				toRet.add(new Dependency(dm));
				it.remove();
			}
		}
		if (level >= 1) {
			addByLevel(level -1, clone, toRet);
		}
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
		dm.setClazzName(clazzName);
		dm.addReference();
		addDependency(dm);
		
		List<I_Dependency> ods =  other.getDependencies();
		for (I_Dependency od: ods) {
			addDependency(od);
		}
	}
}
