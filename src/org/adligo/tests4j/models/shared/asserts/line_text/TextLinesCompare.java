package org.adligo.tests4j.models.shared.asserts.line_text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * A NON Threadsafe model class to compare large multi line strings
 * (aka files as strings).  A fairly simple algorithm which 
 * attempts to find different text, in a way that can be shown
 * to a user similar to a version control system's diff (cvs diff, git, svn exc).
 * 
 * @author scott
 */
public class TextLinesCompare  {
	private I_TextLines exampleLT;
	private I_TextLines actualLT;
	private TreeSet<I_LineDiff> diffs = new TreeSet<I_LineDiff>();
	private Map<Integer, I_LineDiff> expectedToDiffs = new HashMap<Integer, I_LineDiff>();
	private Map<Integer, I_LineDiff> actualToDiffs = new HashMap<Integer, I_LineDiff>();
	
	private TreeSet<Integer> exampleLinesWithoutMatch = new TreeSet<Integer>();
	private TreeSet<Integer> actualLinesWithoutMatch = new TreeSet<Integer>();
	
	
	public I_TextLinesCompareResult compare(String example, String actual) {
		
		I_TextLines exampleLT = new TextLines(example);
		I_TextLines actualLT = new TextLines(actual);
		
		findMatches(exampleLT, actualLT);

		compareLineChars();
		
		//do actuals first as they come out on top, unless example misses at top
		// ie    'a' vs 'b'  ....     ^\ b
		//                          a          
		//
		//       'a' vs 'b'  ....   a /^ 
		//       'b'    'b'         b  = b
		//                            ^\ b
		//
		//
		addMissingActualLines();
		
		addMissingExampleLines();
		
		
		for (Integer lineNbr: exampleLinesWithoutMatch) {
			LineDiffMutant ldm = new LineDiffMutant();
			ldm.setExampleLineNbr(lineNbr);
			ldm.setType(LineDiffType.MISSING_EXAMPLE_LINE);
		}
		boolean matched = true;
		for (I_LineDiff ld: diffs) {
			if (ld.getType() != LineDiffType.MATCH) {
				matched = false;
			}
		}
		return new TextLinesCompareResult(exampleLT, actualLT, 
				matched, new ArrayList<I_LineDiff>(diffs));
	}


	private void addMissingActualLines() {
		Iterator<Integer> it = actualLinesWithoutMatch.iterator();
		while (it.hasNext()) {
			Integer lineNbr = it.next();
			it.remove();
			
			I_LineDiff diff = actualToDiffs.get(lineNbr);
			int i = lineNbr -1;
			while (diff == null) {
				diff = actualToDiffs.get(i--);
				if (i < 0) {
					i = -1;
					break;
				}
			}
			LineDiffMutant ldm = new LineDiffMutant();
			if (diff != null) {
				ldm.setExampleLineNbr(diff.getExampleLineNbr());
			} else {
				ldm.setExampleLineNbr(i);
			}
			ldm.setActualLineNbr(lineNbr);
			ldm.setType(LineDiffType.MISSING_ACTUAL_LINE);
			
			LineDiff ld = new LineDiff(ldm);
			
			
			actualToDiffs.put(ld.getActualLineNbr(), ld);
			expectedToDiffs.put(ld.getExampleLineNbr(), ld);
			diffs.add(ld);
		}
	}


