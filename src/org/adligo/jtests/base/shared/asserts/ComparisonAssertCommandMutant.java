package org.adligo.jtests.base.shared.asserts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adligo.jtests.base.shared.asserts.line_text.LineTextCompair;
import org.adligo.jtests.base.shared.asserts.line_text.LineTextCompairResult;

public class ComparisonAssertCommandMutant implements I_AssertCommand, I_AssertData, I_ComparisonAssertCommand {
	public static final String MUST_BE_ONE_OF_THE_ASSERT_COMMAND_TYPE_COMPARISON_TYPES = "  must be one of the AssertCommandType.COMPARISON_TYPES";
	public static final String THE_TYPE_OF_A = "The type of a ";
	public static final String EXPECTED = "expected";
	public static final String ACTUAL = "actual";
	public static final String LINE_TEXT_COMPAIR_RESULT = "lineTextCompairResult";
	private static final List<String> KEYS = getKeysIn();
	private String failureMessage;
	private AssertCommandType type;
	private Object expected;
	private Object actual;
	private LineTextCompairResult lineTextCompairResult;
	
	public ComparisonAssertCommandMutant() {}

	public ComparisonAssertCommandMutant(I_ComparisonAssertCommand p) {
		failureMessage = p.getFailureMessage();
		type = p.getType();
		if (!AssertCommandType.COMPARISON_TYPES.contains(type)) {
			throw new IllegalArgumentException(THE_TYPE_OF_A + this.getClass().getSimpleName() +
					MUST_BE_ONE_OF_THE_ASSERT_COMMAND_TYPE_COMPARISON_TYPES);
		}
		expected = p.getExpected();
		actual = p.getActual();
		lineTextCompairResult = p.getLineTextCompairResult();
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.asserts.I_ComparisonAssertCommand#getFailureMessage()
	 */
	@Override
	public String getFailureMessage() {
		return failureMessage;
	}

	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.asserts.I_ComparisonAssertCommand#getType()
	 */
	@Override
	public AssertCommandType getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.asserts.I_ComparisonAssertCommand#getExpected()
	 */
	@Override
	public Object getExpected() {
		return expected;
	}

	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.asserts.I_ComparisonAssertCommand#getActual()
	 */
	@Override
	public Object getActual() {
		return actual;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	public void setType(AssertCommandType type) {
		this.type = type;
	}

	public void setExpected(Object expected) {
		this.expected = expected;
	}

	public void setActual(Object actual) {
		this.actual = actual;
	}

	@Override
	public Object getData(String key) {
		if (EXPECTED.equals(key)) {
			return expected;
		} else if (ACTUAL.equals(key)) {
			return actual;
		} else if (LINE_TEXT_COMPAIR_RESULT.equals(key)) {
			return lineTextCompairResult;
		}
		return null;
	}

	@Override
	public boolean evaluate() {
		switch (type) {
			case AssertEquals:
				return equals();
			case AssertNotEquals:
				return !equals();
			case AssertSame:
				return same();
			case AssertNotSame:
				return !same();
		}
		return false;
	}

	private boolean equals() {
		if (expected == null) {
			if (actual == null) {
				return true;
			} else {
				return false;
			}
		}
		if (actual == null) {
			return false;
		}
		if (bothAreStringsAndAReturnCharacterExists()) {
			lineTextCompairResult = LineTextCompair.equals(
					(String) expected, (String) actual);
			return lineTextCompairResult.isMatched();
		} else {
			return expected.equals(actual);
		}
	}
	
	private boolean same() {
		if (expected == null) {
			if (actual == null) {
				return true;
			} else {
				return false;
			}
		}
		return expected == actual;
	}

	private boolean bothAreStringsAndAReturnCharacterExists() {
		String stringClassName = String.class.getName();
		
		if (stringClassName.equals(expected.getClass().getName())) {
			if (stringClassName.equals(actual.getClass().getName())) {
				String exp = (String) expected;
				if (exp.indexOf("\r") != -1 || exp.indexOf("\r") != -1) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public I_AssertData getData() {
		return this;
	}

	@Override
	public LineTextCompairResult getLineTextCompairResult() {
		return lineTextCompairResult;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actual == null) ? 0 : actual.hashCode());
		result = prime * result
				+ ((expected == null) ? 0 : expected.hashCode());
		result = prime * result
				+ ((failureMessage == null) ? 0 : failureMessage.hashCode());
		result = prime
				* result
				+ ((lineTextCompairResult == null) ? 0 : lineTextCompairResult
						.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComparisonAssertCommandMutant other = (ComparisonAssertCommandMutant) obj;
		if (actual == null) {
			if (other.actual != null)
				return false;
		} else if (!actual.equals(other.actual))
			return false;
		if (expected == null) {
			if (other.expected != null)
				return false;
		} else if (!expected.equals(other.expected))
			return false;
		if (failureMessage == null) {
			if (other.failureMessage != null)
				return false;
		} else if (!failureMessage.equals(other.failureMessage))
			return false;
		if (lineTextCompairResult == null) {
			if (other.lineTextCompairResult != null)
				return false;
		} else if (!lineTextCompairResult.equals(other.lineTextCompairResult))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public static List<String> getKeysIn() {
		List<String> toRet = new ArrayList<String>();
		toRet.add(EXPECTED);
		toRet.add(ACTUAL);
		toRet.add(LINE_TEXT_COMPAIR_RESULT);
		return Collections.unmodifiableList(toRet);
	}
	
	@Override
	public  List<String> getKeys() {
		return KEYS;
	}
	
}
