package org.adligo.tests4j.run.common;

import org.adligo.tests4j.shared.common.I_System;

import java.util.Locale;

/**
 * This class was added to 
 * create a aggregate of some
 * of the interfaces in this class.
 * Most of which shouldn't require additional classes
 * now that mockito is working with tests4j.
 * 
 * @author scott
 *
 */
public interface I_JseSystem extends I_System, I_Files {
  /**
   * Locale local = Locale.getDefault();
    String language = local.getLanguage();
   * @return
   */
  public String getLanguage();
  
  /**
   * a wrapper around  Thread.currentThread().interrupt();
   * so interrupts can be stubbed for mock instanct
   * testing.
   * @return
   */
  public void interruptCurrentThread();
}
