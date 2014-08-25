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
		setClassMethods(other.getClassMethods());
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
	public Collection<I_ClassAttributes> getClassMethods() {
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
			deps.put(cm.getClassName(), new ClassAttributesMutant(cm));
		}
	}
	
	public void intersect(I_DependencyGroup other) {
		Set<String> classes =  other.getClassNames();
		Set<String> depsClasses = new HashSet<String>(deps.keySet());
		depsClasses.retainAll(classes);
		
		Set<Entry<String, ClassAttributesMutant>> entires = deps.entrySet();
		Iterator<Entry<String,ClassAttributesMutant>> it = entires.iterator();
		while (it.hasNext()) {
			Entry<String,ClassAttributesMutant> entry = it.next();
			String className = entry.getKey();
			if ( !depsClasses.contains(className)) {
				it.remove();
			} else {
				Set<I_MethodSignature> methSigs =  other.getMethods(className);
				 
			}
		}
	}
}
