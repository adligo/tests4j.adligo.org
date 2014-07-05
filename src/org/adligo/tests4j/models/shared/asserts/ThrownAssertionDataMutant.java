package org.adligo.tests4j.models.shared.asserts;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.common.I_ThrownAssertionData;

/**
 * a mutable class to represent 
 * data for a thrown comparison.
 *  
 * @author scott
 *
 */
public class ThrownAssertionDataMutant implements I_ThrownAssertionData {
	private static Set<String> KEYS = getKeysStatic();
	
	private Class<? extends Throwable> expectedThrowable;
	private String expectedMessage;
	private Class<? extends Throwable> actualThrowable;
	private String actualMessage;
	
	public ThrownAssertionDataMutant() {}
	
	public ThrownAssertionDataMutant(I_ThrownAssertionData p) {
		expectedThrowable = p.getExpectedThrowable();
		expectedMessage = p.getExpectedMessage();
		actualThrowable = p.getActualThrowable();
		actualMessage = p.getActualMessage();
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.I_ThrownAssertionData#getExpectedThrowable()
	 */
	@Override
	public Class<? extends Throwable> getExpectedThrowable() {
		return expectedThrowable;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.I_ThrownAssertionData#getExpectedMessage()
	 */
	@Override
	public String getExpectedMessage() {
		return expectedMessage;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.I_ThrownAssertionData#getActualThrowable()
	 */
	@Override
	public Class<? extends Throwable> getActualThrowable() {
		return actualThrowable;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.I_ThrownAssertionData#getActualMessage()
	 */
	@Override
	public String getActualMessage() {
		return actualMessage;
	}
	public void setExpectedThrowable(Class<? extends Throwable> expectedThrowable) {
		this.expectedThrowable = expectedThrowable;
	}
	public void setExpectedMessage(String expectedMessage) {
		this.expectedMessage = expectedMessage;
	}
	public void setActualThrowable(Class<? extends Throwable> actualThrowable) {
		this.actualThrowable = actualThrowable;
	}
	public void setActualMessage(String actualMessage) {
		this.actualMessage = actualMessage;
	}
	@Override
	public Set<String> getKeys() {
		return KEYS;
	}
	@Override
	public Object getData(String key) {
		switch (key) {
		case I_ThrownAssertionData.EXPECTED_THROWABLE_CLASS:
			   return expectedThrowable;
		case I_ThrownAssertionData.EXPECTED_MESSAGE:
			   return expectedMessage;
		case I_ThrownAssertionData.ACTUAL_MESSAGE:
			   return actualMessage;
		case I_ThrownAssertionData.ACTUAL_THROWABLE_CLASS:
			   return actualThrowable;
		}
		return null;
	}
	
	private static Set<String> getKeysStatic() {
		Set<String> toRet = new HashSet<String>();
		toRet.add(I_ThrownAssertionData.EXPECTED_THROWABLE_CLASS);
		toRet.add(I_ThrownAssertionData.EXPECTED_MESSAGE);
		toRet.add(I_ThrownAssertionData.ACTUAL_MESSAGE);
		toRet.add(I_ThrownAssertionData.ACTUAL_THROWABLE_CLASS);
		
		return Collections.unmodifiableSet(toRet);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((actualMessage == null) ? 0 : actualMessage.hashCode());
		result = prime * result
				+ ((actualThrowable == null) ? 0 : actualThrowable.hashCode());
		result = prime * result
				+ ((expectedMessage == null) ? 0 : expectedMessage.hashCode());
		result = prime
				* result
				+ ((expectedThrowable == null) ? 0 : expectedThrowable
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		try {
			I_ThrownAssertionData other = (I_ThrownAssertionData) obj;
			if (actualMessage == null) {
				if (other.getActualMessage() != null)
					return false;
			} else if (!actualMessage.equals(other.getActualMessage()))
				return false;
			if (actualThrowable == null) {
				if (other.getActualThrowable() != null)
					return false;
			} else if (!actualThrowable.equals(other.getActualThrowable()))
				return false;
			if (expectedMessage == null) {
				if (other.getExpectedMessage() != null)
					return false;
			} else if (!expectedMessage.equals(other.getExpectedMessage()))
				return false;
			if (expectedThrowable == null) {
				if (other.getExpectedThrowable() != null)
					return false;
			} else if (!expectedThrowable.equals(other.getExpectedThrowable()))
				return false;
		} catch (ClassCastException x) {
			return false;
		}
		return true;
	}
}
