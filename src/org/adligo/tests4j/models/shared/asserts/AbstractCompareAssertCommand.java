package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_SimpleCompareAssertCommand;



public abstract class AbstractCompareAssertCommand extends AbstractAssertCommand implements I_SimpleCompareAssertCommand {
	public static final String NULL_DATA = "AbstractCompareAssertCommand requires non null data.";
	private I_CompareAssertionData<?> data;
	protected AssertType type;
	
	public AbstractCompareAssertCommand(AssertType pType, String failureMessage, I_CompareAssertionData<?> pData) {
		super(pType, failureMessage);
		type = (AssertType) pType;
		data = pData;
		if (data == null) {
			throw new IllegalArgumentException(NULL_DATA);
		}
	}
	
	@Override
	public I_AssertionData getData() {
		return data;
	}

	@Override
	public Object getExpected() {
		return data.getExpected();
	}

	@Override
	public Object getActual() {
		return data.getActual();
	}

	
}
