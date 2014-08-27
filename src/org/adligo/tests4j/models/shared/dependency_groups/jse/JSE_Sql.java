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
public class JSE_Sql implements JSE_1_7_Sql {
	public static final JSE_Sql INSTANCE = new JSE_Sql();
	
	private JSE_Sql() {};
}
