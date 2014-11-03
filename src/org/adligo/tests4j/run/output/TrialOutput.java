package org.adligo.tests4j.run.output;

import org.adligo.tests4j.shared.common.DefaultSystem;
import org.adligo.tests4j.shared.common.I_System;
import org.adligo.tests4j.shared.output.I_ToggleOutputBuffer;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Output for a trial (ie System.out, System.err, log.log),
 * a ConcurrentLinkedQueue is used to allow the trial thread and 
 * test thread to share the output data.
 * @author scott
 *
 */
public class TrialOutput implements I_ToggleOutputBuffer {
  private static final I_System SYS = new DefaultSystem();
	private ConcurrentLinkedQueue<String> output = new ConcurrentLinkedQueue<String>();
	private AtomicBoolean printing_ = new AtomicBoolean(true);
	
	@Override
	public void add(String p) {
		output.add(p);
	}
	
	public String getResults() {
		StringBuilder sb = new StringBuilder();
		String s = output.poll();
		while (s != null) {
			sb.append(s);
			sb.append(SYS.lineSeperator());
			s = output.poll();
		}
		return sb.toString();
	}

	public boolean isPrinting() {
		return printing_.get();
	}

	public void setPrinting(boolean printing) {
		printing_.set(printing);
	}
	

}
