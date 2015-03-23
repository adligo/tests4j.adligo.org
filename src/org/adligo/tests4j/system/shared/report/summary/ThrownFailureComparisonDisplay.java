package org.adligo.tests4j.system.shared.report.summary;

import org.adligo.tests4j.shared.asserts.common.I_AssertThrownFailure;
import org.adligo.tests4j.shared.asserts.common.I_MatchType;
import org.adligo.tests4j.shared.asserts.common.I_ThrowableInfo;
import org.adligo.tests4j.shared.asserts.common.MatchType;
import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class ThrownFailureComparisonDisplay {
  private final I_Tests4J_Constants constants_;
  private final boolean ltr_;
  private final I_Tests4J_ReportMessages messages_;
  private final I_Tests4J_Log log_;
  private final TextDataDisplay textDataDisplay_;
  private StringBuilder expBuilder_;
  private StringBuilder actBuilder_;
  
  public ThrownFailureComparisonDisplay(I_Tests4J_Constants constants, I_Tests4J_Log log) {
    constants_ = constants;
    ltr_ = constants.isLeftToRight();
    messages_ = constants_.getReportMessages();
    
    log_ = log;
    textDataDisplay_ = new TextDataDisplay(constants, log);
    
  }
  
  public void addThrownMessages(I_AssertThrownFailure atf, StringBuilder sb) {
    if (atf == null) {
      return;
    }
    
    I_ThrowableInfo exp = atf.getExpected();
    I_ThrowableInfo act = atf.getActual();
    expBuilder_ = new StringBuilder();
    actBuilder_ = new StringBuilder();
    
    StringBuilder expectedBuilder = new StringBuilder();
    StringBuilder actualBuilder = new StringBuilder();
    expectedBuilder.append(StringMethods.orderLine(ltr_, messages_.getIndent(),
        messages_.getExpected()));
    expectedBuilder.append(log_.lineSeparator());
    
    actualBuilder.append(StringMethods.orderLine(ltr_, messages_.getIndent(),
        messages_.getActual()));
    actualBuilder.append(log_.lineSeparator());
    
    if (act == null) {
      expectedBuilder.append(StringMethods.orderLine(ltr_, messages_.getIndent(),
          exp.getClassName()));
      expectedBuilder.append(log_.lineSeparator());
      
      actualBuilder.append(StringMethods.orderLine(ltr_, messages_.getIndent(),
          "null"));
      actualBuilder.append(log_.lineSeparator());
    } else {
      int whichOne = atf.getThrowable();
      StringBuilder indent = new StringBuilder();
      indent.append(messages_.getIndent());
      
      String expString = null;
      String actString = null;
      I_MatchType matchType = null;
      
      boolean classMatch = true;
      String currentIndent = indent.toString();
      for (int i = 0; i < whichOne; i++) {
        
        String expClass = exp.getClassName();
        expectedBuilder.append(StringMethods.orderLine(ltr_, currentIndent,
            expClass));
        expectedBuilder.append(log_.lineSeparator());
        expString = exp.getMessage();
        matchType = exp.getMatchType();
        
        exp = exp.getCause();
        
        String actClass = null;
        if (act == null) {
          actualBuilder.append(StringMethods.orderLine(ltr_, currentIndent,
              "null"));
          actualBuilder.append(log_.lineSeparator());
        } else {
          actClass = act.getClassName();
          actualBuilder.append(StringMethods.orderLine(ltr_, currentIndent,
              actClass));
          actualBuilder.append(log_.lineSeparator());
          actString = act.getMessage();
          act = act.getCause();
        }
        if (expClass == null || actClass == null || !expClass.equals(actClass)) {
          classMatch = false;
          break;
        }
        indent.append(messages_.getIndent());
        currentIndent = indent.toString();
      }
      
      if (classMatch) {
        if (matchType != null) {
          MatchType matchTypeEnum = MatchType.get(matchType);
          switch (matchTypeEnum) {
            case CONTAINS:
              I_Tests4J_ReportMessages messages = constants_.getReportMessages();
              expBuilder_.append(StringMethods.orderLine(ltr_, currentIndent,
                  messages.getTheFollowingTextWasNotFoundInTheActualText()));
              expBuilder_.append(log_.lineSeparator());
              
              textDataDisplay_.display(expBuilder_, expString, currentIndent);
              textDataDisplay_.display(actBuilder_, actString, currentIndent);
              break;
            case NULL:
              expBuilder_.append(StringMethods.orderLine(constants_.isLeftToRight(),
                  currentIndent, "null"));
              expBuilder_.append(log_.lineSeparator());
              textDataDisplay_.display(actBuilder_, actString, currentIndent);
              
              break;  
            case ANY:
              //any should never occur here, if it occurs do the default;
            case EQUALS:
            default:
              displayDefault(expString, actString, currentIndent);
          }
        } else {
          displayDefault(expString, actString, currentIndent);
        }
      }
    }
    String exp1 = expectedBuilder.toString();
    sb.append(exp1);
    String exp2 = expBuilder_.toString();
    sb.append(exp2);
    String act1 = actualBuilder.toString();
    sb.append(act1);
    String act2 = actBuilder_.toString();
    sb.append(act2);
  }

  public void displayDefault(String expString, String actString, String currentIndent) {
    if (actString == null || expString == null) {
        textDataDisplay_.display(expBuilder_, expString, currentIndent);
        textDataDisplay_.display(actBuilder_, actString, currentIndent);
    } else {
      TextLineCompareDisplay display = new TextLineCompareDisplay(constants_, log_);
      display.setActualBuilder(actBuilder_);
      display.setExpectedBuilder(expBuilder_);
      display.setIndent(currentIndent);
      display.addStringCompare(expString, actString);
    }
  }
}
