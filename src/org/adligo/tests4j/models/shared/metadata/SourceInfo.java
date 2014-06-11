package org.adligo.tests4j.models.shared.metadata;

public class SourceInfo implements I_SourceInfo {
	private SourceInfoMutant mutant;
	
	public SourceInfo() {
		mutant = new SourceInfoMutant();
	}
	
	public SourceInfo(I_SourceInfo p) {
		mutant = new SourceInfoMutant(p);
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
}
