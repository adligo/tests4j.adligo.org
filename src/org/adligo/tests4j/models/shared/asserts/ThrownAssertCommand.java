package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertionData;
import org.adligo.tests4j.models.shared.asserts.common.I_ExpectedThrownData;
import org.adligo.tests4j.models.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.models.shared.asserts.common.I_ThrownAssertCommand;
import org.adligo.tests4j.models.shared.common.I_Immutable;
import org.adligo.tests4j.models.shared.i18n.asserts.I_Tests4J_AssertionResultMessages;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

public class ThrownAssertCommand extends AbstractAssertCommand 
	implements I_Immutable, I_ThrownAssertCommand {
	/**
	 * should only show up to a developer of tests4j
	 */
	private static final String BAD_TYPE = "ThrownAssertCommand requires a type in AssertType.AssertThrown.";
	
	private I_ExpectedThrownData data;
	private AssertType type;
	private Throwable caught;
	
	public ThrownAssertCommand(I_AssertType pType, String pFailureMessage) {
		super(pType, pFailureMessage);
		if (!AssertType.AssertThrown.equals(pType)) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
	}
	
	public ThrownAssertCommand(I_AssertType pType, 
			String pFailureMessage, I_ExpectedThrownData pData) {
		super(pType, pFailureMessage);
		if (AssertType.AssertThrown != pType) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
		type = (AssertType) pType;
		data = pData;
		if (data == null) {
			I_Tests4J_AssertionResultMessages messages = Tests4J_Constants.CONSTANTS.getAssertionResultMessages();
			throw new IllegalArgumentException(messages.getTheExpectedValueShouldNeverBeNull());
		}
	}

	@Override
	public boolean evaluate(I_Thrower thrower) {
		if (thrower == null) {
			I_Tests4J_AssertionResultMessages messages = Tests4J_Constants.CONSTANTS.getAssertionResultMessages();
			throw new IllegalArgumentException(messages.getIThrowerIsRequired());
		}
		Class<? extends Throwable> throwableClazz = 
				data.getThrowableClass();
		String expected_message = 
				data.getMessage();
		
		
		try {
			thrower.run();
		} catch (Throwable x) {
			caught = x;
		}
		if (caught == null) {
			return false;
		}
		if ( !throwableClazz.equals(caught.getClass())) {
			return false;
		}
		if (expected_message.equals(caught.getMessage())) {
			return true;
		}
		return false;
	}

	@Override
	public I_AssertionData getData() {
		ThrownAssertionDataMutant tadm = new ThrownAssertionDataMutant();
		tadm.setExpectedMessage(data.getMessage());
		tadm.setExpectedThrowable(data.getThrowableClass());
		
		//there may not have been a caught exception
		if (caught != null) {
			tadm.setActualThrowable(caught.getClass());
			tadm.setActualMessage(caught.getMessage());
		}
		return new ThrownAssertionData(tadm);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((caught == null) ? 0 : caught.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

}
