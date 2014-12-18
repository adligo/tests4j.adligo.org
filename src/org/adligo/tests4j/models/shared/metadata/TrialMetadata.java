package org.adligo.tests4j.models.shared.metadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.adligo.tests4j.shared.common.I_TrialType;
import org.adligo.tests4j.shared.xml.I_XML_Builder;

public class TrialMetadata implements I_TrialMetadata {
	private TrialMetadataMutant mutant;
	private List<I_TestMetadata> tests;
	
	public TrialMetadata() {
		mutant = new TrialMetadataMutant();
	}
	
	public TrialMetadata(I_TrialMetadata p) {
		mutant = new TrialMetadataMutant(p);
		cloneCollections(p);
	}

	private synchronized void cloneCollections(I_TrialMetadata p) {
		tests = new ArrayList<I_TestMetadata>();
		List<? extends I_TestMetadata> oTests = p.getTests();
		Iterator<? extends I_TestMetadata> oti = oTests.iterator();
		while (oti.hasNext()) {
			tests.add(new TestMetadata(oti.next()));
		}
		tests = Collections.unmodifiableList(tests);
	}

	public String getTrialName() {
		return mutant.getTrialName();
	}

	public Long getTimeout() {
		return mutant.getTimeout();
	}

	public boolean isIgnored() {
		return mutant.isIgnored();
	}

	public String getBeforeTrialMethodName() {
		return mutant.getBeforeTrialMethodName();
	}

	public String getAfterTrialMethodName() {
		return mutant.getAfterTrialMethodName();
	}

	public int hashCode() {
		return mutant.hashCode();
	}

	public List<I_TestMetadata> getTests() {
		return tests;
	}

	public int getTestCount() {
		return mutant.getTestCount();
	}

	public int getIgnoredTestCount() {
		return mutant.getIgnoredTestCount();
	}

	public boolean equals(Object obj) {
		return mutant.equals(obj);
	}

	public I_TrialType getType() {
		return mutant.getType();
	}

	public String getTestedSourceFile() {
		return mutant.getTestedSourceFile();
	}

	public String getTestedPackage() {
		return mutant.getTestedPackage();
	}



	public Double getMinimumCodeCoverage() {
		return mutant.getMinimumCodeCoverage();
	}
}
