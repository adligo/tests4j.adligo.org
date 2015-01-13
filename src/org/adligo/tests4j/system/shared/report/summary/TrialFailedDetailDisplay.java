package org.adligo.tests4j.system.shared.report.summary;

import org.adligo.tests4j.models.shared.results.I_TestResult;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
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
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.shared.output.DefaultLog;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.trials.I_SourceFileTrial;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TrialFailedDetailDisplay {
  private final I_Tests4J_Constants constants_;
  private final I_Tests4J_ReportMessages messages_;
  private final I_Tests4J_Log log_;
	
	private Map<I_AssertType,Runnable> switchReplacement_;
	private StringBuilder sb_;
	private I_TestFailure testFailure_;
	private I_TrialResult trialResult_;
	
	public TrialFailedDetailDisplay(I_Tests4J_Constants constants, I_Tests4J_Log pLog) {
	  constants_ = constants;
	  messages_ = constants_.getReportMessages();
	  
		log_ = pLog;
		//this is a dumb way to obvoid using a switch
		//statement because it puts a java.lang.NoSuchFieldError
		// in the class, which isn't implemented by GWT
		switchReplacement_ = new HashMap<I_AssertType, Runnable>();
		Runnable thownRun = new Runnable() {
			@Override
			public void run() {
				I_AssertThrownFailure atf = (I_AssertThrownFailure) testFailure_;
				addThrownMessages(atf,sb_);
			}
		};
		switchReplacement_.put(AssertType.AssertThrown, thownRun);
		switchReplacement_.put(AssertType.AssertThrownUniform, thownRun);
		
		switchReplacement_.put(AssertType.AssertContains, new Runnable() {
			public void run() {
				I_AssertCompareFailure ac = (I_AssertCompareFailure) testFailure_;
				addExpected(ac.getExpectedValue(), ac.getExpectedClass());
			}
		});
		
		switchReplacement_.put(AssertType.AssertReferences, new Runnable() {
			
			@Override
			public void run() {
				addReferenceFailure(
						 trialResult_, (I_AllowedReferencesFailure) testFailure_);
			}
		});
		 
		switchReplacement_.put(AssertType.AssertCircularDependency, new Runnable() {
			
			@Override
			public void run() {
				 addCircularDependencyFailure(
						 trialResult_, (I_CircularDependencyFailure) testFailure_);
			}
		});
	}

	public void logTrialFailure(I_TrialResult trial) {
		if ( !log_.isLogEnabled(TrialFailedDetailDisplay.class)) {
			return;
		}
		trialResult_ = trial;
		sb_ = new StringBuilder();
		
		String trialName = trial.getName();
		sb_.append(trialName + messages_.getFailedEOS() + log_.getLineSeperator());
		
		List<I_TrialFailure> failures =  trial.getFailures();
		if (failures != null) {
			for (I_TrialFailure failure: failures) {
				sb_.append(messages_.getIndent() + failure.getMessage() + log_.getLineSeperator());
				
				String detail = failure.getFailureDetail();
				if (detail != null) {
					sb_.append(messages_.getIndent() +  messages_.getIndent() + detail + log_.getLineSeperator());
				}
			}
		}
		List<I_TestResult> testResults = trial.getResults();
		for (I_TestResult tr: testResults) {
			if (!tr.isPassed() && !tr.isIgnored()) {
				String testName = tr.getName();
				
				sb_.append(messages_.getIndent() + trialName + "."  + testName + "()" + messages_.getFailedEOS() +
						log_.getLineSeperator());
				testFailure_ = tr.getFailure();
				if (testFailure_ != null) {
					sb_.append(messages_.getIndent() + testFailure_.getFailureMessage() + log_.getLineSeperator());
					I_AssertType type =  testFailure_.getAssertType();
					
					if (type != null) {
						Runnable run = switchReplacement_.get(type);
						if (run != null) {
							run.run();
						} else {
						  I_TestFailureType tft =  testFailure_.getType();
						  if (TestFailureType.AssertThrownFailure.equals(TestFailureType.get(tft))) {
						    I_AssertThrownFailure atf = (I_AssertThrownFailure) testFailure_;
						    sb_.append(messages_.getIndent() + messages_.getIndent() + 
						        atf.getFailureDetail());
						  } else {
  							I_AssertCompareFailure acf = (I_AssertCompareFailure) testFailure_;
  							if (String.class.getName().equals(acf.getExpectedClass())) {
  								if (String.class.getName().equals(acf.getActualClass())) {
  									addStringCompare(acf.getExpectedValue(), acf.getActualValue());
  								} else  {
  									addCompareFailure(acf);
  								}
  							} else if (I_SourceFileTrial.TEST_MIN_COVERAGE.equals(testName)) {
  								addCoverageFailure(trial, acf);
  							} else {
  								addCompareFailure(acf);
  							}
  						}
						}
					}
					String failureDetail = testFailure_.getFailureDetail();
					if (failureDetail != null) {
						sb_.append(failureDetail);
					}
				}
			}
		}
		log_.log(sb_.toString());
	}

	private void addExpected(String exp, String expClass) {
		sb_.append(messages_.getIndent() + messages_.getExpected() + log_.getLineSeperator());
		sb_.append(messages_.getIndent() + messages_.getIndent()+ messages_.getClassHeadding() + 
				expClass + log_.getLineSeperator());
		sb_.append(messages_.getIndent() + messages_.getIndent() + "'" + exp + "'" +
				log_.getLineSeperator());
	}
	
	private void addCompareFailure(I_AssertCompareFailure acf) {
		addExpected(acf.getExpectedValue(), acf.getExpectedClass());
	
		sb_.append(messages_.getIndent() + messages_.getActual() + log_.getLineSeperator());
		sb_.append(messages_.getIndent() + messages_.getIndent()+ messages_.getClassHeadding() + 
					acf.getActualClass()
					+ log_.getLineSeperator());
		sb_.append(messages_.getIndent() + messages_.getIndent() + "'" + acf.getActualValue() + "'"
				+ log_.getLineSeperator());
	}
	
	private void addCoverageFailure(I_TrialResult tr, I_AssertCompareFailure acf) {
		StackTraceElement[] stack = createSourceClassTrialStack(tr);
		
		AssertionFailedException afe = new AssertionFailedException(sb_.toString() +
				messages_.getIndent() + messages_.getExpected() + " " + acf.getExpectedValue() + 
				messages_.getIndent() + messages_.getActual() + " " + acf.getActualValue());
		afe.setStackTrace(stack);
		log_.onThrowable(afe);
		
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
		message =  messages_.getIndent() +  messages_.getIndent() +
				"@AllowedReferences: " + groupNames.toString() + log_.getLineSeperator();
		I_FieldSignature field = tf.getField();
		I_MethodSignature method = tf.getMethod();
		if (field != null) {
			message = message + messages_.getIndent() + messages_.getIndent()+
					"SourceClass: " + tf.getSourceClass().getName() + 
					".java" + log_.getLineSeperator() +
					messages_.getIndent() + messages_.getIndent()+
					" called field " + tf.getCalledClass() + ". " + field;
		} else if (method != null){
			message = message + messages_.getIndent() + messages_.getIndent()+
					"SourceClass: " + tf.getSourceClass().getName() + ".java" + 
					log_.getLineSeperator() +
					messages_.getIndent() + messages_.getIndent()+
					" called method " + tf.getCalledClass() + ". " + method;
		} else {
			message = message + messages_.getIndent() + messages_.getIndent()+
					"SourceClass: " + tf.getSourceClass().getName() + ".java" + 
					log_.getLineSeperator() +
					messages_.getIndent() + messages_.getIndent()+
					" called class " + tf.getCalledClass() + ". ";
		}
		
		AllowedReferencesFailureException dfe = 
				new AllowedReferencesFailureException(tf.getFailureMessage() + log_.getLineSeperator() +
						message,
						stack);
		log_.onThrowable(dfe);
	}
	
	
	private void addCircularDependencyFailure(I_TrialResult trial, I_CircularDependencyFailure tf) {
		StackTraceElement[] stack = createSourceClassTrialStack(trial, tf);
		
		String message = null;
		
		List<String> classNames = tf.getClassesOutOfBounds();
		message =  messages_.getIndent() +  messages_.getIndent() +
				"SourceClass: " + tf.getSourceClass().getName() + log_.getLineSeperator() +
				messages_.getIndent() +  messages_.getIndent() +
				"CircularDependencies: " + tf.getAllowedType() + " " +
					classNames.toString() + log_.getLineSeperator();
		
		
		AllowedReferencesFailureException dfe = 
				new AllowedReferencesFailureException(tf.getFailureMessage() + log_.getLineSeperator() +
						message,
						stack);
		log_.onThrowable(dfe);
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
			StackTraceElement[] stackClone = ArrayUtils.copyOf(stack, new StackTraceElement[stack.length]);
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
			StackTraceElement[] stackClone = ArrayUtils.copyOf(stack, new StackTraceElement[stack.length]);
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
	@SuppressWarnings("boxing")
  private void addStringCompare(String expectedValue, String actualValue) {
		TextLinesCompare tlc = new TextLinesCompare();
		
		
		I_TextLinesCompareResult result =  tlc.compare(constants_, expectedValue, actualValue, true);
		
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
		  String message = DefaultLog.orderLine(constants_.isLeftToRight(),
		      messages_.getIndent(), messages_.getTheFollowingExpectedLineNumbersWereMissing());
			sb_.append(message);
			sb_.append(log_.getLineSeperator());
      I_TextLines actualLines =  result.getExpectedLines();
      for (Integer lineNbr: missingExpectedLines) {
        String line = actualLines.getLine(lineNbr);
        //line numbers are zero based in the data
        message = DefaultLog.orderLine(constants_.isLeftToRight(),
            messages_.getIndent(), "" + (lineNbr + 1) ,":"," ",line);
        sb_.append(message);
        sb_.append(log_.getLineSeperator());
      }
      
		} else if (firstDiff != null) {
			sb_.append(messages_.getIndent() + messages_.getExpected() +
					log_.getLineSeperator());
			int nbr = firstDiff.getExpectedLineNbr();
			I_TextLines expectedLines = result.getExpectedLines();
			String line = expectedLines.getLine(nbr);
			sb_.append(messages_.getIndent() + messages_.getIndent() + "'" + line + "'" +
					log_.getLineSeperator());
			I_DiffIndexesPair pair =  firstDiff.getIndexes();
			
			sb_.append(messages_.getIndent() + messages_.getDifferences() +
					log_.getLineSeperator());
			I_DiffIndexes expectedLineDiff =  pair.getExpected();
			String [] diffs = expectedLineDiff.getDifferences(line);
			for (String dif: diffs) {
				sb_.append(messages_.getIndent() + messages_.getIndent() + "'" + dif + "'" +
						log_.getLineSeperator());
			}
		} else {
			sb_.append(messages_.getIndent() + messages_.getExpected() +
					log_.getLineSeperator());
			
			sb_.append(messages_.getIndent() + messages_.getIndent() + "'" + expectedValue + "'"
					+ log_.getLineSeperator());
		}
		
		if (missingActualLines.size() > 1) {
		  String message = DefaultLog.orderLine(constants_.isLeftToRight(),
		      messages_.getIndent(), messages_.getTheFollowingActualLineNumberNotExpected());
			sb_.append(message);

			sb_.append(log_.getLineSeperator());
			I_TextLines actualLines =  result.getActualLines();
			for (Integer lineNbr: missingActualLines) {
			  String line = actualLines.getLine(lineNbr);
			  //line numbers are zero based in the data
			  message = DefaultLog.orderLine(constants_.isLeftToRight(), 
			      messages_.getIndent(), "" + (lineNbr + 1), ":", " ", line);
			  sb_.append(message);
			  sb_.append(log_.getLineSeperator());
      }
		} else if (firstDiff != null) {
			sb_.append(messages_.getIndent() + messages_.getActual() +
					log_.getLineSeperator());
			
			int nbr = firstDiff.getActualLineNbr();
			I_TextLines actualLines = result.getActualLines();
			String line = actualLines.getLine(nbr);
			String message = DefaultLog.orderLine(constants_.isLeftToRight(), 
			    messages_.getIndent(), messages_.getIndent(), "'" + line + "'");
			sb_.append(message + log_.getLineSeperator());
			I_DiffIndexesPair pair =  firstDiff.getIndexes();
			
			sb_.append(messages_.getIndent() + messages_.getDifferences() +
					log_.getLineSeperator());
			I_DiffIndexes actualLineDiff =  pair.getActual();
			String [] diffs = actualLineDiff.getDifferences(line);
			for (String dif: diffs) {
				sb_.append(messages_.getIndent() + messages_.getIndent() + "'" + dif + "'" +
						log_.getLineSeperator());
			}
		} else {
			sb_.append(messages_.getIndent() + messages_.getActual() +
					log_.getLineSeperator());
			
			sb_.append(messages_.getIndent() + messages_.getIndent() + "'" + actualValue + "'"
					+ log_.getLineSeperator());
		}
	}
	
	public void addThrownMessages(I_AssertThrownFailure atf, StringBuilder sb) {
	  if (atf == null) {
	    return;
	  }
		sb_.append(messages_.getIndent() + atf.getFailureReason() +
				log_.getLineSeperator());
		
		I_ThrowableInfo exp = atf.getExpected();
		I_ThrowableInfo act = atf.getActual();
		
		if (act == null) {
			sb_.append(messages_.getIndent() + messages_.getExpected() + log_.getLineSeperator());
			sb_.append(messages_.getIndent() + exp.getClassName() + log_.getLineSeperator());
			
			sb_.append(messages_.getIndent() + messages_.getActual() + log_.getLineSeperator());
			sb_.append(messages_.getIndent() + "null" +  log_.getLineSeperator());
		} else {
			int whichOne = atf.getThrowable();
			StringBuilder indent = new StringBuilder();
			indent.append(messages_.getIndent());
			
			String actualClass = null;
			String expectedClass = exp.getClassName();
			boolean classMismatch = false;
			for (int i = 0; i < whichOne; i++) {
				sb_.append(indent.toString());
				indent.append(messages_.getIndent());
				expectedClass = exp.getClassName();
				sb_.append(exp.getClassName() + log_.getLineSeperator());
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
				sb_.append(messages_.getIndent() + messages_.getExpected() + log_.getLineSeperator());
				sb_.append(messages_.getIndent() + expectedClass +  log_.getLineSeperator());
				
				sb_.append(messages_.getIndent() + messages_.getActual() + log_.getLineSeperator());
				sb_.append(messages_.getIndent() + actualClass+  log_.getLineSeperator());
			} else {
				String expString = exp.getMessage();
				String actString = act.getMessage();
				addStringCompare(expString, actString);
			}
			//compare class
		}
	}
}
