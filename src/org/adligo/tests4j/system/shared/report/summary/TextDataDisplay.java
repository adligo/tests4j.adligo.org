package org.adligo.tests4j.system.shared.report.summary;

import org.adligo.tests4j.shared.asserts.line_text.TextLines;
import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

/**
 * This class displays multiple line text in a way that
 * makes sense to the reader of the tests4j console.
 * @author scott
 *
 */
public class TextDataDisplay {
  private final I_Tests4J_Log log_;
  private final I_Tests4J_Constants constants_;
  private final I_Tests4J_ReportMessages messages_;
  
  public TextDataDisplay(I_Tests4J_Constants constants, I_Tests4J_Log log) {
    constants_ = constants;
    messages_ = constants_.getReportMessages();
    log_ = log;
  }
  
  public void display(StringBuilder sb, String text) {
    display(sb, text, messages_.getIndent());
  }
  
  public void display(StringBuilder sb, String text, String indent) {
    if (text == null) {
      String message = StringMethods.orderLine(constants_.isLeftToRight(), indent,
          "null");
      sb.append(message);
      sb.append(log_.lineSeparator());
      return;
    }
    if (StringMethods.isEmpty(text)) {
      String message = StringMethods.orderLine(constants_.isLeftToRight(), indent,
          "''");
      sb.append(message);
      sb.append(log_.lineSeparator());
      return;
    }
    TextLines lines = new TextLines(text, true);
    
    if (lines.getLines() == 1) {
      String message = StringMethods.orderLine(constants_.isLeftToRight(), indent,
          "'" + text + "'");
      sb.append(message);
      sb.append(log_.lineSeparator());
    } else {
      for (int i = 0; i < lines.getLines(); i++) {
        String line = lines.getLine(i);
        String message = StringMethods.orderLine(constants_.isLeftToRight(), indent,
            "" + (i + 1),":"," ","'" + line + "'");
        sb.append(message);
        sb.append(log_.lineSeparator());
      }
    }
  }
}
