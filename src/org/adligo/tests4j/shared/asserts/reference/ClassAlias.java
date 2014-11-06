package org.adligo.tests4j.shared.asserts.reference;

import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.xml.I_XML_Builder;

/**
 * A immutable implementation of I_ClassAlias.
 * 
 * @author scott
 *
 */
public class ClassAlias implements I_ClassAlias {
	public static final String NO_NAME = "ClassAlias requires a name";
	private String name;
	
	public ClassAlias(Class<?> p) {
		name = p.getName();
	}
	
	public ClassAlias(String p) {
		name = p;
		if (StringMethods.isEmpty(name)) {
			throw new IllegalArgumentException(NO_NAME);
		}
	}
	
	@Override
	public int compareTo(Object o) {
		String oClassName = o.getClass().getName();
		switch (oClassName) {
			case "java.lang.String":
					return name.compareTo((String) o);
			case "java.lang.Class":
					return name.compareTo(((Class<?>) o).getName());
			default:
				I_ClassAlias other = (I_ClassAlias) o;
				return getName().compareTo(other.getName());
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		
		try {
			I_ClassAlias other = (I_ClassAlias) obj;
			return name.equals(other.getName());
		} catch (ClassCastException x) {
			//do nothing 
		}
		return false;
	}

	@Override
	public String toString() {
		return name;
	}
}