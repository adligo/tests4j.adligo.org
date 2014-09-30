package org.adligo.tests4j.system.shared.api;

import org.adligo.tests4j.shared.output.I_Tests4J_Log;

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
	public I_Tests4J_CoveragePlugin create(I_Tests4J_CoveragePluginParams params, I_Tests4J_Log logger);
}
