package org.adligo.tests4j.models.shared.metadata;

public class TestMetadataMutant implements I_TestMetadata {
	private String testName;
	private Long timeout;
	private boolean skipped = false;
	
	public TestMetadataMutant() {}
	
	public TestMetadataMutant(I_TestMetadata p) {
		testName = p.getTestName();
		timeout = p.getTimeout();
		skipped = p.isSkipped();
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TestMetadata#getTestName()
	 */
	@Override
	public String getTestName() {
		return testName;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TestMetadata#getTimeout()
	 */
	@Override
	public Long getTimeout() {
		return timeout;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TestMetadata#isSkipped()
	 */
	@Override
	public boolean isSkipped() {
		return skipped;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}
	public void setSkipped(boolean skipped) {
		this.skipped = skipped;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (skipped ? 1231 : 1237);
		result = prime * result
				+ ((testName == null) ? 0 : testName.hashCode());
		result = prime * result + ((timeout == null) ? 0 : timeout.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		I_TestMetadata other = (I_TestMetadata) obj;
		if (skipped != other.isSkipped())
			return false;
		if (testName == null) {
			if (other.getTestName() != null)
				return false;
		} else if (!testName.equals(other.getTestName()))
			return false;
		if (timeout == null) {
			if (other.getTimeout() != null)
				return false;
		} else if (!timeout.equals(other.getTimeout()))
			return false;
		return true;
	}
	
}
