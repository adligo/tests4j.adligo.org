package org.adligo.tests4j.models.shared.dependency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * a immutable implementation of {@link I_ClassDependencies}
 * @author scott
 *
 */
public class ClassDependenciesLocal extends ClassParentsLocal implements I_ClassDependenciesLocal {
	private Set<I_ClassAliasLocal> circularDependencies;
	private Set<I_ClassParentsLocal> dependencies;
	private List<I_ClassMethods> calls;
	
	public ClassDependenciesLocal(I_ClassParentsLocal p) {
		super(p);
		List<I_ClassParentsLocal> refs = p.getParentsLocal();
		if (refs != null && refs.size() >= 1) {
			dependencies = new HashSet<I_ClassParentsLocal>();
			for (I_ClassParentsLocal loc: refs) {
				dependencies.add(new ClassParentsLocal(loc));
			}
			dependencies = Collections.unmodifiableSet(dependencies);
		} else {
			dependencies = Collections.emptySet();
		}
	}
	
	public ClassDependenciesLocal(I_ClassDependenciesLocal p) {
		super(p);
		
		
		Set<I_ClassAliasLocal> circles = p.getCircularDependenciesLocal();
		if (circles != null && circles.size() >= 1) {
			circularDependencies = new HashSet<I_ClassAliasLocal>();
			for (I_ClassAliasLocal loc: circles) {
				circularDependencies.add(new ClassAliasLocal(loc));
			}
			circularDependencies = Collections.unmodifiableSet(circularDependencies);
		} else {
			circularDependencies = Collections.emptySet();
		}
		Set<I_ClassParentsLocal> refs = p.getDependenciesLocal();
		if (refs != null && refs.size() >= 1) {
			dependencies = new HashSet<I_ClassParentsLocal>();
			for (I_ClassParentsLocal loc: refs) {
				dependencies.add(new ClassParentsLocal(loc));
			}
			dependencies = Collections.unmodifiableSet(dependencies);
		} else {
			dependencies = Collections.emptySet();
		}
		List<I_ClassMethods> cs = p.getCalls();
		if (cs != null && cs.size() >= 1) {
			calls = new ArrayList<I_ClassMethods>();
			for (I_ClassMethods cm: cs) {
				calls.add(new ClassMethods(cm));
			}
			calls = Collections.unmodifiableList(calls);
		} else {
			calls = Collections.emptyList();
		}
	}


	public Set<I_ClassAlias> getCircularDependencies() {
		return Collections.unmodifiableSet(new HashSet<I_ClassAlias>(circularDependencies));
	}

	public Set<I_ClassAliasLocal> getCircularDependenciesLocal() {
		return circularDependencies;
	}
	
	@Override
	public boolean hasCircularDependencies() {
		if (circularDependencies.size() >= 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return ClassDependenciesLocalMutant.toString(this);
	}

	@Override
	public Set<I_ClassAlias> getDependencies() {
		if (dependencies.size() == 0) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(new HashSet<I_ClassAlias>(dependencies)) ;
	}


	@Override
	public Set<I_ClassParentsLocal> getDependenciesLocal() {
		return dependencies;
	}
	
	@Override
	public boolean hasDependencies() {
		if (dependencies == null) {
			return false;
		}
		if (dependencies.size() >= 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public Set<String> getDependencyNames() {
		if (dependencies == null || dependencies.size() == 0) {
			return Collections.emptySet();
		}
		Set<String> toRet = new HashSet<String>();
		for (I_ClassAliasLocal alias: dependencies) {
			toRet.add(alias.getName());
		}
		return toRet;
	}
	
	@Override
	public Set<String> getCircularDependenciesNames() {
		if (circularDependencies == null || circularDependencies.size() == 0) {
			return Collections.emptySet();
		}
		Set<String> toRet = new HashSet<String>();
		for (I_ClassAliasLocal alias: circularDependencies) {
			toRet.add(alias.getName());
		}
		return toRet;
	}

	@Override
	public boolean hasCalls() {
		if (calls.size() >= 1) {
			return true;
		}
		return false;
	}

	@Override
	public List<I_ClassMethods> getCalls() {
		return calls;
	}
}
