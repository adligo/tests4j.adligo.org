package org.adligo.tests4j.models.shared.asserts;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_SimpleAssertCommand;

public class ContainsAssertCommand extends AbstractAssertCommand 
implements I_SimpleAssertCommand, I_AssertionData {
		
	public static final String VALUE = "value";
	public static final String BOOLEAN_ASSERT_COMMAND_REQUIRES_A_BOOLEAN_TYPE = "BooleanAssertCommand requires a boolean type.";
	private Object value;
	private Collection<?> collection;
	
	public ContainsAssertCommand(String pFailureMessage,Collection<?> pCollection, Object pValue) {
		super(AssertType.AssertContains, pFailureMessage);
		collection = pCollection;
		value = pValue;
	}
	
	
	@Override
	public boolean evaluate() {
		if (collection == null) {
			return false;
		}
		if (collection.contains(value)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((value == null) ? 0 : value.hashCode());
		return result;
	}
	
	
	public Set<String> getKeys() {
		// TODO Auto-generated method stub
		return Collections.singleton(VALUE);
	}
	
	public Object getData(String key) {
		if (!VALUE.equals(key)) {
			return null;
		}
		return value;
	}
	
	
	@Override
	public I_AssertionData getData() {
		return this;
	}
}
