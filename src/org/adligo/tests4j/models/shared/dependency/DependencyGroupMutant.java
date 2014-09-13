package org.adligo.tests4j.models.shared.dependency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DependencyGroupMutant implements I_DependencyGroup {
	private Map<String, ClassAttributesMutant> deps = new HashMap<String, ClassAttributesMutant>();

	public DependencyGroupMutant() {
		
	}
	
	public DependencyGroupMutant(I_DependencyGroup other) {
		setClassMethods(other.getClassAttributes());
	}

	@Override
	public Set<String> getClassNames() {
		// TODO Auto-generated method stub
		return deps.keySet();
	}

	@Override
	public Set<I_MethodSignature> getMethods(String className) {
		ClassAttributesMutant cmm = deps.get(className);
		if (cmm == null) {
			return Collections.emptySet();
		}
		return cmm.getMethods();
	}

	@Override
	public boolean isInGroup(String className, I_MethodSignature method) {
		ClassAttributesMutant cmm = deps.get(className);
		if (cmm == null) {
			return false;
		}
		Set<I_MethodSignature> methods = cmm.getMethods();
		return methods.contains(method);
	}
	
	@Override
	public List<I_ClassAttributes> getClassAttributes() {
		List<I_ClassAttributes> toRet = new ArrayList<I_ClassAttributes>();
		toRet.addAll(deps.values());
		return toRet;
	}
	
	public void setClassMethods(Collection<? extends I_ClassAttributes> cms) {
		if (cms != null) {
			deps.clear(); 
			for (I_ClassAttributes cm: cms) {
				addClassMethods(cm);
			}
		}
	}
	
	public void addClassMethods(I_ClassAttributes cm) {
		if (cm != null) {
			deps.put(cm.getName(), new ClassAttributesMutant(cm));
		}
	}
	
	public void intersect(I_DependencyGroup other) {
		Set<String> classes =  other.getClassNames();
		Set<String> depsClasses = new HashSet<String>(deps.keySet());
		depsClasses.removeAll(classes);
		//remove classes on in the other
		for (String clazz: depsClasses) {
			deps.remove(clazz);
		}
		
		Set<Entry<String, ClassAttributesMutant>> entires = deps.entrySet();
		Iterator<Entry<String,ClassAttributesMutant>> it = entires.iterator();
		while (it.hasNext()) {
			Entry<String,ClassAttributesMutant> entry = it.next();
			String className = entry.getKey();
			
			Set<I_MethodSignature> otherMethSigs =  other.getMethods(className);
			ClassAttributesMutant cam = entry.getValue();
			Set<I_MethodSignature> methSigs = new HashSet<I_MethodSignature>(cam.getMethods());
			methSigs.retainAll(otherMethSigs);
			cam.setMethods(methSigs);
			
			Set<I_FieldSignature> otherFieldSigs =  other.getFields(className);
			Set<I_FieldSignature> fieldSigs = new HashSet<I_FieldSignature>(cam.getFields());
			fieldSigs.retainAll(otherFieldSigs);
			cam.setFields(fieldSigs);
		}
	}

	@Override
	public Set<I_FieldSignature> getFields(String className) {
		ClassAttributesMutant cmm = deps.get(className);
		if (cmm == null) {
			return Collections.emptySet();
		}
		return cmm.getFields();
	}

	@Override
	public boolean isInGroup(String className, I_FieldSignature field) {
		ClassAttributesMutant cmm = deps.get(className);
		if (cmm == null) {
			return false;
		}
		Set<I_FieldSignature> fields = cmm.getFields();
		return fields.contains(field);
	}

	@Override
	public boolean isInGroup(String className) {
		//since this class check the field and method calls,
		// it doesn't allow the whole class
		return false;
	}
}
