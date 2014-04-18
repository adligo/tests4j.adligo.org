package org.adligo.tests4j.run;

import java.io.IOException;
import java.io.OutputStream;

import org.adligo.tests4j.models.shared.system.ByteListOutputStream;


public class ThreadLocalOutputStream extends OutputStream  {
	private ThreadLocal<ByteListOutputStream> out = new ThreadLocal<ByteListOutputStream>();
	private int chunckSize = 64;
	
	public ThreadLocalOutputStream() {
		
	}
	public ThreadLocalOutputStream(int pChunckSize) {
		chunckSize = pChunckSize;
	}

	
	@Override
	public void write(int b) throws IOException {
		ByteListOutputStream blos = get();
		blos.write(b);
	}

	public ByteListOutputStream get() {
		ByteListOutputStream blos = out.get();
		if (blos == null) {
			blos = new ByteListOutputStream(chunckSize);
			out.set(blos);
		}
		return blos;
	}

}
