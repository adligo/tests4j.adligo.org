package org.adligo.tests4j.run.output;

import java.io.PrintStream;

import org.adligo.tests4j.shared.output.I_OutputBuffer;
import org.adligo.tests4j.shared.output.I_OutputDelegateor;

/**
 * All messages are put here before getting,
 * printed to the console during a run of tests4j,
 * this includes System.out.* System.err.*, I_Tests4J_Log.*.
 * 
 * Messages are stored as strings, because we want to keep
 * the integrity of stack traces (so they stay ordered),
 * and are not broken up by line.
 * @author scott
 *
 */
public class OutputDelegateor implements I_OutputDelegateor {
	private final PrintStream out;
	private I_OutputBuffer delegate;
	
	public OutputDelegateor (PrintStream pOut) {
		out = pOut;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.shared.output.I_OutputDelegateor#setDelegate(org.adligo.tests4j.shared.output.I_OutputBuffer)
	 */
	public void setDelegate(I_OutputBuffer buffer) {
		delegate = buffer;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.shared.output.I_OutputBuffer#add(java.lang.String)
	 */
	@Override
	public void add(String p) {
		out.println(p);
		if (delegate != null) {
			delegate.add(p);
		}
	}

}
