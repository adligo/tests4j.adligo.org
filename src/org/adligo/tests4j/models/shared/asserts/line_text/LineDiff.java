package org.adligo.tests4j.models.shared.asserts.line_text;


public class LineDiff implements I_LineDiff {
	private LineDiffMutant mutant;
	
	public LineDiff() {
		mutant = new LineDiffMutant();
	}
	public LineDiff(I_LineDiff p) {
		mutant = new LineDiffMutant(p);
	}
	public LineDiffType getType() {
		return mutant.getType();
	}
	public int getExpectedLineNbr() {
		return mutant.getExpectedLineNbr();
	}
	public Integer getActualLineNbr() {
		return mutant.getActualLineNbr();
	}
	public I_DiffIndexesPair getIndexes() {
		return mutant.getIndexes();
	}
	
	@Override
	public int compareTo(I_LineDiff o) {
		return mutant.compareTo(o);
	}
	public int hashCode() {
		return mutant.hashCode();
	}
	public boolean equals(Object obj) {
		return mutant.equals(obj);
	}
	
	@Override
	public String toString() {
		return mutant.toString(LineDiff.class);
	}
	
}
