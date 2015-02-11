package org.adligo.tests4j.system.shared.report.summary;

import com.sun.xml.internal.ws.util.StringUtils;

import org.adligo.tests4j.shared.asserts.line_text.I_DiffIndexes;
import org.adligo.tests4j.shared.asserts.line_text.I_DiffIndexesPair;
import org.adligo.tests4j.shared.asserts.line_text.I_LineDiff;
import org.adligo.tests4j.shared.asserts.line_text.I_LineDiffType;
import org.adligo.tests4j.shared.asserts.line_text.I_TextLines;
import org.adligo.tests4j.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.shared.asserts.line_text.LineDiffType;
import org.adligo.tests4j.shared.asserts.line_text.TextLinesCompare;
import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TextLineCompareDisplay {
  private final I_Tests4J_Constants constants_;
  private final boolean ltr_;
  private final I_Tests4J_ReportMessages messages_;
  private final I_Tests4J_Log log_;
  private final TextDataDisplay textDisplay_;
  private StringBuilder expectedBuilder_;
  private boolean setExpectedBuilder_ = false; 
  private StringBuilder actualBuilder_;
  private boolean setActualBuilder_ = false; 
  private String indent_;
  
  public TextLineCompareDisplay(I_Tests4J_Constants constants, I_Tests4J_Log log) {
    constants_ = constants;
    ltr_ = constants.isLeftToRight();
    messages_ = constants_.getReportMessages();
    log_ = log;
    indent_ = messages_.getIndent();
    textDisplay_ = new TextDataDisplay(constants, log);
  }
  
  @SuppressWarnings("boxing")
  public void addStringCompare(String expectedValue, String actualValue) {
    if (!setExpectedBuilder_) {
      expectedBuilder_ = new StringBuilder();
    } else {
      setExpectedBuilder_ = false;
    }
    if (!setActualBuilder_) {
      actualBuilder_ = new StringBuilder();
    } else {
      setActualBuilder_ = false;
    }
    
    String message = StringMethods.orderLine(ltr_, indent_, messages_.getExpected());
    expectedBuilder_.append(message);
    expectedBuilder_.append(log_.lineSeparator());
    textDisplay_.display(expectedBuilder_, expectedValue, indent_);
    
    
    actualBuilder_.append(StringMethods.orderLine(ltr_, indent_, messages_.getActual()));
    actualBuilder_.append(log_.lineSeparator());
    textDisplay_.display(actualBuilder_, actualValue, indent_);
    
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
      expectedBuilder_.append(StringMethods.orderLine(ltr_,
          indent_, messages_.getTheFollowingExpectedLineNumbersWereMissing()));
      expectedBuilder_.append(log_.lineSeparator());
      I_TextLines actualLines =  result.getExpectedLines();
      for (Integer lineNbr: missingExpectedLines) {
        String line = actualLines.getLine(lineNbr);
        //line numbers are zero based in the data
        message = StringMethods.orderLine(constants_.isLeftToRight(),
            indent_  + messages_.getIndent(), "" + (lineNbr + 1) ,":"," ",
            "'" + line + "'");
        expectedBuilder_.append(message);
        expectedBuilder_.append(log_.lineSeparator());
      }
      
    } else if (firstDiff != null) {

      
      int nbr = firstDiff.getExpectedLineNbr();
      I_TextLines expectedLines = result.getExpectedLines();
      String line = expectedLines.getLine(nbr);

      
      I_DiffIndexesPair pair =  firstDiff.getIndexes();
      
      String diffs = getDifferences(nbr  + 1, line, pair.getExpected());
      if (!StringMethods.isEmpty(diffs)) {
        expectedBuilder_.append(StringMethods.orderLine(ltr_,
            indent_ + messages_.getIndent(), 
            messages_.getDifferences()));
        expectedBuilder_.append(log_.lineSeparator());
        expectedBuilder_.append(diffs);
        expectedBuilder_.append(log_.lineSeparator());
      }
    } 
    
    if (missingActualLines.size() > 1) {
      actualBuilder_.append(StringMethods.orderLine(ltr_,
          messages_.getIndent(), messages_.getTheFollowingActualLineNumberNotExpected()));
      actualBuilder_.append(log_.lineSeparator());
      
      I_TextLines actualLines =  result.getActualLines();
      for (Integer lineNbr: missingActualLines) {
        String line = actualLines.getLine(lineNbr);
        //line numbers are zero based in the data
        message = StringMethods.orderLine(constants_.isLeftToRight(), 
            indent_ + messages_.getIndent(), 
            "" + (lineNbr + 1), ":", " ", 
            "'" + line + "'");
        actualBuilder_.append(message);
        actualBuilder_.append(log_.lineSeparator());
      }
    } else if (firstDiff != null) {
      
      int nbr = firstDiff.getActualLineNbr();
      I_TextLines actualLines = result.getActualLines();
      String line = actualLines.getLine(nbr);
      
      I_DiffIndexesPair pair =  firstDiff.getIndexes();
      String diffs = getDifferences(nbr + 1, line, pair.getActual());
      if (!StringMethods.isEmpty(diffs)) {
        actualBuilder_.append(StringMethods.orderLine(ltr_,
            indent_ + messages_.getIndent(), 
            messages_.getDifferences()));
        actualBuilder_.append(log_.lineSeparator());
        actualBuilder_.append(diffs);
        actualBuilder_.append(log_.lineSeparator());
      }
    } 
  }

  public String getDifferences(int lineNbr, String line, I_DiffIndexes lineDiff) {
    StringBuilder sb = new StringBuilder();
    
    String [] diffs = lineDiff.getDifferences(line);
    String content = "";
    if (diffs.length == 2) {
      String [] newDiffs = new String[3];
      content = "'" + diffs[0] + "'*'" + diffs[1] + "'";
      diffs = newDiffs;
    } else if (diffs.length >= 1) {
      content = "'" + diffs[0] + "'";
    }
    if (diffs.length >= 1) {
      sb.append(StringMethods.orderLine(ltr_,indent_ + messages_.getIndent(),
          "" + lineNbr,":"," ", content));
    }
    return sb.toString();
  }

  public String getIndent() {
    return indent_;
  }

  public void setIndent(String indent) {
    this.indent_ = indent;
  }

  public StringBuilder getExpectedBuilder() {
    return expectedBuilder_;
  }

  public StringBuilder getActualBuilder() {
    return actualBuilder_;
  }

  public void setExpectedBuilder(StringBuilder expectedBuilder) {
    expectedBuilder_ = expectedBuilder;
    setExpectedBuilder_ = true;
  }

  public void setActualBuilder(StringBuilder actualBuilder) {
    actualBuilder_ = actualBuilder;
    setActualBuilder_ = true;
  }
  
  public String getDefaultResult() {
    return expectedBuilder_.toString() + actualBuilder_.toString();
  }
}
