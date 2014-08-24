package org.adligo.tests4j.models.shared.dependency;

import java.util.Collections;
import java.util.Set;

import org.adligo.tests4j.models.shared.common.StringRoutines;

public class ClassMethods implements I_ClassMethods {
	public static final String REQUIRES_A_CLASS_NAME = ClassMethods.class.getSimpleName() + " requires a className.";
	private String className;
	private Set<I_MethodSignature> methods;
	
	public ClassMethods(I_ClassMethods p) {
		ClassMethodsMutant cmm = new ClassMethodsMutant(p);
		className = cmm.getClassName();
		if (StringRoutines.isEmpty(className)) {
			throw new IllegalArgumentException(REQUIRES_A_CLASS_NAME);
		}
		methods = Collections.unmodifiableSet(cmm.getMethods());
	}

	public String getClassName() {
		return className;
	}

	public Set<I_MethodSignature> getMethods() {
		return methods;
	}

	@Override
	public String toString() {
		return ClassMethodsMutant.toString(this);
	}
	
}
