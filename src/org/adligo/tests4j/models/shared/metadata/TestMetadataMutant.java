package org.adligo.tests4j.models.shared.metadata;

import org.adligo.tests4j.shared.xml.I_XML_Builder;
import org.adligo.tests4j.shared.xml.XML_Parser;

public class TestMetadataMutant implements I_TestMetadata {
	private String testName_;
	private String useCaseName_;
	private Long timeout_;
	private boolean ignored_ = false;
	
	public TestMetadataMutant() {}
	
	public TestMetadataMutant(I_TestMetadata p) {
		testName_ = p.getTestName();
		timeout_ = p.getTimeout();
		ignored_ = p.isIgnored();
		useCaseName_ = p.getUseCaseName();
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TestMetadata#getTestName()
	 */
	@Override
	public String getTestName() {
		return testName_;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TestMetadata#getTimeout()
	 */
	@Override
	public Long getTimeout() {
		return timeout_;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TestMetadata#isIgnored()
	 */
	@Override
	public boolean isIgnored() {
		return ignored_;
	}
	public void setTestName(String testName) {
		this.testName_ = testName;
	}
	public void setTimeout(Long timeout) {
		this.timeout_ = timeout;
	}
	public void setIgnored(boolean pIgnroed) {
		this.ignored_ = pIgnroed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((testName_ == null) ? 0 : testName_.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		I_TestMetadata other = (I_TestMetadata) obj;
		if (testName_ == null) {
			if (other.getTestName() != null)
				return false;
		} else if (!testName_.equals(other.getTestName()))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return testName_;
	}

  public String getUseCaseName() {
    return useCaseName_;
  }

  public void setUseCaseName(String useCaseName) {
    this.useCaseName_ = useCaseName;
  }
	
}
