package org.adligo.tests4j.models.shared.metadata;

/**
 * mutable implementation of I_SourceFile 
 * @see I_SourceInfo
 * @author scott
 *
 */
public class SourceInfoMutant implements I_SourceInfo {
	private String name;
	private boolean available;
	private boolean hasClass;
	private boolean hasInterface;
	private boolean hasEnum;
	
	public SourceInfoMutant () {
	}
	
	public SourceInfoMutant (I_SourceInfo p) { 
		name = p.getName();
		available = p.isAvailable();
		hasClass = p.hasClass();
		hasInterface = p.hasInterface();
		hasEnum = p.hasEnum();
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_SourceFile#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_SourceFile#hasClass()
	 */
	@Override
	public boolean hasClass() {
		return hasClass;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_SourceFile#hasInterface()
	 */
	@Override
	public boolean hasInterface() {
		return hasInterface;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_SourceFile#hasEnum()
	 */
	@Override
	public boolean hasEnum() {
		return hasEnum;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setHasClass(Boolean hasClass) {
		this.hasClass = hasClass;
	}
	public void setHasInterface(Boolean hasInterface) {
		this.hasInterface = hasInterface;
	}
	public void setHasEnum(Boolean hasEnum) {
		this.hasEnum = hasEnum;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_SourceFile#isAvailable()
	 */
	@Override
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		try {
			I_SourceInfo other = (I_SourceInfo) obj;
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
	
}
