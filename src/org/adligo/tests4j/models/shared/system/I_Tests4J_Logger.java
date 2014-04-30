package org.adligo.tests4j.models.shared.system;

import java.io.PrintStream;

public interface I_Tests4J_Logger {
	/**
	 * 
	 * @return
	 *    if false, then Tests4J will modify
	 *    System.out and System.err 
	 *    (so that output to the default console is disabled).
	 *    
	 *    if true then the DuplicatingPrintStream
	 *    will be used to pass System.out.print*
	 *    and System.err.print* to the original PrintStreams
	 *    (so that you see it on the console).
	 *    
	 *    Regardless all System.out 
	 *    and System.err will be put it in the 
	 *    I_TrialResults, so it can be
	 *    ordered by I_TrailRunListener impl.
	 *    
	 */
	public boolean isEnabled();
	public void log(String p);
	public void log(Throwable p);
	public PrintStream getOutput();
}
