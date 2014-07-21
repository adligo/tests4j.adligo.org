package org.adligo.tests4j.models.shared.system;

/**
 * a interface to configure a coverage plugin
 * after it has been constructed.
 * Implementations are must to contain a zero arg constructor,
 * this facilitates passing which one between jvms.
 * 
 * @author scott
 *
 */
public interface I_CoveragePluginFactory {
	public I_CoveragePlugin create(I_Tests4J_Logger reporter);
}
