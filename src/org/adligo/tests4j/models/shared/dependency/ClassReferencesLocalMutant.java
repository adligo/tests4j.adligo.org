package org.adligo.tests4j.models.shared.dependency;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.xml.I_XML_Builder;

/**
 * a mutable implementation of {@link I_ClassReferences}
 * @author scott
 *
 */
public class ClassReferencesLocalMutant extends ClassParentsLocalMutant 
	implements I_ClassReferencesLocal {
	
	private Set<I_ClassAliasLocal> circularReferences;
	private Set<I_ClassParentsLocal> references;
	
	public ClassReferencesLocalMutant(Class<?> c) {
		super(c);
	}
	
	public ClassReferencesLocalMutant(I_ClassParentsLocal p) {
		super(p);
		List<I_ClassParentsLocal> cpus = p.getParentsLocal();
		setReferences(cpus);
	}
	
	public ClassReferencesLocalMutant(I_ClassReferencesLocal p) {
		super(p);
		setCircularReferences(p.getCircularReferencesLocal());
		setReferences(p.getReferencesLocal());
	}
	
	/**
	 * @see I_ClassReferences#hasCircularReferences()
	 */
	@Override
	public boolean hasCircularReferences() {
		if (circularReferences == null) {
			return false;
		}
		if (circularReferences.size() >= 1) {
			return true;
		}
		return false;
	}
	/**
	 * @see I_ClassReferences#getCircularReferences()
	 */
	@Override
	public Set<I_ClassAlias> getCircularReferences() {
		if (circularReferences == null) {
			return Collections.emptySet();
		}
		return new HashSet<I_ClassAlias>(circularReferences);
	}
	
	/**
	 * @see I_ClassReferencesLocal#getCircularReferencesLocal()
	 */
	@Override
	public Set<I_ClassAliasLocal> getCircularReferencesLocal() {
		if (circularReferences == null) {
			return Collections.emptySet();
		}
		return circularReferences;
	}
	
	public void setCircularReferences(Collection<I_ClassAliasLocal> other) {
		if (other != null) {
			if (circularReferences != null) {
				circularReferences.clear();
			}
			for (I_ClassAliasLocal cr: other) {
				addCircularReferences(cr);
			}
		}
	}

	public void addCircularReferences(I_ClassAliasLocal other) {
		if (other != null) {
			if (circularReferences == null) {
				circularReferences = new HashSet<I_ClassAliasLocal>();
			}
			circularReferences.add(new ClassAliasLocal(other));
		}
	}

	@Override
	public String toString() {
		return toString(this);
	}
	
	public static String toString(I_ClassReferencesLocal p) {
		StringBuilder sb = new StringBuilder();
		sb.append(ClassParentsLocalMutant.toString(p));
		if (p.hasCircularReferences()) {
			Set<I_ClassAliasLocal> refs =  p.getCircularReferencesLocal();
			
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
		if (p.hasReferences()) {
			Set<I_ClassAlias> refs =  p.getReferences();
			
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
	public Set<I_ClassAlias> getReferences() {
		if (references == null) {
			return Collections.emptySet();
		}
		return new HashSet<I_ClassAlias>(references);
	}

	@Override
	public Set<I_ClassParentsLocal> getReferencesLocal() {
		return references;
	}
	
	public void setReferences(Collection<I_ClassParentsLocal> p) {
		if (p != null) {
			if (references != null) {
				references.clear();
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
			if (references == null) {
				references = new HashSet<I_ClassParentsLocal>();
			}
			references.add(p);
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

	@Override
	public Set<String> getReferenceNames() {
		if (references == null || references.size() == 0) {
			return Collections.emptySet();
		}
		Set<String> toRet = new HashSet<String>();
		for (I_ClassAliasLocal alias: references) {
			toRet.add(alias.getName());
		}
		return toRet;
	}
	
	@Override
	public Set<String> getCircularReferenceNames() {
		if (circularReferences == null || circularReferences.size() == 0) {
			return Collections.emptySet();
		}
		Set<String> toRet = new HashSet<String>();
		for (I_ClassAliasLocal alias: circularReferences) {
			toRet.add(alias.getName());
		}
		return toRet;
	}
}
