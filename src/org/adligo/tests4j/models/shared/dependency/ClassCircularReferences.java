package org.adligo.tests4j.models.shared.dependency;

import java.util.Collections;
import java.util.Set;

import org.adligo.tests4j.models.shared.common.StringMethods;

/**
 * a immutable implementation of {@link I_ClassCircluarReferences}
 * @author scott
 *
 */
public class ClassCircularReferences implements I_ClassCircluarReferences {
	public static final String UNKNOWN_CLASS_NAME = "UnknownClassName in ClassCircularReferences";

	private String className;
	private Set<String> circularReferences;
	
	public ClassCircularReferences() {
		className = UNKNOWN_CLASS_NAME;
		circularReferences = Collections.emptySet();
	}
	
	public ClassCircularReferences(I_ClassCircluarReferences p) {
		className = p.getClassName();
		if (StringMethods.isEmpty(className)) {
			throw new IllegalArgumentException(UNKNOWN_CLASS_NAME);
		}
		Set<String> circle = p.getCircularReferences();
		if (circle.size() >= 1) {
			//clean out nulls
			ClassCircularReferencesMutant mutant = new ClassCircularReferencesMutant(p);
			circularReferences = Collections.unmodifiableSet(mutant.getCircularReferences());
		} else {
			circularReferences = Collections.emptySet();
		}
	}

	public String getClassName() {
		return className;
	}

	public Set<String> getCircularReferences() {
		return circularReferences;
	}

	@Override
	public boolean hasCircularReferences() {
		if (circularReferences.size() >= 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return ClassCircularReferencesMutant.toString(this) + "]";
	}
}
