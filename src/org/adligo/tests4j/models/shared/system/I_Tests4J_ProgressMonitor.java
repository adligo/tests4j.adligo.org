package org.adligo.tests4j.models.shared.system;

/**
 * this interface allows you to 
 * customize when tests4j should send
 * progress notifications.
 * 
 * @author scott
 *
 */
public interface I_Tests4J_ProgressMonitor {
	public static final String SETUP = "setup";
	public static final String TRIALS = "trials";
	public static final String REMOTES = "remotes";
	
	/**
	 * the interval to to check 
	 * if the next time has been reached.
	 * @return
	 */
	public long getSleepTime();
	/**
	 * the next time to send notification
	 * for a phase
	 * @param phase one of the constants in this class.
	 * @return
	 */
	public long getNextNotifyTime(String phase);
}
