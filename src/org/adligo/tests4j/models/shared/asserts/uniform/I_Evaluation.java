package org.adligo.tests4j.models.shared.asserts.uniform;

import java.util.Map;

/**
 * This interface contains information
 * about the evaluation of a assertUniform
 * or assertNotUniform method.
 * 
 * @author scott
 *
 */
public interface I_Evaluation {
	public boolean isSuccess();
	public String getFailureSubMessage();
	/**
	 * return special data pertaining to this
	 * evaluation result (ie the map could contain a entry like;
	 * "LineText", I_LineTextCompareResult)
	 * @return
	 */
	public Map<String,Object> getData();
}
