package org.adligo.tests4j.models.shared.dependency;

import java.util.ArrayList;
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
	private List<I_ClassAttributes> references;
	
	public ClassDependenciesLocalMutant(Class<?> c) {
		super(c);
	}
	
	public ClassDependenciesLocalMutant(I_ClassParentsLocal p) {
		super(p);
		List<I_ClassParentsLocal> cpus = p.getParentsLocal();
		setDependencies(cpus);
	}
	
	public ClassDependenciesLocalMutant(I_ClassDependenciesLocal p) {
		super(p);
		setCircularReferences(p.getCircularDependenciesLocal());
		setDependencies(p.getDependenciesLocal());
		setReferences(p.getReferences());
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
		if (p.hasReferences()) {
			List<I_ClassAttributes> refs =  p.getReferences();
			
			sb.append(", calls=[");
			boolean first = true;
			
			for (I_ClassAttributes loc: refs) {
				if (!first) {
					sb.append(",");
				}
				if (loc != null) {
					sb.append(loc.toString());
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
	
	public void setDependencies(Collection<I_ClassParentsLocal> p) {
		if (p != null) {
			if (dependencies != null) {
				dependencies.clear();
			}
			addDependencies(p);
		}
	}
	
	public void addDependencies(Collection<I_ClassParentsLocal> p) {
		if (p != null) {
			for (I_ClassParentsLocal cpu: p) {
				addDependency(cpu);
			}
		}
	}
	
	public void addDependency(I_ClassParentsLocal p) {
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

	public List<I_ClassAttributes> getReferences() {
		return references;
	}

	public void setReferences(List<I_ClassAttributes> callsIn) {
		if (callsIn != null) {
			if (references != null) {
				references.clear();
			}
			for (I_ClassAttributes classCalls: callsIn) {
				addCall(classCalls);
			}
		}
	}

	public void addCall(I_ClassAttributes classCalls) {
		if (classCalls != null) {
			if (references == null) {
				references = new ArrayList<I_ClassAttributes>();
			}
			references.add(classCalls);
		}
	}

	@Override
	public boolean hasReferences() {
		if (references == null) {
			return false;
		}
		if (references.size() >= 1) {
			return true;
		}
		return false;
	}
}
