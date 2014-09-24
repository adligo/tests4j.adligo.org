package org.adligo.tests4j.models.shared.trials;

import org.adligo.tests4j.shared.xml.I_XML_Producer;

/**
 * Implement this map like interface to 
 * pass parameters to your trial instance.
 * For other trial types, the I_TrialParamsQueue next method is used
 * each instance using a trailing id. I.E.
 * create("org.adligo.tests4j.models.shared.trials.SourceFileTrial[0]");
 * create("org.adligo.tests4j.models.shared.trials.SourceFileTrial[0]");
 * 
 * 
 * @author scott
 *
 */
public interface I_TrialParams extends I_XML_Producer {

	public String get(String key);
	public Integer getInt(String key);
	public Short getShort(String key);
	public Long getLong(String key);
	
	public Double getDouble(String key);
	public Float getFloat(String key);

	public Byte getByte(String key);
	public Boolean getBoolean(String key);
	public Character getChar(String key);
}
