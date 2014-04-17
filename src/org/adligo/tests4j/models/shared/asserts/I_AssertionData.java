package org.adligo.tests4j.models.shared.asserts;

import java.util.Set;

public interface I_AssertionData {
	public static final String LINE_TEXT_RESULT = "line_text_result";
	public static final String FAILURE_SUB_MESSAGE = "failure_sub_message";
	
	public Set<String> getKeys();
	public Object getData(String key);
}
