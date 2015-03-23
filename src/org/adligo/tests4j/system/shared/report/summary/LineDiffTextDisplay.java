package org.adligo.tests4j.system.shared.report.summary;

import org.adligo.tests4j.shared.asserts.line_text.I_DiffIndexes;
import org.adligo.tests4j.shared.asserts.line_text.I_DiffIndexesPair;
import org.adligo.tests4j.shared.asserts.line_text.I_LineDiff;
import org.adligo.tests4j.shared.asserts.line_text.I_LineDiffType;
import org.adligo.tests4j.shared.asserts.line_text.I_TextLines;
import org.adligo.tests4j.shared.asserts.line_text.I_TextLinesCompareResult;
import org.adligo.tests4j.shared.asserts.line_text.LineDiffType;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_LineDiffTextDisplayMessages;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

import java.util.List;
/**
 * is NOT thread safe
 * @author scott
 *
 */
public class LineDiffTextDisplay {
  private final I_Tests4J_LineDiffTextDisplayMessages messages_;
  private I_TextLines actualLines;
  private I_TextLines exampleLines;

  public LineDiffTextDisplay(I_Tests4J_Constants constants) {
    messages_ = constants.getLineDiffTextDisplayMessages();
  }
  
  @SuppressWarnings({"incomplete-switch", "boxing"})
  public void display(I_Tests4J_Log out, I_TextLinesCompareResult result, int diffLimit) {
    actualLines = result.getActualLines();
    exampleLines = result.getExpectedLines();
    
    List<I_LineDiff> diffs =  result.getLineDiffs();
    
    int diffCount = 0;
    for (I_LineDiff diff: diffs) {
      if (diff != null) {
        I_LineDiffType pType = diff.getType();
        LineDiffType type = LineDiffType.get(pType);
        if (diffCount > diffLimit) {
          return;
        }
        I_DiffIndexesPair pair = diff.getIndexes();
        if (type == null) {
          out.log(messages_.getError());
        } else if ( type != LineDiffType.Match) {
          
          Integer expected = diff.getExpectedLineNbr();
          Integer actual = diff.getActualLineNbr();
        
          String expectedLine = getExpectedLine(exampleLines,
              expected);
          
          String actualLine = getActualLine(actualLines, actual);
          
          if (expectedLine == null && actualLine == null) {
            out.log(messages_.getError());
          } else {
          
            switch (type) {
              case PartialMatch:
                if (pair == null) {
                  out.log(messages_.getError());
                  break;
                }
                if (expectedLine == null || actualLine == null) {
                  out.log(messages_.getError());
                  break;
                } 
                diffCount++;
                
                I_DiffIndexes exampleIndexes = pair.getExpected();
                I_DiffIndexes actualIndexes = pair.getActual();
                
                try {
                  out.log(messages_.getTheLineOfTextIsDifferent());
                  
                  String [] exampleDiffs = exampleIndexes.getDifferences(expectedLine);
                  
                  out.log(messages_.getExpected() + " " + expected);
                  out.log(expectedLine);
                  if (exampleDiffs.length >= 1) {
                    out.log(messages_.getDifferences());
                    for (int i = 0; i < exampleDiffs.length; i++) {
                      out.log("'" + exampleDiffs[i] + "'");
                    }
                  }
                  out.log(messages_.getActual()  + " " + actual);
                  out.log(actualLine);
                  String [] actualDiffs = actualIndexes.getDifferences(actualLine);
                  if (actualDiffs.length >= 1) {
                    out.log(messages_.getDifferences());
                    for (int i = 0; i < actualDiffs.length; i++) {
                      out.log("'" + actualDiffs[i] + "'");
                    } 
                  }
                } catch (StringIndexOutOfBoundsException x) {
                  out.log(messages_.getError());
                }
                break;
              case MissingActualLine:
                if (actualLine != null) {
                  diffCount++;
                  out.log(messages_.getTheFollowingActualLineOfTextIsMissing() + actual);
                  out.log(actualLine);
                } else {
                  out.log(messages_.getError());
                }
                break;
              case MissingExpectedLine:
                if (expectedLine != null) {
                  diffCount++;
                  out.log(messages_.getTheFollowingExpectedLineOfTextIsMissing() + expected);
                  out.log(expectedLine);
                } else {
                  out.log(messages_.getError());
                }
                break;
            }
          }
        }
      }
    }
  }

  @SuppressWarnings("boxing")
  public String getActualLine(I_TextLines actualLines, Integer actual) {
    String actualLine = null;
    if (actual != null ) {
      if (actual >= 0 && actual < actualLines.getLines()) {
        actualLine = actualLines.getLine(actual);
      }
    }
    return actualLine;
  }

  public String getExpectedLine(I_TextLines exampleLines, int expected) {
    String expectedLine = null;
    if (expected >= 0 && expected < exampleLines.getLines()) {
      expectedLine = exampleLines.getLine(expected);
    }
    return expectedLine;
  }

  
}
