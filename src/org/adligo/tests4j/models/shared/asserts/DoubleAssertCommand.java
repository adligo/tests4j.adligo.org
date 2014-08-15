package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_SimpleCompareAssertCommand;


/**
 * a class 
 * that represents a command to compare two double values.
 * 
 * @author scott
 *
 */
public class DoubleAssertCommand extends AbstractCompareAssertCommand 
	implements I_SimpleCompareAssertCommand {
	public static final String BAD_TYPE = 
			"DoubleAssertCommand requires it's type to be one of AssertType.AssertGreaterThanOrEquals";
	private CompareAssertionData<Double> data;
	
	/**
	 * 
	 * @param pType this may accept types other than AssertType.AssertGreaterThanOrEquals
	 *  in the future.
	 * @param failureMessage
	 * @param pData
	 */
	public DoubleAssertCommand(String failureMessage, CompareAssertionData<Double> pData) {
		super(failureMessage, pData);
		
		//copy it between classloaders
		AssertType type = AssertType.getType(super.getType());
		if (AssertType.AssertGreaterThanOrEquals != type) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
		data = pData;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public boolean evaluate() {
		Double expected = data.getExpected();
		Double actual = data.getActual();
		
		//class loader issues
		AssertType type = AssertType.getType(super.getType());
		switch (type) {
			case AssertGreaterThanOrEquals:
				if (expected == null || actual == null) {
					return false;
				}
			   if(actual.doubleValue() >= expected.doubleValue()) {
				   return true;
			   } else {
				   return false;
			   }
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoubleAssertCommand other = (DoubleAssertCommand) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public I_AssertionData getData() {
		return data;
	}
}
