package org.adligo.jtests.base.shared.asserts;

import java.util.Collections;
import java.util.List;

public class EmptyAssertData implements I_AssertData {

	@Override
	public Object getData(String key) {
		return null;
	}

	public List<String> getKeys() {
		return Collections.EMPTY_LIST;
	}
}
