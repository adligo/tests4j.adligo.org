package org.adligo.tests4j.shared.asserts.common;


/**
 * The common Interface of AssertCommands @see Command Pattern.
 * Implementations must also implement hashCode() for correct unique assertion hashes!
 * It is recommend that the data fields/instance be used in the hashCode calculation,
 * for better statistical distribution.
 * 
 * @author scott
 *
 */
public interface I_AssertCommand {
	/**
	 * a enum for switch statmes
	 * AssertTypeEnum in the tests4j project
	 * intended for extension.
	 * @return
	 */
	public I_AssertType getType();
	/**
	 * The common unformatted failure message for assertion failures based on type.
	 * @return
	 */
	public String getFailureMessage();
	/**
	 * The data pertaining to a pass fail decision,
	 * also the data instance/fields should be used
	 * in the hashCode() of the I_AssertCommand objects.
	 * @return
	 */
	public I_AssertionData getData();
}
