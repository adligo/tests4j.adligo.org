package org.adligo.tests4j.models.shared.metadata;

public class TestRunInfoMutant implements I_AbstractTestRunInfo {
	private boolean recordedCoverage = false;
	private TrialMetadataMutant trialMetadata;
	private long uniqueAssertions;
	private long assertions;
	
	public TestRunInfoMutant() {}
	
	public TestRunInfoMutant(I_AbstractTestRunInfo p) {
		recordedCoverage = p.hasRecordedCoverage();
		trialMetadata = new TrialMetadataMutant(p.getTrialMetadata());
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
	 * @see org.adligo.tests4j.models.shared.metadata.I_AbstractTestsRunInfo#getTrialMetadata()
	 */
	@Override
	public I_TrialMetadata getTrialMetadata() {
		return trialMetadata;
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
	public void setTrialMetadata(I_TrialMetadata p) {
		this.trialMetadata = new TrialMetadataMutant(p);
	}
	
	public void setTrialMetadata(TrialMetadataMutant p) {
		this.trialMetadata = p;
	}
	
	public void setUniqueAssertions(long uniqueAssertions) {
		this.uniqueAssertions = uniqueAssertions;
	}
	public void setAssertions(long assertions) {
		this.assertions = assertions;
	}
}
