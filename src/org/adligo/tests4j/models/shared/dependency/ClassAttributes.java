package org.adligo.tests4j.models.shared.dependency;

import java.util.Collections;
import java.util.Set;

import org.adligo.tests4j.models.shared.common.StringMethods;

public class ClassAttributes implements I_ClassAttributes {
	public static final String REQUIRES_A_CLASS_NAME = ClassAttributes.class.getSimpleName() + " requires a className.";
	private String className;
	private Set<I_MethodSignature> methods;
	private Set<I_FieldSignature> fields;
	
	public ClassAttributes(I_ClassAttributes p) {
		ClassAttributesMutant cmm = new ClassAttributesMutant(p);
		className = cmm.getClassName();
		if (StringMethods.isEmpty(className)) {
			throw new IllegalArgumentException(REQUIRES_A_CLASS_NAME);
		}
		methods = Collections.unmodifiableSet(cmm.getMethods());
		fields = Collections.unmodifiableSet(cmm.getFields());
	}

	public String getClassName() {
		return className;
	}

	public Set<I_MethodSignature> getMethods() {
		return methods;
	}

	@Override
	public String toString() {
		return ClassAttributesMutant.toString(this);
	}

	@Override
	public Set<I_FieldSignature> getFields() {
		return fields;
	}
	
}
