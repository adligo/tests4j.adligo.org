package org.adligo.tests4j.models.shared.dependency;

public class ClassAliasLocal extends ClassAlias implements I_ClassAliasLocal {
	private Class<?> target;
	
	public ClassAliasLocal(Class<?> p) {
		super(p);
		target = p;
	}

	public ClassAliasLocal(I_ClassAliasLocal loc) {
		super(loc.getTarget());
		target = loc.getTarget();
	}
	
	@Override
	public Class<?> getTarget() {
		return target;
	}

}
