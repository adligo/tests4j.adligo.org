package org.adligo.tests4j.gen.dependency_groups;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

import org.adligo.tests4j.shared.asserts.dependency.ClassAttributes;
import org.adligo.tests4j.shared.asserts.dependency.ClassAttributesMutant;
import org.adligo.tests4j.shared.asserts.dependency.FieldSignature;
import org.adligo.tests4j.shared.asserts.dependency.I_ClassAttributes;
import org.adligo.tests4j.shared.asserts.dependency.I_FieldSignature;
import org.adligo.tests4j.shared.asserts.dependency.I_MethodSignature;
import org.adligo.tests4j.shared.asserts.dependency.MethodSignature;

/**
 * this should filter out the parent material
 * @author scott
 *
 */
public class ClassAndAttributes {
	private ClassAndAttributes parent_;
	private I_ClassAttributes parentAttribs_;
	private Class<?> parentClass_;
	private Set<I_FieldSignature> parentFieldSigs_ = null;
	private Set<I_MethodSignature> parentMethodSigs_ = null;
	private I_ClassAttributes attributes;
	private Class<?> clazz;
	private int methodCount_ = 0;
	private int methodAndConstructorCount_ = 0;
	
	public ClassAndAttributes(Class<?> c) {
		attributes = toAttributes(c);
		clazz = c;
		parentClass_ =  clazz.getSuperclass();
		if (parentClass_ != null) {
			parent_ = new ClassAndAttributes(parentClass_);
			parentAttribs_ = parent_.getAttributes();
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
				methodAndConstructorCount_++;
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
				methodAndConstructorCount_++;
				methodCount_++;
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
	public I_ClassAttributes getParentAttribs() {
		return parentAttribs_;
	}
	public ClassAndAttributes getParent() {
		return parent_;
	}
	
	public int getMethodsCount() {
		int toRet = methodAndConstructorCount_;
		ClassAndAttributes upP = parent_;
		while (upP != null) {
			toRet += upP.methodCount_;
			upP = upP.parent_;
		}
		return toRet;
	}
}
