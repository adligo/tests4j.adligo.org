package org.adligo.jtests.models.shared.asserts;

import java.util.Set;

public interface I_AssertionData {
	public Set<String> getKeys();
	public Object getData(String key);
}
