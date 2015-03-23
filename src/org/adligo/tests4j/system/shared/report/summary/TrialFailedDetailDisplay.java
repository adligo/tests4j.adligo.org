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
import org.adligo.tests4j.shared.asserts.common.TestFailureType;
import org.adligo.tests4j.shared.asserts.reference.I_AllowedReferencesFailure;
import org.adligo.tests4j.shared.asserts.reference.I_CircularDependencyFailure;
import org.adligo.tests4j.shared.asserts.reference.I_FieldSignature;
import org.adligo.tests4j.shared.asserts.reference.I_MethodSignature;
import org.adligo.tests4j.shared.common.ArrayUtils;
import org.adligo.tests4j.shared.common.ClassMethods;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.trials.I_SourceFileTrial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrialFailedDetailDisplay {
  private final I_Tests4J_Constants constants_;
  private final I_Tests4J_ReportMessages messages_;
  private final I_Tests4J_Log log_;
	
	private Map<I_AssertType,Runnable> switchReplacement_;
	private StringBuilder sb_;
	private I_TestFailure testFailure_;
	private I_TrialResult trialResult_;
	private ThrownFailureComparisonDisplay thrownFailureComparisonDisplay_;
	
	public TrialFailedDetailDisplay(I_Tests4J_Constants constants, I_Tests4J_Log log) {
	  constants_ = constants;
	  messages_ = constants_.getReportMessages();
	  
		log_ = log;
		thrownFailureComparisonDisplay_ = new ThrownFailureComparisonDisplay(constants, log);
		//this is a dumb way to obvoid using a switch
		//statement because it puts a java.lang.NoSuchFieldError
		// in the class, which isn't implemented by GWT
		switchReplacement_ = new HashMap<I_AssertType, Runnable>();
		Runnable thownRun = new Runnable() {
			@Override
			public void run() {
				I_AssertThrownFailure atf = (I_AssertThrownFailure) testFailure_;
				thrownFailureComparisonDisplay_.addThrownMessages(atf, sb_);
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
		sb_.append(trialName + messages_.getFailedEOS() + log_.lineSeparator());
		
		List<I_TrialFailure> failures =  trial.getFailures();
		if (failures != null) {
			for (I_TrialFailure failure: failures) {
				sb_.append(messages_.getIndent() + failure.getMessage() + log_.lineSeparator());
				
				String detail = failure.getFailureDetail();
				if (detail != null) {
					sb_.append(messages_.getIndent() +  messages_.getIndent() + detail + log_.lineSeparator());
				}
			}
		}
		List<I_TestResult> testResults = trial.getResults();
		for (I_TestResult tr: testResults) {
			if (!tr.isPassed() && !tr.isIgnored()) {
				String testName = tr.getName();
				
				sb_.append(messages_.getIndent() + trialName + "."  + testName + "()" + messages_.getFailedEOS() +
						log_.lineSeparator());
				testFailure_ = tr.getFailure();
				if (testFailure_ != null) {
					sb_.append(messages_.getIndent() + testFailure_.getFailureMessage() + log_.lineSeparator());
					I_AssertType type =  testFailure_.getAssertType();
					
					if (type != null) {
					  AssertType at = AssertType.getType(type);
					  Runnable run = switchReplacement_.get(at);
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
  									TextLineCompareDisplay display = new TextLineCompareDisplay(constants_, log_);
  							    display.addStringCompare(acf.getExpectedValue(), acf.getActualValue());
  							    sb_.append(display.getDefaultResult());
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
		String string = sb_.toString();
		log_.log(string);
	}

	private void addExpected(String exp, String expClass) {
		sb_.append(messages_.getIndent() + messages_.getExpected() + log_.lineSeparator());
		sb_.append(messages_.getIndent() + messages_.getIndent()+ messages_.getClassHeadding() + 
				expClass + log_.lineSeparator());
		sb_.append(messages_.getIndent() + messages_.getIndent() + "'" + exp + "'" +
				log_.lineSeparator());
	}
	
	private void addCompareFailure(I_AssertCompareFailure acf) {
		addExpected(acf.getExpectedValue(), acf.getExpectedClass());
	
		sb_.append(messages_.getIndent() + messages_.getActual() + log_.lineSeparator());
		sb_.append(messages_.getIndent() + messages_.getIndent()+ messages_.getClassHeadding() + 
					acf.getActualClass()
					+ log_.lineSeparator());
		sb_.append(messages_.getIndent() + messages_.getIndent() + "'" + acf.getActualValue() + "'"
				+ log_.lineSeparator());
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
				"@AllowedReferences: " + groupNames.toString() + log_.lineSeparator();
		I_FieldSignature field = tf.getField();
		I_MethodSignature method = tf.getMethod();
		if (field != null) {
			message = message + messages_.getIndent() + messages_.getIndent()+
					"SourceClass: " + tf.getSourceClass().getName() + 
					".java" + log_.lineSeparator() +
					messages_.getIndent() + messages_.getIndent()+
					" called field " + tf.getCalledClass() + ". " + field;
		} else if (method != null){
			message = message + messages_.getIndent() + messages_.getIndent()+
					"SourceClass: " + tf.getSourceClass().getName() + ".java" + 
					log_.lineSeparator() +
					messages_.getIndent() + messages_.getIndent()+
					" called method " + tf.getCalledClass() + ". " + method;
		} else {
			message = message + messages_.getIndent() + messages_.getIndent()+
					"SourceClass: " + tf.getSourceClass().getName() + ".java" + 
					log_.lineSeparator() +
					messages_.getIndent() + messages_.getIndent()+
					" called class " + tf.getCalledClass() + ". ";
		}
		
		AllowedReferencesFailureException dfe = 
				new AllowedReferencesFailureException(tf.getFailureMessage() + log_.lineSeparator() +
						message,
						stack);
		log_.onThrowable(dfe);
	}
	
	
	private void addCircularDependencyFailure(I_TrialResult trial, I_CircularDependencyFailure tf) {
		StackTraceElement[] stack = createSourceClassTrialStack(trial, tf);
		
		String message = null;
		
		List<String> classNames = tf.getClassesOutOfBounds();
		message =  messages_.getIndent() +  messages_.getIndent() +
				"SourceClass: " + tf.getSourceClass().getName() + log_.lineSeparator() +
				messages_.getIndent() +  messages_.getIndent() +
				"CircularDependencies: " + tf.getAllowedType() + " " +
					classNames.toString() + log_.lineSeparator();
		
		
		AllowedReferencesFailureException dfe = 
				new AllowedReferencesFailureException(tf.getFailureMessage() + log_.lineSeparator() +
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
	
	
}
