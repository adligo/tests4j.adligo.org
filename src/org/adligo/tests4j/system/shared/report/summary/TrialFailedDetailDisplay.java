package org.adligo.tests4j.system.shared.report.summary;

import org.adligo.tests4j.models.shared.results.I_TestResult;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.shared.asserts.common.AssertThrownFailure;
import org.adligo.tests4j.shared.asserts.common.AssertType;
import org.adligo.tests4j.shared.asserts.common.AssertionFailedException;
import org.adligo.tests4j.shared.asserts.common.I_AssertCompareFailure;
import org.adligo.tests4j.shared.asserts.common.I_AssertThrownFailure;
import org.adligo.tests4j.shared.asserts.common.I_AssertType;
import org.adligo.tests4j.shared.asserts.common.I_SourceTestFailure;
import org.adligo.tests4j.shared.asserts.common.I_TestFailure;
import org.adligo.tests4j.shared.asserts.common.I_TestFailureType;
import org.adligo.tests4j.shared.asserts.common.I_ThrowableInfo;
import org.adligo.tests4j.shared.asserts.common.TestFailureType;
import org.adligo.tests4j.shared.asserts.line_text.I_DiffIndexes;
import org.adligo.tests4j.shared.asserts.line_text.I_DiffIndexesPair;
import org.adligo.tests4j.shared.asserts.line_text.I_LineDiff;
import org.adligo.tests4j.shared.asserts.line_text.I_LineDiffType;
import org.adligo.tests4j.shared.asserts.line_text.I_TextLines;
import org.adligo.tests4j.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.shared.asserts.line_text.LineDiffType;
import org.adligo.tests4j.shared.asserts.line_text.TextLinesCompare;
import org.adligo.tests4j.shared.asserts.reference.I_AllowedReferencesFailure;
import org.adligo.tests4j.shared.asserts.reference.I_CircularDependencyFailure;
import org.adligo.tests4j.shared.asserts.reference.I_FieldSignature;
import org.adligo.tests4j.shared.asserts.reference.I_MethodSignature;
import org.adligo.tests4j.shared.common.ArrayUtils;
import org.adligo.tests4j.shared.common.ClassMethods;
import org.adligo.tests4j.shared.common.Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.trials.I_SourceFileTrial;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TrialFailedDetailDisplay {
	private I_Tests4J_Log log;
	private I_Tests4J_ReportMessages messages =  Tests4J_Constants.CONSTANTS.getReportMessages();
	private Map<I_AssertType,Runnable> switchReplacement;
	private StringBuilder sb;
	private I_TestFailure testFailure;
	private I_TrialResult trialResult;
	
	public TrialFailedDetailDisplay(I_Tests4J_Log pLog) {
		log = pLog;
		//this is a dumb way to obvoid using a switch
		//statement because it puts a java.lang.NoSuchFieldError
		// in the class, which isn't implemented by GWT
		switchReplacement = new HashMap<I_AssertType, Runnable>();
		Runnable thownRun = new Runnable() {
			@Override
			public void run() {
				I_AssertThrownFailure atf = (I_AssertThrownFailure) testFailure;
				addThrownMessages(atf,sb);
			}
		};
		switchReplacement.put(AssertType.AssertThrown, thownRun);
		switchReplacement.put(AssertType.AssertThrownUniform, thownRun);
		
		switchReplacement.put(AssertType.AssertContains, new Runnable() {
			public void run() {
				I_AssertCompareFailure ac = (I_AssertCompareFailure) testFailure;
				addExpected(ac.getExpectedValue(), ac.getExpectedClass(), sb);
			}
		});
		
		switchReplacement.put(AssertType.AssertReferences, new Runnable() {
			
			@Override
			public void run() {
				addReferenceFailure(
						 trialResult, (I_AllowedReferencesFailure) testFailure);
			}
		});
		 
		switchReplacement.put(AssertType.AssertCircularDependency, new Runnable() {
			
			@Override
			public void run() {
				 addCircularDependencyFailure(
						 trialResult, (I_CircularDependencyFailure) testFailure);
			}
		});
	}

	public void logTrialFailure(I_TrialResult trial) {
		if ( !log.isLogEnabled(TrialFailedDetailDisplay.class)) {
			return;
		}
		trialResult = trial;
		
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
				testFailure = tr.getFailure();
				if (testFailure != null) {
					sb.append(messages.getIndent() + testFailure.getFailureMessage() + log.getLineSeperator());
					I_AssertType type =  testFailure.getAssertType();
					
					if (type != null) {
						Runnable run = switchReplacement.get(type);
						if (run != null) {
							run.run();
						} else {
						  I_TestFailureType tft =  testFailure.getType();
						  if (TestFailureType.AssertThrownFailure.equals(TestFailureType.get(tft))) {
						    I_AssertThrownFailure atf = (I_AssertThrownFailure) testFailure;
						    sb.append(messages.getIndent() + messages.getIndent() + 
						        atf.getFailureDetail());
						  } else {
  						  I_AssertType ast = testFailure.getAssertType();
  						  
  						  
  							I_AssertCompareFailure acf = (I_AssertCompareFailure) testFailure;
  							if (String.class.getName().equals(acf.getExpectedClass())) {
  								if (String.class.getName().equals(acf.getActualClass())) {
  									addStringCompare(acf.getExpectedValue(), acf.getActualValue(), sb);
  								} else  {
  									addCompareFailure(acf, sb);
  								}
  							} else if (I_SourceFileTrial.TEST_MIN_COVERAGE.equals(testName)) {
  								addCoverageFailure(trial, acf, sb);
  							} else {
  								addCompareFailure(acf, sb);
  							}
  						}
						}
					}
					String failureDetail = testFailure.getFailureDetail();
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
	
	private void addCoverageFailure(I_TrialResult tr, I_AssertCompareFailure acf, StringBuilder sb) {
		StackTraceElement[] stack = createSourceClassTrialStack(tr);
		
		AssertionFailedException afe = new AssertionFailedException(sb.toString() +
				messages.getIndent() + messages.getExpected() + " " + acf.getExpectedValue() + 
				messages.getIndent() + messages.getActual() + " " + acf.getActualValue());
		afe.setStackTrace(stack);
		log.onThrowable(afe);
		
	}
	
	private void addReferenceFailure(I_TrialResult trial, I_AllowedReferencesFailure tf) {
		IllegalStateException x = new IllegalStateException();
		x.fillInStackTrace();
		StackTraceElement[] stack = x.getStackTrace();
		
		String trialClassName = trial.getTrialClassName();
		StackTraceElement top = new StackTraceElement(
				trialClassName, "<init>", 
				ClassMethods.getSimpleName(trialClassName) + ".java",
				1);
		if (stack.length == 0) {
			stack = new StackTraceElement[1];
		}
		stack[0] = top;
		
		String message = null;
		
		List<String> groupNames = tf.getGroupNames();
		message =  messages.getIndent() +  messages.getIndent() +
				"@AllowedReferences: " + groupNames.toString() + log.getLineSeperator();
		I_FieldSignature field = tf.getField();
		I_MethodSignature method = tf.getMethod();
		if (field != null) {
			message = message + messages.getIndent() + messages.getIndent()+
					"SourceClass: " + tf.getSourceClass().getName() + 
					".java" + log.getLineSeperator() +
					messages.getIndent() + messages.getIndent()+
					" called field " + tf.getCalledClass() + ". " + field;
		} else if (method != null){
			message = message + messages.getIndent() + messages.getIndent()+
					"SourceClass: " + tf.getSourceClass().getName() + ".java" + 
					log.getLineSeperator() +
					messages.getIndent() + messages.getIndent()+
					" called method " + tf.getCalledClass() + ". " + method;
		} else {
			message = message + messages.getIndent() + messages.getIndent()+
					"SourceClass: " + tf.getSourceClass().getName() + ".java" + 
					log.getLineSeperator() +
					messages.getIndent() + messages.getIndent()+
					" called class " + tf.getCalledClass() + ". ";
		}
		
		DependencyFailureException dfe = 
				new DependencyFailureException(tf.getFailureMessage() + log.getLineSeperator() +
						message,
						stack);
		log.onThrowable(dfe);
	}
	
	
	private void addCircularDependencyFailure(I_TrialResult trial, I_CircularDependencyFailure tf) {
		StackTraceElement[] stack = createSourceClassTrialStack(trial, tf);
		
		String message = null;
		
		List<String> classNames = tf.getClassesOutOfBounds();
		message =  messages.getIndent() +  messages.getIndent() +
				"SourceClass: " + tf.getSourceClass().getName() + log.getLineSeperator() +
				messages.getIndent() +  messages.getIndent() +
				"CircularDependencies: " + tf.getAllowedType() + " " +
					classNames.toString() + log.getLineSeperator();
		
		
		DependencyFailureException dfe = 
				new DependencyFailureException(tf.getFailureMessage() + log.getLineSeperator() +
						message,
						stack);
		log.onThrowable(dfe);
	}

	public StackTraceElement[] createSourceClassTrialStack(I_TrialResult trial,
			I_SourceTestFailure tf) {
		IllegalStateException x = new IllegalStateException();
		x.fillInStackTrace();
		StackTraceElement[] stack = x.getStackTrace();
		//generate a clickable stack trace for the trial and source class
		String trialClassName = trial.getTrialClassName();
		StackTraceElement top = new StackTraceElement(
				trialClassName, "<init>", 
				ClassMethods.getSimpleName(trialClassName) + ".java",
				1);
		
		Class<?> sc = tf.getSourceClass();
		String sourceClassName = sc.getName();
		StackTraceElement second = new StackTraceElement(
				sourceClassName, "<init>", 
				ClassMethods.getSimpleName(sourceClassName) + ".java",
				1);
		if (stack.length <= 2) {
			stack = new StackTraceElement[2];
		} else {
			//move the stack down so we can add 2 elements at the top
			StackTraceElement[] stackClone = ArrayUtils.copyOf(stack);
			for (int i = 0; i < stack.length; i++) {
				if (i + 2 < stack.length) {
					StackTraceElement ste = stackClone[i];
					stack[i + 2] = ste;
				}
			}
		}
		stack[0] = top;
		stack[1] = second;
		return stack;
	}
	
	public StackTraceElement[] createSourceClassTrialStack(I_TrialResult trial) {
		IllegalStateException x = new IllegalStateException();
		x.fillInStackTrace();
		StackTraceElement[] stack = x.getStackTrace();
		//generate a clickable stack trace for the trial and source class
		String trialClassName = trial.getTrialClassName();
		StackTraceElement top = new StackTraceElement(
				trialClassName, "<init>", 
				ClassMethods.getSimpleName(trialClassName) + ".java",
				1);
	
		if (stack.length <= 1) {
			stack = new StackTraceElement[1];
		} else {
			//move the stack down so we can add 2 elements at the top
			StackTraceElement[] stackClone = ArrayUtils.copyOf(stack);
			for (int i = 0; i < stack.length; i++) {
				if (i + 1 < stack.length) {
					StackTraceElement ste = stackClone[i];
					stack[i + 1] = ste;
				}
			}
		}
		stack[0] = top;
		return stack;
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
			if (LineDiffType.PartialMatch == ldt) {
				if( firstDiff == null) {
					firstDiff = diff;
				}
			} else if (LineDiffType.MissingActualLine == ldt) {
				missingActualLines.add(diff.getActualLineNbr());
			} else if (LineDiffType.MissingExpectedLine == ldt) {
				missingExpectedLines.add(diff.getExpectedLineNbr());
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
	  if (atf == null) {
	    return;
	  }
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
