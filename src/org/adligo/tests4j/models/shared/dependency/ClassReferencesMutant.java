package org.adligo.tests4j.models.shared.dependency;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.common.StringMethods;

/**
 * This class simply tracks the names
 * of referenced classes. The counts of
 * references are not calculated here.
 * 
 * @author scott
 *
 */
public class ClassReferencesMutant  extends ClassCircularReferencesMutant implements I_ClassReferences {
	private String className;
	private Set<String> references = new HashSet<String>();
	
	public ClassReferencesMutant() {}
	
	public ClassReferencesMutant(I_ClassReferences other) {
		super(other);
		className = other.getClassName();
		setReferences(other.getReferences());
	}
	
	public ClassReferencesMutant(I_ClassDependencies deps) {
		super(deps);
		className = deps.getClassName();
		List<I_Dependency> dl = deps.getDependencies();
		for (I_Dependency d: dl) {
			String ref = d.getClassName();
			addReference(ref);
		}
	}
	
	public void addReference(String className) {
		if (!StringMethods.isEmpty(className)) {
			references.add(className);
		}
	}
	public void setReferences(Collection<String> refs) {
		if (refs != null) {
			references.clear();
			references.addAll(refs);
		}
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.discovery.I_ClassReferences#getReferencedCassNames()
	 */
	@Override
	public Set<String> getReferences() {
		return references;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.discovery.I_ClassReferences#getClassName()
	 */
	@Override
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	
}
