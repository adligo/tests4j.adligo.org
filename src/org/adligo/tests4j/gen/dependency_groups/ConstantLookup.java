package org.adligo.tests4j.gen.dependency_groups;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.dependency_groups.jse.I_PackageConstantLookup;
import org.adligo.tests4j.shared.common.ClassMethods;

public class ConstantLookup {
	private List<I_PackageConstantLookup> lookups_ = new ArrayList<I_PackageConstantLookup>();
	
	public String get(String javaName) {
		if ("void".equals(javaName)) {
			return null;
		}
		int arrays = ClassMethods.getArrays(javaName);
		javaName = javaName.substring(arrays, javaName.length());
		if (ClassMethods.isPrimitive(javaName)) {
			return "ClassMethods." + ClassMethods.getPrimitiveConstant(javaName);
		} else {
			for (I_PackageConstantLookup look: lookups_) {
				String result = look.getConstantName(javaName);
				if (result != null) {
					return look.getClass().getSimpleName() + "." + result;
				}
			}
		}
		return "\"" + javaName + "\"";
	}

	public List<I_PackageConstantLookup> getLookups() {
		return lookups_;
	}

	public void setLookups(List<I_PackageConstantLookup> lookups) {
		lookups_.clear();
		lookups_.addAll(lookups);
	}
	
	public void addLookups(I_PackageConstantLookup lookup) {
		lookups_.add(lookup);
	}
}
