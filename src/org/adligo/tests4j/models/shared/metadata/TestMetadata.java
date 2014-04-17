package org.adligo.tests4j.models.shared.metadata;

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

	public boolean isSkipped() {
		return mutant.isSkipped();
	}

	public int hashCode() {
		return mutant.hashCode();
	}

	public boolean equals(Object obj) {
		return mutant.equals(obj);
	}
}
