package org.adligo.tests4j.shared.output;

import java.io.IOException;
import java.io.OutputStream;

public class SafeOutputStreamBuffer implements I_OutputBuffer {
	private OutputStream delegate;
	private I_Tests4J_Log log;
	private byte [] newLineBytes;
	private boolean threwException = false;
	
	public SafeOutputStreamBuffer(I_Tests4J_Log pLog,  OutputStream out) {
		delegate = out;
		log = pLog;
		newLineBytes = log.lineSeparator().getBytes();
	}

	@Override
	public void add(String p) {
		if (!threwException) {
			try {
				delegate.write(p.getBytes());
				delegate.write(newLineBytes);
				delegate.flush();
			} catch (IOException x) {
				log.onThrowable(x);
				threwException = true;
			}
		}
	}
	
}
