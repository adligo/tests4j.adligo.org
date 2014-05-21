package org.adligo.tests4j.models.shared.system;

/**
 * a interface to configure a coverage plugin
 * after it has been constructed.
 * Implementations are must to contain a zero arg constructor.
 * 
 * @author scott
 *
 */
public interface I_CoveragePluginConfigurator {
	public void configure(I_CoveragePlugin p);
}
