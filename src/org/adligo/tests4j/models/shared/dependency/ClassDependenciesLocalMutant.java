package org.adligo.tests4j.models.shared.dependency;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.xml.I_XML_Builder;

/**
 * a mutable implementation of {@link I_ClassDependencies}
 * @author scott
 *
 */
public class ClassDependenciesLocalMutant extends ClassParentsLocalMutant 
	implements I_ClassDependenciesLocal {
	
	private Set<I_ClassAliasLocal> circularDependencies;
	private Set<I_ClassParentsLocal> dependencies;
	
	public ClassDependenciesLocalMutant(Class<?> c) {
		super(c);
	}
	
	public ClassDependenciesLocalMutant(I_ClassParentsLocal p) {
		super(p);
		List<I_ClassParentsLocal> cpus = p.getParentsLocal();
		setReferences(cpus);
	}
	
	public ClassDependenciesLocalMutant(I_ClassDependenciesLocal p) {
		super(p);
		setCircularReferences(p.getCircularDependenciesLocal());
		setReferences(p.getDependenciesLocal());
	}
	
	/**
	 * @see I_ClassDependencies#hasCircularDependencies()
	 */
	@Override
	public boolean hasCircularDependencies() {
		if (circularDependencies == null) {
			return false;
		}
		if (circularDependencies.size() >= 1) {
			return true;
		}
		return false;
	}
	/**
	 * @see I_ClassDependencies#getCircularDependencies()
	 */
	@Override
	public Set<I_ClassAlias> getCircularDependencies() {
		if (circularDependencies == null) {
			return Collections.emptySet();
		}
		return new HashSet<I_ClassAlias>(circularDependencies);
	}
	
	/**
	 * @see I_ClassDependenciesLocal#getCircularDependenciesLocal()
	 */
	@Override
	public Set<I_ClassAliasLocal> getCircularDependenciesLocal() {
		if (circularDependencies == null) {
			return Collections.emptySet();
		}
		return circularDependencies;
	}
	
	public void setCircularReferences(Collection<I_ClassAliasLocal> other) {
		if (other != null) {
			if (circularDependencies != null) {
				circularDependencies.clear();
			}
			for (I_ClassAliasLocal cr: other) {
				addCircularReferences(cr);
			}
		}
	}

	public void addCircularReferences(I_ClassAliasLocal other) {
		if (other != null) {
			if (circularDependencies == null) {
				circularDependencies = new HashSet<I_ClassAliasLocal>();
			}
			circularDependencies.add(new ClassAliasLocal(other));
		}
	}

	@Override
	public String toString() {
		return toString(this);
	}
	
	public static String toString(I_ClassDependenciesLocal p) {
		StringBuilder sb = new StringBuilder();
		sb.append(ClassParentsLocalMutant.toString(p));
		if (p.hasCircularDependencies()) {
			Set<I_ClassAliasLocal> refs =  p.getCircularDependenciesLocal();
			
			sb.append(", circularRefs=[");
			boolean first = true;
			
			for (I_ClassAliasLocal loc: refs) {
				if (!first) {
					sb.append(",");
				}
				if (loc != null) {
					sb.append(loc);
				} else {
					sb.append("null");
				}
				first = false;
			}
			sb.append("]");
		}
		if (p.hasDependencies()) {
			Set<I_ClassAlias> refs =  p.getDependencies();
			
			sb.append(", references=[");
			boolean first = true;
			
			for (I_ClassAlias loc: refs) {
				if (!first) {
					sb.append(",");
				}
				if (loc != null) {
					sb.append(loc.getName());
				} else {
					sb.append("null");
				}
				first = false;
			}
			sb.append("]");
		}
		sb.append("]");
		return sb.toString();
	}


	@Override
	public void toXml(I_XML_Builder builder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<I_ClassAlias> getDependencies() {
		if (dependencies == null) {
			return Collections.emptySet();
		}
		return new HashSet<I_ClassAlias>(dependencies);
	}

	@Override
	public Set<I_ClassParentsLocal> getDependenciesLocal() {
		return dependencies;
	}
	
	public void setReferences(Collection<I_ClassParentsLocal> p) {
		if (p != null) {
			if (dependencies != null) {
				dependencies.clear();
			}
			addReferences(p);
		}
	}
	
	public void addReferences(Collection<I_ClassParentsLocal> p) {
		if (p != null) {
			for (I_ClassParentsLocal cpu: p) {
				addReference(cpu);
			}
		}
	}
	
	public void addReference(I_ClassParentsLocal p) {
		if (p != null) {
			if (dependencies == null) {
				dependencies = new HashSet<I_ClassParentsLocal>();
			}
			dependencies.add(p);
		}
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
}