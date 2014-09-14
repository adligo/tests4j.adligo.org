package org.adligo.tests4j.models.shared.asserts;

import java.util.Collection;
import java.util.Iterator;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_CollectionContainsAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_SimpleAssertCommand;
import org.adligo.tests4j.models.shared.common.Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionInputMessages;

/**
 * a class 
 * that contains information about
 * a assertContains or assertNotContains command.
 * @author scott
 *
 */
public class ContainsAssertCommand extends AbstractAssertCommand 
implements I_SimpleAssertCommand, I_CollectionContainsAssertionData {
		
	private Object value;
	private Collection<?> collection;
	
	
	public ContainsAssertCommand(String pFailureMessage,Collection<?> pCollection, Object pValue) {
		super(AssertType.AssertContains, pFailureMessage);
		collection = pCollection;
		if (collection == null) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			
			throw new IllegalArgumentException(messages.getTheExpectedValueShouldNeverBeNull());
		}
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
		if (collection != null) {
			result = prime * result + collection.hashCode();
			Iterator<?> it = collection.iterator();
			while (it.hasNext()) {
				Object o = it.next();
				if (o != null) {
					result = prime * result + o.hashCode();
				}
			}
		}
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	
	@Override
	public I_AssertionData getData() {
		return this;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContainsAssertCommand other = (ContainsAssertCommand) obj;
		if (collection == null) {
			if (other.collection != null)
				return false;
		} else if (collection.size() == other.collection.size()) {
			Iterator<?> it = collection.iterator();
			Iterator<?> oIt = other.collection.iterator();
			while (it.hasNext()) {
				Object o = it.next();
				Object oo = oIt.next();
				if (o == null) {
					if (oo != null) {
						return false;
					}
				} else if (!o.equals(oo)) {
					return false;
				}
			}
		} else {
			return false; 	
		}
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}


	public Object getValue() {
		return value;
	}


	public Collection<?> getCollection() {
		return collection;
	}
}
