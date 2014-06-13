package org.adligo.tests4j.models.shared.asserts;

import org.adligo.tests4j.models.shared.asserts.line_text.I_LineTextCompareResult;
import org.adligo.tests4j.models.shared.asserts.line_text.LineTextCompare;
import org.adligo.tests4j.models.shared.common.I_Immutable;

public class ThrownAssertCommand extends AbstractAssertCommand 
	implements I_Immutable, I_ThrownAssertCommand {
	/**
	 * should only show up to a developer of tests4j
	 */
	public static final String THROWABLE_ASSERTION_COMMAND_REQUIRES_DATA = "ThrowableAssertionCommand requires data.";
	/**
	 * should only show up to a developer of tests4j
	 */
	private static final String BAD_TYPE = "ThrowableAssertionCommand requires a type in AssertType.THROWN_TYPES.";
	
	private I_ExpectedThrownData data;
	private AssertType type;
	private Throwable caught;
	private I_LineTextCompareResult lineTextResult;
	
	public ThrownAssertCommand(I_AssertType pType, String pFailureMessage) {
		super(pType, pFailureMessage);
		if (!AssertType.AssertNotThrown.equals(pType)) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
	}
	
	public ThrownAssertCommand(I_AssertType pType, 
			String pFailureMessage, I_ExpectedThrownData pData) {
		super(pType, pFailureMessage);
		if (!AssertType.THROWN_TYPES.contains(pType)) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
		type = (AssertType) pType;
		data = pData;
		if (data == null) {
			throw new IllegalArgumentException(THROWABLE_ASSERTION_COMMAND_REQUIRES_DATA);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean evaluate(I_Thrower thrower) {
		Class<? extends Throwable> throwableClazz = 
				data.getThrowableClass();
		String expected_message = 
				data.getMessage();
		
		switch (type) {
			case AssertThrown:
				try {
					thrower.run();
				} catch (Exception x) {
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
			case AssertNotThrown:
				try {
					thrower.run();
				} catch (Exception x) {
					caught = x;
				}
				if (caught != null) {
					return false;
				}
				return true;
			case AssertThrownUniform:
				try {
					thrower.run();
				} catch (Exception x) {
					caught = x;
				}
				if (caught == null) {
					return false;
				}
				if ( !throwableClazz.equals(caught.getClass())) {
					return false;
				}
				lineTextResult =
						LineTextCompare.compare(expected_message, caught.getMessage());
				if (lineTextResult.isMatched()) {
					return true;
				}
				return false;
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
		result = prime * result
				+ ((lineTextResult == null) ? 0 : lineTextResult.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

}
