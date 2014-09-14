package org.adligo.tests4j.models.shared.dependency;

import java.util.List;
import java.util.Set;

/**
 * this is a Dependency Group that delegates to another 
 * dependency group, usage is intended to be 
 * extension.
 * 
 * @author scott
 *
 */
public class DependencyGroupBaseDelegate implements I_DependencyGroup {
	private I_DependencyGroup delegate;

	public Set<String> getClassNames() {
		return delegate.getClassNames();
	}

	public List<I_ClassAttributes> getClassAttributes() {
		return delegate.getClassAttributes();
	}

	public Set<I_MethodSignature> getMethods(String className) {
		return delegate.getMethods(className);
	}

	public Set<I_FieldSignature> getFields(String className) {
		return delegate.getFields(className);
	}

	public boolean isInGroup(String className, I_MethodSignature method) {
		return delegate.isInGroup(className, method);
	}

	public boolean isInGroup(String className, I_FieldSignature method) {
		return delegate.isInGroup(className, method);
	}

	protected I_DependencyGroup getDelegate() {
		return delegate;
	}

	protected void setDelegate(I_DependencyGroup delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean isInGroup(String className) {
		return delegate.isInGroup(className);
	}

	@Override
	public Set<String> getSubGroupNames() {
		return delegate.getSubGroupNames();
	}
	
}
