package org.adligo.tests4j.system.shared.api;

import java.math.BigDecimal;

/**
 * this interface allows you to 
 * customize when tests4j should send
 * progress notifications.
 * 
 * @author scott
 *
 */
public interface I_Tests4J_ProgressParams {
  public static final BigDecimal ONE_HUNDRED = new BigDecimal("100.00");
	public static final String SETUP = "setup";
	public static final String TRIALS = "trials";
	public static final String REMOTES = "remotes";
	
	/**
	 * The amount of time (milliseconds) between percentage notifications
	 * about the progress of one of the phases
	 * @return
	 */
	public long getNotificationInterval();
}
