package org.adligo.jtests.base.shared.asserts;

import java.util.List;

public interface I_AssertData {
	public List<String> getKeys();
	public Object getData(String key);
}
