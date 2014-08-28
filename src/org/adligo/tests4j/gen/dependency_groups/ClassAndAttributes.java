package org.adligo.tests4j.gen.dependency_groups;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

import org.adligo.tests4j.models.shared.dependency.ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.ClassAttributesMutant;
import org.adligo.tests4j.models.shared.dependency.FieldSignature;
import org.adligo.tests4j.models.shared.dependency.I_ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.I_FieldSignature;
import org.adligo.tests4j.models.shared.dependency.I_MethodSignature;
import org.adligo.tests4j.models.shared.dependency.MethodSignature;

/**
 * this should filter out the parent material
 * @author scott
 *
 */
public class ClassAndAttributes {
	private I_ClassAttributes parentAttribs_;
	private Class<?> parentClass_;
	private Set<I_FieldSignature> parentFieldSigs_ = null;
	private Set<I_MethodSignature> parentMethodSigs_ = null;
	private I_ClassAttributes attributes;
	private Class<?> clazz;
	
	public ClassAndAttributes(Class<?> c) {
		attributes = toAttributes(c);
		clazz = c;
		parentClass_ =  clazz.getSuperclass();
		if (parentClass_ != null) {
			parentAttribs_ = new ClassAndAttributes(parentClass_).getAttributes();
			if (parentAttribs_ != null) {
				parentFieldSigs_ = parentAttribs_.getFields();
				parentMethodSigs_ = parentAttribs_.getMethods();
			}
		}
	}
	private I_ClassAttributes toAttributes(Class<?> c) {
		ClassAttributesMutant cam = new ClassAttributesMutant();
		cam.setName(c.getName());
		
		
		Field [] fields = c.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			if (Modifier.isPublic(f.getModifiers()) ) {
				FieldSignature fs = new FieldSignature(f.getName() , f.getType().getName());
				if (parentFieldSigs_ != null) {
					if (!parentFieldSigs_.contains(fs)) {
						cam.addField(fs);
					}
				} else {
					cam.addField(fs);
				}
			}
		}
		
		
		Constructor<?> [] constructors = c.getDeclaredConstructors();
		for (int i = 0; i < constructors.length; i++) {
			Constructor<?> f = constructors[i];
			if (Modifier.isPublic(f.getModifiers()) ) {
				MethodSignature ms = new MethodSignature("<init>", getClassNames(f.getParameterTypes()));
				if (parentMethodSigs_ != null) {
					if (!parentMethodSigs_.contains(ms)) {
						cam.addMethod(ms);
					}
				} else {
					cam.addMethod(ms);
				}
			}
		}
		
		Method [] methods = c.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method f = methods[i];
			if (Modifier.isPublic(f.getModifiers()) ) {
				MethodSignature ms =  new MethodSignature(f.getName(), 
						getClassNames(f.getParameterTypes()),
						f.getReturnType().getName());
				if (parentMethodSigs_ != null) {
					if (!parentMethodSigs_.contains(ms)) {
						cam.addMethod(ms);
					}
				} else {
					cam.addMethod(ms);
				}
			}
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
