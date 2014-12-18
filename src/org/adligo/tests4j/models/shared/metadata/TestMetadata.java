package org.adligo.tests4j.models.shared.metadata;

import org.adligo.tests4j.shared.xml.I_XML_Builder;

public class TestMetadata implements I_TestMetadata {
	private TestMetadataMutant mutant;
	
	public TestMetadata() {
		mutant = new TestMetadataMutant();
	}
	
	public TestMetadata(I_TestMetadata p) {
		mutant = new TestMetadataMutant(p);
	}

	public String getTestName() {
		return mutant.getTestName();
	}

	public Long getTimeout() {
		return mutant.getTimeout();
	}

	public boolean isIgnored() {
		return mutant.isIgnored();
	}

	public int hashCode() {
		return mutant.hashCode();
	}

	public boolean equals(Object obj) {
		return mutant.equals(obj);
	}

	public String toString() {
		return mutant.toString();
	}

  @Override
  public String getUseCaseName() {
    return mutant.getUseCaseName();
  }
}
