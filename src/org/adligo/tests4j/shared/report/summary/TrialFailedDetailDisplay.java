package org.adligo.tests4j.shared.report.summary;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.common.AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertCompareFailure;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertThrownFailure;
import org.adligo.tests4j.models.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.models.shared.asserts.common.I_TestFailure;
import org.adligo.tests4j.models.shared.asserts.common.I_ThrowableInfo;
import org.adligo.tests4j.models.shared.asserts.line_text.I_DiffIndexes;
import org.adligo.tests4j.models.shared.asserts.line_text.I_DiffIndexesPair;
import org.adligo.tests4j.models.shared.asserts.line_text.I_LineDiff;
import org.adligo.tests4j.models.shared.asserts.line_text.I_LineDiffType;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLines;
import org.adligo.tests4j.models.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.models.shared.asserts.line_text.LineDiffType;
import org.adligo.tests4j.models.shared.asserts.line_text.TextLinesCompare;
import org.adligo.tests4j.models.shared.common.Tests4J_Constants;
import org.adligo.tests4j.models.shared.dependency.I_FieldSignature;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.results.I_DependencyTestFailure;
import org.adligo.tests4j.models.shared.results.I_TestResult;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class TrialFailedDetailDisplay {
	private I_Tests4J_Log log;
	private I_Tests4J_ReportMessages messages =  Tests4J_Constants.CONSTANTS.getReportMessages();
	
	public TrialFailedDetailDisplay(I_Tests4J_Log pLog) {
		log = pLog;
	}

	public void logTrialFailure(I_TrialResult trial) {
		if ( !log.isLogEnabled(TrialFailedDetailDisplay.class)) {
			return;
		}
		
		String trialName = trial.getName();
		StringBuilder sb = new StringBuilder();
		sb.append(trialName + messages.getFailedEOS() + log.getLineSeperator());
		
		List<I_TrialFailure> failures =  trial.getFailures();
		if (failures != null) {
			for (I_TrialFailure failure: failures) {
				sb.append(messages.getIndent() + failure.getMessage() + log.getLineSeperator());
				
				String detail = failure.getFailureDetail();
				if (detail != null) {
					sb.append(messages.getIndent() +  messages.getIndent() + detail + log.getLineSeperator());
				}
			}
		}
		List<I_TestResult> testResults = trial.getResults();
		for (I_TestResult tr: testResults) {
			if (!tr.isPassed() && !tr.isIgnored()) {
				String testName = tr.getName();
				
				sb.append(messages.getIndent() + trialName + "."  + testName + messages.getFailedEOS() +
						log.getLineSeperator());
				I_TestFailure tf = tr.getFailure();
				if (tf != null) {
					sb.append(messages.getIndent() + tf.getFailureMessage() + log.getLineSeperator());
					I_AssertType type =  tf.getAssertType();
					
					if (type != null) {
						AssertType at = AssertType.getType(type);
						switch (at) {
							case AssertThrown:
							case AssertThrownUniform:
								I_AssertThrownFailure atf = (I_AssertThrownFailure) tf;
								addThrownMessages(atf,sb);
								break;
							case AssertContains:
								I_AssertCompareFailure ac = (I_AssertCompareFailure) tf;
								addExpected(ac.getExpectedValue(), ac.getExpectedClass(), sb);
								break;
							case AssertDependency:
								 addDependencyFailure(
										 trial, (I_DependencyTestFailure) tf);
								break;
							case AssertNull:
							case AssertNotNull:
								break;
							default:
								I_AssertCompareFailure acf = (I_AssertCompareFailure) tf;
								if (String.class.getName().equals(acf.getExpectedClass())) {
									if (String.class.getName().equals(acf.getActualClass())) {
										addStringCompare(acf.getExpectedValue(), acf.getActualValue(), sb);
									} else {
										addCompareFailure(acf, sb);
									}
								} else {
									addCompareFailure(acf, sb);
								}
						}
					}
					String failureDetail = tf.getFailureDetail();
					if (failureDetail != null) {
						sb.append(failureDetail);
					}
				}
			}
		}
		log.log(sb.toString());
	}

	private void addExpected(String exp, String expClass,  StringBuilder sb) {
		sb.append(messages.getIndent() + messages.getExpected() + log.getLineSeperator());
		sb.append(messages.getIndent() + messages.getIndent()+ messages.getClassHeadding() + 
				expClass + log.getLineSeperator());
		sb.append(messages.getIndent() + messages.getIndent() + "'" + exp + "'" +
				log.getLineSeperator());
	}
	
	private void addCompareFailure(I_AssertCompareFailure acf, StringBuilder sb) {
		addExpected(acf.getExpectedValue(), acf.getExpectedClass(), sb);
		sb.append(messages.getIndent() + messages.getActual() + log.getLineSeperator());
		sb.append(messages.getIndent() + messages.getIndent()+ messages.getClassHeadding() + 
					acf.getActualClass()
					+ log.getLineSeperator());
		sb.append(messages.getIndent() + messages.getIndent() + "'" + acf.getActualValue() + "'"
				+ log.getLineSeperator());
	}
	
	private void addDependencyFailure(I_TrialResult trial, I_DependencyTestFailure tf) {
		IllegalStateException x = new IllegalStateException();
		x.fillInStackTrace();
		StackTraceElement[] stack = x.getStackTrace();
		StackTraceElement top = new StackTraceElement(
				trial.getTrialClassName(), "<init>", 
				tf.getSourceClass().getSimpleName() + ".java",
				1);
		if (stack.length == 0) {
			stack = new StackTraceElement[1];
		}
		stack[0] = top;
		
		String message = null;
		
		List<String> groupNames = tf.getGroupNames();
		message =  messages.getIndent() +  messages.getIndent() +
				"@AllowedDependencies: " + groupNames.toString() + log.getLineSeperator();
		I_FieldSignature field = tf.getField();
		if (field != null) {
			message = message + messages.getIndent() + messages.getIndent()+
					"SourceClass: " + tf.getSourceClass().getName() + 
					".java" + log.getLineSeperator() +
					messages.getIndent() + messages.getIndent()+
					" called field " + tf.getCalledClass() + ". " + field;
		} else {
			message = message + messages.getIndent() + messages.getIndent()+
					"SourceClass: " + tf.getSourceClass().getName() + ".java" + 
					log.getLineSeperator() +
					messages.getIndent() + messages.getIndent()+
					" called method " + tf.getCalledClass() + ". " + tf.getMethod();
		}
		
		DependencyFailureException dfe = 
				new DependencyFailureException(tf.getFailureMessage() + log.getLineSeperator() +
						message,
						stack);
		log.onThrowable(dfe);
	}
	
	private void addStringCompare(String expectedValue, String actualValue, StringBuilder sb) {
		TextLinesCompare tlc = new TextLinesCompare();
		
		
		I_TextLinesCompareResult result =  tlc.compare(expectedValue, actualValue, true);
		
		Set<Integer> missingActualLines = new HashSet<Integer>();
		Set<Integer> missingExpectedLines = new HashSet<Integer>();
		
		I_LineDiff firstDiff = null;
		List<I_LineDiff> lineDiffs = result.getLineDiffs();
		for (I_LineDiff diff: lineDiffs) {
			I_LineDiffType diffType = diff.getType();
			LineDiffType ldt = LineDiffType.get(diffType);
			switch(ldt) {
				case PartialMatch:
					if( firstDiff == null) {
						firstDiff = diff;
					}
					break;
				case MissingActualLine:
						missingActualLines.add(diff.getActualLineNbr());
					break;
				case MissingExpectedLine:
						missingExpectedLines.add(diff.getExpectedLineNbr());
					break;
				default:
					//do nothing
			}
		}
		if (missingExpectedLines.size() > 1) {
			sb.append(messages.getTheFollowingExpectedLineNumbersWereMissing());
			boolean first = true;
			for (Integer lineNbr: missingExpectedLines) {
				if (!first) {
					sb.append(",");
				}
				sb.append(lineNbr);
				first = false;
			}
			sb.append(log.getLineSeperator());
		} else if (firstDiff != null) {
			sb.append(messages.getIndent() + messages.getExpected() +
					log.getLineSeperator());
			int nbr = firstDiff.getExpectedLineNbr();
			I_TextLines expectedLines = result.getExpectedLines();
			String line = expectedLines.getLine(nbr);
			sb.append(messages.getIndent() + messages.getIndent() + "'" + line + "'" +
					log.getLineSeperator());
			I_DiffIndexesPair pair =  firstDiff.getIndexes();
			
			sb.append(messages.getIndent() + messages.getDifferences() +
					log.getLineSeperator());
			I_DiffIndexes expectedLineDiff =  pair.getExpected();
			String [] diffs = expectedLineDiff.getDifferences(line);
			for (String dif: diffs) {
				sb.append(messages.getIndent() + messages.getIndent() + "'" + dif + "'" +
						log.getLineSeperator());
			}
		} else {
			sb.append(messages.getIndent() + messages.getExpected() +
					log.getLineSeperator());
			
			sb.append(messages.getIndent() + messages.getIndent() + "'" + expectedValue + "'"
					+ log.getLineSeperator());
		}
		
		if (missingActualLines.size() > 1) {
			sb.append(messages.getTheFollowingActualLineNumberNotExpected());
			boolean first = true;
			for (Integer lineNbr: missingActualLines) {
				if (!first) {
					sb.append(",");
				}
				sb.append(lineNbr);
				first = false;
			}
			sb.append(log.getLineSeperator());
		} else if (firstDiff != null) {
			sb.append(messages.getIndent() + messages.getActual() +
					log.getLineSeperator());
			
			int nbr = firstDiff.getActualLineNbr();
			I_TextLines actualLines = result.getActualLines();
			String line = actualLines.getLine(nbr);
			sb.append(messages.getIndent() + messages.getIndent() + "'" + line + "'" +
					log.getLineSeperator());
			I_DiffIndexesPair pair =  firstDiff.getIndexes();
			
			sb.append(messages.getIndent() + messages.getDifferences() +
					log.getLineSeperator());
			I_DiffIndexes actualLineDiff =  pair.getActual();
			String [] diffs = actualLineDiff.getDifferences(line);
			for (String dif: diffs) {
				sb.append(messages.getIndent() + messages.getIndent() + "'" + dif + "'" +
						log.getLineSeperator());
			}
		} else {
			sb.append(messages.getIndent() + messages.getActual() +
					log.getLineSeperator());
			
			sb.append(messages.getIndent() + messages.getIndent() + "'" + actualValue + "'"
					+ log.getLineSeperator());
		}
	}
	
	public void addThrownMessages(I_AssertThrownFailure atf, StringBuilder sb) {
		sb.append(messages.getIndent() + atf.getFailureReason() +
				log.getLineSeperator());
		
		I_ThrowableInfo exp = atf.getExpected();
		I_ThrowableInfo act = atf.getActual();
		
		if (act == null) {
			sb.append(messages.getIndent() + messages.getExpected() + log.getLineSeperator());
			sb.append(messages.getIndent() + exp.getClassName() + log.getLineSeperator());
			
			sb.append(messages.getIndent() + messages.getActual() + log.getLineSeperator());
			sb.append(messages.getIndent() + "null" +  log.getLineSeperator());
		} else {
			int whichOne = atf.getThrowable();
			StringBuilder indent = new StringBuilder();
			indent.append(messages.getIndent());
			
			String actualClass = null;
			String expectedClass = exp.getClassName();
			boolean classMismatch = false;
			for (int i = 0; i < whichOne; i++) {
				sb.append(indent.toString());
				indent.append(messages.getIndent());
				expectedClass = exp.getClassName();
				sb.append(exp.getClassName() + log.getLineSeperator());
				if (act == null) {
					actualClass = "null";
					classMismatch = true;
					break;
				} else {
					actualClass = act.getClassName();
					if (!expectedClass.equals(actualClass)) {
						classMismatch = true;
						break;
					} 
				}
				I_ThrowableInfo nextExp = exp.getCause();
				if (nextExp == null) {
					break;
				} else {
					exp = nextExp;
				}
				act = act.getCause();
			}
			if (classMismatch) {
				sb.append(messages.getIndent() + messages.getExpected() + log.getLineSeperator());
				sb.append(messages.getIndent() + expectedClass +  log.getLineSeperator());
				
				sb.append(messages.getIndent() + messages.getActual() + log.getLineSeperator());
				sb.append(messages.getIndent() + actualClass+  log.getLineSeperator());
			} else {
				String expString = exp.getMessage();
				String actString = act.getMessage();
				addStringCompare(expString, actString, sb);
			}
			//compare class
		}
	}
}
