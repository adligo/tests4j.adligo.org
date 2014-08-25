package org.adligo.tests4j.models.shared.dependency;

import org.adligo.tests4j.models.shared.common.StringMethods;

public class FieldSignature implements I_FieldSignature {
	public static final String FIELD_SIGNATURE_REQUIRES_A_CLASS_NAME = "FieldSignature requires a className";
	public static final String FIELD_SIGNATURE_REQUIRES_A_NAME = "FieldSignature requires a name.";
	
	private String className;
	private String name;
	
	public FieldSignature(String nameIn, String classNameIn) {
		if (StringMethods.isEmpty(nameIn)) {
			throw new IllegalArgumentException(FIELD_SIGNATURE_REQUIRES_A_NAME);
		}
		name = nameIn;
		if (StringMethods.isEmpty(classNameIn)) {
			throw new IllegalArgumentException(FIELD_SIGNATURE_REQUIRES_A_CLASS_NAME);
		}
		className = classNameIn;
	}
	
	public FieldSignature(I_FieldSignature p) {
		this(p.getName(), p.getClassName());
	}
	
	@Override
	public String getClassName() {
		return className;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		try {
			I_FieldSignature other = (I_FieldSignature) obj;
			if (className == null) {
				if (other.getClassName() != null)
					return false;
			} else if (!className.equals(other.getClassName()))
				return false;
			if (name == null) {
				if (other.getName() != null)
					return false;
			} else if (!name.equals(other.getName()))
				return false;
		} catch (ClassCastException x) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FieldSignature [");
		builder.append(className);
		builder.append(" ");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(I_FieldSignature o) {
		return new Integer(hashCode()).compareTo(o.hashCode());
	}

}
