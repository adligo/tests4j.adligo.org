package org.adligo.tests4j.models.shared.dependency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adligo.tests4j.models.shared.xml.I_XML_Builder;


public class ClassParentsLocal implements I_ClassParentsLocal {
	private Class<?> initial;
	private List<I_ClassParentsLocal> parents;
	private List<String> parentNames;
	private ClassAlias alias;
	
	public ClassParentsLocal(Class<?> pInital) {
		initial = pInital;
		alias = new ClassAlias(initial);
		setEmptyLists();
	}

	private void setEmptyLists() {
		parents = Collections.emptyList();
		parentNames = Collections.emptyList();
	}

	public ClassParentsLocal(I_ClassParentsLocal other) {
		initial = other.getTarget();
		alias = new ClassAlias(initial);
		List<I_ClassParentsLocal> ops = other.getParentsLocal();
		if (ops != null) {
			if (ops.size() >= 1) {
				parents = new ArrayList<I_ClassParentsLocal>();
				parentNames = new ArrayList<String>();
				for (I_ClassParentsLocal cp: ops) {
					parents.add(new ClassParentsLocal(cp));
					parentNames.add(cp.getName());
				}
				parents = Collections.unmodifiableList(parents);
				parentNames = Collections.unmodifiableList(parentNames);
			} else {
				setEmptyLists();
			}
		} else {
			setEmptyLists();
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
		if (parents.size() == 0) {
			return Collections.emptyList();
		}
		return  Collections.unmodifiableList(new ArrayList<I_ClassParents>(parents));
	}

	@Override
	public List<String> getParentNames() {
		return parentNames;
	}

	@Override
	public int compareTo(Object o) {
		return alias.compareTo(o);
	}

	@Override
	public void toXml(I_XML_Builder builder) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return ClassParentsLocalMutant.toString(this) + "]";
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
