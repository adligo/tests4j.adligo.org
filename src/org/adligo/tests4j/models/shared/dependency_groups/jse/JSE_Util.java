package org.adligo.tests4j.models.shared.dependency_groups.jse;


/**
 * these are constants for the versions
 * of java, the plan is to support at least the 
 * previous 3 minor versions (today on 8/26/2014 that would be 1.8, 1.7, 1.6)
 * 
 * use, use the constant imports.
 * 
 * @author scott
 *
 */
public class JSE_Util implements JSE_1_7_Util {
	public static final JSE_Util INSTANCE = new JSE_Util();
	
	private JSE_Util() {};
}
