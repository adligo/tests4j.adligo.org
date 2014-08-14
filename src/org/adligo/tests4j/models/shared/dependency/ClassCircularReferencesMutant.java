package org.adligo.tests4j.models.shared.dependency;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * a mutable implementation of {@link I_ClassCircluarReferences}
 * @author scott
 *
 */
public class ClassCircularReferencesMutant implements I_ClassCircluarReferences {
	private String className;
	private Set<String> circularReferences;
	
	public ClassCircularReferencesMutant() {
		
	}
	
	public ClassCircularReferencesMutant(I_ClassCircluarReferences p) {
		className = p.getClassName();
		setCircularReferences(p.getCircularReferences());
	}
	
	/**
	 * @see I_ClassCircluarReferences#hasCircularReferences()
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
	 * @see I_ClassCircluarReferences#getCircularReferences()
	 */
	@Override
	public Set<String> getCircularReferences() {
		if (circularReferences == null) {
			return Collections.emptySet();
		}
		return circularReferences;
	}
	
	public void setCircularReferences(Set<String> other) {
		if (other != null) {
			if (circularReferences == null) {
				circularReferences = new HashSet<String>();
			} else {
				circularReferences.clear();
			}
			for (String cr: other) {
				if (cr != null) {
					circularReferences.add(cr);
				}
			}
		}
	}

	public void addCircularReferences(String other) {
		if (other != null) {
			if (circularReferences == null) {
				circularReferences = new HashSet<String>();
			}
			circularReferences.add(other);
		}
	}
	
	/**
	 * @see I_ClassCircluarReferences#getClassName()
	 */
	@Override
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	@Override
	public String toString() {
		return toString(this) + "]";
	}
	
	public static String toString(I_ClassCircluarReferences p) {
		StringBuilder sb = new StringBuilder();
		sb.append(p.getClass().getSimpleName());
		sb.append(" [className=");
		sb.append(p.getClassName());
		if (p.hasCircularReferences()) {
			sb.append(", circularRefs=");
			sb.append(p.getCircularReferences());
		}
		return sb.toString();
	}
}
