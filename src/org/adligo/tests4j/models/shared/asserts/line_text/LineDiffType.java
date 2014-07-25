package org.adligo.tests4j.models.shared.asserts.line_text;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * MATCH a example line matches exactly with a actual line
 * PARTIAL_MATCH a example line has a partial match with a actual line (with a StartEndDiffPair)
 * MISSING_EXPECTED_LINE the expected line is not present in the actual lines
 * MISSING_ACTUAL_LINE the actual line is not present in the examle lines
 * @author scott
 *
 */
public enum LineDiffType implements I_LineDiffType {
	MATCH(0), PARTIAL_MATCH(1), MISSING_EXPECTED_LINE(2), MISSING_ACTUAL_LINE(3);
	
	public static final Map<Integer, LineDiffType> TYPES_BY_ID = getTypesById();
	
	public static Map<Integer, LineDiffType> getTypesById() {
		Map<Integer, LineDiffType> toRet = new HashMap<Integer, LineDiffType>();
		toRet.put(MATCH.getId(), MATCH);
		toRet.put(PARTIAL_MATCH.getId(), PARTIAL_MATCH);
		toRet.put(MISSING_EXPECTED_LINE.getId(), MISSING_EXPECTED_LINE);
		toRet.put(MISSING_ACTUAL_LINE.getId(), MISSING_ACTUAL_LINE);
		return Collections.unmodifiableMap(toRet);
	}
	
	public static LineDiffType get(I_LineDiffType p) {
		int id = p.getId();
		return TYPES_BY_ID.get(id);
	}
	
	private int id;
	
	private LineDiffType(int p) {
		id = p;
	}
	@Override
	public int getId() {
		return id;
	} 
}
