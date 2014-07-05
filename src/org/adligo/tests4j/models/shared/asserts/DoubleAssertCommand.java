package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
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
	private AssertType type;
	
	/**
	 * 
	 * @param pType this may accept types other than AssertType.AssertGreaterThanOrEquals
	 *  in the future.
	 * @param failureMessage
	 * @param pData
	 */
	public DoubleAssertCommand(AssertType pType, String failureMessage, CompareAssertionData<Double> pData) {
		super(pType, failureMessage, pData);
		if (AssertType.AssertGreaterThanOrEquals != pType) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
		type = (AssertType) pType;
		data = pData;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public boolean evaluate() {
		Double expected = data.getExpected();
		Double actual = data.getActual();
		
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
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public I_AssertionData getData() {
		return data;
	}
}
