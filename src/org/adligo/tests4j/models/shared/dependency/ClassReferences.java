package org.adligo.tests4j.models.shared.dependency;

import java.util.Collections;
import java.util.Set;

/**
 * immutable impl of I_ClassReferences
 * @author scott
 *
 */
public class ClassReferences extends ClassCircularReferences implements I_ClassReferences {
	
	/**
	 * really a final feild, but don't want to retype 
	 */
	private Set<String> references;
	private Set<String> circularReferences;
	
	public ClassReferences() {
		super();
		references = Collections.emptySet();
		circularReferences = Collections.emptySet();
	}
	
	public ClassReferences(I_ClassReferences other) {
		super(other);
		copy(other);
	}

	/**
	 * hmm if java could detect method delegation 
	 * from constructor to 
	 * methods which assign final fields
	 * 
	 * @param other
	 */
	private void copy(I_ClassReferences other) {
		references = Collections.unmodifiableSet(other.getReferences());
	}
	
	public ClassReferences(I_ClassDependencies other) {
		ClassReferencesMutant mutant = new ClassReferencesMutant(other);
		copy(mutant);
	}

	public Set<String> getReferences() {
		return references;
	}

}
