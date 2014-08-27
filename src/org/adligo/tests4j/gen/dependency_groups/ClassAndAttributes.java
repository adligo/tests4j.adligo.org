package org.adligo.tests4j.gen.dependency_groups;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.adligo.tests4j.models.shared.dependency.ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.ClassAttributesMutant;
import org.adligo.tests4j.models.shared.dependency.FieldSignature;
import org.adligo.tests4j.models.shared.dependency.I_ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.MethodSignature;

public class ClassAndAttributes {
	private I_ClassAttributes attributes;
	private Class<?> clazz;
	
	public ClassAndAttributes(Class<?> c) {
		attributes = toAttributes(c);
		clazz = c;
	}
	private I_ClassAttributes toAttributes(Class<?> c) {
		ClassAttributesMutant cam = new ClassAttributesMutant();
		cam.setClassName(c.getName());
		
		Field [] fields = c.getFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			cam.addField(new FieldSignature(f.getName() , f.getType().getName()));
		}
		
		Constructor<?> [] constructors = c.getConstructors();
		for (int i = 0; i < constructors.length; i++) {
			Constructor<?> f = constructors[i];
			cam.addMethod(new MethodSignature("<init>", getClassNames(f.getParameterTypes())));
		}
		
		Method [] methods = c.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method f = methods[i];
			cam.addMethod(new MethodSignature(f.getName(), getClassNames(f.getParameterTypes()),
					f.getReturnType().getName()));
		}
		return new ClassAttributes(cam);
	}
	
	
	private String [] getClassNames(Class<?> [] params) {
		String [] toRet = new String[params.length];
		for (int i = 0; i < params.length; i++) {
			toRet[i] = params[i].getName();
		}
		return toRet;
	}
	public I_ClassAttributes getAttributes() {
		return attributes;
	}
	public Class<?> getClazz() {
		return clazz;
	}
	public void setAttributes(I_ClassAttributes attributes) {
		this.attributes = attributes;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
}