	private void addMissingExampleLines() {
		Iterator<Integer> it = exampleLinesWithoutMatch.iterator();
		while (it.hasNext()) {
			Integer lineNbr = it.next();
			it.remove();
			
			LineDiffMutant ldm = new LineDiffMutant();
			ldm.setExampleLineNbr(lineNbr);
			if (lineNbr == 0) {
				I_LineDiff diff = expectedToDiffs.get(-1);
				if (diff == null) {
					ldm.setActualLineNbr(-1);
				} else {
					ldm.setActualLineNbr(0);
				}
			} else {
				I_LineDiff diff = expectedToDiffs.get(lineNbr);
				int i = lineNbr -1;
				while (diff == null) {
					diff = expectedToDiffs.get(i--);
					if (i < 0) {
						i = 0;
						break;
					}
				}
				if (diff != null) {
					Integer actNbr = diff.getActualLineNbr();
					if (actNbr != null) {
						ldm.setActualLineNbr(actNbr);
					} else {
						ldm.setActualLineNbr(i);
					}
					
				} else {
					ldm.setActualLineNbr(i);
				}
			}
			ldm.setType(LineDiffType.MISSING_EXAMPLE_LINE);
			LineDiff ld = new LineDiff(ldm);
			
			
			actualToDiffs.put(ld.getActualLineNbr(), ld);
			expectedToDiffs.put(ld.getExampleLineNbr(), ld);
			diffs.add(ld);
		}
	}
	
	/**
	 * the rough logic here is
	 * spin through the matches two at a time
	 * I_LineDiff a,b;
	 *    if there are expectedLinesWithoutMatch before a's [expected line nbr], 
	 *    		check for partial matches in actualLinesWithoutMatch before a's [actual line nbr]
	 *    		remove partial matches from *LinesWithoutMatch
	 *    if there are actualLinesWithoutMatch before a's [actual line nbr], 
	 *    		check for partial matches in expectedLinesWithoutMatch before a's [expected line nbr]
	 *    		remove partial matches from *LinesWithoutMatch
	 *    
	 *    if there are expectedLinesWithoutMatch between a's [expected line nbr] and b's [expected line nbr]
	 *    		check for partial matches in actualLinesWithoutMatch between a's [actual line nbr] and b's [actual line nbr]
	 *    		
	 * at the end if there are expected lines after a's [expected line nbr]
	 *    check for partial matches in actual
	 */
	private void compareLineChars() {
		//do character comparisons
		int throughExpected = -1;
		int throughActual = -1;
		I_LineDiff lastDiff = null;
		List<I_LineDiff> matches = new ArrayList<I_LineDiff>(diffs);
		
		for (I_LineDiff ld: matches) {
			if (lastDiff == null) {
				lastDiff = ld;
			} else {
				int exp = lastDiff.getExampleLineNbr();
				Integer act = lastDiff.getActualLineNbr();
				if (throughExpected == -1 && exp == -1) {
					if (throughActual < act) {
						throughActual = act;
					}
				} else if (throughExpected == -1 && exp == 0) {
					throughExpected = 0;
				} else if (exp -1 == throughExpected) {
					throughExpected = exp;
				}
				lastDiff = ld;
			}
		}
	}


	private void findMatches(I_TextLines exampleLT, I_TextLines actualLT) {
		int minActualLineMatch = 0;
		int j = 0;
		for (int i = 0; i < exampleLT.getLines(); i++) {
			String exampleLine = exampleLT.getLine(i);
			j = minActualLineMatch;
			
			boolean foundMatch = false;
			
			while (actualLT.getLines() > j) {
				String actualLine = actualLT.getLine(j);
				if (exampleLine.equals(actualLine)) {
					LineDiffMutant ldm = new LineDiffMutant();
					ldm.setExampleLineNbr(i);
					ldm.setActualLineNbr(j);
					ldm.setType(LineDiffType.MATCH);
					LineDiff ld = new LineDiff(ldm);
					expectedToDiffs.put(i, ld);
					actualToDiffs.put(j, ld);
					diffs.add(ld);
					foundMatch = true;
					minActualLineMatch = j;
					break;
				} 
				j++;
			}
			if (!foundMatch) {
				exampleLinesWithoutMatch.add(i);
			}
		}
		
		for (int i = 0; i < actualLT.getLines(); i++) {
			actualLinesWithoutMatch.add(i);
		}
		for (I_LineDiff ld: diffs) {
			Integer actualLineNbr = ld.getActualLineNbr();
			actualLinesWithoutMatch.remove(actualLineNbr);
		}
	}
}
