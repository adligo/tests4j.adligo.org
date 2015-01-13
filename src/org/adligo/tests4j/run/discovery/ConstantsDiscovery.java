package org.adligo.tests4j.run.discovery;

import org.adligo.tests4j.shared.i18n.I_Tests4J_AnnotationMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_EclipseErrors;
import org.adligo.tests4j.shared.i18n.I_Tests4J_LineDiffTextDisplayMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_LogMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ParamsReaderMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_ResultMessages;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This is the general idea how i18n will
 * work eventually.  All text fields should be checked 
 * and throw a IOException if StringMethods.isEmpty() is true.
 * 
 * @author scott
 *
 */
public class ConstantsDiscovery implements I_Tests4J_Constants {
  private Properties labels_;
  private boolean leftToRight_ = true;
  
  public ConstantsDiscovery(String languageCode) throws IOException {
    labels_ = new Properties();
    InputStream in = ConstantsDiscovery.class.getResourceAsStream("/org/adligo/tests4j/shared/i18n/Constants_" + languageCode + ".properties");
    
    if (in == null) {
      throw new IOException();
    }
    labels_.load(in);
    String ltr = labels_.getProperty("ltr");
    if ("false".equalsIgnoreCase(ltr)) {
      leftToRight_ = false;
    }
  }

  @Override
  public boolean isLeftToRight() {
    return leftToRight_;
  }

  @Override
  public String getPrefix() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getHeader() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public I_Tests4J_AnnotationMessages getAnnotationMessages() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public I_Tests4J_AssertionInputMessages getAssertionInputMessages() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public I_Tests4J_ResultMessages getResultMessages() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public I_Tests4J_EclipseErrors getEclipseErrors() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public I_Tests4J_LineDiffTextDisplayMessages getLineDiffTextDisplayMessages() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public I_Tests4J_LogMessages getLogMessages() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public I_Tests4J_ParamsReaderMessages getParamReaderMessages() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public I_Tests4J_ReportMessages getReportMessages() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getBadConstuctor() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getMethodBlockerRequiresAtLeastOneAllowedCallerClassNames() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getMethodBlockerRequiresABlockingClass() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getMethodBlockerRequiresABlockingMethod() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getNullParamsExceptionMessage() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getNullListenerExceptionMessage() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getTheMethodCanOnlyBeCalledBy_PartOne() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getTheMethodCanOnlyBeCalledBy_PartTwo() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getAnotherTests4J_InstanceIsRunningHere() {
    // TODO Auto-generated method stub
    return null;
  }
}
