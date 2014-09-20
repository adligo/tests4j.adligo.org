package org.adligo.tests4j.models.shared.dependency;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * a mutable implementation of {@link I_ClassParents}
 * which blocks all external mutation of internal instances.
 * 
 * @author scott
 *
 */
public class ClassParentsLocalMutant implements I_ClassParentsLocal {
	private Class<?> initial;
	private ClassAlias alias;
	private List<I_ClassParentsLocal> parents = new ArrayList<I_ClassParentsLocal>();
	private Set<String> currentParents = new HashSet<String>();
	
	public ClassParentsLocalMutant() {}
	
	public ClassParentsLocalMutant(Class<?> pInital) {
		initial = pInital;
		alias = new ClassAlias(initial);
	}

	public ClassParentsLocalMutant(I_ClassParentsLocal other) {
		initial = other.getTarget();
		alias = new ClassAlias(initial);
		List<I_ClassParents> ops = other.getParents();
		if (ops != null) {
			for (I_ClassParents cp: ops) {
				addParent((I_ClassParentsLocal) cp);
			}
		}
	}
	
	@Override
	public String getName() {
		return initial.getName();
	}

	@Override
	public Class<?> getTarget() {
		return initial;
	}

	@Override
	public List<I_ClassParents> getParents() {
		return new ArrayList<I_ClassParents>(parents);
	}

	@Override
	public List<String> getParentNames() {
		List<String> toRet = new ArrayList<String>();
		for (I_ClassParents cpm: parents) {
			toRet.add(cpm.getName());
		}
		return toRet;
	}
	
	/**
	 * this is a recursive method to add the parent's parents first
	 * and then the children.
	 * 
	 * @param parent
	 */
	public void addParent(I_ClassParentsLocal parent) {
		if (!currentParents.contains(parent.getName())) {
			List<I_ClassParentsLocal> parentParents =  parent.getParentsLocal();
			for (I_ClassParentsLocal pp: parentParents) {
				addParent(pp);
			}
			//allow circular dependency in package
			parents.add(new ClassParentsLocal(parent));
			currentParents.add(parent.getName());
		}
	}

	@Override
	public int compareTo(Object o) {
		return alias.compareTo(o);
	}
	
	@Override
	public String toString() {
		return toString(this) + "]";
	}

	public static String toString(I_ClassParents p) {
		StringBuilder sb = new StringBuilder();
		sb.append(p.getClass().getSimpleName());
		sb.append(" [name=");
		sb.append(p.getName());
		List<String> names = p.getParentNames();
		if (names != null) {
			if (names.size() >= 1) {
				sb.append(", parents=");
				sb.append(p.getParentNames());
			}
		}
		return sb.toString();
	}

	@Override
	public List<I_ClassParentsLocal> getParentsLocal() {
		return parents;
	}

	@Override
	public int hashCode() {
		return alias.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return alias.equals(obj);
	}
}
