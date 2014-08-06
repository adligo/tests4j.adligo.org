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
	Match(0), PartialMatch(1), MissingExpectedLine(2), MissingActualLine(3);
	
	public static final Map<Integer, LineDiffType> TYPES_BY_ID = getTypesById();
	
	public static Map<Integer, LineDiffType> getTypesById() {
		Map<Integer, LineDiffType> toRet = new HashMap<Integer, LineDiffType>();
		toRet.put(Match.getId(), Match);
		toRet.put(PartialMatch.getId(), PartialMatch);
		toRet.put(MissingExpectedLine.getId(), MissingExpectedLine);
		toRet.put(MissingActualLine.getId(), MissingActualLine);
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
