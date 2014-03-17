package org.adligo.jtests.models.shared.asserts;

public abstract class AbstractNumberAssertCommand extends AbstractCompareAssertCommand{

	public AbstractNumberAssertCommand(AssertType pType, String failureMessage, CompareAssertionData<? extends Number> pData) {
		super(pType, failureMessage,pData);
		type = (AssertType) pType;
		if (!AssertType.NUMBER_TYPES.contains(type)) {
			throw new IllegalArgumentException("The AssertType must be one of the AssertType.NUMBER_TYPES");
		}
	}
}
