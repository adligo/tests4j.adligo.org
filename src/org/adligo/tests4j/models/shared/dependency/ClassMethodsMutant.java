package org.adligo.tests4j.models.shared.dependency;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ClassMethodsMutant implements I_ClassMethods {
	private String className;
	private Set<I_MethodSignature> methods = new HashSet<I_MethodSignature>();
	
	public ClassMethodsMutant() {}
	
	public ClassMethodsMutant(I_ClassMethods cmm) {
		className = cmm.getClassName();
		setMethods(cmm.getMethods());
	}
	
	public String getClassName() {
		return className;
	}
	public Set<I_MethodSignature> getMethods() {
		return methods;
	}
	public void setClassName(String classNameIn) {
		className = classNameIn;
	}
	
	public void setMethods(Collection<? extends I_MethodSignature> methodsIn) {
		if (methodsIn != null) {
			methods.clear();
			for (I_MethodSignature ms: methodsIn) {
				addMethod(ms);
			}
		}
	}
	public void addMethod(I_MethodSignature ms) {
		if (ms != null) {
			methods.add(ms);
		}
	}

	@Override
	public String toString() {
		return toString(this);
	}
			
	public static String toString(I_ClassMethods cm) {
		StringBuilder builder = new StringBuilder();
		builder.append(cm.getClass().getSimpleName());
		builder.append(" [className=");
		builder.append(cm.getClassName());
		Set<I_MethodSignature> sigs = cm.getMethods();
		if (sigs.size() >= 1) {
			builder.append(", methods=");
			builder.append(cm.getMethods());
		}
		builder.append("]");
		return builder.toString();
	}

}
