package org.adligo.tests4j.shared.common;

import org.adligo.tests4j.shared.en.Tests4J_EnglishConstants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;

/**
 * This is where the tests4j framework <br/>
 * gets all of it's text for error messages exc.<br/>
 * Usage is as follows, a reference to CONSTANTS<br/>
 * may be cached as a reference in other classes (i.e.);<br/>
 * private static final I_Tests4J_Constants CONSTANTS = Tests4J_Constants.CONSTANTS;<br/>
 * <br/>
 * All other references to methods in this interchangeable Tests4J_ConstantsWrapper <br/>
 * should be done at runtime, so that the Tests4J system has a chance to<br/>
 * change the reference before you get a value.  <br/>
 * So do NOT do this;<br/>
 * private static final I_Tests4J_LogMessages LOG_MESSAGES = CONSTANTS.getLogMessages();
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
