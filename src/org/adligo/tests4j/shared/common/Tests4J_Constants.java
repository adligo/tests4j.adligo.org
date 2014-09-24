package org.adligo.tests4j.shared.common;

import org.adligo.tests4j.shared.en.Tests4J_EnglishConstants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;

/**
 * this is where the tests4j framework 
 * gets all of it's text for error messages exc.
 * 
 * I chose not to use the default java i18n
 * because it would cause issues later in GWT, 
 * so this looks more like a GWT i18n API
 * although it isn't exactly that either.
 * 
 * @author scott
 *
 */
public class Tests4J_Constants {
	public static final I_Tests4J_Constants CONSTANTS = new Tests4J_ConstantsWrapper(Tests4J_EnglishConstants.ENGLISH);
	
}
