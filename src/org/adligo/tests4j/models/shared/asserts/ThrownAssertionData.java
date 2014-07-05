package org.adligo.tests4j.models.shared.asserts;

import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.common.I_ThrownAssertionData;

/**
 * a immutable class to represent 
 * data about a thrown assertion.
 * @author scott
 *
 */
public class ThrownAssertionData implements I_ThrownAssertionData {
	private ThrownAssertionDataMutant mutant;
	
	public ThrownAssertionData() {
		mutant = new ThrownAssertionDataMutant();
	}
	
	public ThrownAssertionData(I_ThrownAssertionData p) {
		mutant = new ThrownAssertionDataMutant(p);
	}

	public Class<? extends Throwable> getExpectedThrowable() {
		return mutant.getExpectedThrowable();
	}

	public String getExpectedMessage() {
		return mutant.getExpectedMessage();
	}

	public Class<? extends Throwable> getActualThrowable() {
		return mutant.getActualThrowable();
	}

	public String getActualMessage() {
		return mutant.getActualMessage();
	}

	public int hashCode() {
		return mutant.hashCode();
	}

	public Set<String> getKeys() {
		return mutant.getKeys();
	}

	public Object getData(String key) {
		return mutant.getData(key);
	}

	public boolean equals(Object obj) {
		return mutant.equals(obj);
	}
}
