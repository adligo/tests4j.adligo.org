package org.adligo.tests4j.models.shared.dependency_groups.jse;

import org.adligo.tests4j.models.shared.dependency_groups.jse.v1_7.JSE_1_7_LangAnnot;


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
public class JSE_LangAnnot implements JSE_1_7_LangAnnot {
	public static final JSE_LangAnnot INSTANCE = new JSE_LangAnnot();
	
	private JSE_LangAnnot() {};
}
