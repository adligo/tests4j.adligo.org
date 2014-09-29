package org.adligo.tests4j.system.shared.trials;

import java.util.HashMap;
import java.util.Map;

import org.adligo.tests4j.shared.xml.I_XML_Builder;

public class TrialParamsMutant implements I_TrialParams {
	private Map<String, TrialParamValue> items = new HashMap<String, TrialParamValue>();

	@Override
	public void toXml(I_XML_Builder builder) {
		// TODO Auto-generated method stub
		
	}

	public void put(String key, TrialParamValue value) {
		items.put(key, value);
	}
	
	@Override
	public String get(String key) {
		TrialParamValue tpv = items.get(key);
		if (tpv == null) {
			return null;
		}
		return (String) tpv.getValue();
	}

	@Override
	public Integer getInt(String key) {
		TrialParamValue tpv = items.get(key);
		if (tpv == null) {
			return null;
		}
		return (Integer) tpv.getValue();
	}

	@Override
	public Short getShort(String key) {
		TrialParamValue tpv = items.get(key);
		if (tpv == null) {
			return null;
		}
		return (Short) tpv.getValue();
	}

	@Override
	public Long getLong(String key) {
		TrialParamValue tpv = items.get(key);
		if (tpv == null) {
			return null;
		}
		return (Long) tpv.getValue();
	}

	@Override
	public Double getDouble(String key) {
		TrialParamValue tpv = items.get(key);
		if (tpv == null) {
			return null;
		}
		return (Double) tpv.getValue();
	}

	@Override
	public Float getFloat(String key) {
		TrialParamValue tpv = items.get(key);
		if (tpv == null) {
			return null;
		}
		return (Float) tpv.getValue();
	}

	@Override
	public Byte getByte(String key) {
		TrialParamValue tpv = items.get(key);
		if (tpv == null) {
			return null;
		}
		return (Byte) tpv.getValue();
	}

	@Override
	public Boolean getBoolean(String key) {
		TrialParamValue tpv = items.get(key);
		if (tpv == null) {
			return null;
		}
		return (Boolean) tpv.getValue();
	}

	@Override
	public Character getChar(String key) {
		TrialParamValue tpv = items.get(key);
		if (tpv == null) {
			return null;
		}
		return (Character) tpv.getValue();
	}
}
