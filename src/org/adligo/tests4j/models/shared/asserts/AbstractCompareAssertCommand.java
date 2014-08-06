package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_CompareAssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_SimpleCompareAssertCommand;


/**
 * this is a abstract class to represent the comparison
 * of two objects in a assertion (ie assertEquals(One, Two).
 * 
 * @author scott
 *
 */
public abstract class AbstractCompareAssertCommand extends AbstractAssertCommand implements I_SimpleCompareAssertCommand {
	private I_CompareAssertionData<?> data;
	
	public AbstractCompareAssertCommand(String failureMessage, I_CompareAssertionData<?> pData) {
		super(pData.getType(), failureMessage);
		data = pData;
	}
	
	@Override
	public Object getExpected() {
		return data.getExpected();
	}

	@Override
	public Object getActual() {
		return data.getActual();
	}

	public I_AssertionData getData() {
		return data;
	}
	
}
