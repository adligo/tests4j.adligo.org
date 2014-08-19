package org.adligo.tests4j.shared.output;

import java.io.PrintStream;

public class PrintStreamOutputBuffer implements I_OutputBuffer {
	private PrintStream delegate;
	
	public PrintStreamOutputBuffer(PrintStream out) {
		delegate = out;
	}

	@Override
	public void add(String p) {
		delegate.println(p);
	}
}
