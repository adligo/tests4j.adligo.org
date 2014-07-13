package org.adligo.tests4j.models.shared.metadata;

import org.adligo.tests4j.models.shared.xml.I_XML_Builder;

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

	@Override
	public void toXml(I_XML_Builder builder) {
		mutant.toXml(builder);
	}
}
