package org.adligo.tests4j.models.shared.dependency;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NameOnlyDependencyGroup implements I_DependencyGroup {
	private Set<String> names_;

	public NameOnlyDependencyGroup(Collection<String> classNames) {
		names_ = Collections.unmodifiableSet(
				new HashSet<String>(classNames));
	}
	
	@Override
	public Set<String> getClassNames() {
		return names_;
	}

	@Override
	public List<I_ClassAttributes> getClassAttributes() {
		return Collections.emptyList();
	}

	@Override
	public Set<I_MethodSignature> getMethods(String className) {
		return Collections.emptySet();
	}

	@Override
	public Set<I_FieldSignature> getFields(String className) {
		return Collections.emptySet();
	}

	@Override
	public boolean isInGroup(String className, I_MethodSignature method) {
		return names_.contains(className);
	}

	@Override
	public boolean isInGroup(String className, I_FieldSignature method) {
		return names_.contains(className);
	}

	@Override
	public boolean isInGroup(String className) {
		return names_.contains(className);
	}
	
	
}
