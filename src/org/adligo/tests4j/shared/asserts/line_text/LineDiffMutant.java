package org.adligo.tests4j.shared.asserts.line_text;

import org.adligo.tests4j.shared.common.ClassMethods;

public class LineDiffMutant implements I_LineDiff {
	public static final String LINE_NUMBERS_MUST_BE_NEGATIVE_ONE_OR_GREATER = "LineDiff requires line numbers of -1 or greater.";
	private LineDiffType type;
	/**
	 * -1 or greater
	 * -1 means it came before the expected lines
	 */
	private int expectedLineNbr;
	private Integer actualLineNbr;
	private I_DiffIndexesPair indexes;
	
	public LineDiffMutant() {}
	
	public LineDiffMutant(I_LineDiff p) {
		I_LineDiffType pType = p.getType();
		if  (pType != null) {
			//copy between classloaders
			type = LineDiffType.get(pType);
		}
		setExpectedLineNbr(p.getExpectedLineNbr());
		setActualLineNbr(p.getActualLineNbr());
		indexes = p.getIndexes();
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_LineDiff#getType()
	 */
	@Override
	public I_LineDiffType getType() {
		return type;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_LineDiff#getExampleLineNbr()
	 */
	@Override
	public int getExpectedLineNbr() {
		return expectedLineNbr;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_LineDiff#getActualLineNbr()
	 */
	@Override
	public Integer getActualLineNbr() {
		return actualLineNbr;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.line_text.I_LineDiff#getIndexes()
	 */
	@Override
	public I_DiffIndexesPair getIndexes() {
		return indexes;
	}
	
	public void setType(I_LineDiffType type) {
		this.type = LineDiffType.get(type);
	}
	
	public void setExpectedLineNbr(int p) {
		if (p < -1) {
			throw new IllegalArgumentException(LINE_NUMBERS_MUST_BE_NEGATIVE_ONE_OR_GREATER);
		}
		expectedLineNbr = p;
	}
	
	public void setActualLineNbr(Integer p) {
		if (p != null) {
			if (p < -1) {
				throw new IllegalArgumentException(LINE_NUMBERS_MUST_BE_NEGATIVE_ONE_OR_GREATER);
			}
		}
		actualLineNbr = p;
	}
	
	public void setIndexes(I_DiffIndexesPair indexes) {
		this.indexes = indexes;
	}

	@Override
	public int compareTo(I_LineDiff o) {
		int baseResult = expectedLineNbr - o.getExpectedLineNbr();
		if (baseResult != 0) {
			return baseResult;
		}
		if (actualLineNbr == null) {
			return 0;
		}
		return actualLineNbr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((actualLineNbr == null) ? 0 : actualLineNbr.hashCode());
		result = prime * result + expectedLineNbr;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		try {
			I_LineDiff other = (I_LineDiff) obj;
			if (actualLineNbr == null) {
				if (other.getActualLineNbr() != null)
					return false;
			} else if (!actualLineNbr.equals(other.getActualLineNbr()))
				return false;
			if (expectedLineNbr != other.getExpectedLineNbr())
				return false;
			if (type != other.getType())
				return false;
		} catch (ClassCastException x) {
			//do nothing, GWT doesn't have instance of
		}
		return true;
	}

	@Override
	public String toString() {
		return toString(LineDiffMutant.class);
	}
	
	public String toString(Class<?> c) {
		return ClassMethods.getSimpleName(c) + " [type=" + type + ", exampleLineNbr="
				+ expectedLineNbr + ", actualLineNbr=" + actualLineNbr + "]";
	}
}
