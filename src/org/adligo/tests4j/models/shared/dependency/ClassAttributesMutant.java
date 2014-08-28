package org.adligo.tests4j.models.shared.dependency;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class ClassAttributesMutant implements I_ClassAttributes {
	private String name;
	private Set<I_MethodSignature> methods = new TreeSet<I_MethodSignature>();
	private Set<I_FieldSignature> fields = new TreeSet<I_FieldSignature>();
	
	public ClassAttributesMutant() {}
	
	public ClassAttributesMutant(I_ClassAttributes cmm) {
		name = cmm.getName();
		setMethods(cmm.getMethods());
		setFields(cmm.getFields());
	}
	
	public String getName() {
		return name;
	}
	public void setName(String classNameIn) {
		name = classNameIn;
	}
	public Set<I_MethodSignature> getMethods() {
		return methods;
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
			
	public static String toString(I_ClassAttributes cm) {
		StringBuilder builder = new StringBuilder();
		builder.append(cm.getClass().getSimpleName());
		builder.append(" [name=");
		builder.append(cm.getName());
		Set<I_FieldSignature> fields = cm.getFields();
		if (fields.size() >= 1) {
			builder.append(", fields=");
			builder.append(fields);
		}
		Set<I_MethodSignature> sigs = cm.getMethods();
		if (sigs.size() >= 1) {
			builder.append(", methods=");
			builder.append(sigs);
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public Set<I_FieldSignature> getFields() {
		return fields;
	}
	
	public void setFields(Collection<I_FieldSignature> fieldsIn) {
		if (fieldsIn != null) {
			fields.clear();
			for (I_FieldSignature fs: fieldsIn) {
				addField(fs);
			}
		}
	}
	public void addField(I_FieldSignature fs) {
		if (fs != null) {
			fields.add(fs);
		}
	}

}
