package org.adligo.tests4j.models.shared.association;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.shared.asserts.reference.ClassAliasLocal;
import org.adligo.tests4j.shared.asserts.reference.ClassAttributes;
import org.adligo.tests4j.shared.asserts.reference.I_ClassAlias;
import org.adligo.tests4j.shared.asserts.reference.I_ClassAliasLocal;
import org.adligo.tests4j.shared.asserts.reference.I_ClassAttributes;

/**
 * a immutable implementation of {@link I_ClassAssociations}
 * @author scott
 *
 */
public class ClassAssociationsLocal extends ClassParentsLocal implements I_ClassAssociationsLocal {
	private Set<I_ClassAliasLocal> circularDependencies;
	private Set<I_ClassParentsLocal> dependencies;
	private List<I_ClassAttributes> references;
	
	public ClassAssociationsLocal(I_ClassParentsLocal p) {
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
	
	public ClassAssociationsLocal(I_ClassAssociationsLocal p) {
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
		List<I_ClassAttributes> cs = p.getReferences();
		if (cs != null && cs.size() >= 1) {
			references = new ArrayList<I_ClassAttributes>();
			for (I_ClassAttributes cm: cs) {
				references.add(new ClassAttributes(cm));
			}
			references = Collections.unmodifiableList(references);
		} else {
			references = Collections.emptyList();
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
		return ClassAssociationsLocalMutant.toString(this);
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
	public boolean hasReferences() {
		if (references.size() >= 1) {
			return true;
		}
		return false;
	}

	@Override
	public List<I_ClassAttributes> getReferences() {
		return references;
	}
}
