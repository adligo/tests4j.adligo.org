package org.adligo.tests4j.models.shared.asserts;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.line_text.LineTextCompare;
import org.adligo.tests4j.models.shared.asserts.line_text.LineTextCompareResult;
import org.adligo.tests4j.models.shared.common.I_Immutable;

public class ThrownAssertCommand extends AbstractAssertCommand 
	implements I_Immutable, I_ThrownAssertCommand {
	public static final String THROWABLE_ASSERTION_COMMAND_REQUIRES_DATA = "ThrowableAssertionCommand requires data.";
	private static final String BAD_TYPE = "ThrowableAssertionCommand requires a type in AssertType.THROWN_TYPES.";
	private static final String CAUGHT = "caught";
	
	private I_AssertionData data;
	private AssertType type;
	private Throwable caught;
	private LineTextCompareResult lineTextResult;
	
	public ThrownAssertCommand(I_AssertType pType, String pFailureMessage) {
		super(pType, pFailureMessage);
		if (!AssertType.AssertNotThrown.equals(pType)) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
	}
	
	public ThrownAssertCommand(I_AssertType pType, 
			String pFailureMessage, I_AssertionData pData) {
		super(pType, pFailureMessage);
		if (!AssertType.THROWN_TYPES.contains(pType)) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
		data = pData;
		if (data == null) {
			throw new IllegalArgumentException(THROWABLE_ASSERTION_COMMAND_REQUIRES_DATA);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean evaluate(I_Thrower thrower) {
		Class<? extends Throwable> throwableClazz = 
				(Class<? extends Throwable>) 
				data.getData(ThrownAssertionData.THROWABLE_CLASS);
		String expected_message = 
				(String) data.getData(ThrownAssertionData.EXPECTED_MESSAGE);
		
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
		return data;
	}

}
