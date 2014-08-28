package org.adligo.tests4j.models.shared.dependency;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DependencyGroup implements I_DependencyGroup {
	private Map<String, I_ClassAttributes> classMap;
	
	public DependencyGroup() {
		
	}
	
	public DependencyGroup(I_DependencyGroup other) {
		Collection<I_ClassAttributes> cms =  other.getClassMethods();
		
		Map<String, I_ClassAttributes> mClassMap = new TreeMap<String,I_ClassAttributes>();
		for (I_ClassAttributes cm: cms) {
			if (cm != null) {
				mClassMap.put(cm.getName(), new ClassAttributes(cm));
			}
		}
		classMap = Collections.unmodifiableMap(mClassMap);
	}
	
	@Override
	public Set<String> getClassNames() {
		return classMap.keySet();
	}

	@Override
	public Collection<I_ClassAttributes> getClassMethods() {
		return classMap.values();
	}

	@Override
	public Set<I_MethodSignature> getMethods(String className) {
		I_ClassAttributes ca =  classMap.get(className);
		if (ca == null) {
			return Collections.emptySet();
		}
		return ca.getMethods();
	}

	@Override
	public Set<I_FieldSignature> getFields(String className) {
		I_ClassAttributes ca =  classMap.get(className);
		if (ca == null) {
			return Collections.emptySet();
		}
		return ca.getFields();
	}

	@Override
	public boolean isInGroup(String className, I_MethodSignature method) {
		I_ClassAttributes ca =  classMap.get(className);
		if (ca == null) {
			return false;
		}
		Set<I_MethodSignature> methods =  ca.getMethods();
		if (methods.contains(method)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isInGroup(String className, I_FieldSignature method) {
		I_ClassAttributes ca =  classMap.get(className);
		if (ca == null) {
			return false;
		}
		Set<I_FieldSignature> fields =  ca.getFields();
		if (fields.contains(method)) {
			return true;
		}
		return false;
	}

}
