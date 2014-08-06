package org.adligo.tests4j.models.shared.asserts.line_text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;

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
	
	
	public I_TextLinesCompareResult compare(String example, String actual, boolean normalizeLineFeeds) {
		if (actual == null) {
			I_Tests4J_AssertionInputMessages messages = Tests4J_Constants.CONSTANTS.getAssertionInputMessages();
			return new TextLinesCompareResult(messages.getTheActualValueIsNull());
		}
		exampleLT = new TextLines(example, normalizeLineFeeds);
		actualLT = new TextLines(actual, normalizeLineFeeds);
		
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
			ldm.setExpectedLineNbr(lineNbr);
			ldm.setType(LineDiffType.MissingExpectedLine);
		}
		boolean matched = true;
		for (I_LineDiff ld: diffs) {
			if (ld.getType() != LineDiffType.Match) {
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
				ldm.setExpectedLineNbr(diff.getExpectedLineNbr());
			} else {
				ldm.setExpectedLineNbr(i);
			}
			ldm.setActualLineNbr(lineNbr);
			ldm.setType(LineDiffType.MissingActualLine);
			
			LineDiff ld = new LineDiff(ldm);
			
			
			actualToDiffs.put(ld.getActualLineNbr(), ld);
			expectedToDiffs.put(ld.getExpectedLineNbr(), ld);
			diffs.add(ld);
		}
	}


	private void addMissingExampleLines() {
		Iterator<Integer> it = exampleLinesWithoutMatch.iterator();
		while (it.hasNext()) {
			Integer lineNbr = it.next();
			it.remove();
			
			LineDiffMutant ldm = new LineDiffMutant();
			ldm.setExpectedLineNbr(lineNbr);
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
			ldm.setType(LineDiffType.MissingExpectedLine);
			LineDiff ld = new LineDiff(ldm);
			
			
			actualToDiffs.put(ld.getActualLineNbr(), ld);
			expectedToDiffs.put(ld.getExpectedLineNbr(), ld);
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
		
		Iterator<Integer> it = exampleLinesWithoutMatch.iterator();
		while (it.hasNext()) {
			Integer expLineNbr = it.next();
			boolean removedThisIt = false;
			int previousDiffCounter = expLineNbr - 1;
			I_LineDiff beforeDiff = null;
			while (beforeDiff == null && previousDiffCounter >= 0) {
				beforeDiff = expectedToDiffs.get(previousDiffCounter--);
			}
			
			int nextDiffCounter = expLineNbr + 1;
			I_LineDiff afterDiff = null;
			while (afterDiff == null && nextDiffCounter < exampleLT.getLines()) {
				afterDiff = expectedToDiffs.get(nextDiffCounter++);
			}
			
			if (beforeDiff == null) {
				if (afterDiff == null) {
					//no diffs matched
					Iterator<Integer> ait = actualLinesWithoutMatch.iterator();
					while (ait.hasNext() && !removedThisIt) {
						Integer actualLineNbr = ait.next();
						String expected = exampleLT.getLine(expLineNbr);
						String actual = actualLT.getLine(actualLineNbr);
						if (addIfPartialMatch(expLineNbr, expected, actualLineNbr, actual)) {
							ait.remove();
							it.remove();
							removedThisIt = true;
						}
					}
				} else {
					Integer lastActualNbr = afterDiff.getActualLineNbr();
					Iterator<Integer> ait = actualLinesWithoutMatch.iterator();
					while (ait.hasNext() && !removedThisIt) {
						Integer actualLineNbr = ait.next();
						if (lastActualNbr != null){
							if (actualLineNbr < lastActualNbr) {
								String expected = exampleLT.getLine(expLineNbr);
								String actual = actualLT.getLine(actualLineNbr);
								if (addIfPartialMatch(expLineNbr, expected, actualLineNbr, actual)) {
									ait.remove();
									it.remove();
									removedThisIt = true;
								}
							}
						} else {
							String expected = exampleLT.getLine(expLineNbr);
							String actual = actualLT.getLine(actualLineNbr);
							if (addIfPartialMatch(expLineNbr, expected, actualLineNbr, actual)) {
								ait.remove();
								it.remove();
								removedThisIt = true;
							}
						}
					}
				}
			} else {
				int startExampleLineNbr = beforeDiff.getExpectedLineNbr() + 1;
				int startActualLineNbr = beforeDiff.getActualLineNbr();
				
				if (expLineNbr >= startExampleLineNbr) {
					int exampleEnd = exampleLT.getLines() -1;
					if (afterDiff != null) {
						exampleEnd = afterDiff.getExpectedLineNbr();
					}
					
					int actualEnd = actualLT.getLines() -1;
					if (afterDiff != null) {
						actualEnd = afterDiff.getActualLineNbr();
					}
					//loop through the expected lines
					for (int i = expLineNbr; i <= exampleEnd; i++) {
						String expected = exampleLT.getLine(expLineNbr);
						//loop through the actual lines that could be a match
						Iterator<Integer> ait = actualLinesWithoutMatch.iterator();
						while (ait.hasNext() && !removedThisIt) {
							Integer actualLineNbr = ait.next();
							if (startActualLineNbr < actualLineNbr && startActualLineNbr < actualEnd) {
								String actual = actualLT.getLine(actualLineNbr);
								if (addIfPartialMatch(i, expected, actualLineNbr, actual)) {
									ait.remove();
									it.remove();
									removedThisIt = true;
								}
							}
						}
					}
				}
			}
		}
	}


	private boolean addIfPartialMatch(int i, String expected, int j, String actual) {
		DiffIndexesPair dip = new DiffIndexesPair(expected, actual);
		I_DiffIndexes example = dip.getExpected();
		if (example != null) {
			Integer matchLeftToRight = example.getMatchLeftToRight();
			if (matchLeftToRight != null) {
				LineDiffMutant ldm = new LineDiffMutant();
				ldm.setExpectedLineNbr(i);
				ldm.setActualLineNbr(j);
				ldm.setType(LineDiffType.PartialMatch);
				ldm.setIndexes(dip);
				LineDiff ld = new LineDiff(ldm);
				diffs.add(ld);
				expectedToDiffs.put(i, ld);
				actualToDiffs.put(j, ld);
				return true;
			}
		}
		return false;
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
					ldm.setExpectedLineNbr(i);
					ldm.setActualLineNbr(j);
					ldm.setType(LineDiffType.Match);
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
