package org.adligo.tests4j.system.shared.api;

import java.util.List;

/**
 * this interface may be implemented 
 * in extension projects to provide information
 * to a ide (Integrated Development Environment) run of tests4j.
 * i.e. Eclipse, Net Beans, IntellaJ exc.
 * 
 * @author scott
 *
 */
public interface I_Tests4J_IDE {
	/**
	 * 
	 * @return a list of class path entries
	 * for starting up a new tests4j run server.
	 */
	public List<String> getClassPathEntries();
}
