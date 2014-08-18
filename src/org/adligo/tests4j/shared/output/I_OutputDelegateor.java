package org.adligo.tests4j.shared.output;


/**
 * this class delegates output from one location to 
 * another location, there are/will be two main implementations of the class;
 * 
 * JseOutputDeleagor which delegates it's output to the original System.out
 *    at the start of a  Tests4J.run method, and has a InheritableThreadLocal
 *    so that the trial thread and test thread can capture output from the 
 *    trial's methods, for the results.
 * GwtOutputDelegator which delegates it's output to the original System.out
 *    and captures the output for the results (on the javascript thread).
 * @author scott
 *
 */
public interface I_OutputDelegateor extends I_OutputBuffer {
	/**
	 * see class comments.
	 * @param buffer
	 */
	public void setDelegate(I_OutputBuffer buffer);
}
