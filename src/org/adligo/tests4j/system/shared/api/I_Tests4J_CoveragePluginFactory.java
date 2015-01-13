package org.adligo.tests4j.system.shared.api;

import java.util.Map;

/**
 * a interface to configure a coverage plugin
 * after it has been constructed.
 * Implementations are must to contain a zero arg constructor,
 * this facilitates passing which one between jvms.
 * 
 * @author scott
 *
 */
public interface I_Tests4J_CoveragePluginFactory {
  /**
   * key to the value in the runtimeParams map for a I_Tests4J_Constants.
   */
  public static final String CONSTANTS = "constants";
  /**
   * key to the value in the runtimeParams map for a I_TestsJ_Log.
   */
  public static final String LOG = "log";
  /**
   * key to the value in the runtimeParams map for a I_JseSystem
   * if running on JSE, null otherwise.
   */
  public static final String SYSTEM = "system";
  /**
   * @param params
   * @param runtimeParams use one of the constants in this class for the key.
   *    Note this map is also used to avoid the File import for GWT compiling.
   * @return
   */
	public I_Tests4J_CoveragePlugin create(I_Tests4J_Params params, I_Tests4J_CoveragePluginParams pluginParams, Map<String,Object> runtimeParams);
}
