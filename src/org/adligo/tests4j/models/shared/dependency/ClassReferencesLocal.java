package org.adligo.tests4j.models.shared.dependency;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.xml.I_XML_Builder;

/**
 * a immutable implementation of {@link I_ClassReferences}
 * @author scott
 *
 */
public class ClassReferencesLocal extends ClassParentsLocal implements I_ClassReferencesLocal {
	private Set<I_ClassAliasLocal> circularReferences;
	private Set<I_ClassParentsLocal> references;
	
	public ClassReferencesLocal(I_ClassParentsLocal p) {
		super(p);
		List<I_ClassParentsLocal> refs = p.getParentsLocal();
		if (refs != null && refs.size() >= 1) {
			references = new HashSet<I_ClassParentsLocal>();
			for (I_ClassParentsLocal loc: refs) {
				references.add(new ClassParentsLocal(loc));
			}
			references = Collections.unmodifiableSet(references);
		} else {
			references = Collections.emptySet();
		}
	}
	
	public ClassReferencesLocal(I_ClassReferencesLocal p) {
		super(p);
		
		
		Set<I_ClassAliasLocal> circles = p.getCircularReferencesLocal();
		if (circles != null && circles.size() >= 1) {
			circularReferences = new HashSet<I_ClassAliasLocal>();
			for (I_ClassAliasLocal loc: circles) {
				circularReferences.add(new ClassAliasLocal(loc));
			}
			circularReferences = Collections.unmodifiableSet(circularReferences);
		} else {
			circularReferences = Collections.emptySet();
		}
		Set<I_ClassParentsLocal> refs = p.getReferencesLocal();
		if (refs != null && refs.size() >= 1) {
			references = new HashSet<I_ClassParentsLocal>();
			for (I_ClassParentsLocal loc: refs) {
				references.add(new ClassParentsLocal(loc));
			}
			references = Collections.unmodifiableSet(references);
		} else {
			references = Collections.emptySet();
		}
	}


	public Set<I_ClassAlias> getCircularReferences() {
		return Collections.unmodifiableSet(new HashSet<I_ClassAlias>(circularReferences));
	}

	public Set<I_ClassAliasLocal> getCircularReferencesLocal() {
		return circularReferences;
	}
	
	@Override
	public boolean hasCircularReferences() {
		if (circularReferences.size() >= 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return ClassReferencesLocalMutant.toString(this);
	}


	@Override
	public void toXml(I_XML_Builder builder) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Set<I_ClassAlias> getReferences() {
		if (references.size() == 0) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(new HashSet<I_ClassAlias>(references)) ;
	}


	@Override
	public Set<I_ClassParentsLocal> getReferencesLocal() {
		return references;
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
