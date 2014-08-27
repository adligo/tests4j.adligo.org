package org.adligo.tests4j.models.shared.dependency_groups.jse;


/**
 * these are constants for the versions
 * of java, the plan is to support at least the 
 * previous 3 minor versions (today on 8/26/2014 that would be 1.8, 1.7, 1.6)
 * 
 * use, use the constant imports.
 * Classes included but removed before the 
 * first version should be removed ASAP, if that ever happens.
 * 
 * Note this doesn't implements JSE_1_7_Lang or JSE_1_8_Lang
 * since I am trying to support all the way back to 1_6
 * (although running 1.7 now, and will probably move to 1.8 soon).
 * 
 * @author scott
 *
 */
public class JSE_Lang implements JSE_1_6_Lang {
	public static final JSE_Lang INSTANCE = new JSE_Lang();
	
	private JSE_Lang() {};
}
