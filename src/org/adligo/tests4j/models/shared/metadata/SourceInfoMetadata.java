package org.adligo.tests4j.models.shared.metadata;

/**
 * immutable implementation of I_SourceInfo
 * @see I_SourceInfoMetadata
 * @author scott
 *
 */
public class SourceInfoMetadata implements I_SourceInfoMetadata {
	private SourceInfoMetadataMutant mutant;
	
	public SourceInfoMetadata() {
		mutant = new SourceInfoMetadataMutant();
	}
	
	public SourceInfoMetadata(I_SourceInfoMetadata p) {
		mutant = new SourceInfoMetadataMutant(p);
	}
	
	public String getName() {
		return mutant.getName();
	}
	public boolean hasClass() {
		return mutant.hasClass();
	}
	public boolean hasInterface() {
		return mutant.hasInterface();
	}
	public boolean hasEnum() {
		return mutant.hasEnum();
	}
	public boolean isAvailable() {
		return mutant.isAvailable();
	}
	public int hashCode() {
		return mutant.hashCode();
	}
	public boolean equals(Object obj) {
		return mutant.equals(obj);
	}
	
	public String toString() {
		return mutant.toString(SourceInfoMetadata.class);
	}
}
