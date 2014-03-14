package org.adligo.jtests.models.shared.asserts;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.adligo.jtests.models.shared.asserts.line_text.LineTextCompare;
import org.adligo.jtests.models.shared.asserts.line_text.LineTextCompareResult;
import org.adligo.jtests.models.shared.common.I_Immutable;

public class ThrownAssertCommand extends AbstractAssertCommand 
	implements I_Immutable, I_ThrownAssertCommand {
	public static final String THROWABLE_ASSERTION_COMMAND_REQUIRES_DATA = "ThrowableAssertionCommand requires data.";
	private static final String BAD_TYPE = "ThrowableAssertionCommand requires a type in AssertType.THROWN_TYPES.";
	private static final String CAUGHT = "caught";
	
	private ThrownableAssertionData data;
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
			String pFailureMessage, ThrownableAssertionData pData) {
		super(pType, pFailureMessage);
		if (!AssertType.THROWN_TYPES.contains(pType)) {
			throw new IllegalArgumentException(BAD_TYPE);
		}
		data = pData;
		if (data == null) {
			throw new IllegalArgumentException(THROWABLE_ASSERTION_COMMAND_REQUIRES_DATA);
		}
	}

	@Override
	public Set<String> getKeys() {
		Set<String> keys = new HashSet<String>();
		keys.addAll(data.getKeys());
		if (type == AssertType.AssertThrownUniform) {
			keys.add(LINE_TEXT_RESULT);
		}
		keys.add(CAUGHT);
		return Collections.unmodifiableSet(keys);
	}

	@Override
	public Object getData(String key) {
		if (CAUGHT.equals(key)) {
			return caught;
		}
		return data.getData(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean evaluate(I_Thrower thrower) {
		Class<? extends Throwable> throwableClazz = 
				(Class<? extends Throwable>) 
				data.getData(ThrownableAssertionData.THROWABLE_CLASS);
		String expected_message = 
				(String) data.getData(ThrownableAssertionData.EXPECTED_MESSAGE);
		
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
						LineTextCompare.equals(expected_message, caught.getMessage());
				if (lineTextResult.isMatched()) {
					return true;
				}
				return false;
		}
		return false;
	}

}
