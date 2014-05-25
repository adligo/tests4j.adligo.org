package org.adligo.tests4j.models.shared.results.feedback;


public class TestsResultsMutant implements I_TestsResults {
	private boolean recordedCoverage = false;
	private long uniqueAssertions;
	private long assertions;
	
	public TestsResultsMutant() {}
	
	public TestsResultsMutant(I_TestsResults p) {
		recordedCoverage = p.hasRecordedCoverage();
		uniqueAssertions = p.getUniqueAssertions();
		assertions = p.getAssertions();
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_AbstractTestsRunInfo#isRecordedCoverage()
	 */
	@Override
	public boolean hasRecordedCoverage() {
		return recordedCoverage;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_AbstractTestsRunInfo#getUniqueAssertions()
	 */
	@Override
	public long getUniqueAssertions() {
		return uniqueAssertions;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_AbstractTestsRunInfo#getAssertions()
	 */
	@Override
	public long getAssertions() {
		return assertions;
	}
	public void setRecordedCoverage(boolean recordedCoverage) {
		this.recordedCoverage = recordedCoverage;
	}
	
	public void setUniqueAssertions(long uniqueAssertions) {
		this.uniqueAssertions = uniqueAssertions;
	}
	public void setAssertions(long assertions) {
		this.assertions = assertions;
	}
}
