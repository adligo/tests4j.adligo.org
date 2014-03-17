package org.adligo.jtests.models.shared.asserts;

import java.util.Set;


public abstract class AbstractCompareAssertCommand extends AbstractAssertCommand implements I_CompareAssertCommand {
	public static final String NULL_DATA = "AbstractCompareAssertCommand requires non null data.";
	private CompareAssertionData<?> data;
	protected AssertType type;
	
	public AbstractCompareAssertCommand(AssertType pType, String failureMessage, CompareAssertionData<?> pData) {
		super(pType, failureMessage);
		type = (AssertType) pType;
		data = pData;
		if (data == null) {
			throw new IllegalArgumentException(NULL_DATA);
		}
	}

	@Override
	public Set<String> getKeys() {
		return data.getKeys();
	}

	@Override
	public Object getData(String key) {
		return data.getData(key);
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
