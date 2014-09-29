package org.adligo.tests4j.system.shared.trials;

import org.adligo.tests4j.shared.xml.I_XML_Producer;

/**
 * a interface for passing between class loaders
 * 
 * @author scott
 *
 * @param <T> a non void primitive wrapper class name
 * or String.
 */
public interface I_TrialParamValue extends I_XML_Producer {
	public String getClassName();
	public Object getValue();
}
