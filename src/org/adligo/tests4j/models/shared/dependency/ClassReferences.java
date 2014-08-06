package org.adligo.tests4j.models.shared.dependency;

import java.util.Collections;
import java.util.Set;

import org.adligo.tests4j.models.shared.common.StringMethods;

/**
 * immutable impl of I_ClassReferences
 * @author scott
 *
 */
public class ClassReferences implements I_ClassReferences {
	public static final String UNKNOWN_CLASS_NAME = "UnknownClassName in ClassReferences";
	/**
	 * really a final feild, but don't want to retype 
	 */
	private String className;
	/**
	 * really a final feild, but don't want to retype 
	 */
	private Set<String> references;
	
	public ClassReferences() {
		className = UNKNOWN_CLASS_NAME;
		references = Collections.emptySet();
	}
	
	public ClassReferences(I_ClassReferences other) {
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
		String inName = other.getClassName();
		if (StringMethods.isEmpty(inName)) {
			className = UNKNOWN_CLASS_NAME;
		} else {
			className = inName;
		}
		references = Collections.unmodifiableSet(other.getReferences());
	}
	
	public ClassReferences(I_ClassDependencies other) {
		ClassReferencesMutant mutant = new ClassReferencesMutant(other);
		copy(mutant);
	}

	public Set<String> getReferences() {
		return references;
	}

	public String getClassName() {
		return className;
	}
}
