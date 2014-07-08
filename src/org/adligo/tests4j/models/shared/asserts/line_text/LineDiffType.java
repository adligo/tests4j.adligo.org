package org.adligo.tests4j.models.shared.asserts.line_text;

/**
 * MATCH a example line matches exactly with a actual line
 * PARTIAL_MATCH a example line has a partial match with a actual line (with a StartEndDiffPair)
 * MISSING_EXAMPLE_LINE the example line is not present in the actual lines
 * MISSING_ACTUAL_LINE the actual line is not present in the examle lines
 * @author scott
 *
 */
public enum LineDiffType {
	MATCH, PARTIAL_MATCH, MISSING_EXAMPLE_LINE, MISSING_ACTUAL_LINE, 
}
