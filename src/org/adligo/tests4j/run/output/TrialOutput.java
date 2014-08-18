package org.adligo.tests4j.run.output;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.adligo.tests4j.models.shared.common.Tests4J_System;
import org.adligo.tests4j.shared.output.I_OutputBuffer;

/**
 * Output for a trial (ie System.out, System.err, log.log),
 * a ConcurrentLinkedQueue is used to allow the trial thread and 
 * test thread to share the output data.
 * @author scott
 *
 */
public class TrialOutput implements I_OutputBuffer {
	private ConcurrentLinkedQueue<String> output = new ConcurrentLinkedQueue<String>();

	@Override
	public void add(String p) {
		output.add(p);
	}
	
	public String getResults() {
		StringBuilder sb = new StringBuilder();
		String s = output.poll();
		while (s != null) {
			sb.append(s);
			sb.append(Tests4J_System.getLineSeperator());
			s = output.poll();
		}
		return sb.toString();
	}
}
