package org.adligo.tests4j.system.shared.api;

/**
 * This interface is a marker so that 
 * a tool like the eclipse plug-in for tests4j 
 * or fabricate plug-in for tests4j can 
 * identify a factory for customized 
 * parameters.  
 * @author scott
 *
 */
public abstract class AbstractParamsFactory {
  /**
   * @return a customized Tests4J_Params instance,
   * note this does NOT need a MetaTrial 
   * or other trials, as the various plug-ins should
   * fill in these values for the user.  If the returned
   * value does contain MetaTrials or tests, the various 
   * plug-ins should NOT overwrite the values.
   * Other values like additional non instrumented packages,
   * log levels and coverage plug-ins should be added to the 
   * created value.
   */
  public abstract Tests4J_Params create();
}
