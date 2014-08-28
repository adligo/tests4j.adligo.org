package org.adligo.tests4j.run.output;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.adligo.tests4j.shared.output.I_ConcurrentOutputDelegator;
import org.adligo.tests4j.shared.output.I_OutputBuffer;
import org.adligo.tests4j.shared.output.I_OutputDelegateor;
import org.adligo.tests4j.shared.output.I_ToggleOutputBuffer;

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
public class ConcurrentOutputDelegateor implements I_ConcurrentOutputDelegator {
	/**
	 * if you get a out of memory error, try to do less logging, printing.
	 */
	private ConcurrentLinkedQueue<String> buffer = new ConcurrentLinkedQueue<String>();
	private InheritableThreadLocal<I_ToggleOutputBuffer> delegates = new InheritableThreadLocal<I_ToggleOutputBuffer>();
	
	public void setDelegate(I_ToggleOutputBuffer buffer) {
		delegates.set(buffer);
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.text_output.I_TextWriter#add(java.lang.String)
	 */
	@Override
	public void add(String p) {
		I_ToggleOutputBuffer delegate = delegates.get();
		if (delegate != null) {
			if (delegate.isPrinting()) {
				buffer.add(p);
			}
			delegate.add(p);
		} else {
			buffer.add(p);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.text_output.I_TextReader#poll()
	 */
	@Override
	public String poll() {
		return buffer.poll();
	}

}
