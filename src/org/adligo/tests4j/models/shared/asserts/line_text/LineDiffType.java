package org.adligo.tests4j.models.shared.asserts.line_text;

/**
 * MATCH a example line matches exactly with a actual line
 * PARTIAL_MATCH a example line has a partial match with a actual line (with a StartEndDiffPair)
 * MISSING_EXPECTED_LINE the expected line is not present in the actual lines
 * MISSING_ACTUAL_LINE the actual line is not present in the examle lines
 * @author scott
 *
 */
public enum LineDiffType {
	MATCH, PARTIAL_MATCH, MISSING_EXPECTED_LINE, MISSING_ACTUAL_LINE, 
}
