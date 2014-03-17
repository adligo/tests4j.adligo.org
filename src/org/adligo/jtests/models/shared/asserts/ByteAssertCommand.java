package org.adligo.jtests.models.shared.asserts;

public class ByteAssertCommand extends AbstractNumberAssertCommand {
	private CompareAssertionData<Byte> data;
	
	public ByteAssertCommand(AssertType pType, String failureMessage,
			CompareAssertionData<Byte> pData) {
		super(pType, failureMessage, pData);
		data = pData;
	}

	@Override
	public boolean evaluate() {
		Byte expected = data.getExpected();
		Byte actual = data.getActual();
		switch (type) {
			case AssertEquals:
					if (expected == null) {
						if (actual == null) {
							return true;
						}
					} else if (actual == null) {
						return false;
					} else {
						return (expected.equals(actual));
					}
				break;
			case AssertNotEquals:
				if (expected == null) {
					if (actual == null) {
						return false;
					}
				} else if (actual == null) {
					return true;
				} else {
					return !(expected.equals(actual));
				}
			break;
			case AssertGreaterThan:
				if (expected == null) {
					if (actual == null) {
						return false;
					}
				} else if (actual == null) {
					return false;
				} else {
					return expected.shortValue() > actual.shortValue();
				}
			break;
			case AssertNotGreaterThan:
				if (expected == null) {
					if (actual == null) {
						return true;
					}
				} else if (actual == null) {
					return true;
				} else {
					return !(expected.shortValue() > actual.shortValue());
				}
			break;
			case AssertLessThan:
				if (expected == null) {
					if (actual == null) {
						return false;
					}
				} else if (actual == null) {
					return false;
				} else {
					return expected.shortValue() < actual.shortValue();
				}
			break;
			case AssertNotLessThan:
				if (expected == null) {
					if (actual == null) {
						return true;
					}
				} else if (actual == null) {
					return true;
				} else {
					return !(expected.shortValue() < actual.shortValue());
				}
			break;
		}
		return false;
	}

}
