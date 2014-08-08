package org.adligo.tests4j.models.shared.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The various platforms that tests4j will
 * run on;
 * JSE The java standard edition
 * GWTC GWT compiled javascript running in a browser
 * ADFM Oracle's Application Development Framework for Mobile on a mobile device ie 
 * 		(iPhone, iPad, android phone, windows phone, exc)
 * @author scott
 *
 */
public enum Platform implements I_Platform {
	JSE(0), GWTC(1), MOBILE(2);
	private static final Map<Integer, Platform> MAP = getMap();
	
	private static Map<Integer, Platform> getMap() {
		Map<Integer, Platform> toRet = new HashMap<Integer, Platform>();
		
		toRet.put(0, JSE);
		toRet.put(1, GWTC);
		toRet.put(2, MOBILE);
		return Collections.unmodifiableMap(toRet);
	}
	
	public static Platform get(I_Platform p) {
		return MAP.get(p.getId());
	}
	
	private int id;
	
	
	Platform(int i) {
		id = i;
	}


	public int getId() {
		return id;
	}
}
