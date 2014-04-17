package org.adligo.tests4j.models.shared.metadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	private void cloneCollections(I_TrialMetadata p) {
		tests = new ArrayList<I_TestMetadata>();
		List<? extends I_TestMetadata> oTests = p.getTests();
		for (I_TestMetadata test: oTests){
			tests.add(new TestMetadata(test));
		}
		tests = Collections.unmodifiableList(tests);
	}

	public String getTrialName() {
		return mutant.getTrialName();
	}

	public Long getTimeout() {
		return mutant.getTimeout();
	}

	public boolean isSkipped() {
		return mutant.isSkipped();
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

	public int getSkippedTestCount() {
		return mutant.getSkippedTestCount();
	}

	public boolean equals(Object obj) {
		return mutant.equals(obj);
	}
}
